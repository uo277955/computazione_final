package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que sirve para ayudar al loggeo de clientes
 */
public class Logger {
    //Los datos que se introducen
    private String name;
    private String email;
    private String password;
    private String rePassword;

    public Logger(String name, String email, String password, String rePassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
    }

    /**
     * Comprueba que todos los campos tienen texto
     * @return
     */
    public boolean allComplete(){
        if (name.length()==0 || email.length()==0 || password.length()==0|| rePassword.length()==0){
            return false;
        }
        return true;
    }

    /**
     * Comprueba que el email sigue un patron correcto
     * @return
     */
    public boolean emailCorrecto(){
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pat.matcher(email);
       return mat.matches();
    }

    /**
     * Comprueba que contraseña y reContraseña coinciden
     * @return
     */
    public boolean contraseñasCoinciden(){
        return password.equals(rePassword);
    }

    /**
     * Comprueba si existe un usuario con ese email
     * @param e
     * @return
     */
    public boolean unicoUsuario(String e){
        return email.equals(e);
    }

    /**
     * Logea al usuario
     * @param factory
     */
    public void logearse(Factory factory){
        //Crea el nuevo cliente
        Client c = new Client(name, email,password, 20);
        //Añade a ese cliente a la lista de clientes
        factory.getClientes().add(c);
        //Pone como cliente actual al que se acaba de loggear
        factory.setCurrentClient(c);
    }
}
