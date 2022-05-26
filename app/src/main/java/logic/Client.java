package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cliente de la aplicacion
 */
public class Client implements Serializable {


    private String name ; //Nombre del usuario
    private String email; //email del usuario, que usara para identificarse
    private String password; //Contraseña del usuario
    private float money; //Dinero del usuario

    private List<Ticket> tickets; //Lista de tickets del usuario

    /**
     * Constructor
     * @param name
     * @param email
     * @param password
     * @param money
     */
    public Client(String name, String email, String password, float money){
        setPassword(password);
        setName(name);
        setEmail(email);
        setMoney(money);
        //Crea la lista de tickets del usuario (al inicio vacia)
        tickets = new ArrayList<Ticket>();
    }


    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String toString(){
        return email+ "-"+name+"-"+password+"-"+money;
    }
}
