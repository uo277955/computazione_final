package com.example.computazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import logic.Ticket;
import utilities.Utilities;

public class InfoTicket extends AppCompatActivity {
    private Ticket t;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ticket);
        t =(Ticket) getIntent().getSerializableExtra("Ticket");
        email = getIntent().getStringExtra("Id");
        putNameConcert();
        putNameBand();
        putDescription();
        putDate();
        setPhoto();
        setQr();
        setId();
        ImageButton button = findViewById(R.id.buttonBackInfoTickets);
        button.setOnClickListener(Utilities.back(this));
    }

    private void putNameConcert(){
        TextView nombre = (TextView) findViewById(R.id.textViewNameEvent);
        nombre.setText(t.getName());
    }

    private void putNameBand(){
        TextView nombreBanda = (TextView) findViewById(R.id.textViewGroup);
        nombreBanda.setText(t.getArtist());
    }

    private void putDescription(){
        TextView description = (TextView) findViewById(R.id.textViewDescription);
        description.setText(t.getDescription());
    }

    private void putDate(){
        TextView date = (TextView) findViewById(R.id.textViewDate);
        date.setText(t.dateFactor());
    }

    private void setPhoto(){
        ImageView photo = (ImageView) findViewById(R.id.imagePhoto);
        photo.setImageResource(getResources().getIdentifier( t.getPhoto(), "drawable", this.getPackageName()));
    }

    private void setQr(){

        ImageView photo = (ImageView) findViewById(R.id.imageQr);
        photo.setImageResource(getResources().getIdentifier( t.getQr(), "drawable", this.getPackageName()));
    }

    private void setId(){
        TextView text = (TextView) findViewById(R.id.textViewIdCliente);
        text.setText(email);
    }



}