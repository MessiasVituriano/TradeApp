package com.example.messias.trade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
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

public class RetrieveActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference, livros;
    private Livro livro;
    private ArrayList<Livro> arrayLivros = new ArrayList<Livro>();

    private ListView listaDeLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        listaDeLivros = (ListView) findViewById(R.id.lista);

        final ArrayAdapter<Livro> adapter = new ArrayAdapter<Livro>(this,
                android.R.layout.simple_list_item_1, arrayLivros);

        listaDeLivros.setAdapter(adapter);

        livros = reference.child("livros");

        livros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Livro person = postSnapshot.getValue(Livro.class);

                    arrayLivros.add(person);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}