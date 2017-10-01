package com.android.proyectandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class principal extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mailLogin;
    private EditText passLogin;
    private String users="";
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        // Obtengo la instancia de firebase
        mAuth = FirebaseAuth.getInstance();
        //inicializo los campos de texto para ingresar email y password
        mailLogin=(EditText) findViewById(R.id.mailLogin);
        passLogin=(EditText) findViewById(R.id.passLogin);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_sign_in);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.print("jaja");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Método para cambiar la pantalla de login por la de regristro de usuarios
     * @param v
     */
    public void irAregistro(View v){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }


    public void login(View v){
//Valido que el login y el password hayan sido escritos, en caso de que no, se imprime un mensaje
        if(mailLogin.getText().toString().equals("")){
            Toast.makeText(principal.this, "DEBE INGRESAR UN EMAIL",
                    Toast.LENGTH_LONG).show();
        }else  if(passLogin.getText().toString().equals("")){
            Toast.makeText(principal.this, "DEBE INGRESAR UNA CONTRASEÑA",
                    Toast.LENGTH_LONG).show();
        }else {
            //Si el usuario escribió los datos de acceso, se hace uso del método de firebase para autenticación
            mAuth.signInWithEmailAndPassword(mailLogin.getText().toString(), passLogin.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Si el logueo fue exitoso muestro un mensaje de bienvenido

                                FirebaseUser user = mAuth.getCurrentUser();
                                setUsers(user.getDisplayName());
                                Toast.makeText(principal.this, "BIENVENIDO",
                                        Toast.LENGTH_LONG).show();
                                //cambio la vista a la actividad principal donde se van a crear las tareas
                                Intent intent= new Intent(getApplicationContext(), principalCreacion.class);
                                startActivity(intent);
                            } else {
                                // Si no se pudo loguear, entonces muestro un mensaje
                                Toast.makeText(principal.this, "DATOS INCORRECTOS",
                                        Toast.LENGTH_LONG).show();

                            }


                        }
                    });

        }
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
