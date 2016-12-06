package com.example.messias.trade;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference, livros;
    private Livro livro;
    private ArrayList<Livro> arrayLivros = new ArrayList<Livro>();


    private FloatingActionButton btnCadastrar;
    private EditText txtNome, txtDescricao, txtEstado;
    private ListView listaDeLivros;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();


        txtNome = (EditText) findViewById(R.id.editNome);
        txtDescricao = (EditText) findViewById(R.id.editDescricao);
        txtEstado = (EditText) findViewById(R.id.editEstado);
        btnCadastrar = (FloatingActionButton) findViewById(R.id.btnCadastrar);
        listaDeLivros = (ListView) findViewById(R.id.lista);

        ArrayAdapter<Livro> adapter = new ArrayAdapter<Livro>(this,
                android.R.layout.simple_list_item_1, arrayLivros);

        listaDeLivros.setAdapter(adapter);

        livros = reference.child("livros");

        livros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    Livro person = postSnapshot.getValue(Livro.class);
                    //Adding it to a string
                    //arrayLivros.add(person);

                    //String string = "Nome: "+person.getNome()+"\nDescricao: "+person.getDescricao()+"\n\n";

                    //Toast.makeText(SaveActivity.this, string, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome, descricao, estado, userID;
                DatabaseReference livros = reference.child("livros").push();

                nome = txtNome.getText().toString();
                descricao = txtDescricao.getText().toString();
                estado = txtEstado.getText().toString();
                userID = user.getUid();
                if (nome.equals(null) || nome.equals("") || estado.equals(null) || estado.equals("")){
                    Toast.makeText(SaveActivity.this, "Preencha os campos", Toast.LENGTH_SHORT).show();
                }else{
                    livro = new Livro(nome, descricao, estado, userID);

                    livros.child("userID").setValue(livro.getUserID());
                    livros.child("estado").setValue(livro.getEstado());
                    livros.child("descricao").setValue(livro.getDescricao());
                    livros.child("nome").setValue(livro.getNome());
                    Toast.makeText(SaveActivity.this, R.string.confirmacaoLivro, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}