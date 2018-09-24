package br.com.mobile10.avaliasim.presentation.fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.util.InterfaceUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LoginFragment extends Fragment {

    private EditText emailEditText;
    private EditText pswEditText;
    private IUserDAO userDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        emailEditText = view.findViewById(R.id.field_email);
        pswEditText = view.findViewById(R.id.field_password);
        userDAO = new UserDAO();

        view.findViewById(R.id.email_sign_in_button).setOnClickListener(this::onSignInBtnClick);
        view.findViewById(R.id.register_button).setOnClickListener(this::onRegisterBtnClick);
        view.findViewById(R.id.forgot_psw_button).setOnClickListener(this::onForgotPasswordBtnClick);
        return view;
    }

    private void onForgotPasswordBtnClick(View v) {
//        Intent intent = new Intent(getContext(), ResetActivity.class);
//        startActivity(intent);
    }

    private void onSignInBtnClick(View v) {
        String email = emailEditText.getText().toString();
        String password = pswEditText.getText().toString();

        if (isFormValidated()) {
            ProgressDialog progressDialog = InterfaceUtils.showProgressDialog(getContext(), "Logando...");
            userDAO.signIn(email, password, result -> {
                if (((int) result) == 1) {
                    InterfaceUtils.replaceFragment(getFragmentManager(), R.id.login_fragment, new ProfileFragment());
                    InterfaceUtils.hideKeyboard(getActivity());
                } else
                    InterfaceUtils.showToast(getActivity(), "Erro ao logar.");

                InterfaceUtils.hideProgressDialog(progressDialog);
            });
        }
    }

    private void onRegisterBtnClick(View v) {
        String email = emailEditText.getText().toString();
        String password = pswEditText.getText().toString();

        if (isFormValidated()) {
            ProgressDialog progressDialog = InterfaceUtils.showProgressDialog(getContext(), "Criando conta...");

            userDAO.findSignInMethods(email, resultingCodeForAuthVerification -> {
                if (((int) resultingCodeForAuthVerification) == 1) {
                    InterfaceUtils.showToast(getActivity(), "Este email já está cadastrado. Tente recuperar a senha");
                    InterfaceUtils.hideProgressDialog(progressDialog);
                } else
                    userDAO.create(email, password, resultingCodeForAccountCreation -> {
                        if (((int) resultingCodeForAccountCreation) == 1) {
                            InterfaceUtils.showToast(getActivity(), "Usuário registrado com sucesso");
                            InterfaceUtils.replaceFragment(getFragmentManager(), R.id.login_fragment, new ProfileFragment());
                            InterfaceUtils.hideKeyboard(getActivity());
                        } else
                            InterfaceUtils.showToast(getActivity(), "Erro ao registrar usuário");

                        InterfaceUtils.hideProgressDialog(progressDialog);
                    });
            });
        }
    }

    private boolean isFormValidated() {
        AtomicBoolean isValid = new AtomicBoolean(true);

        Arrays.asList(emailEditText, pswEditText).forEach((EditText et) -> {
            if (TextUtils.isEmpty(et.getText().toString())) {
                et.setError("Necessário");
                isValid.set(false);
            } else
                et.setError(null);
        });

        return isValid.get();
    }
}
