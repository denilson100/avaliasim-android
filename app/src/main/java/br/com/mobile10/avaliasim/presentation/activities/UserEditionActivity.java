package br.com.mobile10.avaliasim.presentation.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.User;
import br.com.mobile10.avaliasim.util.InterfaceUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UserEditionActivity extends AppCompatActivity {

    private AutoCompleteTextView nameInput;
    private AutoCompleteTextView phoneInput;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cityInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities));
        stateInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, states));

        nameInput.setText(loggedUser.getName());
        phoneInput.setText(loggedUser.getPhoneNumber());
        addressInput.setText(loggedUser.getAddress());
        cityInput.setText(loggedUser.getCity());
        stateInput.setText(loggedUser.getState());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void initializeViews() {
        cityInput = findViewById(R.id.cityInput);
        cityInput.setOnFocusChangeListener(this::onFocusChange);
        stateInput = findViewById(R.id.stateInput);
        stateInput.setOnFocusChangeListener(this::onFocusChange);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneNumberInput);
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
        loggedUser.setName(nameInput.getText().toString());
        loggedUser.setPhoneNumber(phoneInput.getText().toString());
        loggedUser.setAddress(addressInput.getText().toString());
        loggedUser.setCity(cityInput.getText().toString());
        loggedUser.setState(stateInput.getText().toString());

        userDAO.update(loggedUser, result -> {
            if ((int) result == 1)
                InterfaceUtils.showToast(this, "Usuário editado com sucesso!");
            else
                InterfaceUtils.showToast(this, "Erro ao tentar atualizar informações");
            finish();
        });
    }
}
