package com.example.messias.trade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.messias.trade.R.string.email;

public class SingUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private Button btnLogin, btnSingUp;
    private ImageView imgLogo;
    private EditText editEmail, editPassword;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        firebaseAuth = FirebaseAuth.getInstance();

        imgLogo = (ImageView) findViewById(R.id.logo);
        btnLogin = (Button) findViewById(R.id.btnEntrar);
        btnSingUp = (Button) findViewById(R.id.btnCadastrar);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editSenha);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        imgLogo.setImageResource(R.drawable.ic_launcher);

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();

                if(email.equals(null) || email.equals("")){
                    Toast.makeText(SingUpActivity.this, R.string.emailVazio, Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(null) || password.equals("")){
                        Toast.makeText(SingUpActivity.this, R.string.senhaVazio, Toast.LENGTH_SHORT).show();
                    }else{
                        createUser(email,password);
                    }
                }
            }
        });

        //
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingUpActivity.this, LoginActivity.class));
            }
        });

    }

    private void goHome() {
        startActivity(new Intent(SingUpActivity.this, Home.class));
    }

    private void createUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SingUpActivity.this, R.string.erro,
                            Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.GONE);
                    goHome();
                }
            }
        });
    }
}
