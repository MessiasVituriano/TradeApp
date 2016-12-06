package com.example.messias.trade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private ImageView imgLogo;
    private Button btnLogin, btnSingUp, btnForgotPass;
    private EditText editEmail;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        imgLogo = (ImageView) findViewById(R.id.logo);
        btnSingUp = (Button) findViewById(R.id.btnCadastrar);
        btnForgotPass = (Button) findViewById(R.id.btnRecuperarSenha);
        editEmail = (EditText) findViewById(R.id.editEmail);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        firebaseAuth = FirebaseAuth.getInstance();

        imgLogo.setImageResource(R.drawable.ic_launcher);

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;

                email = editEmail.getText().toString();
                
                if(email.equals(null) || email.equals("")){
                    Toast.makeText(ForgotPassActivity.this, R.string.emailVazio, Toast.LENGTH_SHORT).show();
                }else{
                    resetPass(email);
                }
            }
        });

        //
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassActivity.this, SingUpActivity.class));
            }
        });
    }

    public  void  resetPass(String email){
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(ForgotPassActivity.this, R.string.erro, Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPassActivity.this, R.string.recuperarSenha, Toast.LENGTH_SHORT).show();
                    goLoginScreen();
                }
            }
        });
    }

    public void goLoginScreen(){
        startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
    }

}
