/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompteGui;

import GestionCompte.entities.Client;
import GestionCompte.entities.Compte;
import GestionCompte.entities.EncapsulationComtpe;
import GestionCompte.services.ClientCRUD;
import GestionCompte.services.CompteCRUD;
import GestionReview.tests.MainPlaningClient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author masso
 */
public class GestionCompteClientController implements Initializable {

    @FXML
    private Label l_nom;
    @FXML
    private Label l_prenom;
    @FXML
    private Label l_userName;
    @FXML
    private Label l_age;
    @FXML
    private Label l_email;
    @FXML
    private Label l_password;
    @FXML
    private Label l_numTel;
    @FXML
    private TextField in_userName;
    @FXML
    private TextField in_nom;
    @FXML
    private TextField in_prenom;
    @FXML
    private TextField in_age;
    @FXML
    private TextField in_email;
    @FXML
    private TextField in_password;
    
    @FXML
    private TextField in_numTel;
    @FXML
    private Button btn_valider;
    
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;
    @FXML
    private Button btn_Consulter_planing;
    @FXML
    private Label l_deconnecte;
    @FXML
    private Label l_gestion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficheMode();
    }

    

    

    @FXML
    private void valider(ActionEvent event) {
        
        Client c = new Client(EncapsulationComtpe.getId(), in_userName.getText(), in_nom.getText(), in_prenom.getText(), Integer.parseInt(in_age.getText()), in_email.getText(), CompteCRUD.encrypt(in_password.getText()), Integer.parseInt(in_numTel.getText()));
        ClientCRUD cc = new ClientCRUD();
        cc.modifierClient(c);
        JOptionPane.showMessageDialog(null, "Compte modifié!");
        EncapsulationComtpe encapsulationCompte = new EncapsulationComtpe(c.getId(), c.getNomDutilisateur(), c.getNom(), c.getPrenom(), c.getAge(), c.getAdresseMail(), c.getMotDePasse(), c.getNumTel());
        afficheMode();
    }
    

    private void afficheMode() {
        in_age.setVisible(false);
        in_email.setVisible(false);
        in_nom.setVisible(false);
        in_numTel.setVisible(false);
        in_password.setVisible(false);
        in_prenom.setVisible(false);
        in_userName.setVisible(false);
        
        btn_valider.setVisible(false);
        l_age.setText(Integer.toString(EncapsulationComtpe.getAge()));
        l_email.setText(EncapsulationComtpe.getAdresseMail());
        l_nom.setText(EncapsulationComtpe.getNom());
        l_prenom.setText(EncapsulationComtpe.getPrenom());
        l_password.setText(CompteCRUD.decrypt(EncapsulationComtpe.getMotDePasse()));
        l_userName.setText(EncapsulationComtpe.getNomDutilisateur());
        l_numTel.setText(Integer.toString(EncapsulationComtpe.getNumTel()));
    }

    @FXML
    private void modifier(ActionEvent event) {
        in_age.setVisible(true);
        in_email.setVisible(true);
        in_nom.setVisible(true);
        in_numTel.setVisible(true);
        in_password.setVisible(true);
        in_prenom.setVisible(true);
        in_userName.setVisible(true);
        
        btn_valider.setVisible(true);
        in_age.setText(Integer.toString(EncapsulationComtpe.getAge()));
        in_email.setText(EncapsulationComtpe.getAdresseMail());
        in_nom.setText(EncapsulationComtpe.getNom());
        in_password.setText(CompteCRUD.decrypt(EncapsulationComtpe.getMotDePasse()));
        in_prenom.setText(EncapsulationComtpe.getPrenom());
        in_userName.setText(EncapsulationComtpe.getNomDutilisateur());
        in_numTel.setText(Integer.toString(EncapsulationComtpe.getNumTel()));
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Client c = new Client(EncapsulationComtpe.getId());
        ClientCRUD cc = new ClientCRUD();
        cc.supprimerClient(c);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sauthentifier.fxml"));
        try {
            Parent root = loader.load();
            in_userName.getScene().setRoot(root);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void consulter_planing(ActionEvent event) {
        Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            new MainPlaningClient().start(s);
        //Application.launch(MainPlaningClient.class, args);
    }

    @FXML
    private void signOut(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AccueilVisiteur.fxml"));
                    try {
                        Parent root = loader.load();
                        in_userName.getScene().setRoot(root);
                        

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
    }

    @FXML
    private void accueil(MouseEvent event) {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AccueilClient.fxml"));
                    try {
                        Parent root = loader.load();
                        in_userName.getScene().setRoot(root);
                        

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
    }
    

    
}
