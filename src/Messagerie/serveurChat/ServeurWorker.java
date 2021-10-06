package Messagerie.serveurChat;

import Messagerie.entite.Message;
import Messagerie.entite.UtilisateurBloque;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import tools.MyConnection;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Louay
 */
public class ServeurWorker extends Thread {
    
private final Socket clientSocket;
private final Serveur serveur;
private String login=null;
private String sendTo=null;
private OutputStream outputStream;
Connection cnx = MyConnection.getInstance().getCnx();

    public ServeurWorker(Serveur serveur,Socket clientSocket) {
       this.serveur=serveur;
       this.clientSocket=clientSocket;
    }

    
    @Override
    public void run(){
    try {
        //Quand le  thread va démarrer il va appeler la méthode qui gére la communication avec le client
        handleClientSocket();
    } catch (IOException ex) {
        ex.printStackTrace();
    } catch (InterruptedException ex) {
        ex.printStackTrace();
    }
    }
    
    public String getLogin(){
        return login;
    }
    
    private void handleClientSocket() throws IOException,InterruptedException {
        // préparer le flux entrant et sortant des données
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        
        // pouvoir lire ligne par ligne avec le reader à partir du inputstream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line=reader.readLine())!=null){
            String [] tokens = line.split(" ");
            if (tokens!=null && tokens.length>0)
            {
              String cmd = tokens[0];
            if ("logoff".equalsIgnoreCase(cmd) || "quit".equalsIgnoreCase(line)) {
                //quitter si le client ecrit quit
                handleLogOff();
                break;
            } 
            else if ("login".equalsIgnoreCase(cmd)){
                handleLogin(outputStream, tokens);
            }
            else if ("msg".equalsIgnoreCase(cmd))
            { 
              if ((this.sendTo!=null)&&(this.login!=null))
                      {
                        if(isBlocked(sendTo))
                        {
                           outputStream.write("You blocked this user or this user blocked you \n".getBytes());   
                        }
                        else
                        {

                            String[] tokensMsg = line.split(" ", 2);
                            handleMessage(tokensMsg);        
                        }   
                      }  
              else
              {
                outputStream.write("You need to login and enter a conversation to send a message \n".getBytes());  
              }
            }
            else if("conversation".equalsIgnoreCase(cmd)){
                if (this.login!=null)
                {
                   handleConversation(tokens); 
                }
                else
                {
                    outputStream.write("You need to login to enter conversation \n".getBytes());
                }
            }
            else if("block".equalsIgnoreCase(cmd)){
                if (this.login!=null)
                {
                    String[] tokensBlock = line.split(" ", 2);
                   handleBloque(tokensBlock); 
                }
                else
                {
                    outputStream.write("You need to login to block a user \n".getBytes());
                }    
            }
            else if("unblock".equalsIgnoreCase(cmd)){
                if (this.login!=null)
                {
                    String[] tokensUnblock = line.split(" ", 2);
                    handleDebloque(tokensUnblock); 
                }
                else
                {
                    outputStream.write("You need to login to unblock a user \n".getBytes());
                }    
            }
            else if("delete".equalsIgnoreCase(cmd)){
                if ((this.sendTo!=null)&&(this.login!=null))
                {
                    String[] tokensDelete = line.split(" ", 2);
                    handleDelete(tokensDelete); 
                }
                else
                {
                    outputStream.write("You need to enter conversation to delete a message \n".getBytes());
                }    
            }
            else if("modify".equalsIgnoreCase(cmd)){
                if ((this.sendTo!=null)&&(this.login!=null))
                {
                    String[] tokensModify = line.split(" ", 3);
                    handleModify(tokensModify); 
                }
                else
                {
                    outputStream.write("You need to enter conversation to modify a message \n".getBytes());
                }    
            }
            else 
            {
                String msg="unkown "+cmd+"\n";
                outputStream.write(msg.getBytes());
            }           
            }
        }

        clientSocket.close();
    }
    
    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException{
        if (tokens.length==3) {
            String login=tokens[1];
            String password=tokens[2];
//            if ((login.equals("guest")&& password.equals("guest")) ||
//                  (login.equals("Louay")&& password.equals("Louay"))||
//                    (login.equals("tester")&& password.equals("tester")))
                if(login != null && password !=null){
                String msg="ok login\n";
                outputStream.write(msg.getBytes());
                this.login=login;
                System.out.println("User logged in successfully :"+login+"\n");
                
                
                List<ServeurWorker> workerList=serveur.getWorkerList();
                //Envoyer l'utilisateur actuelle aux autres utilisateur en ligne
                for(ServeurWorker worker : workerList){
                    if (worker.getLogin()!=null){
                        if(!login.equals(worker.getLogin()))
                        {
                         String onlineMsg1 = "online "+worker.getLogin()+"\n";
                         send(onlineMsg1);   
                        }
                    }
                }
                //Envoyer aux autres utilisateur en ligne le statut de l'utilisateur actuelle
                String onlineMsg2 = "online "+login+"\n";
                for(ServeurWorker worker : workerList){
                    if(!login.equals(worker.getLogin()))
                    {
                        worker.send(onlineMsg2);
                    }
                    
                }
            } 
            else
            {
                String msg="Error login \n";
                outputStream.write(msg.getBytes());
                System.err.println("Login failed for "+login+"\n");
            }
        }
    }
    

    private void handleLogOff() throws IOException {
        //enlever le worker deconnecté de la liste (éviter exception)
        serveur.removeWorker(this);
        List<ServeurWorker> workerList=serveur.getWorkerList();
        //Envoyer aux autres utilisateur en ligne le statut de l'utilisateur actuelle
                String onlineMsg2 = "Offline "+login+"\n";
                for(ServeurWorker worker : workerList){
                    if(!login.equals(worker.getLogin()))
                    {
                        worker.send(onlineMsg2);
                    }
                    
                }
        clientSocket.close();
        System.out.println("User loggedOff successfully :"+login);
    }

    //format : "msg" "login" texte(body)...
    //format : "msg" "#topic" texte(body)...
    private void handleMessage(String[] tokens) throws IOException { 
       String body=tokens[1];

       List<ServeurWorker> workerList=serveur.getWorkerList();
       for(ServeurWorker worker:workerList){
             if(sendTo.equalsIgnoreCase(worker.getLogin())){
                 if (this.login!=null){
                     if (!body.equals("**UpdateActionEnvoie**") && !body.equals("**UpdateActionReception**"))
                     {
                       body=profanityFilter(body);
                       Message message=new Message(sendTo,login,body);
                       ajouterMessage(message);  
                     }  
                     String outMsg="msg "+ login +" "+ body+"\n";
                     worker.sendMessage(outMsg); 
                 }
                 else
                 {
                     outputStream.write("You need to login \n".getBytes());
                 }
               
              }   
       }    
    }
    
    
    private void send(String onlineMsg) throws IOException {
        if (login !=null){
        outputStream.write(onlineMsg.getBytes());
        }
    }
    private void sendMessage(String onlineMsg) throws IOException {
        if (login !=null){
        outputStream.write(onlineMsg.getBytes());
        }
    }
    
    public void ajouterMessage(Message message) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

            String requete = "INSERT INTO message (destinataire,expediteur,contenuMessage,dateEnvoie) "
                    + "VALUES ('" + message.getDestinataire() + "','" + 
                    message.getExpediteur() + "','" + message.getContenuMessage() + "','" + LocalDateTime.now().format(formatter)+ "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("!! Message Ajoutee a la table !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public List<Message> afficherMessage() {
        List<Message> List = new ArrayList<>();
        try {
            String requete = "SELECT * FROM message ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()) {
            List.add(new Message(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5)));
            }
            
            System.out.println("Message Client affichée !! ");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return List;
    }

    private void handleConversation(String[] tokens) throws IOException {
        this.sendTo=tokens[1];
        String actualSenderReceiver=this.login+this.sendTo;
        List<Message> messages=afficherMessage();
        if (messages!=null){
            for(int i=0;i<messages.size();i++){
            String expediteur=messages.get(i).getExpediteur();
            String destinataire=messages.get(i).getDestinataire();
            String nextSenderReceiver1=expediteur+destinataire;
            String nextSenderReceiver2=destinataire+expediteur;
            if ((actualSenderReceiver.equals(nextSenderReceiver1))||
                (actualSenderReceiver.equals(nextSenderReceiver2)))
            {
               //String messageHistorique="msg "+expediteur +" "+ messages.get(i).getContenuMessage()+"  ("+String.valueOf(messages.get(i).getIdMessage())+")\n"; 
               String messageHistorique="msg "+expediteur +" "+ messages.get(i).getContenuMessage()+"\n"; 
               outputStream.write(messageHistorique.getBytes());
            }
           }  
        }
        
        
    }
    
    public void ajouterBloque(UtilisateurBloque utilisateurBloque) {
        try {

            String requete = "INSERT INTO utilisateurbloque (utilisateur,abloque ) "
                    + "VALUES ('" + utilisateurBloque.getUtilisateur()+ "','" + 
                    utilisateurBloque.getAbloque() +"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("!! UtilisateurBloque Ajoutee a la table !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void handleBloque(String[] tokensBlock) throws IOException {
        String abloque=tokensBlock[1];

          UtilisateurBloque utilisateurBloque=new UtilisateurBloque(this.login, abloque);
          ajouterBloque(utilisateurBloque);
          outputStream.write("User blocked \n".getBytes());
        
        
    }
    
    public List<UtilisateurBloque> afficherUtilisateurBloque() {
        List<UtilisateurBloque> List = new ArrayList<>();
        try {
            String requete = "SELECT * FROM utilisateurbloque ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()) {
            List.add(new UtilisateurBloque(rs.getString(2),rs.getString(3)));
            }
            
            System.out.println("Liste utilisateur bloque recuperee !! ");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return List;
    }

    private boolean isBlocked(String sendTo) {
        boolean etatBloque=false;
        List<UtilisateurBloque> utilisateurBloques=afficherUtilisateurBloque();
        int i=0;
        while ((i<utilisateurBloques.size())&&(etatBloque==false)) 
        {
            if (((utilisateurBloques.get(i).getUtilisateur().equals(this.login))&&
               (utilisateurBloques.get(i).getAbloque().equals(sendTo)))||
               ((utilisateurBloques.get(i).getUtilisateur().equals(sendTo))&&
               (utilisateurBloques.get(i).getAbloque().equals(this.login))))
            {
                etatBloque=true;
            }
            i++;
            
        }
        return etatBloque;
    }

    public void supprimerUtilisateurBloque(UtilisateurBloque utilisateurBloque) {
        try {
            String requete = "DELETE FROM utilisateurbloque WHERE ( utilisateur LIKE '" + 
                    utilisateurBloque.getUtilisateur()+ "' AND abloque LIKE'" + 
                    utilisateurBloque.getAbloque()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Bloque Supprimée !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private void handleDebloque(String[] tokensUnblock) throws IOException {
        String abloque=tokensUnblock[1];
            boolean etatBloque=false;
            List<UtilisateurBloque> utilisateurBloques=afficherUtilisateurBloque();
            int i=0;
            while ((i<utilisateurBloques.size())&&(etatBloque==false)) 
            {
              if ((utilisateurBloques.get(i).getUtilisateur().equals(this.login))&&
                 (utilisateurBloques.get(i).getAbloque().equals(abloque)))
              {
                    etatBloque=true;
              }
              i++;
            
            }
            if (etatBloque)
                  {
                    supprimerUtilisateurBloque(new UtilisateurBloque(this.login,abloque));
                    outputStream.write("User is unblocked \n".getBytes());
                  }
            else 
            {
                outputStream.write("User is not blocked \n".getBytes());
            }
              
    }
    
    public String profanityFilter(String messageAFiltre){
        String tempMessage=null;
  
        try
        {
            HttpPost httpPost = new HttpPost("https://neutrinoapi.net/bad-word-filter");
 
            List<NameValuePair> postData = new ArrayList<>();
            postData.add(new BasicNameValuePair("user-id", "Mr0justice"));
            postData.add(new BasicNameValuePair("api-key", "qA6yqulRCo0JdxYQcIoFf4SfXFW8b3jsVOMrerdJMFKSJ1dA"));
            postData.add(new BasicNameValuePair("content", messageAFiltre));
            postData.add(new BasicNameValuePair("censor-character", "*"));
            httpPost.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
 
            HttpResponse response = HttpClients.createDefault().execute(httpPost);
            String jsonStr = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(jsonStr);
            tempMessage=json.getString("censored-content");
            
        }
        catch (IOException | ParseException | JSONException ex)
        {
            ex.printStackTrace();
        }
        return tempMessage;
    }
    
    public boolean verifierMonMessage(int idMessage){
        List<Message> messages = afficherMessage();
        boolean etatMonMessage=false;
        int i=0;
        while ((i<messages.size())&&(etatMonMessage==false)) 
        {
            if ((messages.get(i).getIdMessage()==idMessage) &&
                (messages.get(i).getExpediteur().equals(login)))
            {
                etatMonMessage=true;
            }
            i++;
            
        }
        return etatMonMessage;
    }

    private void handleDelete(String[] tokensDelete) throws IOException {
        int idMessage=Integer.parseInt(tokensDelete[1]);

            try {
            String requete = "DELETE FROM message WHERE ( idMessage = '" +idMessage+ "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Message Supprimée !! ");

            } catch (SQLException ex) {
              System.err.println(ex.getMessage());
            }
        
    }

    private void handleModify(String[] tokensModify) throws IOException {
        int idMessage=Integer.parseInt(tokensModify[1]);
        String messageAModifie=tokensModify[2];
        if (verifierMonMessage(idMessage))
        {
            try {
            String requete = "UPDATE message SET "
                    + "contenuMessage = '" + messageAModifie+"' WHERE ( idMessage = "+ idMessage+ ")";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Message modifiée !! ");
            
            } catch (SQLException ex) {
              System.err.println(ex.getMessage());
            }   
        }
        else
        {
            outputStream.write("You can only modify your messages \n".getBytes());  
        }
        
    }
    
    
    
    

    

    
    
}


