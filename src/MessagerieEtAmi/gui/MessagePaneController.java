/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagerieEtAmi.gui;

import Messagerie.clientChat.ChatClient;
import Messagerie.clientChat.MessageListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tools.MyConnection;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public final class MessagePaneController implements Initializable, MessageListener {

    @FXML
    private ScrollPane scrollPaneMessage;
    @FXML
    private ListView<String> listViewMessage;
    @FXML
    private TextField textFieldMessage;
    @FXML
    private AnchorPane anchorPane;
    private String login;
    private ChatClient client;
    private String myLogin;
    //private ArrayList<String> messagesList;
    private Connection cnx = MyConnection.getInstance().getCnx();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EncapsulationUserListMessage eulm= new EncapsulationUserListMessage();
        login=eulm.getLogin();
        client=eulm.getClient();
        myLogin=eulm.getMyLogin();
        client.addMessageListener(this);
        try {
            client.enterConversation(login);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //messagesList=new ArrayList<>();
        listViewMessage.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
            if (event.getClickCount()==2){
                String messageSelectionne=listViewMessage.getSelectionModel().getSelectedItem();
                if (messageSelectionne.substring(0, 5).equals("You: "))
                {
                    messageSelectionne=messageSelectionne.substring(5);
                    EncapsulationSupprimerModifier esm= new EncapsulationSupprimerModifier(messageSelectionne, client, getIdMessage(messageSelectionne),login);
                    Stage stage= new Stage();
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("SupprimerModifier.fxml"));
                    try {
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setTitle("Supprimer ou modifier");
                        stage.setScene(scene);
                       stage.show();
                     } catch (IOException ex) {
                     }
                   // actionRefresh();
                }
            }
      });
        
        
    }
    

    @FXML
    private void envoyerMessage(ActionEvent event) throws IOException, SQLException {
        String text=textFieldMessage.getText();
          
        Platform.runLater(new Runnable() {
        @Override
        public void run() {
            try {
                int idMessage=actualIdMessage()+1;
                client.msg(text+"  ("+idMessage+")");
                //messagesList.add("You: "+text+"  ("+idMessage+")");
                listViewMessage.getItems().add("You: "+text+"  ("+idMessage+")");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        });
        textFieldMessage.setText("");
        
    }

    @Override
    public void onMessage(String fromLogin, String msgBody) {
        if(msgBody.equals("**UpdateActionEnvoie**")){
            try {
                client.msg("**UpdateActionReception**");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            actionRefresh();
        }
        else
        {
            if(msgBody.equals("**UpdateActionReception**")) 
            {
               actionRefresh(); 
            }
            else
        {
        if (fromLogin.equals(myLogin)){
            fromLogin="You";
        }
        String line=fromLogin+": "+msgBody; 
        //messagesList.add(line);
        Platform.runLater(new Runnable() {
        @Override
        public void run() {
        listViewMessage.getItems().add(line);
        }
        });
        }
        }
        
    }

    @FXML
    private void blockAction(ActionEvent event) throws IOException {
        client.block(login);
    }

    @FXML
    private void unBlockAction(ActionEvent event) throws IOException {
        client.unBlock(login);
    }
    
    private int actualIdMessage() throws SQLException{
        int idMessage=0;
        String requete = "SELECT MAX(idMessage) FROM message";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);
        if(rs.next())
        {
            idMessage=(rs.getInt(1));
        }
        return idMessage;   
    }
    
    private int getIdMessage(String messageAvecId){
        int i=messageAvecId.length()-1;
        String tempId="";
        while((i>0)&&(messageAvecId.charAt(i)!='(')){
            i--;
            tempId=messageAvecId.charAt(i)+tempId;
        }
        tempId=tempId.substring(1);
        return(Integer.parseInt(tempId));
    }

    private void actionRefresh() {
        
        Platform.runLater(new Runnable() {
        @Override
        public void run() {
        try {
            listViewMessage.getItems().clear();
            client.enterConversation(login);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }
        });
    }
    
    

    




    
}
