/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReclamation.gui;

import GestionCompte.entities.EncapsulationComtpe;
import GestionReclamation.entite.Reclamation;
import GestionReclamation.services.ReclamationCRUD;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class ReclamationClientController implements Initializable {

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextArea reclamationTextArea;
    @FXML
    private TableView<Reclamation> reclamationTableView;
    @FXML
    private TableColumn<Reclamation, String> columnDate;
    @FXML
    private TableColumn<Reclamation, String> columnReclamation;
    @FXML
    private TableColumn<Reclamation, String> columnEtat;
    @FXML
    private Button envoyerButton;
    private String login;
    private ReclamationCRUD reclamationCRUD;
    private List<Reclamation> reclamationList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reclamationCRUD=new ReclamationCRUD();
        EncapsulationComtpe ec= new EncapsulationComtpe();
        this.login=ec.getNomDutilisateur();
        typeComboBox.setItems(FXCollections.observableArrayList("Probléme technique",
                "Attitude négative",
                "Autre"));
        typeComboBox.getSelectionModel().selectFirst();
        reclamationList=reclamationCRUD.afficherReclamation();
        columnDate.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("dateReclamation"));
        columnReclamation.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("descriptionReclamation"));
        columnEtat.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("etat"));
        for (int i=0;i<reclamationList.size();i++)
        {
            if(reclamationList.get(i).getLogin().equals(login))
            {             
            String etat;
            if (reclamationList.get(i).getEnCours()==1)
            {
                etat="En Cours";
            }
            else
            {
                if (reclamationList.get(i).getTraite()==1)
                {
                    etat="Traité";
                }
                else
                {
                    etat="-";
                }
            }
            Reclamation maReclamation= new Reclamation(reclamationList.get(i).getDateReclamation(),reclamationList.get(i).getDescriptionReclamation(), etat);
            reclamationTableView.getItems().add(maReclamation);
            }
        }
        
        reclamationTableView.setOnMouseClicked((javafx.scene.input.MouseEvent event) ->{
            if (event.getClickCount()==2){
                Reclamation maReclamationASupprime=reclamationTableView.getSelectionModel().getSelectedItem();
                int selectedIndex = reclamationTableView.getSelectionModel().getSelectedIndex();
                if (JOptionPane.showConfirmDialog(null, "Voulez-vous annuler cette réclamation ?", "Annuler Réclamation",
                   JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                  reclamationCRUD.supprimerReclamation(new Reclamation(login,maReclamationASupprime.getDateReclamation()));
                  Platform.runLater(new Runnable() {
                  @Override
                  public void run() {
                  reclamationTableView.getItems().remove(selectedIndex);
                  }
                  }); 
                } else {
                 // no option
                }
            }    
        });
         
        
        
    }    

    @FXML
    private void envoyerAction(ActionEvent event) {
        if (reclamationTextArea.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Vous devez saisir une réclamation d'abord !");
        }
        else
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
            LocalDateTime now = LocalDateTime.now();
            String actualDate = dtf.format(now);
            Reclamation reclamation= new Reclamation(login, reclamationTextArea.getText(), typeComboBox.getValue(), actualDate);
            reclamationCRUD.ajouterReclamation(reclamation);
            Reclamation maReclamation= new Reclamation(actualDate,reclamationTextArea.getText(), "-");
            reclamationTableView.getItems().add(maReclamation);
            reclamationTextArea.setText("");
            typeComboBox.getSelectionModel().selectFirst();
            
        }
    }


    
}
