/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCategorieSport.gui;

import GestionCategorieSport.models.CategorieSport;
import GestionCategorieSport.services.CategorieService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AjoutCategorieController implements Initializable {
  final Button openButton = new Button("photo");
    final FileChooser fileChooser = new FileChooser();
    String imagepath = "";
    String imageName = "";

    @FXML
    private TextField tfNom;

    @FXML
    private TextArea tfDescription;
    String imageViewpath = "";
    @FXML
    private Button IDajout;
    int id;
    @FXML
    private Button btn_annuler;

    @FXML
    private ImageView imgview;
    @FXML
    private Button photo;
    @FXML
    private Button back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        filechooser.setInitialDirectory(new File("C:\\Users\\asus\\Desktop\\photo"));
    }

    @FXML
    private void OpenPhoto(ActionEvent event) throws MalformedURLException, IOException {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println(file.toURI().toString());
            String location = (file.getAbsoluteFile().toURI().toString());
            imagepath = file.getPath();
            imageName = file.getName();
            imageViewpath = file.toURI().toString();
            Image image = new Image(file.toURI().toString());
            imgview.setImage(image);
            try {
                System.out.println(file.toPath());
                System.out.println(Paths.get(System.getProperty("user.dir")));
                String s = System.getProperty("user.dir") + "\\src\\uploads\\" + file.getName();
                Files.copy(file.toPath(), Paths.get(s), REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("error");

                System.out.println(e.getMessage());
            }

        }

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
    
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void Ajouter(ActionEvent event) throws ParseException {
        CategorieService cp = new CategorieService();

        if (tfNom.getText().length() == 0) {
            tfNom.setStyle("-fx-border-color: red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(tfNom).play();
        } else {
            tfNom.setStyle(null);
        }

        if (tfDescription.getText().length() == 0) {
            tfDescription.setStyle("-fx-border-color: red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(tfDescription).play();
        } else {
            tfDescription.setStyle(null);
        }

        String s = "";
        if (tfNom.getText().trim().isEmpty() || tfDescription.getText().trim().isEmpty()) {
            Image img = new Image("images/canc.png");
            Notifications notificationBuilder = Notifications.create()
                    .title("Notification")
                    .text("l'attribut ne doit pas etre vide ")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);

            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {
            cp.ajouter(new CategorieSport(tfNom.getText(), tfDescription.getText(), imageName));

            Image img = new Image("images/images.png");//*../../../uploads/mel BD
            Notifications notificationBuilder = Notifications.create()
                    .title("Ajout categorie")
                    .text("categorie ajoutée")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);

            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
    }

    @FXML
    private void AnnulerCateg(ActionEvent event) {
        Stage stage = (Stage) btn_annuler.getScene().getWindow();

        stage.close();
    }

    public void setimagepath(String path) {

        this.imageViewpath = path;
        Image image = new Image(imageViewpath);
        imgview.setImage(image);
    }

    public void SetTextField1(String a) {
        this.tfNom.setText(a);
    }

    public void SetTextField2(String a) {
        this.tfDescription.setText(a);
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
