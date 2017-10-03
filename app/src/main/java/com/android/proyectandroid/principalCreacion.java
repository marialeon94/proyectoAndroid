package com.android.proyectandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class principalCreacion extends AppCompatActivity {

    //componente para establecer la lista de tareas
    private RecyclerView mRecyclerView;
    //componente para convertir el listado de datos
    private RecyclerView.Adapter mAdapter;
    //layout donde se va a mostrar el listado
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_creacion);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tareas");
        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot

                        List<InformacionTarea> tarea = new ArrayList<>();


                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                            tarea.add(dsp.getValue(InformacionTarea.class));
                        }
                        mAdapter = new TareasAdapter(tarea);

                        mRecyclerView.setAdapter(mAdapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Abro el dialog para crear la nueva tarea
                DialogCreacion cdd=new DialogCreacion(principalCreacion.this);
                cdd.show();
            }
        });
    }



}
