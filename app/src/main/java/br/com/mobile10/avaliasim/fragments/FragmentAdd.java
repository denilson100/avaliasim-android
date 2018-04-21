package br.com.mobile10.avaliasim.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.dao.AvaliacaoDaoImplementacao;
import br.com.mobile10.avaliasim.interfaces.AvaliacaoDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.util.ListEstados;

/**
 * Created by denmont on 20/04/2018.
 */

public class FragmentAdd extends Fragment implements AvaliacaoDao, View.OnClickListener {

    private EditText editTitle;
    private EditText editFeature;
    private EditText editCidade;
    MaterialBetterSpinner materialDesignSpinner, materialDesignSpinnerEstado;
    private String type, estado, setForm = "vazio";

    private FirebaseAuth mAuth;
    FirebaseUser users;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add, container, false);

//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        editTitle = (EditText) getActivity().findViewById(R.id.edit_title);
        editFeature = (EditText) getActivity().findViewById(R.id.edit_features);
        editCidade = (EditText) getActivity().findViewById(R.id.edit_cidade);

        AppCompatButton b = (AppCompatButton) getActivity().findViewById(R.id.saveNewAvaliacao);
        b.setOnClickListener(this);

        List<String> list = new ArrayList<String>();
        list.add("Produto");
        list.add("Serviço");
        list.add("Outro");

        // Bloco Spinner para pegar click
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list);
        materialDesignSpinner = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.categoria);
        materialDesignSpinner.setAdapter(arrayAdapter);
        materialDesignSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                type = materialDesignSpinner.getText().toString();
                setForm = type;
                updateUI();
                Log.d("TAG", "Pegou a categoria: " + type);

            }
        });

        // Bloco Spinner para pegar click estado
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, ListEstados.getList());
        materialDesignSpinnerEstado = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.estado);
        materialDesignSpinnerEstado.setAdapter(arrayAdapter1);
        materialDesignSpinnerEstado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                estado = materialDesignSpinnerEstado.getText().toString();
                Log.d("TAG", "Pegou a categoria: " + estado);

            }
        });
    }

    public void updateUI() {

        LinearLayout contentService = (LinearLayout) getActivity().findViewById(R.id.content_service);

        switch (setForm) {
            case "Produto":
                contentService.setVisibility(View.GONE);
                break;
            case "Serviço":
                contentService.setVisibility(View.VISIBLE);
        }

    }

    public void saveNewAvaliacao() {

        List<String> list = new ArrayList<String>();
        list.add(editFeature.getText().toString());

        String title = editTitle.getText().toString();
        String cidade = editCidade.getText().toString();

        switch (setForm) {

            case "Produto":
                if (!validateFormProduct()) {
                    return;
                }
                long timeStamp = 0;
                Avaliacao2 a = new Avaliacao2(users.getUid(), "", "", getNamesFeatures(), "", timeStamp, title, type, true);
                newAvaliacao(a, users.getUid());
//                getActivity().finish();
                break;

            case "Serviço":
                if (!validateFormService()) {
                    return;
                }
                long timeStamp1 = 0;
                Avaliacao2 a2 = new Avaliacao2(users.getUid(), cidade, estado, getNamesFeatures(), "", timeStamp1, title, type, true);
                newAvaliacao(a2, users.getUid());
//                getActivity().finish();
                break;

            default:
                if (!validateFormProduct()) {
                    return;
                }
                Snackbar.make(rootView, "Nao adicionou. Tente novamente", Snackbar.LENGTH_LONG).show();
        }

    }

    /**
     * Conversor de list pars array
     * @return
     */
    private List<String> getNamesFeatures() {
        List<String> list = new ArrayList<String>();
        String[] textoSeparado = editFeature.getText().toString().split(";\\s");
        for (int i=0; i < textoSeparado.length; i++) {
            list.add(textoSeparado[i]);
        }
        String[] stringArray = list.toArray(new String[0]);

        return list;
    }

    @Override
    public void newAvaliacao(Avaliacao2 avaliacao, String userId) {
        AvaliacaoDaoImplementacao dao = new AvaliacaoDaoImplementacao();
        dao.newAvaliacao(avaliacao, users.getUid());
        Snackbar.make(rootView, "Avaliação criada com sucesso!", Snackbar.LENGTH_LONG).show();
        limparCampos();
//        showToast("Avaliação criada com sucess!");
//        finish();
    }

    private boolean validateFormProduct() {
        boolean valid = true;

        String nome = editTitle.getText().toString();
        if (TextUtils.isEmpty(nome)) {
            editTitle.setError("Required.");
            Log.d("TAG", "Valid: " + valid);
            valid = false;
        } else {
            editTitle.setError(null);
        }

        String desc = editFeature.getText().toString();
        if (TextUtils.isEmpty(desc)) {
            editFeature.setError("Required.");
            valid = false;
        } else {
            editFeature.setError(null);
        }

        String categoria = materialDesignSpinner.getText().toString();
        if (TextUtils.isEmpty(categoria)) {
            materialDesignSpinner.setError("Required.");
            valid = false;
        } else {
            materialDesignSpinner.setError(null);
        }

        return valid;
    }

    private boolean validateFormService() {
        boolean valid = true;

        String nome = editTitle.getText().toString();
        if (TextUtils.isEmpty(nome)) {
            editTitle.setError("Required.");
            Log.d("TAG", "Valid: " + valid);
            valid = false;
        } else {
            editTitle.setError(null);
        }

        String desc = editFeature.getText().toString();
        if (TextUtils.isEmpty(desc)) {
            editFeature.setError("Required.");
            valid = false;
        } else {
            editFeature.setError(null);
        }

        String cidade = editCidade.getText().toString();
        if (TextUtils.isEmpty(cidade)) {
            editCidade.setError("Required.");
            valid = false;
        } else {
            editCidade.setError(null);
        }

        String categoria = materialDesignSpinner.getText().toString();
        if (TextUtils.isEmpty(categoria)) {
            materialDesignSpinner.setError("Required.");
            valid = false;
        } else {
            materialDesignSpinner.setError(null);
        }

        String estado = materialDesignSpinnerEstado.getText().toString();
        if (TextUtils.isEmpty(estado)) {
            materialDesignSpinnerEstado.setError("Required.");
            valid = false;
        } else {
            materialDesignSpinnerEstado.setError(null);
        }

        return valid;
    }

    public void limparCampos() {
        editTitle.setText("", TextView.BufferType.EDITABLE);
        editFeature.setText("", TextView.BufferType.EDITABLE);
        editCidade.setText("", TextView.BufferType.EDITABLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveNewAvaliacao:
                saveNewAvaliacao();
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;
        }
    }
}
