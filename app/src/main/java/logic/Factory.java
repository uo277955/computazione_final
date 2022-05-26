package logic;



import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

/**
 * Clase factory que sirve para administrar las listas de clientes y de tickets
 */
public class Factory implements Serializable {
    public static final String USERS_FILE="users2.txt";
    public static final String TICKETS_FILE="tickets2.txt";

    //Lista de tickets sin Vender, tiene una copia para implementar los SearchViewers
    private List<Ticket> ticketsSinVender;
    private List<Ticket> copiaSinVender;

    //Lista de tickets vendidos, tiene una copia para implementar los SearchViewers
    private List<Ticket> copiaVendidas;
    //Lista original de tickets
    private List<Ticket> listaOriginal;
    //Lista de clientes
    private List<Client> clientes;
    //Cliente actual de la aplicacion
    private Client currentClient;


    /**
     * Constructor
     * @param iTickets
     * @param iClients
     */
    public Factory(InputStream iTickets, InputStream iClients){
        listaOriginal = loadTicketsIntoApp(iTickets); //LEE LOS TICKETS DE EL ARCHIVO
        clientes = loadClientsIntoApp(iClients); //LEE LOS CLIENTES DE ARCHIVO

        setTicketsSinVender(); //Asigna los tickets sin vender
    }


    /**
     * Divide los tickets entre vendidos o no vendidos
     */
    private void setTicketsSinVender(){
        ticketsSinVender = new ArrayList<Ticket>(); //Todo por el buscado
        for (Ticket t : listaOriginal){
            if (t.getOwner().equals("null")){
                ticketsSinVender.add(t);
            }
        }
        copiaSinVender=new ArrayList<>();
        copiaSinVender.addAll(ticketsSinVender);
    }


    /**
     * Método que lee los tickets de el fichero y los mete en una lista
     * @param iS
     * @return
     */
    private List<Ticket> loadTicketsIntoApp(InputStream iS){
        List<Ticket> list = new ArrayList<>();
        FileUtil.loadTicket(iS,list);
        return list;
    }

    /**
     * Método que lee los clientes de el fichero y los mete en una lista
     * @param iS
     * @return
     */
    private List<Client> loadClientsIntoApp(InputStream iS){
        List<Client> list = new ArrayList<>();
        FileUtil.loadClients(iS,list);
        return list;
    }

    /**
     * Método que asigna el usuario actual y carga los tickets que le pertenecen
     * @param c
     */
    public void setCurrentClient(Client c){
        setCurrentClientTickets(c);
        this.currentClient=c;
    }

    /**
     * Dado un cliente, carga en la lista de userTickets todos lso tickets
     * que tienen a ese usuario como owner
     * @param c
     */
    private void setCurrentClientTickets(Client c){
        List<Ticket> userTickets = new ArrayList<>();
        for (Ticket t : listaOriginal){
            if (t.getOwner().equals(c.getEmail())){
                userTickets.add(t);
            }
        }
        c.setTickets(userTickets);
        copiaVendidas = new ArrayList<>();
        copiaVendidas.addAll(userTickets);
    }

    /**
     * Pone a  null el currentClient, es usado cuando se hace el logOut
     */
    public void noCurrentClient(){
        this.currentClient=null;

    }

    public Client getCurrentClient(){
        return currentClient;
    }
    public List<Client> getClientes() {
        return clientes;
    }
    public List<Ticket> getTicketsSinVender() {
        return ticketsSinVender;
    }

    /**
     * Método que hace el filtrado de tickets
     * @param txtBuscar
     * @param iniciales
     * @param option, depende de la opcion se hara filtrado de la lista sinVender
     *                o de la lista de vendidos. 1=sinVender; 2=vendidos
     */
    public void filtrate(String txtBuscar, List<Ticket> iniciales, int option){
        List<Ticket > copia=null;
        switch(option){
            case 1:
                copia = copiaSinVender;
                break;
            case 2:
                copia=copiaVendidas;
                break;
        }
        int lng = txtBuscar.length();
        if(lng==0) {//Si la longitud es 0, no muestra nada
            iniciales.clear();
            iniciales.addAll(copia);
        }else{//Muestra los que contienen la cadena
            iniciales.clear();
            for(Ticket t: copia) {
                if (t.getName().toLowerCase().contains(txtBuscar.toLowerCase())
                || t.getArtist().toLowerCase().contains(txtBuscar.toLowerCase())) {
                    iniciales.add(t);
                }
            }
        }
    }

    /**
     * Método que vende los tickets
     * @param t
     * @param c
     */
    public void sellTicket(Ticket t, Client c){
        assignownerOriginalList(t,c);//Asigna al usuario en la lista original
        setTicketsSinVender(); //Reasigna los vendidos y no vendidos
        setCurrentClientTickets(c); //Reasigna los tickets del usuario

    }

    /**
     * Asigna a un owner un ticket en la lista original
     * @param t
     * @param c
     */
    private void assignownerOriginalList(Ticket t, Client c){
        for (Ticket ticket: listaOriginal){
            if (ticket.getId() == t.getId()){
                ticket.setOwner(c.getEmail());
            }
        }

    }

    /**
     * GUARDA TODOS LOS DATOS EN FICHERO
     * @param tickets
     * @param clients
     */
    public void guardarTodo(OutputStream tickets, OutputStream clients){

        FileUtil.saveTickets(tickets, listaOriginal);
        FileUtil.saveClients(clients, getClientes());
    }




}
