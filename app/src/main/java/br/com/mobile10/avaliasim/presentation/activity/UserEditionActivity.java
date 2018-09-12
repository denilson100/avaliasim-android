package br.com.mobile10.avaliasim.presentation.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.User;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UserEditionActivity extends AppCompatActivity {

    private AutoCompleteTextView nameInput;
    private AutoCompleteTextView phoneInput;
    private AutoCompleteTextView emailInput;
    private AutoCompleteTextView addressInput;
    private AutoCompleteTextView cityInput;
    private AutoCompleteTextView stateInput;
    private IUserDAO userDAO;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edition_activity);
        setTitle("Editar informações");

        loggedUser = (User) getIntent().getSerializableExtra("loggedUser");
        userDAO = new UserDAO();

        initializeViews();

        String[] cities = getResources().getStringArray(R.array.cities);
        String[] states = getResources().getStringArray(R.array.states);

        cityInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities));
        stateInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, states));

        nameInput.setText(loggedUser.getName());
        phoneInput.setText(loggedUser.getPhoneNumber());
        emailInput.setText(loggedUser.getEmail());
        addressInput.setText(loggedUser.getAddress());
        cityInput.setText(loggedUser.getCity());
        stateInput.setText(loggedUser.getState());
    }

    private void initializeViews() {
        cityInput = findViewById(R.id.cityInput);
        cityInput.setOnFocusChangeListener(this::onFocusChange);
        stateInput = findViewById(R.id.stateInput);
        stateInput.setOnFocusChangeListener(this::onFocusChange);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneNumberInput);
        emailInput = findViewById(R.id.emailInput);
        addressInput = findViewById(R.id.addressInput);

        findViewById(R.id.save_edit_btn).setOnClickListener(this::onEditionBtnClick);
    }

    private void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            AutoCompleteTextView acTextView = (AutoCompleteTextView) view;
            String str = acTextView.getText().toString();

            ListAdapter listAdapter = acTextView.getAdapter();
            for (int i = 0; i < listAdapter.getCount(); i++) {
                String temp = listAdapter.getItem(i).toString();
                if (str.compareTo(temp) == 0)
                    return;
            }

            acTextView.setText("");
        }
    }

    private void onEditionBtnClick(View view) {
        if (isFormValidated()) {
            loggedUser.setName(nameInput.getText().toString());
            loggedUser.setPhoneNumber(phoneInput.getText().toString());
            loggedUser.setEmail(emailInput.getText().toString());
            loggedUser.setAddress(addressInput.getText().toString());
            loggedUser.setCity(cityInput.getText().toString());
            loggedUser.setState(stateInput.getText().toString());

            userDAO.update(loggedUser, result -> {
                Toast.makeText(this, "Usuário editado com sucesso!", Toast.LENGTH_LONG).show();
                //TODO: atualizar os dados do usuário após a edição
                finish();
            });
        }
        else
            Toast.makeText(this,"Preencha todos os campos", Toast.LENGTH_SHORT).show();
    }

    private boolean isFormValidated() {
        AtomicBoolean isValid = new AtomicBoolean(true);

        Arrays.asList(nameInput, phoneInput, addressInput, cityInput, stateInput).forEach((et) -> {
            if (TextUtils.isEmpty(et.getText().toString())) {
                et.setError("Necessário");
                isValid.set(false);
            } else
                et.setError(null);
        });

        return isValid.get();
    }
}
