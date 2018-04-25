/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.mobile10.avaliasim.auth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.activity.HomeActivity2;
import br.com.mobile10.avaliasim.activity.Main4Activity;
import br.com.mobile10.avaliasim.interfaces.TaskInformer;
import br.com.mobile10.avaliasim.util.BaseActivity;


public class EmailPasswordActivity extends BaseActivity implements
        View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, TaskInformer {

    private static final String TAG = "EmailPassword";
    private static final int RC_SIGN_IN = 9001;

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
    private TextView textSign, textCriarConta;
    private DatabaseReference mDatabase;
    private Context context;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]
    boolean chavePulaValidate;
    String nome;
    FirebaseUser users;

    private GoogleApiClient mGoogleApiClient;
    View promptsView, btEmail;
    private EditText editEmail, editSenha;
    AlertDialog dialog;
    AlertDialog.Builder alertDialogBuilder;
    String resposta;
    TextView txtResposta;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_emailpassword);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        txtResposta = (TextView) findViewById(R.id.resposta);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog("Carregando...");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            Toast.makeText(EmailPasswordActivity.this, "Cadastro realizado com sucesso.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EmailPasswordActivity.this, "Digite email e senha válidos.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if(chavePulaValidate != true) {
            if (!validateForm()) {
                return;
            }
            chavePulaValidate = false;
        }

        showProgressDialog("Carregando...");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,  new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            showToast(getString(R.string.auth_falhou));
                        }

                        hideProgressDialog();
                    }
                });
    }

    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mAuth.signOut();
        mEmailField.setText("");
        mPasswordField.setText("");

        updateUI(null);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Necessário.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Necessário.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private boolean validateFormNewAcauth() {
        boolean valid = true;

        String email = editEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Necessário.");
            valid = false;
        } else {
            editEmail.setError(null);
        }

        String password = editSenha.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editSenha.setError("Necessário.");
            valid = false;
        } else {
            editSenha.setError(null);
        }

        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                updateUI(null);
            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        showProgressDialog("Carregando...");

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        users = user;
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt, user.getEmail()));
            Log.d("ID", "ID é: " + user.getUid());

            mDetailTextView.setText(getString(R.string.ola, user.getEmail()));
            String userEmail = user.getEmail();

//            Intent intent = new Intent( this, Main4Activity.class );
//            startActivity(intent);
            finish();

            findViewById(R.id.content_form).setVisibility(View.GONE);
            findViewById(R.id.content_logado).setVisibility(View.VISIBLE);
            // Proxima intent
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.content_form).setVisibility(View.VISIBLE);
            findViewById(R.id.content_logado).setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_create_account_button:
                alertNovaConta();
                break;
            case R.id.email_sign_in_button:
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                nome = mEmailField.getText().toString();
                break;
        }
    }

    public void alertNovaConta(){

        LayoutInflater li = LayoutInflater.from(this);
        promptsView = li.inflate(R.layout.alert_nova_conta, null);

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        mEmailField = (EditText) promptsView.findViewById(R.id.email);
        mPasswordField = (EditText) promptsView.findViewById(R.id.senha);

        Button btNovaConta = (Button) promptsView.findViewById(R.id.nova_conta);
        btNovaConta.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!validateForm()) {
                            return;
                        } else {
                            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                            dialog.dismiss();
                        }

                    }
                }
        );

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("DESCARTAR",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }

                        });
//        if(!validateForm()) {
        // create alertAddPaoquente dialog
        dialog = alertDialogBuilder.create();
        // show it
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        dialog.show();
//        }
    }

    public void callSignUp(View view){
        Intent intent = new Intent( this, EmailPasswordActivity.class );
        startActivity(intent);
    }

    public void callReset(View view){
        Intent intent = new Intent( this, ResetActivity.class );
        startActivity(intent);
    }

    @Override
    public void onResponseReceive(String data) {

    }

}
