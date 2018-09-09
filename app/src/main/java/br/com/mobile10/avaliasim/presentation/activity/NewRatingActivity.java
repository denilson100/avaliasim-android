package br.com.mobile10.avaliasim.presentation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import br.com.mobile10.avaliasim.R;

public class NewRatingActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editFeature;
    private EditText editCidade;
    MaterialBetterSpinner materialDesignSpinner, materialDesignSpinnerEstado;
    private String type, estado, setForm = "vazio";

    private FirebaseAuth mAuth;
    FirebaseUser users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_rating_activity);

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        users = user;
//
//        editTitle = findViewById(R.id.edit_title);
//        editFeature = findViewById(R.id.edit_features);
//        editCidade = findViewById(R.id.edit_cidade);
//
//        List<String> list = new ArrayList<String>();
//        list.add("Produto");
//        list.add("Serviço");
//
//        // Bloco Spinner para pegar click
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, list);
//        materialDesignSpinner = findViewById(R.id.categoria);
//        materialDesignSpinner.setAdapter(arrayAdapter);
//        materialDesignSpinner.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                type = materialDesignSpinner.getText().toString();
//                setForm = type;
//                updateUI();
//                Log.d("TAG", "Pegou a categoria: " + type);
//
//            }
//        });
//
//        // Bloco Spinner para pegar click estado
//        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, ListEstados.getList());
//        materialDesignSpinnerEstado = (MaterialBetterSpinner)
//                findViewById(R.id.estado);
//        materialDesignSpinnerEstado.setAdapter(arrayAdapter1);
//        materialDesignSpinnerEstado.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                estado = materialDesignSpinnerEstado.getText().toString();
//                Log.d("TAG", "Pegou a categoria: " + estado);
//
//            }
//        });

    }

//    public void updateUI() {
//
//        LinearLayout contentService = (LinearLayout) findViewById(R.id.content_service);
//
//        switch (setForm) {
//            case "Produto":
//                contentService.setVisibility(View.GONE);
//                break;
//            case "Serviço":
//                contentService.setVisibility(View.VISIBLE);
//            case "Entreterimento":
//                contentService.setVisibility(View.VISIBLE);
//        }
//
//    }
//
//    public void saveNewAvaliacao(View view) {
//        rating.setTimestamp(System.currentTimeMillis());
//        r.setTitle("Horrível desde o começo");
//        Product product = new Product("Carro elérico", 25300f);
//        r.setDeliverable(product);
//        Feature feature1 = new Feature("Durabilidade", 3.5f);
//        Feature feature2 = new Feature("Conforto", 1400f);
//        r.setFeatures(Arrays.asList(feature1, feature2));
//
//        List<String> list = new ArrayList<String>();
//        list.add(editFeature.getText().toString());
//
//        String title = editTitle.getText().toString();
//        String cidade = editCidade.getText().toString();
//
//        switch (setForm) {
//
//            case "Produto":
//                if (!validateFormProduct()) {
//                    return;
//                }
//                long timeStamp = 0;
//                Avaliacao2 a = new Avaliacao2(users.getUid(), "", "", getNamesFeatures(), "", timeStamp, title, type, true);
//                newAvaliacao(a, users.getUid());
//                finish();
//                break;
//
//            case "Serviço":
//                if (!validateFormService()) {
//                    return;
//                }
//                long timeStamp1 = 0;
//                Avaliacao2 a2 = new Avaliacao2(users.getUid(), cidade, estado, getNamesFeatures(), "", timeStamp1, title, type, true);
//                newAvaliacao(a2, users.getUid());
//                finish();
//                break;
//
//            case "Entreterimento":
//                if (!validateFormService()) {
//                    return;
//                }
//                long timeStamp2 = 0;
//                Avaliacao2 a3 = new Avaliacao2(users.getUid(), cidade, estado, getNamesFeatures(), "", timeStamp2, title, type, true);
//                newAvaliacao(a3, users.getUid());
//                finish();
//                break;
//
//            default:
//                if (!validateFormProduct()) {
//                    return;
//                }
////                showToast("Nao adicionou. Tente novamente");
//        }
//
//    }
//
//    /**
//     * Conversor de list pars array
//     *
//     * @return
//     */
//    private List<String> getNamesFeatures() {
//        List<String> list = new ArrayList<String>();
//        String[] textoSeparado = editFeature.getText().toString().split(";\\s");
//        for (int i = 0; i < textoSeparado.length; i++) {
//            list.add(textoSeparado[i]);
//        }
//        String[] stringArray = list.toArray(new String[0]);
//
//        return list;
//    }
//
//    @Override
//    public void newAvaliacao(Avaliacao2 avaliacao, String userId) {
//        AvaliacaoDaoImplementacao dao = new AvaliacaoDaoImplementacao();
//        dao.newAvaliacao(avaliacao, users.getUid());
////        showToast("Avaliação criada com sucess!");
//        finish();
//    }
//
//    private boolean validateFormProduct() {
//        boolean valid = true;
//
//        String nome = editTitle.getText().toString();
//        if (TextUtils.isEmpty(nome)) {
//            editTitle.setError("Required.");
//            Log.d("TAG", "Valid: " + valid);
//            valid = false;
//        } else {
//            editTitle.setError(null);
//        }
//
//        String desc = editFeature.getText().toString();
//        if (TextUtils.isEmpty(desc)) {
//            editFeature.setError("Required.");
//            valid = false;
//        } else {
//            editFeature.setError(null);
//        }
//
//        String categoria = materialDesignSpinner.getText().toString();
//        if (TextUtils.isEmpty(categoria)) {
//            materialDesignSpinner.setError("Required.");
//            valid = false;
//        } else {
//            materialDesignSpinner.setError(null);
//        }
//
//        return valid;
//    }
//
//    private boolean validateFormService() {
//        boolean valid = true;
//
//        String nome = editTitle.getText().toString();
//        if (TextUtils.isEmpty(nome)) {
//            editTitle.setError("Required.");
//            Log.d("TAG", "Valid: " + valid);
//            valid = false;
//        } else {
//            editTitle.setError(null);
//        }
//
//        String desc = editFeature.getText().toString();
//        if (TextUtils.isEmpty(desc)) {
//            editFeature.setError("Required.");
//            valid = false;
//        } else {
//            editFeature.setError(null);
//        }
//
//        String cidade = editCidade.getText().toString();
//        if (TextUtils.isEmpty(cidade)) {
//            editCidade.setError("Required.");
//            valid = false;
//        } else {
//            editCidade.setError(null);
//        }
//
//        String categoria = materialDesignSpinner.getText().toString();
//        if (TextUtils.isEmpty(categoria)) {
//            materialDesignSpinner.setError("Required.");
//            valid = false;
//        } else {
//            materialDesignSpinner.setError(null);
//        }
//
//        String estado = materialDesignSpinnerEstado.getText().toString();
//        if (TextUtils.isEmpty(estado)) {
//            materialDesignSpinnerEstado.setError("Required.");
//            valid = false;
//        } else {
//            materialDesignSpinnerEstado.setError(null);
//        }
//
//        return valid;
//    }

}
