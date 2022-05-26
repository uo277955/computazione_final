package logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Clase ticket
 */
public class Ticket implements Serializable {
    private String artist; //Artista de el ticket
    private String name; //Nombre del ticket
    private String description; //DEscripcion del ticket
    private Date date; //Fecha en la que se hara la funcion
    private float price; //Precio de el ticket
    private String qr; //Direccion del qr del ticket
    private String photo; //Foto del ticket
    private int id; //Id del ticket para identificarlo, es un int ascendente



    private String owner; //Owner del ticket

    /**
     * Constructor
     * @param artist
     * @param name
     * @param description
     * @param date
     * @param price
     * @param qr
     * @param photo
     * @param owner
     * @param id
     */
    public Ticket(String artist, String name, String description, Date date, float price, String
                  qr, String photo, String owner, int id){
        setName(name);
        setArtist(artist);
        setDate(date);
        setDescription(description);
        setPrice(price);
        setPhoto(photo);
        setQr(qr);
        setOwner(owner);
        setId(id);

    }
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * String que se mostrara en los botones de myTickets y de pantalla Pirncipal
     * @return
     */
    public String toString(){
        return getArtist()+ " - "+ getName() +" - "+ getPrice() + "€";
    }

    /**
     * Con este String se guardan los tickets en el fichero
     * @return
     */
    public String save_String(){
       String fecha=  dateFactor();
        return getArtist()+ "-"+ getName() +"-"+getDescription()+"-"+ fecha+"-"+getPrice()+
                "-"+getQr()+"-"+getPhoto()+"-"+getOwner();
    }

    public void setId(int value){
        this.id=value;
    }

    public int getId(){
        return id;
    }

    /**
     * Devuelve la fecha del ticket en el factor (dd/MMM/yyyy)
     * @return
     */
    public String dateFactor(){
        SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fecha=  formateadorFecha.format(date);
        return fecha;
    }


}
