package com.example.messias.trade;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.location.LocationManager.PASSIVE_PROVIDER;

public class SingUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference, usuarios;

    private Button btnLogin, btnSingUp;
    private ImageView imgLogo;
    private EditText editEmail, editPassword, editNome;
    private ProgressBar progressBar;

    private LocationManager locationManager;
    private double longitude,latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        usuarios = reference.child("usuarios");

        imgLogo = (ImageView) findViewById(R.id.logo);
        btnLogin = (Button) findViewById(R.id.btnEntrar);
        btnSingUp = (Button) findViewById(R.id.btnCadastrar);
        editNome = (EditText) findViewById(R.id.editNome);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editSenha);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        imgLogo.setImageResource(R.drawable.ic_launcher);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(PASSIVE_PROVIDER);

        longitude = location.getLongitude();
        latitude = location.getLatitude();

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        //
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingUpActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void createUser() {
        final String nome;
        final String email, password;

        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        nome = editNome.getText().toString();

        if(email.equals(null) || email.equals("")){
            Toast.makeText(SingUpActivity.this, R.string.emailVazio, Toast.LENGTH_SHORT).show();
        }else{
            if(password.equals(null) || password.equals("")){
                Toast.makeText(SingUpActivity.this, R.string.senhaVazio, Toast.LENGTH_SHORT).show();
            }else{
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SingUpActivity.this, R.string.erro,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            progressBar.setVisibility(View.GONE);

                            String userID = firebaseAuth.getCurrentUser().getUid();

                            DatabaseReference usuario = usuarios.child(userID);

                            usuario.child("nome").setValue(nome);
                            usuario.child("email").setValue(email);
                            usuario.child("lat").setValue(latitude);
                            usuario.child("long").setValue(longitude);
                            usuario.child("distancia").setValue("100");
                            goHome();
                        }
                    }
                });
            }
        }
    }
    private void goHome() {
        startActivity(new Intent(SingUpActivity.this, Home.class));
    }
}
