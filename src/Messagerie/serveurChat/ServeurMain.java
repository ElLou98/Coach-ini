package Messagerie.serveurChat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;



/**
 *
 * @author Louay
 */
public class ServeurMain {
    public static void main(String[] args) {
        int port=8818;
        Serveur serveur= new Serveur(port);
        //lancer le thread serveur
        serveur.start();
    }
    
    
    
}
