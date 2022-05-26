package com.example.computazione;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

import logic.Client;
import logic.Logger;
import utilities.Utilities;

public class LogIn extends AppCompatActivity {
    private Logger logger;
    private TextView errorName;
    private EditText eName;
    private EditText eMail;
    private EditText ePassword;
    private EditText eRePassword;
    private TextView errorEmail;
    private TextView errorPassword;
    private TextView errorEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        eName = ((EditText)findViewById(R.id.editTextTextPersonName));
        eMail = ((EditText)findViewById(R.id.editTextEmailLogin));
        ePassword = ((EditText)findViewById(R.id.editTextPasswordLogin));
        eRePassword = ((EditText)findViewById(R.id.editTextRepiteContraseñaLogin));
        View.OnTouchListener v = esconderError();
        eName.setOnTouchListener(v);
        eMail.setOnTouchListener(v);
        ePassword.setOnTouchListener(v);
        eRePassword.setOnTouchListener(v);
        errorEmpty = ((TextView)findViewById(R.id.textViewErrorLogInVacio));
        errorEmail = ((TextView)findViewById(R.id.textViewErrorLogInEmail));
        errorName = ((TextView)findViewById(R.id.textViewErrorLogInUsuarioExiste));
        errorPassword = ((TextView)findViewById(R.id.textViewErrorLogInContraseñas));
        actionVolver();

    }

    /**
     * Vuelve a la pantalla incial
     */
    private void actionVolver(){
        ImageButton back = (ImageButton) findViewById(R.id.imageButtonSalir);
        back.setOnClickListener(Utilities.back(this));
    }

    private void actionLogIn(){
        Button b = (Button) findViewById(R.id.buttonLogin);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    private void logIn(){
        String name = eName.getText().toString();
        String email = eMail.getText().toString();
        String password = ePassword.getText().toString();
        String rePassword = eRePassword.getText().toString();
        //Consigue los valores de los EditText
        logger = new Logger(name, email, password, rePassword);
        detectarErrores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionLogIn();
    }

    /**
     * Detecta si hay errores, si no hace el logIn
     */
    private void detectarErrores(){
        if (!logger.allComplete()){ //Si no esta todo completo
            errorEmpty.setVisibility(View.VISIBLE);
        }else if (!logger.emailCorrecto()){ //Si el formato del email no es correcto
            errorEmail.setVisibility(View.VISIBLE);
        }else  if (!logger.contraseñasCoinciden()){ //Si las contraseñas no coinciden
            errorPassword.setVisibility(View.VISIBLE);
            ePassword.setText(null);
            eRePassword.setText(null);
        }else if(!actionExistsUser()){ //Si no existe un usuario con ese mail ejecuta el logIn
           alertDialog().show();
        }
    }

    /**
     * Esconde los errores
     * @param
     * @return
     */
    private View.OnTouchListener esconderError(){
        View.OnTouchListener v = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                errorEmail.setVisibility(View.INVISIBLE);
                errorName.setVisibility(View.INVISIBLE);
                errorPassword.setVisibility(View.INVISIBLE);
                errorEmpty.setVisibility(View.INVISIBLE);
                return false;
            }
        };
        return v;
    }

    /**
     * Comprueba si existe un cliente con el mismo usuario, si es asi devuelve true
     * @return
     */
    private boolean actionExistsUser(){
        List<Client> l = MainActivity.factory.getClientes();
        for(Client c: l){
            if (logger.unicoUsuario(c.getEmail())){
                errorName.setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;

    }

    private AlertDialog alertDialog(){
        AlertDialog dialogo = new AlertDialog
                .Builder(LogIn.this)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logger.logearse(MainActivity.factory);
                        Utilities.changeView(LogIn.this, PantallaPrincipal.class);
                        finish();
                    }
                })
                .setMessage(R.string.mensajeDinero) // El mensaje
                .create();// No olvides llamar a Create, ¡pues eso crea el AlertDialog

        return dialogo;
    }


}