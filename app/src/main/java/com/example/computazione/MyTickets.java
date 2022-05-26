package com.example.computazione;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.List;

import logic.Client;
import logic.Factory;
import logic.Ticket;
import utilities.Utilities;

public class MyTickets extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Factory f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        f = MainActivity.factory;
        SearchView txtBuscar = findViewById(R.id.searcherMyTickets);
        txtBuscar.setOnQueryTextListener(this);
        ImageButton button = findViewById(R.id.buttonBack);
        button.setOnClickListener(Utilities.back(this));
    }
    @Override
    protected void onResume() {

        super.onResume();
        setButtomsInLayout();
    }

    @SuppressLint("ResourceAsColor")
    private void setButtomsInLayout(){

        List<Ticket> tickets =  f.getCurrentClient().getTickets();
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
            button.setOnClickListener(getInfoTickets(tickets.get(i)));
        }
    }

    private View.OnClickListener getInfoTickets(Ticket t){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyTickets.this, InfoTicket.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Ticket" , t);
                intent.putExtra("Id", f.getCurrentClient().getEmail());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        return listener;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * SISTEMA DE BUSCADO
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        f.filtrate(newText, f.getCurrentClient().getTickets(), 2);
        setButtomsInLayout();
        return false;
    }


}