package com.example.computazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import logic.Client;
import logic.Factory;
import logic.Ticket;
import utilities.Utilities;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class BuyTicket extends AppCompatActivity {
    private Ticket t;
    private Button b;
    private AlertDialog alertDialog;
    private Factory factory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        t =(Ticket) getIntent().getSerializableExtra("Ticket");
        putNameConcert();
        putNameBand();
        putDescription();
        putDate();
        setPhoto();
        putMoney();
        ImageButton back = findViewById(R.id.imageButtonBackBuy);
        back.setOnClickListener(Utilities.back(this));
        factory = MainActivity.factory;
        b = (Button)findViewById(R.id.buttonBuy);
        activateOrDesactivateButton();
        alertDialog = alertDialog();
        buyTicket();

    }

    /**
     * Si el saldo es insuficiente muestra un mensaje de error
     */
    private void activateOrDesactivateButton(){
        TextView text = findViewById(R.id.textViewSaldoInsuficiente);
        if(factory.getCurrentClient().getMoney()< t.getPrice()){
            b.setEnabled(false);
            text.setVisibility(View.VISIBLE);
        }else{
            text.setVisibility(View.INVISIBLE);
        }
    }

    private void putNameConcert(){
        TextView nombre = (TextView) findViewById(R.id.textViewNameBuy);
        nombre.setText(t.getName());
    }

    private void putNameBand(){
        TextView nombreBanda = (TextView) findViewById(R.id.textViewNameBandBuy);
        nombreBanda.setText(t.getArtist());
    }

    private void putDescription(){
        TextView description = (TextView) findViewById(R.id.textViewDescriptionBuy);
        description.setText(t.getDescription());
    }

    private void putDate(){
        TextView date = (TextView) findViewById(R.id.textViewDateBuy);
        date.setText(t.dateFactor());
    }

    private void putMoney(){
        TextView money = (TextView) findViewById(R.id.textViewPriceBuy);
        money.setText(t.getPrice()+"€");
    }

    private void setPhoto(){
        ImageView photo = (ImageView) findViewById(R.id.imagePhotoBuy);
        photo.setImageResource(getResources().getIdentifier( t.getPhoto(), "drawable", this.getPackageName()));
    }



    /**
     * Cuando se quiera comprar un ticket saldra un mensaje de confirmacion
     */
    private void buyTicket(){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    private AlertDialog alertDialog(){
        AlertDialog dialogo = new AlertDialog
                .Builder(BuyTicket.this)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Client c = factory.getCurrentClient();
                       Float money = c.getMoney();
                       c.setMoney(money-t.getPrice());
                       factory.sellTicket(t, c);
                       finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Hicieron click en el botón negativo, no confirmaron
                        // Simplemente descartamos el diálogo
                        dialog.dismiss();
                    }
                })
                .setTitle(R.string.confirmar) // El título
                .setMessage(R.string.message) // El mensaje
                .create();// No olvides llamar a Create, ¡pues eso crea el AlertDialog

        return dialogo;
    }


}