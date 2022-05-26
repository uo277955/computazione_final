package logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

/**
 * Clase que se usa para cargar y salvar las lista de tickets y de clientes
 * a los ficheros correspondientes
 */
public abstract class FileUtil {
    /**
     * Lee los tickets
     */
    public static void loadTicket(InputStream iS, List<Ticket> listaTickets) {

        String linea;
        String[] datosTicket = null;

        try {

            BufferedReader fichero = new BufferedReader(new InputStreamReader(iS));
            int id =0;
            while (fichero.ready()) {
                linea = fichero.readLine();
                datosTicket = linea.split("-");
                Float price = Float.parseFloat(datosTicket[4]);
                listaTickets.add(new Ticket(datosTicket[0],datosTicket[1],
                        datosTicket[2],new Date(datosTicket[3]),price,  datosTicket[5],
                        datosTicket[6], datosTicket[7], id));
                id++;
            }
            fichero.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("El archivo no se ha encontrado.");
        } catch (IOException ioe) {
            new RuntimeException("Error de entrada/salida.");
        }
    }

    /**
     * Salva los tickets
     * @param oS
     * @param lista
     */
    public static void saveTickets(OutputStream oS, List<Ticket> lista) {
        try {
            BufferedWriter fichero = new BufferedWriter(new OutputStreamWriter(oS));
            String linea = "";
            for (Ticket c : lista) {
                linea = linea + c.save_String() + "\n";

            }
            fichero.write(linea);
            fichero.close();
        }

        catch (FileNotFoundException fnfe) {
            System.out.println("El archivo no se ha podido guardar");
        } catch (IOException ioe) {
            new RuntimeException("Error de entrada/salida");
        }
    }


    /**
     * Lee los clientes
     * @param iS
     * @param listaClientes
     */
    public static void loadClients(InputStream iS, List<Client> listaClientes) {

        String linea;
        String[] datosCliente = null;

        try {

            BufferedReader fichero = new BufferedReader(new InputStreamReader(iS));
            while (fichero.ready()) {
                String path = "@drawable/";
                linea = fichero.readLine();
                datosCliente = linea.split("-");
                float money = Float.parseFloat(datosCliente[3]);
                listaClientes.add(new Client(datosCliente[1],datosCliente[0],datosCliente[2], money));
            }
            fichero.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("El archivo no se ha encontrado.");
        } catch (IOException ioe) {
            new RuntimeException("Error de entrada/salida.");
        }
    }

    /**
     * Salva los clientes
     * @param oS
     * @param lista
     */
    public static void saveClients(OutputStream oS, List<Client> lista) {
        try {
            BufferedWriter fichero = new BufferedWriter(new OutputStreamWriter(oS));
            String linea = "";
            for (Client c : lista) {
                linea = linea + c.toString() + "\n";

            }
            fichero.write(linea);
            fichero.close();
        }

        catch (FileNotFoundException fnfe) {
            System.out.println("El archivo no se ha podido guardar");
        } catch (IOException ioe) {
            new RuntimeException("Error de entrada/salida");
        }
    }


}
