package Messagerie.serveurChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Louay
 */
public class Serveur extends Thread {
    
    private final int portServeur;
    private ArrayList<ServeurWorker> workerList= new ArrayList<>();
    
    public Serveur (int portServeur) {
        this.portServeur=portServeur;
    }
    
    @Override
    public void run() {
        try {
            //Creation du socket et du port associé au socket
            ServerSocket serverSocket = new ServerSocket(portServeur);
            while(true){
                //boucle en attendant que le socket accepte une requête de connexion
              System.out.println("About to accept client connection...");
              Socket clientSocket = serverSocket.accept();
              System.out.println("Accepted connection from "+ clientSocket);
              // requête accepte, instanciation du worker (qui est un Thread)
              ServeurWorker worker=new ServeurWorker(this,clientSocket);
              workerList.add(worker);
              worker.start();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<ServeurWorker> getWorkerList(){
        return workerList;
    }
    
    public void removeWorker(ServeurWorker serveurWorker) {
        workerList.remove(serveurWorker);
    }
       
}
