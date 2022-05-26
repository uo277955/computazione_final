package com.example.computazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import logic.Client;
import logic.Factory;
import utilities.Utilities;

/**
 * Actividad inicial de la aplicacion.
 *
 */
public class MainActivity extends AppCompatActivity {

    private Button sign_in;
    private List<Client> clients;
    public static Factory factory;
    private EditText editTextUser;
    private EditText editTextPassword;
    private TextView textError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize(); //inicializa la aplicacion
        editTextUser = ((EditText)findViewById(R.id.editTextTextEmailAddress));
        editTextPassword = ((EditText)findViewById(R.id.editTextTextPassword));

        textError = findViewById(R.id.textViewError);
        //Cuando se muestre el mensaje de error, este desaparencera si se toca alguno de los
        //campos de insercion de datos
        editTextPassword.setOnTouchListener(esconderError(textError));
        editTextUser.setOnTouchListener(esconderError(textError));

    }

    /**
     * Método que solo se ejecuta cuando la aplicacion vuelve a tener el foco
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        factory.noCurrentClient(); //Pone a null el valor de el cliente actual
        vaciarCampos(); //Vacia los campos de user y password
    }

    @Override
    protected void onResume() {
        TextView textError = findViewById(R.id.textViewError);
        textError.setVisibility(View.INVISIBLE);
        super.onResume();
        sign_in = (Button)findViewById(R.id.signInButtom);
        sign_in.setOnClickListener(changeViewPantallPrincipal()); //Asigna la accion al boton
        logIn();
    }

    /**
     * Accion del boton, si se accede correctamente pasa a la pantalla siguiente
     * Si no, muestra un mensaje de errror
     * @return
     */
    private View.OnClickListener changeViewPantallPrincipal(){
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign_in()){
                    Utilities.changeView(MainActivity.this, PantallaPrincipal.class);
                }else{
                    mostrarError();
                }
            }
        };
        return v;
    }

    /**
     * Hace visible el campo de error
     */
    private void mostrarError() {
        vaciarCampos();
        textError.setVisibility(View.VISIBLE);
    }

    /**
     * Esconde el mensaje de error si se tocan los dos campos de entrada de datos
     * O password o email
     * @param textError
     * @return
     */
    private View.OnTouchListener esconderError(TextView textError){
        View.OnTouchListener v = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                textError.setVisibility(View.INVISIBLE);
                return false;
            }
        };
        return v;
    }

    /**
     * INICIALIZA LA APLICACION
     * Atencion:
     * En el fichero res/raw hay dos ficheros con los datos iniciales que van dinamicamente con
     * la aplicacion. NO PUEDO ESCRIBIR EN ESOS FICHEROS
     *
     * Si es la primera vez que se ejecuta esa aplicacion, al hacer el try, no encontrara
     * esa ruta (FileNotFoundException) porque esos ficheros se crean cuando escribimos los datos a la
     * hora de cerrar la aploicacion. Pasara al catch y leera inicialmente del res/raw/tickets
     * y del res/raw/users.
     *
     * Cuando se cierre la app (Pantalla principal sea destruida) guardaremos los datos, si el fichero
     * no existe en memoria interna del telefono (cosa que es normal en la primera ejecucion), lo
     * crea. Si existe, lo sobreescribe.
     *
     * En las siguientes ejecuciones SI encontrara la ruta
     */
    private void initialize(){
        File file = new File(this.getFilesDir() + File.separator + factory.TICKETS_FILE);
        File fileUsers = new File(this.getFilesDir() + File.separator +  factory.USERS_FILE);
        try {
            InputStream i = new FileInputStream(file);
            InputStream iClients = new FileInputStream(fileUsers);
            factory = new Factory(i, iClients);
        }catch(Exception e){
            cargarDatosManualmente();
        }finally{
            clients=factory.getClientes();
        }
        //Aqui ya se ha generado una lista de clientes y una lista de tickets disponibles
    }

    /**
     *Lee del res/raw (fichero que va con la app)
     * @return
     */
    private void cargarDatosManualmente(){
        InputStream iClients = this.getResources().openRawResource(R.raw.users);
        InputStream iTickets = this.getResources().openRawResource(R.raw.tickets);
        factory = new Factory(iTickets, iClients);
    }

    /**
     * Inicia sesion, si existe algun usuario con ese nombre y contraseña
     * @return false si no existe usuario
     */
    private boolean sign_in(){
        String user = editTextUser.getText().toString();
        String password = editTextPassword.getText().toString();
        for (Client c : clients){
            if (c.getEmail().equals(user) && c.getPassword().equals(password)){
                factory.setCurrentClient(c);
                return true;
            }
        }
        return false;
    }

    /**
     * Vacia los campos
     */
    private void vaciarCampos(){
        editTextUser.setText(null);
        editTextPassword.setText(null);
    }

    /**
     * Da sentido al boton de registrarse
     */
    private void logIn(){
        Button b = (Button) findViewById(R.id.registrarse);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.changeView(MainActivity.this, LogIn.class);
            }
        });
    }




}