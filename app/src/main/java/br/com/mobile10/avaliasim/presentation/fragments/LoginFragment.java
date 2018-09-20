package br.com.mobile10.avaliasim.presentation.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.presentation.activity.ResetActivity;
import br.com.mobile10.avaliasim.util.Alerts;
import br.com.mobile10.avaliasim.util.InterfaceUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LoginFragment extends Fragment {

    private EditText emailEditText;
    private EditText pswEditText;
    private IUserDAO userDAO;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            goToPerfilFragement();
        }

    }

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
        Intent intent = new Intent(getContext(), ResetActivity.class);
        startActivity(intent);
    }

    private void onSignInBtnClick(View v) {
        String email = emailEditText.getText().toString();
        String password = pswEditText.getText().toString();

        if (isFormValidated()) {
            ProgressDialog progressDialog = InterfaceUtils.showProgressDialog(getContext(), "Logando...");
            userDAO.signIn(email, password, result -> {
                if (((int) result) == 1) {
                    goToPerfilFragement();
                    InterfaceUtils.hideKeyboard(getActivity());
                } else
                Alerts.toast(getActivity(), "Erro ao logar.");

                InterfaceUtils.hideProgressDialog(progressDialog);
            });

        }
    }

    private void goToPerfilFragement() {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.login_fragment, new ProfileFragment())
                .commit();
    }

    private void onRegisterBtnClick(View v) {
        String email = emailEditText.getText().toString();
        String password = pswEditText.getText().toString();

        if (isFormValidated()) {
            ProgressDialog progressDialog = InterfaceUtils.showProgressDialog(getContext(), "Criando conta...");

            userDAO.findSignInMethods(email, resultingCodeForAuthVerification -> {
                if (((int) resultingCodeForAuthVerification) == 1) {
                    Alerts.toast(getActivity(), "Este email já está cadastrado. Tente recuperar a senha");
                    InterfaceUtils.hideProgressDialog(progressDialog);
                } else
                    userDAO.create(email, password, resultingCodeForAccountCreation -> {
                        if (((int) resultingCodeForAccountCreation) == 1) {
                            Alerts.toast(getActivity(), "Usuário registrado com sucesso");
                            getFragmentManager()
                                    .beginTransaction()
                                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                                    .replace(R.id.login_fragment, new ProfileFragment())
                                    .commit();
                            InterfaceUtils.hideKeyboard(getActivity());
                        } else
                        Alerts.toast(getActivity(), "Erro ao registrar usuário");

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
