package br.com.mobile10.avaliasim.presentation.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.DeliverableDAO;
import br.com.mobile10.avaliasim.data.dao.EvaluationDAO;
import br.com.mobile10.avaliasim.data.dao.RatingDAO;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IClockDAO;
import br.com.mobile10.avaliasim.data.interfaces.IDeliverableDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.IRatingDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.model.User;
import br.com.mobile10.avaliasim.presentation.adapters.RecyclerViewAdapter;
import br.com.mobile10.avaliasim.presentation.adapters.RecyclerViewAdapterEvaluation;
import br.com.mobile10.avaliasim.presentation.fragments.ProfileFragment;
import br.com.mobile10.avaliasim.util.InterfaceUtils;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EvaluationsActivity extends AppCompatActivity {

    private IUserDAO userDAO;
    private IRatingDAO ratingDAO;
    private CoordinatorLayout coordinatorLayout;
    private Deliverable deliverable;

    private SwipeRefreshLayout swipeLayout;
    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;

    private IClockDAO clockDAO;
    private IEvaluationDAO evaluationDAO;

    private CollapsingToolbarLayout toolbarLayout;
    private View promptsView;
    private EditText mEditChipsFeatures;
    private AlertDialog alertDialog;

    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();

        deliverable = (Deliverable) getIntent().getSerializableExtra("deliverable");
        // Se o DAO estivessa na activity ou fragment o usuario poderia ser enviado por intent como o deliverable
        userDAO = new UserDAO();
        userDAO.getLoggedUser();
        userDAO.findById(FirebaseAuth.getInstance().getCurrentUser().getUid(), this::onUserLoaded);

        toolbarLayout.setTitle(deliverable.getName());

        evaluationDAO = new EvaluationDAO(deliverable.getId());
        evaluationDAO.findAll(this::loadEvaluationsCards);
//        clockDAO.synchronizeClock();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                alertNewEvaluation();
            }
        });
    }

    private void initializeViews() {
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        recyclerView = findViewById(R.id.cards_recycler_view);
        viewSwitcher = findViewById(R.id.cards_switcher);
        swipeLayout = findViewById(R.id.swipe_container);

        swipeLayout.setOnRefreshListener(this::onRefreshSwipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
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

    public void alertNewEvaluation(){

        LayoutInflater li = LayoutInflater.from(this);
        promptsView = li.inflate(R.layout.alert_new_evaluation, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        mEditChipsFeatures = (EditText) promptsView.findViewById(R.id.edit_chips_features);
        AppCompatButton btnNew = (AppCompatButton) promptsView.findViewById(R.id.new_evaluation);
        btnNew.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO Verificar se o editText está vazio. Outros tipos tb, ex: se ja tem a feature criada por outro usuario
                        if (loggedUser != null) {
                            newEvaluation();
                        }
                        alertDialog.cancel();
                    }
                }
        );

        alertDialogBuilder
                .setNegativeButton("Descartar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();
    }

    private void onUserLoaded(Object result) {
        loggedUser = (User) result;
    }

    private void newEvaluation() {
        Evaluation evaluation = new Evaluation();
        evaluation.setNameAuthor(loggedUser.getName());
        evaluation.setPhotoAuthor(loggedUser.getPhotoUrl().toString());

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

}
