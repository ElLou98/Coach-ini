/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import GestionCompte.entities.EncapsulationComtpe;
import GestionProfile.entities.Profile;
import GestionProfile.services.ProfileService;
import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Espace Info
 */
public class AjoutProfileController implements Initializable {

    @FXML
    private AnchorPane detail;
    final Button openButton = new Button("photo");
    final FileChooser fileChooser = new FileChooser();
    String imagepath = "";
    String imageViewpath = "";
    int ratingNumber = 0;
    @FXML
    private Button btn;
    @FXML
    private TextArea Description;
    @FXML
    private TextField nom;
    @FXML
    private ChoiceBox categorie;
    @FXML
    private TextArea Detail;
    @FXML
    private ImageView imageuser;

    private Webcam webcam;
    int idCoatch;
    @FXML
    private Button photo;
    @FXML
    private Label name1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        EncapsulationComtpe ec = new EncapsulationComtpe();
        name1.setText(ec.getNom() + " " + ec.getPrenom());

        List<String> sp = new ArrayList<String>();
        sp.add("Musculation");
        sp.add("Squat");
        sp.add("Yoga");
        sp.add("Elliptique");
        sp.add("Strength Training");

        ObservableList<String> list = FXCollections.observableArrayList(sp);
        categorie.setItems(list);

    }
    @FXML

  private void OpenPhoto(ActionEvent event) throws MalformedURLException, IOException {
        File file = fileChooser.showOpenDialog(null);
        
        if (file != null) {
             String location = (file.getAbsoluteFile().toURI().toString());
           
            File dest = new File("E:\\web&mobile\\webCoachini\\public\\ProfileImages\\"+file.getName());
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
          
             imagepath = file.getName();
             imageViewpath = file.getName();
             Image image = new Image("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+file.getName());
             imageuser.setImage(image);

          
           // imageView=new ImageView ("file:"+imagepath);
        }

    }

      @FXML
    private void capture(MouseEvent event) throws IOException {
           Random rand = new Random();
EncapsulationComtpe ec=new EncapsulationComtpe();
         name1.setText(ec.getNom()+" "+ec.getPrenom());
// Obtain a number between [0 - 49].
        int n = rand.nextInt(100000);
        String nom1=nom.getText();
        Webcam webcam = Webcam.getDefault();
        boolean open = webcam.open();
        ImageIO.write(webcam.getImage(), "PNG", new File("E:\\web&mobile\\webCoachini\\public\\ProfileImages\\"+ec.getNom()+""+ec.getPrenom()+n+".png"));
         webcam.close();
        String imagePath = ec.getNom()+""+ec.getPrenom()+n+".png";
           
        Image image = new Image("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+imagePath);
         imageuser.setImage(image);
        imagepath=imagePath;
        imageViewpath=imagePath;
  
    }

    @FXML
    private void AjouterProfile(ActionEvent event) throws IOException {
        ProfileService sp = new ProfileService();
        EncapsulationComtpe ec = new EncapsulationComtpe();

        if (!"".equals(imagepath) && !"".equals(Description.getText()) && String.valueOf(categorie.getValue()) != "null" && !"".equals(Detail.getText())) {
            Profile p =new Profile(imagepath, Description.getText(), ratingNumber, String.valueOf(categorie.getValue()), Detail.getText(), ec.getNom(), ec.getId());
            System.out.println(p);
            idCoatch = sp.ajouter(new Profile(imagepath, Description.getText(), ratingNumber, String.valueOf(categorie.getValue()), Detail.getText(), ec.getNom(), ec.getId()));
           //System.out.println("coach"+idCoatch);
            //JOptionPane.showMessageDialog(null, "Profile ajouter !");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutActualite.fxml"));
            Parent root = loader.load();

            nom.getScene().setRoot(root);
            AjoutActualiteController dcp = loader.getController();
            dcp.setDescription(Description.getText());
            dcp.setCategorie(categorie.getValue().toString());
            dcp.setDetail(Detail.getText());
            dcp.setRating(ratingNumber);
            dcp.setimageuser1path(imageViewpath);
            dcp.setIdCoach(idCoatch);

            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    // update message property
                    updateMessage("Profile ajouter !");
                    return null;
                }

            };

// display message changes as notifications
            task.messageProperty().addListener((observable, oldMessage, newMessage)
                    -> Notifications.create().title("Notification").text(newMessage).darkStyle().show());

// execute long running task on background thread
            new Thread(task).start();
        } else {
            JOptionPane.showMessageDialog(null, "Saisir tous les données");
        }

    }

    @FXML
    private void back(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccueilCoach.fxml"));
        Parent root = loader.load();

        nom.getScene().setRoot(root);
        AccueilCoachController dcp = loader.getController();
    }

    private void AjoutAct(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutActualite.fxml"));
        Parent root = loader.load();

        nom.getScene().setRoot(root);

    }
}
