package com.android.proyectandroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mailRegistro;
    private EditText passRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
//Inicializo la instancia de firebase
        mAuth = FirebaseAuth.getInstance();
        //Inicializo los campos para registrar al usuario
        mailRegistro= (EditText) findViewById(R.id.emailRegistro);
        passRegistro=(EditText) findViewById(R.id.passRegistro);
    }



    public void registrarUsuario( View v){
        //Valido que se hayan llenado los campos para registro
        if(mailRegistro.getText().toString().equals("")){
            Toast.makeText(Registro.this, "DEBE INGRESAR UN EMAIL",
                    Toast.LENGTH_LONG).show();
        }else if(passRegistro.getText().toString().equals("")){
            Toast.makeText(Registro.this, "DEBE INGRESAR EL PASSWORD",
                    Toast.LENGTH_LONG).show();
        }else{
            //Hago uso del método prar crear usuarios de firebase, enviando el email y la contraseña
            mAuth.createUserWithEmailAndPassword(mailRegistro.getText().toString(), passRegistro.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Si se crea exitosamente entonces muestro un mensaje

                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Registro.this, "TE HAS REGISTRADO CON ÉXITO",
                                        Toast.LENGTH_LONG).show();
                                //updateUI(user);
                            } else {
                                // Si falla, muestro un mensaje
                                Log.w("", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Registro.this, "REGISTRO FALLIDO",
                                        Toast.LENGTH_LONG).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }
}
