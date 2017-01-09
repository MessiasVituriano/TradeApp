package com.example.messias.trade;

import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RetrieveActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference, livros, usuarios;
    private ArrayList<Livro> arrayLivros = new ArrayList<Livro>();
    private Geocoder geocoder;

    private ListView listaDeLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        livros = reference.child("livros");
        usuarios = reference.child("usuarios");

        geocoder = new Geocoder(this, Locale.getDefault());

        listaDeLivros = (ListView) findViewById(R.id.lista);

        final ArrayAdapter<Livro> adapter = new ArrayAdapter<Livro>(this,
                android.R.layout.simple_list_item_1, arrayLivros);

        listaDeLivros.setAdapter(adapter);

        livros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Livro livro = postSnapshot.getValue(Livro.class);
                    arrayLivros.add(livro);
                    //Toast.makeText(RetrieveActivity.this, livro.getUserID().toString(), Toast.LENGTH_SHORT).show();

                    usuarios.child(livro.getUserID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Double lat, lon;
                            lat = dataSnapshot.child("lat").getValue(Double.class);
                            lon = dataSnapshot.child("long").getValue(Double.class);
                            try {
                                List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);

                                Toast.makeText(RetrieveActivity.this, addresses.get(0).getLocality(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}