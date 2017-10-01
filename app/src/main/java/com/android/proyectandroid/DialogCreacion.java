package com.android.proyectandroid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DialogCreacion extends Dialog implements
        android.view.View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText nombreTarea;
    private EditText fechaTarea;
    private int dia;
    private int mes;
    private int ano;
private Context context;
    private DatabaseReference mDatabase;
    private Button btn;





    public DialogCreacion(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_creacion);

        //creo una instancia de firebase base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nombreTarea=(EditText) findViewById(R.id.nombreTarea);
        btn=(Button) findViewById(R.id.btnCrearRegistro);
        //establezco la accion que va a ejecutar el boton que crea el registro
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearRegistro();
            }
        });
        fechaTarea= (EditText) findViewById(R.id.fechaTarea);
        fechaTarea.setOnClickListener(this);

    }

    //metodo encargado de crear el registro de la tarea
    public void crearRegistro(){
        try {
            InformacionTarea info = new InformacionTarea();
            //genero un numero aleatorio para ponerlo como id
            Integer id = Integer.valueOf(Math.round(Math.random() * 100) + "");
            //pongo el id en la variable que vamos a guardar
            info.setIdTarea(id);
            //capturo el nombre de la tarea
            info.setNombreTarea(nombreTarea.getText().toString());
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd");
            //convierto la fecha que viene en formato texto a formato fecha
            Date fecha = null;
            try {

                fecha = formatoDelTexto.parse(fechaTarea.getText().toString());

            } catch (ParseException ex) {

                ex.printStackTrace();

            }
            //seteo la fecha de la tarea
            info.setFechaTarea(fecha);
            //envio los datos a almacenr en firebase
            mDatabase.child("Tareas").push().setValue(info);




            Toast.makeText(getContext().getApplicationContext(), "Tarea creada con éxito",
                    Toast.LENGTH_LONG).show();





        }catch (Exception e){
            Toast.makeText(getContext().getApplicationContext(), "Se presentó un error "+e,
                    Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getContext(), this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int anio, int mess, int dia) {
    this.ano=anio;
        this.dia=dia;
        this.mes=mess;
        String fechaArmada= anio+"/"+(mes+1)+"/"+dia;
        fechaTarea.setText(fechaArmada);
    }
}
