/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagerieEtAmi.gui;

import Messagerie.clientChat.UserStatusListener;
import GestionAmis.entite.Amis;
import GestionAmis.services.AmisCRUD;
import GestionCompte.entities.Compte;
import GestionCompte.entities.EncapsulationComtpe;
import GestionCompte.services.CompteCRUD;
import Messagerie.clientChat.ChatClient;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class MessagerieEtAmiClientController implements Initializable, UserStatusListener {

    @FXML
    private TextField textFieldRecherche;
    @FXML
    private ListView<String> listViewRecherche;
    @FXML
    private ListView<String> listViewMesAmis;
    @FXML
    private ListView<String> listViewEnAttente;
    private List<String> usersList;
    private List<String> usersSearchList;
    private List<String> friendsList;
    private List<String> enAttenteList;
    private List<Amis> amisList;
    @FXML
    private ListView<String> listViewEnvoyee;
    
    private ChatClient client;
    private String[] users;
    private ArrayList<String> usersListConnected;
    private String login;
    private int indexLogin;
    Stage stage;
    private String myLogin;
    private String myPassword="NoAccessNeeded";

    
    @FXML
    private ListView<String> usersListView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EncapsulationComtpe ec= new EncapsulationComtpe();
        myLogin=ec.getNomDutilisateur();
        
        usersList=new LinkedList<>();
        usersSearchList=new LinkedList<>();
        friendsList=new LinkedList<>();
        enAttenteList=new LinkedList<>();
        amisList=new LinkedList<>();
        AmisCRUD amiCRUD= new AmisCRUD();
        amisList=amiCRUD.consulterAmis();
        
        CompteCRUD compteCRUD=new CompteCRUD();
        List<Compte> listeCompte=new ArrayList();
        listeCompte=compteCRUD.comptesList();
        for (int k=0;k<listeCompte.size();k++)
        {
            if (listeCompte.get(k).getNomDutilisateur()!= null)
            {
                if (!listeCompte.get(k).getNomDutilisateur().equals(myLogin))
                {
                    usersList.add(listeCompte.get(k).getNomDutilisateur());
                }
            }
            
        }
        
        
        
        for (String tempUser : usersList)
        {
            boolean isMyFriend=false;
            int i=0;
            while(i<amisList.size() && !isMyFriend)
            {
                Amis tempAmi=amisList.get(i);
                String tempDestinataire=tempAmi.getDestinataire();
                String tempExpediteur=tempAmi.getExpediteur();
                int tempEtat=tempAmi.getEtatDemande();
                
                if ((tempExpediteur.equals(myLogin)&&
                        tempDestinataire.equals(tempUser))||
                                (tempExpediteur.equals(tempUser)&&
                                tempDestinataire.equals(myLogin)))
                {
                    isMyFriend=true;
                }
                i++;
                                
            }
            
            if (!isMyFriend)
            {
                Platform.runLater(new Runnable() {
                @Override
                public void run() {
                listViewRecherche.getItems().add(tempUser);
                }
                });
                usersSearchList.add(tempUser);
            }
            
        }
        
        for (Amis tempAmi : amisList)
        {
            if (tempAmi.getExpediteur().equals(myLogin)&&tempAmi.getEtatDemande()==1)
            {
                Platform.runLater(new Runnable() {
                @Override
                public void run() {
                listViewMesAmis.getItems().add(tempAmi.getDestinataire());
                friendsList.add(tempAmi.getDestinataire());
                }
                });
                
            }
            else
            {
                if (tempAmi.getDestinataire().equals(myLogin)&&tempAmi.getEtatDemande()==1)
                {
                    Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                    listViewMesAmis.getItems().add(tempAmi.getExpediteur());
                    friendsList.add(tempAmi.getExpediteur());
                    }
                    });
                }
                else
                {
                    if(tempAmi.getDestinataire().equals(myLogin)&&tempAmi.getEtatDemande()==0)
                    {
                        Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                        listViewEnAttente.getItems().add(tempAmi.getExpediteur());
                        }
                        });
                    }
                    else 
                    {
                        if(tempAmi.getExpediteur().equals(myLogin)&&tempAmi.getEtatDemande()==0)
                        {
                            Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                            listViewEnvoyee.getItems().add(tempAmi.getDestinataire());
                            }
                            });
                        }
                    }
                }
            }
        }
        
        listViewRecherche.setOnMouseClicked((javafx.scene.input.MouseEvent event) ->{
            if (event.getClickCount()==2){
                String userSelectionne=listViewRecherche.getSelectionModel().getSelectedItem();
                int indexSelectionne=listViewRecherche.getSelectionModel().getSelectedIndex();
                if (JOptionPane.showConfirmDialog(null, "Voulez-vous ajouter cette personne en ami(e) ?", "Ajouter Ami(e)",
                   JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                  Amis ami=new Amis(userSelectionne,myLogin);
                  amiCRUD.ajouterDemandeAmis(ami);
                  Platform.runLater(new Runnable() {
                  @Override
                  public void run() {
                  listViewRecherche.getItems().remove(indexSelectionne);
                  listViewEnvoyee.getItems().add(userSelectionne);
                  usersSearchList.remove(rechercheIndexListe(usersSearchList,userSelectionne));
                  }
                  }); 
                  javaMailUtil.sendMail("yahyaouilouay0@gmail.com",myLogin,userSelectionne);
                } else {
                 // no option
                }
            }    
        });
        
        
        listViewEnAttente.setOnMouseClicked((javafx.scene.input.MouseEvent event) ->{
            if (event.getClickCount()==2){
                String userSelectionne=listViewEnAttente.getSelectionModel().getSelectedItem();
                int indexSelectionne=listViewEnAttente.getSelectionModel().getSelectedIndex();
                if (JOptionPane.showConfirmDialog(null, "Voulez-vous accepter cette personne en ami(e) ?", "Accepter Ami(e)",
                   JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                  Amis ami=new Amis(myLogin,userSelectionne);
                  amiCRUD.accepterDemandeAmis(ami);
                  Platform.runLater(new Runnable() {
                  @Override
                  public void run() {
                  listViewEnAttente.getItems().remove(indexSelectionne);
                  listViewMesAmis.getItems().add(userSelectionne);
                  friendsList.add(userSelectionne);
                  }
                  });
                  
                } else {
                 // no option
                }
            }    
        });
        
        listViewMesAmis.setOnMouseClicked((javafx.scene.input.MouseEvent event) ->{
            if (event.getClickCount()==2){
                String userSelectionne=listViewMesAmis.getSelectionModel().getSelectedItem();
                int indexSelectionne=listViewMesAmis.getSelectionModel().getSelectedIndex();
                if (JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer cette personne de votre liste d'ami(e) ?", "Supprimer Ami(e)",
                   JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                  Amis ami=new Amis(myLogin,userSelectionne);
                  Amis ami1=new Amis(userSelectionne,myLogin);
                  amiCRUD.supprimerAmis(ami);
                  amiCRUD.supprimerAmis(ami1);
                  int indexFriendsList=rechercheIndexListe(friendsList, userSelectionne);
                  friendsList.remove(indexFriendsList);
                  Platform.runLater(new Runnable() {
                  @Override
                  public void run() {
                  listViewMesAmis.getItems().remove(indexSelectionne);
                  usersSearchList.add(userSelectionne);
                  listViewRecherche.getItems().add(userSelectionne);
                  }
                  }); 
                } else {
                 // no option
                }
            }    
        });
        
        listViewEnvoyee.setOnMouseClicked((javafx.scene.input.MouseEvent event) ->{
            if (event.getClickCount()==2){
                String userSelectionne=listViewEnvoyee.getSelectionModel().getSelectedItem();
                int indexSelectionne=listViewEnvoyee.getSelectionModel().getSelectedIndex();
                if (JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer cette demande d'ami(e) ?", "Supprimer demande d'ami(e)",
                   JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                  Amis ami=new Amis(userSelectionne,myLogin);
                  amiCRUD.supprimerAmis(ami);
                  Platform.runLater(new Runnable() {
                  @Override
                  public void run() {
                  listViewEnvoyee.getItems().remove(indexSelectionne);
                  usersSearchList.add(userSelectionne);
                  listViewRecherche.getItems().add(userSelectionne);
                  }
                  }); 
                } else {
                 // no option
                }
            }    
        });
        
// ____________________________________________________________________________
      ChatClient clientULPC;
      clientULPC = new ChatClient("localhost",8818);
      if(clientULPC.connect()){
            try {
                clientULPC.login(myLogin,myPassword);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            }
      ec.setClient(clientULPC);
      this.client=clientULPC;
          this.client.addUserStatusListener(this);     
      usersListConnected= new ArrayList<>(); 
           
        
        
        usersListView.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
            if (event.getClickCount()==2){
                login=usersListView.getSelectionModel().getSelectedItem();
                indexLogin=usersListView.getSelectionModel().getSelectedIndex();
                EncapsulationUserListMessage eulm=new EncapsulationUserListMessage(login,clientULPC,myLogin);
                stage= new Stage();
                FXMLLoader loader= new FXMLLoader(getClass().getResource("MessagePane.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Message "+login);
                    stage.setScene(scene);
                    stage.show();
//                    stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
                } catch (IOException ex) {
                }
                
                
            }
      });
        
    }    

    @FXML
    private void rechercherListView(KeyEvent event) {
        ObservableList<String> usersObservableList = FXCollections.observableArrayList(usersSearchList);
        FilteredList<String> filteredData = new FilteredList<>(usersObservableList, b -> true);
        textFieldRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(String -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.toLowerCase().indexOf(lowerCaseFilter) != -1) {

                    return true; // Filter matches titre
                } else {
                    return false; // Does not match.
                }

            });
        });
        
         SortedList<String> sortedData = new SortedList<>(filteredData);
         ObservableList<String> observableList = FXCollections.observableList((List) sortedData); 
         Platform.runLater(new Runnable() {
         @Override
         public void run() {
         listViewRecherche.setItems(observableList);
         }
         });
        
    }

    private int rechercheIndexListe(List<String> randomList, String randomUser) {
        int j=-1;
        boolean found=false;
        while (j<randomList.size() && !found)
        {
            j++;
            if (randomList.get(j).equals(randomUser))
                    {
                        found=true;
                    }   
        }
        
        return j;
    }

    
    @Override
    public void online(String login) {
        try {
            sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessagerieEtAmiClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean isMyFriend=false;
        int i=0;
        while (i<friendsList.size() && !isMyFriend)
        {
            if (login.equals(friendsList.get(i)))
            {
                isMyFriend=true;;
            }
            i++;
        }
        if (isMyFriend && alreadyConnected(login))
        {
            usersListConnected.add(login);
            Platform.runLater(new Runnable() {
            @Override
            public void run() {
            usersListView.getItems().add(login);
            }
            });
        }
       
    }

    @Override
    public void offline(String login) {
        for(int i=0;i<usersListConnected.size();i++)
        {
            if(usersListConnected.get(i).equals(login))
            {
                usersListConnected.remove(i);
                Platform.runLater(new Runnable() {
                @Override
                public void run() {
                usersListView.getItems().remove(login);
                }
                });
                
            }
        }
    }

    private boolean alreadyConnected(String login) {
        boolean isConnected = true;
        int i=0;
        while (i<usersListConnected.size() && isConnected)
        {
            if (usersListConnected.get(i).equals(login))
            {
                isConnected=false;
            }
            i++;
        }
        
        return isConnected;
    }

    
}
