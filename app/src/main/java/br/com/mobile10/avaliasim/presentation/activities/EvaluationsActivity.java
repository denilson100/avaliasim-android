package br.com.mobile10.avaliasim.presentation.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.EvaluationDAO;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.model.User;
import br.com.mobile10.avaliasim.presentation.adapters.RecyclerViewAdapterEvaluation;
import br.com.mobile10.avaliasim.util.InterfaceUtils;
import br.com.mobile10.avaliasim.util.Support;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EvaluationsActivity extends AppCompatActivity {

    private IUserDAO userDAO;
    private Deliverable deliverable;

    private SwipeRefreshLayout swipeLayout;
    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;

    private IEvaluationDAO evaluationDAO;

    private CollapsingToolbarLayout toolbarLayout;
    private View promptsView;
    private RecipientEditTextView chips;
    private AlertDialog alertDialog;
    private Toolbar toolbar;

    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluations);
        userDAO = new UserDAO();
        deliverable = (Deliverable) getIntent().getSerializableExtra("deliverable");
        evaluationDAO = new EvaluationDAO(deliverable.getId());
        userDAO.findById(userDAO.getLoggedUser().getUid(), this::onUserLoaded);
    }

    private void onUserLoaded(Object result) {
        loggedUser = (User) result;
        initializeViews();
        setSupportActionBar(toolbar);
        evaluationDAO.findAll(this::loadEvaluationsCards);
        toolbarLayout.setTitle(deliverable.getName());
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        recyclerView = findViewById(R.id.cards_recycler_view);
        viewSwitcher = findViewById(R.id.cards_switcher);
        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this::onRefreshSwipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        findViewById(R.id.fab).setOnClickListener(view -> alertNewEvaluation());
    }

    private void onRefreshSwipe() {
        evaluationDAO.findAll(result -> recyclerView.setAdapter(new RecyclerViewAdapterEvaluation((List<Evaluation>) result)));
        swipeLayout.setRefreshing(false);
    }

    public void loadEvaluationsCards(Object result) {
        List<Evaluation> evaluations = (List<Evaluation>) result;
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapterEvaluation(evaluations));
//        viewSwitcher.showNext();
    }

    public void alertNewEvaluation() {
        if (userDAO.getLoggedUser() != null) {
            LayoutInflater li = LayoutInflater.from(this);
            promptsView = li.inflate(R.layout.alert_new_evaluation, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptsView);
            chips = promptsView.findViewById(R.id.edit_chips_features);
            chips.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            BaseRecipientAdapter baseRecipientAdapter = new BaseRecipientAdapter(this);

            baseRecipientAdapter.setShowMobileOnly(false);
            chips.isHorizontalScrollBarEnabled();
            chips.setAdapter(baseRecipientAdapter);

            AppCompatButton btnNew = promptsView.findViewById(R.id.new_evaluation);
            btnNew.setOnClickListener(view -> {
                if (isFormValidated()) {
                    String features1 = chips.getText().toString();
                    String features2 = features1.replace("<", "");
                    String featuresFinal = features2.replace(">", "");
                    newEvaluation(Support.getFeaturesList(featuresFinal));
                    alertDialog.cancel();
                }
            });

            alertDialogBuilder.setNegativeButton("Descartar", null);
            alertDialog = alertDialogBuilder.create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
            alertDialog.show();
        } else {
            Toast.makeText(this, "Faça login para criar uma avaliação", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void newEvaluation(List<String> featureList) {
        Evaluation evaluation = new Evaluation();
        evaluation.setNameAuthor(loggedUser.getName());
        evaluation.setPhotoAuthor(loggedUser.getPhotoUrl().toString());
        evaluation.setFeatures(featureList);

        ProgressDialog progressDialog = InterfaceUtils.showProgressDialog(this, "Criando...");
        evaluationDAO.create(evaluation, result -> {
            if (((int) result) == 1) {
                Snackbar.make(toolbarLayout, "Criado com sucesso", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                onRefreshSwipe();
            } else
                InterfaceUtils.showToast(this, "Erro ao criar.");

            InterfaceUtils.hideProgressDialog(progressDialog);
        });
    }

    private boolean isFormValidated() {
        AtomicBoolean isValid = new AtomicBoolean(true);

        Arrays.asList(chips).forEach(chip -> {
            if (TextUtils.isEmpty(chip.getText().toString())) {
                chip.setError("Necessário");
                isValid.set(false);
            } else
                chip.setError(null);
        });

        return isValid.get();
    }

}
