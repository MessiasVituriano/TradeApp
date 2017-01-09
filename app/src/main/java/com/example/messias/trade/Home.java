package com.example.messias.trade;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        FacebookSdk.sdkInitialize(getApplicationContext());
        //AccessToken.getCurrentAccessToken() == null
        if(user == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton add, view, prof;
            add = (FloatingActionButton) findViewById(R.id.add);
            view = (FloatingActionButton) findViewById(R.id.rem);
            prof = (FloatingActionButton) findViewById(R.id.prof);


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //      .setAction("Action", null).show();
                    startActivity(new Intent(Home.this, SaveActivity.class));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //      .setAction("Action", null).show();
                    startActivity(new Intent(Home.this, RetrieveActivity.class));
                }
            });

            prof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Perfil", Snackbar.LENGTH_LONG)
                          .setAction("Action", null).show();
                }
            });
            //txtNome = (TextView) findViewById(R.id.nomePerfil);
            //imgPerfil = (ImageView) findViewById(R.id.imagemPerfil);


            //Profile profile = Profile.getCurrentProfile();
            //String user_id = profile.getId();
            //String imgURL = "https://graph.facebook.com/" + user_id + "/picture?type=large";

            //txtNome.setText(user.getEmail());
        }
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
