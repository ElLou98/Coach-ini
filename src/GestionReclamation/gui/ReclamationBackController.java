/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReclamation.gui;


import GestionCompte.entities.Compte;
import GestionCompte.services.CompteCRUD;
import GestionReclamation.entite.Reclamation;
import GestionReclamation.services.ReclamationCRUD;
import GestionReclamation.services.javaMailUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class ReclamationBackController implements Initializable {

    @FXML
    private TableView<Reclamation> reclamationTableView;
    @FXML
    private TableColumn<Reclamation, String> columnLogin;
    @FXML
    private TableColumn<Reclamation, String> columnReclamation;
    @FXML
    private TableColumn<Reclamation, String> columnType;
    @FXML
    private TableColumn<Reclamation, String> columnDate;
    @FXML
    private TableColumn<Reclamation, String> columnEnCours;
    @FXML
    private TableColumn<Reclamation, String> columnTraite;
    private ReclamationCRUD reclamationCRUD;
    private List<Reclamation> reclamationList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reclamationTableView.setEditable(true);
        reclamationCRUD=new ReclamationCRUD();
        reclamationList=reclamationCRUD.afficherReclamation();
        
        columnLogin.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("login"));
        columnReclamation.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("descriptionReclamation"));
        columnType.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("typeReclamation"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("dateReclamation"));
        columnEnCours.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("enCoursString"));
        columnTraite.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("traiteString"));
        
        for(int i=0; i<reclamationList.size();i++)
        {            
            Reclamation uneReclamation;
            if (reclamationList.get(i).getEnCours()==1)
            {
                uneReclamation=new Reclamation(reclamationList.get(i).getLogin(), 
                        reclamationList.get(i).getDescriptionReclamation(), 
                        reclamationList.get(i).getTypeReclamation(), 
                        reclamationList.get(i).getDateReclamation(), 1, 0);
            }
            else
            {
                if (reclamationList.get(i).getTraite()==1)
                {
                    uneReclamation=new Reclamation(reclamationList.get(i).getLogin(), 
                        reclamationList.get(i).getDescriptionReclamation(), 
                        reclamationList.get(i).getTypeReclamation(), 
                        reclamationList.get(i).getDateReclamation(), 0, 1);
                }
                else
                {
                    uneReclamation=new Reclamation(reclamationList.get(i).getLogin(), 
                        reclamationList.get(i).getDescriptionReclamation(), 
                        reclamationList.get(i).getTypeReclamation(), 
                        reclamationList.get(i).getDateReclamation(), 0, 0);
                }
            }
            reclamationTableView.getItems().add(uneReclamation);   
        }
        
        reclamationTableView.setOnMouseClicked((javafx.scene.input.MouseEvent event) ->{
            if (event.getClickCount()==2){
                Reclamation reclamationSelectionne=reclamationTableView.getSelectionModel().getSelectedItem();
                int selectedIndex = reclamationTableView.getSelectionModel().getSelectedIndex();
                if (reclamationSelectionne.getEnCoursString().equals("Oui")&&reclamationSelectionne.getTraiteString().equals("Non"))
                {
                    if(JOptionPane.showConfirmDialog(null, "Voulez-vous mettre l'état à Traité ?","Traiter réclamation",
                            JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION){
                        Reclamation uneReclamation=new Reclamation(reclamationSelectionne.getLogin(), 
                                reclamationSelectionne.getDescriptionReclamation(), 
                                reclamationSelectionne.getTypeReclamation(), 
                                reclamationSelectionne.getDateReclamation(), 
                                "Non", 
                                "Oui");
                        String userMail=mailObtainer(reclamationSelectionne.getLogin());
                        javaMailUtil.sendMail("yahyaouilouay0@gmail.com",reclamationSelectionne.getLogin());
                        reclamationCRUD.modiferReclamation(uneReclamation);
                        Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                        reclamationTableView.getItems().remove(selectedIndex);
                        reclamationTableView.getItems().add(selectedIndex, uneReclamation);
                        }
                        });
                        
                    }
                }
                else 
                {
                    if (reclamationSelectionne.getEnCoursString().equals("Non")&&reclamationSelectionne.getTraiteString().equals("Non"))
                    {
                    if(JOptionPane.showConfirmDialog(null, "Voulez-vous mettre l'état à En cours ?","Traiter réclamation",
                            JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION){
                        Reclamation uneReclamation=new Reclamation(reclamationSelectionne.getLogin(), 
                                reclamationSelectionne.getDescriptionReclamation(), 
                                reclamationSelectionne.getTypeReclamation(), 
                                reclamationSelectionne.getDateReclamation(), 
                                "Oui", 
                                "Non");
                        reclamationCRUD.modiferReclamation(uneReclamation);
                        Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                        reclamationTableView.getItems().remove(selectedIndex);
                        reclamationTableView.getItems().add(selectedIndex, uneReclamation);
                        }
                        });
                        
                    }
                    }
                }
                
            }    
        });
        
    }

    private String mailObtainer(String login) {
        CompteCRUD compteCRUD=new CompteCRUD();
        List<Compte> listeCompte=new ArrayList();
        listeCompte=compteCRUD.comptesList();
        String obtainedMail = null;
        int i=0;
        boolean test=true;
        while (i<listeCompte.size() && test)
        {
            if (listeCompte.get(i).getNomDutilisateur()!= null && listeCompte.get(i).getNomDutilisateur()!=null)
            {
                if (listeCompte.get(i).getNomDutilisateur().equals(login))
                {
                obtainedMail=listeCompte.get(i).getAdresseMail();
                test=false;
                }
            }
            
            i++;
        }
        
        return obtainedMail;
    }




    
    
}
