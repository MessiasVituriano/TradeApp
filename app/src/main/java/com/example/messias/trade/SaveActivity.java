package com.example.messias.trade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class SaveActivity extends AppCompatActivity {

    final static int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage;

    private StorageReference storage;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Livro livro;
    private ArrayList<Livro> arrayLivros = new ArrayList<Livro>();

    private FloatingActionButton btnCadastrar;
    private EditText txtNome, txtDescricao, txtEstado;
    private ImageButton img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();

        txtNome = (EditText) findViewById(R.id.editNome);
        txtDescricao = (EditText) findViewById(R.id.editDescricao);
        txtEstado = (EditText) findViewById(R.id.editEstado);
        img1 = (ImageButton) findViewById(R.id.img1);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });


        btnCadastrar = (FloatingActionButton) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nome, descricao, estado, userID;

                nome = txtNome.getText().toString();
                descricao = txtDescricao.getText().toString();
                estado = txtEstado.getText().toString();
                userID = user.getUid();

                if (nome.equals(null) || nome.equals("") || estado.equals(null) || estado.equals("")){
                    Toast.makeText(SaveActivity.this, "Preencha os campos", Toast.LENGTH_SHORT).show();
                }else{

                    StorageReference imgLivro = storage.child("livros").child(UUID.randomUUID().toString());

                    imgLivro.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri urlImage = taskSnapshot.getDownloadUrl();
                            DatabaseReference livros = reference.child("livros").push();

                            livros.child("userID").setValue(userID);
                            livros.child("estado").setValue(estado);
                            livros.child("descricao").setValue(descricao);
                            livros.child("nome").setValue(nome);
                            livros.child("image").setValue(urlImage.toString());

                            Toast.makeText(SaveActivity.this, "Livro cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();

            img1.setImageURI(selectedImage);
        }
    }
}