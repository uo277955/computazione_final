package com.example.computazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import logic.Client;
import logic.Factory;
import logic.FileUtil;
import logic.Ticket;
import utilities.Utilities;

public class PantallaPrincipal extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Factory c;
    private Client client; //Cliente activo
    private SearchView txtBuscar;
    private NavigationView nV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaprincipal);
        c = MainActivity.factory; //Recibo el objeto factory de main
        client = c.getCurrentClient();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setButtomsInLayout();
        txtBuscar = findViewById(R.id.searcherTickets);
        txtBuscar.setOnQueryTextListener(this); //Al momento de escribir un texto busca
        showLateralMenu();
        nV.setVisibility(View.INVISIBLE);
        actionsLateralMenu();
        setMoney();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setMoney();//Cuando se vuelve a tomar el foco la aplicacion, modifica el texto de el dinero
    }

    /**
     * Método que se ejecuta cuando se destruye la aplicacion (se ejecuta finish)
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
           File fileTickets = new File(this.getFilesDir() + File.separator + MainActivity.factory.TICKETS_FILE);
           File fileUsers = new File(this.getFilesDir() + File.separator + MainActivity.factory.USERS_FILE);
           OutputStream outTickets = new FileOutputStream(fileTickets);
           OutputStream outUsers = new FileOutputStream(fileUsers);
           //Guarda todo en los ficheros locales de el dispositivo. Si no existen (primera ejecucion)
            //Los crea para que luego sean leidos en las siguientes ejecuciones
           MainActivity.factory.guardarTodo(outTickets,outUsers);
        } catch (IOException e) {
            System.err.println("FILE NOT FOUND");
        }
    }

    private void setButtomsInLayout(){
        List<Ticket> tickets = c.getTicketsSinVender();
        //Obtenemos el linearLayout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.layoutBotones);
        llBotonera.removeAllViews();
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        200);
        //Creamos los botones en bucle
        for (int i = 0; i < tickets.size(); i++) {
            Button button = new Button(this);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(tickets.get(i).toString());
            //Añadimos el botón a la botonera
            llBotonera.addView(button);
            button.setOnClickListener(getInfoBuy(tickets.get(i)));
        }
    }

    /**
     * Para mostrar el nombre y email del usuario activo
     */
    private void setTextInUser(){
        TextView textNombre = (TextView) findViewById(R.id.textViewUser);
        String email = client.getEmail();
        String name = client.getName();
        textNombre.setText(email+"\n\n"+name);
    }

    /**
     * Inicializa el menuLateral
     */
    private void showLateralMenu(){
        setTextInUser();
        nV = (NavigationView)findViewById(R.id.panelOpciones);
        Button button = (Button) findViewById(R.id.buttonOptions);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nV.setVisibility(View.VISIBLE);
            }
        });
        ConstraintLayout cP = (ConstraintLayout) findViewById(R.id.constraintPrincipal);
        cP.setOnClickListener(getBack(nV));
        ImageButton backButton = (ImageButton) findViewById(R.id.imageButtonBack);
        backButton.setOnClickListener(getBack(nV));
    }

    /**
     * Asigna las acciones del menu lateral
     */
    private void actionsLateralMenu(){
        logOut();
        goToMyTickets();
    }
    /**
     * Se vuelve a la pantalla de inicio
     */
    private void logOut(){
        Button logout = (Button) findViewById(R.id.buttonSalir);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void goToMyTickets(){
        Button myTickets = (Button) findViewById(R.id.myTickets);
        myTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.changeView(PantallaPrincipal.this, MyTickets.class);
            }
        });
    }

    /**
     * ESTIOS METODOS SIRVEN PARA EL BUSCADO
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        c.filtrate(newText, c.getTicketsSinVender(), 1);
        setButtomsInLayout();
        return false;
    }

    /**
     * Metodo que hace que no sea Visible el panel lateral ( cuando se toca la flecha atras o se
     * toca cualquier parte de la pantalla que no sea de ese menu)
     * @param nV
     * @return
     */
    private View.OnClickListener getBack(NavigationView nV){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nV.setVisibility(View.INVISIBLE);
            }
        };
        return listener;
    }

    /**
     * Modifica el texto de el dinero
     */
    private void setMoney(){
        TextView money = (TextView) findViewById(R.id.textMoney);
        money.setText(c.getCurrentClient().getMoney() + "€");
    }


    /**
     * Accion que llevaran los botones principales cuando se les pulsa para comprar
     * @param t
     * @return
     */
    private View.OnClickListener getInfoBuy(Ticket t){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nV.getVisibility() != View.VISIBLE){
                    Intent intent = new Intent(PantallaPrincipal.this, BuyTicket.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Ticket" , t);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    //Si se pulsan cuando el panel lateral esta visible, simplemente o hacen invisible
                    nV.setVisibility(View.INVISIBLE);
                }
            }
        };
        return listener;
    }
}