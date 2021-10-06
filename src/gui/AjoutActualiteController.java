/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import GestionCompte.entities.EncapsulationComtpe;
import GestionProfile.entities.Actualite;
import GestionProfile.entities.Profile;
import GestionProfile.services.ActualiteService;
import GestionProfile.services.ProfileService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Espace Info
 */
 
public class AjoutActualiteController implements Initializable {

    int id = 0;
    int idCoach = 0;
    int idCompte = 0;
    
    @FXML
    private TextArea statut;
    ;
    @FXML
    private TextArea biographie;
    @FXML
    private TextArea competence;
    @FXML
    private Button imagepub;
    final FileChooser fileChooser = new FileChooser();
    String imagepath = "";
    String imageuser1path = "";
    String fichierpath = "";
    @FXML
    private Button fichier;
    @FXML
    private Button btnactu;
    @FXML
    private Button btnsupp;
    @FXML
    private Button btnmodif;

    @FXML
    private Text nom;
    @FXML
    private Text description;
    @FXML
    private Text detail;
    @FXML
    private Text categorie;
    @FXML
    private Text rating;
    @FXML
    private ImageView imageuser1;
     @FXML
    private ImageView imageactu;
     @FXML
    private Label namefich;
     @FXML
    private Label name1;
    @FXML
    private Button btnafficheAct;

    /**
     * Initializes the control    @FXML
    private Label name1;
ler class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   EncapsulationComtpe ec=new EncapsulationComtpe();
         name1.setText(ec.getNom()+" "+ec.getPrenom());
       
    }

    
    public void setNom(String nom) {
        this.nom.setText(nom);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setDetail(String detail) {
        this.detail.setText(detail);
    }

    public void setCategorie(String categorie) {
        this.categorie.setText(categorie);
    }

    public void setRating(int rating) {
        this.rating.setText(String.valueOf(rating));
    }

    public void setIdCoach(int id) {
        this.idCoach = id;
    }
    
    public void setIdCompte(int id) {
        this.idCompte = id;
    }

    public void setimageuser1path(String path) {

        this.imageuser1path = path;
        Image image = new Image("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+path);
        imageuser1.setImage(image);
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
    return java.sql.Date.valueOf(dateToConvert);
    }

   @FXML
    private void AjouterActualite(ActionEvent event) throws IOException {
      
        LocalDate dt=LocalDate.now();
        Date date1=convertToDateViaSqlDate(dt);
        System.out.println(statut.getText());
        System.out.println(competence.getText());
        System.out.println(biographie.getText());
        if ( "".equals(statut.getText()) && "".equals(competence.getText())&& "".equals(biographie.getText())){
                    JOptionPane.showMessageDialog(null, "saisir les donnees ");

        }else
        {
             ActualiteService sp = new ActualiteService();
        id = sp.ajouter1(new Actualite(statut.getText(), imagepath, fichierpath, competence.getText(), biographie.getText(), 0, date1));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent root = loader.load();

        nom.getScene().setRoot(root);
        ProfileController dcp = loader.getController();
        dcp.setId(id);
        dcp.setNom(nom.getText());
        dcp.setDescription(description.getText());
        dcp.setCategorie(categorie.getText());
        dcp.setDetail(detail.getText());
        dcp.setRating(rating.getText());
       dcp.setBiographie(biographie.getText());
        dcp.setImagepath(imagepath);
        dcp.setCompetence(competence.getText());
        dcp.setFichierpath(fichierpath);
        dcp.setimageuser2(imageuser1path);
        dcp.setPub(statut.getText());
           Task<Void> task = new Task<Void>() {

    @Override
    protected Void call() throws Exception {
        // update message property
        updateMessage("actualiter ajouter !");
        return null;
    }

};

// display message changes as notifications
        task.messageProperty().addListener((observable, oldMessage, newMessage) ->
        Notifications.create().title("Notification").text(newMessage).darkStyle().show());

// execute long running task on background thread
        new Thread(task).start();
        }
       
    }
     @FXML
    private void Openphoto(ActionEvent event) throws MalformedURLException {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String location = (file.getAbsoluteFile().toURI().toString());
            imagepath = file.getPath();
                 Image image = new Image(file.toURI().toString());
             imageactu.setImage(image); 
            //  imageView = new ImageView(location);
            // imageView=new ImageView ("file:"+imagepath);
        }
    }


    @FXML
    private void Openfichier(ActionEvent event) throws MalformedURLException {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String location = (file.getAbsoluteFile().toURI().toString());
            fichierpath = file.getPath();
            namefich.setText(file.getName());

            //  imageView = new ImageView(location);
            // imageView=new ImageView ("file:"+imagepath);
        }
    }
    @FXML
    private void deleteprofil(ActionEvent event) throws IOException {
        ProfileService sp = new ProfileService();
        sp.supprimer(new Profile(imagepath, description.getText(), Integer.parseInt(rating.getText()), categorie.getText(), detail.getText(), nom.getText(), idCompte ,idCoach));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutProfile.fxml"));
        Parent root = loader.load();

        nom.getScene().setRoot(root);
         Task<Void> task = new Task<Void>() {

    @Override
    protected Void call() throws Exception {
        // update message property
        updateMessage("Profile Supprimer !");
        return null;
    }

};

// display message changes as notifications
        task.messageProperty().addListener((observable, oldMessage, newMessage) ->
        Notifications.create().title("Notification").text(newMessage).darkStyle().show());

// execute long running task on background thread
        new Thread(task).start();
        }
       

   @FXML
    private void modifprofil(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierProfile.fxml"));
        Parent root = loader.load();

        nom.getScene().setRoot(root);

        ModifierProfileController dcp = loader.getController();
        dcp.setDescription(description.getText());
        dcp.setCategorie(categorie.getText());
        dcp.setDetail(detail.getText());
        dcp.setRating(Integer.parseInt(rating.getText()));
        dcp.setimageuser1path(imageuser1path);
        dcp.setIdCoach(idCoach);
        dcp.setIdCompte(idCompte);
    }

    @FXML
    private void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccueilCoach.fxml"));
        Parent root = loader.load();

        nom.getScene().setRoot(root);
    }

    @FXML
    private void afficherAct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent root = loader.load();

        nom.getScene().setRoot(root);
        
        ProfileController dcp = loader.getController();
        dcp.setId(id);
        dcp.setNom(nom.getText());
        dcp.setDescription(description.getText());
        dcp.setCategorie(categorie.getText());
        dcp.setDetail(detail.getText());
        dcp.setRating(rating.getText());
       dcp.setBiographie(biographie.getText());
        dcp.setImagepath(imagepath);
        dcp.setCompetence(competence.getText());
        dcp.setFichierpath(fichierpath);
        dcp.setimageuser2(imageuser1path);
        dcp.setPub(statut.getText());
    }
    
}

