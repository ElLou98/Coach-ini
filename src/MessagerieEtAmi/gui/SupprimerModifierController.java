/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagerieEtAmi.gui;

import Messagerie.clientChat.ChatClient;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.MyConnection;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class SupprimerModifierController implements Initializable {
    
    private ChatClient client;
    private String messageToEdit;
    private int idMessage;
    private String login;
    @FXML
    private TextField textFieldToEdit;
    @FXML
    private Button buttonSupprimer;
    @FXML
    private Button buttonModifier;
    Connection cnx = MyConnection.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EncapsulationSupprimerModifier esm=new EncapsulationSupprimerModifier();
        client=esm.getClient();
        messageToEdit=esm.getMessageToEdit();
        idMessage=esm.getIdMessage();
        login =esm.getLogin();
        textFieldToEdit.setText(messageToEdit);
        
    }    

    @FXML
    private void actionDelete(ActionEvent event) throws IOException, SQLException {
        client.deleteMessage(idMessage);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String requete = "INSERT INTO message (destinataire,expediteur,contenuMessage,dateEnvoie) "
                    + "VALUES ('1n2o3n4e','1n2o3n4e','Edit','"+LocalDateTime.now().format(formatter)+ "')";
        Statement st = cnx.createStatement();
        st.executeUpdate(requete);
        client.msg("**UpdateActionEnvoie**");
        Stage stage = (Stage) buttonSupprimer.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionModifier(ActionEvent event) throws IOException, SQLException {
        client.modifyMessage(idMessage, textFieldToEdit.getText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String requete = "INSERT INTO message (destinataire,expediteur,contenuMessage,dateEnvoie) "
                    + "VALUES ('1n2o3n4e','1n2o3n4e','Edit','"+LocalDateTime.now().format(formatter)+ "')";
        Statement st = cnx.createStatement();
        st.executeUpdate(requete);
        client.msg("**UpdateActionEnvoie**");
        Stage stage = (Stage) buttonModifier.getScene().getWindow();
        stage.close();
    }
    
}
