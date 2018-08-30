package br.com.mobile10.avaliasim.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.auth.ResetActivity;
import br.com.mobile10.avaliasim.util.InterfaceUtils;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LoginFragment extends Fragment {

    private EditText emailEditText;
    private EditText pswEditText;
    private FirebaseAuth authenticator;

    public LoginFragment() {
        this.setArguments(new Bundle());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Quando substituído, o fragmento antigo permanece em exibição atrás do novo fragmento
        // removeAllViews remove o fragmento antigo definitivamente
        if (container != null)
            container.removeAllViews();

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        authenticator = FirebaseAuth.getInstance();

        emailEditText = view.findViewById(R.id.field_email);
        pswEditText = view.findViewById(R.id.field_password);

        view.findViewById(R.id.email_sign_in_button).setOnClickListener(this::signIn);
        view.findViewById(R.id.register_button).setOnClickListener(this::register);
        view.findViewById(R.id.forgot_psw_button).setOnClickListener(this::startForgotPswActivity);

        return view;
    }

    private void startForgotPswActivity(View v) {
        Intent intent = new Intent(getContext(), ResetActivity.class);
        startActivity(intent);
    }

    private void signIn(View v) {
        String email = emailEditText.getText().toString();
        String password = pswEditText.getText().toString();

        if (isFormValidated()) {
            ProgressDialog progressDialog = InterfaceUtils.showProgressDialog(getContext(), "Logando...");

            authenticator.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .replace(R.id.login_fragment, new ProfileFragment())
                            .commit();
                    InterfaceUtils.hideKeyboard(getActivity());
                } else {
                    Toast.makeText(getContext(), "Erro ao logar.", Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }

                InterfaceUtils.hideProgressDialog(progressDialog);
            });
        }
    }

    private void register(View v) {
        String email = emailEditText.getText().toString();
        String password = pswEditText.getText().toString();


        if (isFormValidated()) {
            ProgressDialog progressDialog = InterfaceUtils.showProgressDialog(getContext(), "Logando...");

            authenticator.fetchSignInMethodsForEmail(email).addOnCompleteListener(listener -> {
                List<String> signInMethods = listener.getResult().getSignInMethods();
                AtomicBoolean isRegistered = new AtomicBoolean(false);

                signInMethods.forEach(method -> {
                    if ("password".equals(method))
                        isRegistered.set(true);
                });

                if (!isRegistered.get())
                    authenticator.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->
                            Toast.makeText(getContext(), "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show());
                else
                    Toast.makeText(getActivity(), "Este email já está cadastrado. Tente recuperar a senha", Toast.LENGTH_SHORT).show();

                InterfaceUtils.hideProgressDialog(progressDialog);
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
