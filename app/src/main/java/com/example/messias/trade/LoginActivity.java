package com.example.messias.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressBar progressBar;

    private ImageView imgLogo;
    private Button btnLogin, btnSingUp, btnForgotPass;
    private EditText editEmail, editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        firebaseAuth = FirebaseAuth.getInstance();

        imgLogo = (ImageView) findViewById(R.id.logo);
        btnLogin = (Button) findViewById(R.id.btnEntrar);
        btnSingUp = (Button) findViewById(R.id.btnCadastrar);
        btnForgotPass = (Button) findViewById(R.id.btnRecuperarSenha);
        editPassword = (EditText) findViewById(R.id.editSenha);
        editEmail = (EditText) findViewById(R.id.editEmail);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        imgLogo.setImageResource(R.drawable.ic_launcher);

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, R.string.falhou, Toast.LENGTH_SHORT).show();
            }
            });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    goHome();
                }
            }
        };


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();

                if(email.equals(null) || email.equals("")){
                    Toast.makeText(LoginActivity.this, R.string.emailVazio, Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(null) || password.equals("")){
                        Toast.makeText(LoginActivity.this, R.string.senhaVazio, Toast.LENGTH_SHORT).show();
                    }else{
                        login(email,password);
                    }
                }

            }
        });

        //
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SingUpActivity.class));
            }
        });

        //
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });


    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        progressBar.setVisibility(View.VISIBLE);
        findViewById(R.id.login_button).setVisibility(View.GONE);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {        
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, R.string.falhou, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                findViewById(R.id.login_button).setVisibility(View.VISIBLE);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public void login(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.erro,
                                    Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    public void goHome(){
        startActivity(new Intent(LoginActivity.this, Home.class));
    }
}