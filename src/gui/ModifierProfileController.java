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
import java.net.URL;
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
public class ModifierProfileController implements Initializable {

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
    private Button photo;
    private Rating rating;
    @FXML
    private ImageView imageuser3;

    final FileChooser fileChooser = new FileChooser();
    String imagepath = "";
    String imageViewpath = "";
    int ratingNumber = 0;
    int idCoatch;
    int idCompte;
    String cat;
       @FXML
    private Label name1;

    public void setNom(String nom) {
        this.nom.setText(nom);
    }

    public void setDescription(String description) {
        this.Description.setText(description);
    }

    public void setDetail(String detail) {
        this.Detail.setText(detail);
    }

    public void setCategorie(String categorie) {
        this.cat = categorie;
    }

    public void setRating(int rating) {
        this.ratingNumber = rating;
    }

    public void setIdCoach(int id) {
        this.idCoatch = id;
    }
    
    public void setIdCompte(int id) {
        this.idCompte = id;
    }

    public void setimageuser1path(String path) {
        this.imagepath = path;
        this.imageViewpath = path;
        Image image = new Image(imageViewpath);
        imageuser3.setImage(image);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          EncapsulationComtpe ec=new EncapsulationComtpe();
         name1.setText(ec.getNom());
         
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
    private void OpenPhoto(ActionEvent event) {
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file.toURI().toString());
            String location = (file.getAbsoluteFile().toURI().toString());
            imagepath = file.getPath();
            imageViewpath = file.toURI().toString();
            Image image = new Image(file.toURI().toString());
            imageuser3.setImage(image);
            //   imageView = new ImageView(location);
            // imageView=new ImageView ("file:"+imagepath);
        }

    }

    @FXML
    private void modifierprofile(ActionEvent event) throws IOException {
       
          if(!"".equals(imagepath) && !"".equals(Description.getText()) && String.valueOf(categorie.getValue())!="null" && !"".equals(Detail.getText()) ){
              ProfileService sp = new ProfileService();
        sp.modifier(new Profile(imagepath, Description.getText(), ratingNumber, String.valueOf(categorie.getValue()), Detail.getText(), nom.getText(),idCompte, idCoatch));
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
        updateMessage("Profile modifier !");
        return null;
    }

};

// display message changes as notifications
        task.messageProperty().addListener((observable, oldMessage, newMessage) ->
        Notifications.create().title("Notification").text(newMessage).darkStyle().show());

// execute long running task on background thread
        new Thread(task).start();
          }else{
              JOptionPane.showMessageDialog(null, "Saisir tous les données");

          }
        

    }
    
    @FXML
    private void capture(MouseEvent event) throws IOException {
        Random rand = new Random();

// Obtain a number between [0 - 49].
        int n = rand.nextInt(100000);
        
        
        String nom1=nom.getText().toString();
        Webcam webcam = Webcam.getDefault();
        boolean open = webcam.open();
        ImageIO.write(webcam.getImage(), "PNG", new File("E:/web&mobile/webCoachini/public/ProfileImages/"+nom1+n+".png"));
         webcam.close();
        String imagePath = "file:/E:"+nom1+n+".png";
        Image image = new Image(imagePath);
        imageuser3.setImage(image);
        imagepath=imagePath;
        imageViewpath=imagePath;
  
    }
}
