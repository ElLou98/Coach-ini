/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.GUICategorieAdmin;

import static GestionEvent.GUICategorieAdmin.AfficherCategorieController.getIt;
import GestionEvent.Models.Categorie_Event;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import rest.file.uploader.tn.FileUploader;
import GestionEvent.Services.Service_Categorie_Event;

/**
 * FXML Controller class
 *
 * @author kagha
 */
public class AjouterCategorieController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextArea description;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_valider;
    @FXML
    private TextField path;
    @FXML
    private ImageView image;
    private String bpath;

    public void setTfNom(String tfNom) {
        this.tfNom.setText(tfNom);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setPath(String path) {
        this.path.setText(path);
    }

    public void setImage(Image image) {
        this.image.setImage(image);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setImage(null);

    }

    @FXML
    public void AddPicture(ActionEvent event) throws ProtocolException, IOException {

        Stage stage = (Stage) description.getScene().getWindow();
        final FileChooser fcho = new FileChooser();
        fcho.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG File", "*.png", "JPG File", "*.jpg", "BMP File", "*.bmp"));
        File file = fcho.showOpenDialog(stage);
        if (file != null) {
            try {
                //System.out.println("Path"+file.getAbsolutePath());
                path.setText(file.getAbsolutePath());
                Image image = new Image(file.toURI().toString());
                String format = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
                String name = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("/") + 1, file.getAbsolutePath().lastIndexOf("."));
                //System.out.println(format);
                //System.out.println(name);
                BufferedImage img = null;
                img = ImageIO.read(file);
                //File outputfile = new File("/home/kagha/Desktop/"+name+"."+format);
                //ImageIO.write(img,format, outputfile);
                FileUploader fu = new FileUploader("http://localhost/coachini/");
                String fileNameInServer = fu.upload(file.getAbsolutePath());
                bpath = fileNameInServer.substring(fileNameInServer.indexOf("f"));
                //System.out.println(file.getAbsolutePath());
                //System.out.println(fileNameInServer.substring(fileNameInServer.indexOf("f")));

                this.image.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(AjouterCategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void End_cate(ActionEvent event) {
    }

    @FXML
    public void Ajouter_cate(ActionEvent event) {

        if (tfNom.getText().trim().isEmpty() || description.getText().trim().isEmpty() || path.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Problem dans l'insertion ");
            alert.setHeaderText("Erreur");
            alert.setContentText("Attribue doit être  ni nulle ni vide \n");
            alert.showAndWait();
            return;

        }

        Service_Categorie_Event sc = new Service_Categorie_Event();
        sc.ajouter(new Categorie_Event(tfNom.getText(), description.getText(), path.getText(), "localhost/coachini/uploads/" + bpath));
        JOptionPane.showMessageDialog(null, "Catégorie ajoutée !");
        tfNom.clear();
        description.clear();
        path.clear();
        setImage(null);

    }

    public void Modifier_cate(ActionEvent event) throws IOException {
        Service_Categorie_Event sc = new Service_Categorie_Event();
        AfficherCategorieController e = new AfficherCategorieController();
        FileUploader fu = new FileUploader("http://localhost/coachini/");
        String Db_picture = sc.get_Db_picture(sc.get_ID(e.getIt()));
        if (fu.delete(Db_picture.substring(Db_picture.indexOf("f")))) {
            //System.out.println("File " + Db_picture + " deleted.");
        }
        sc.modifier1(new Categorie_Event(sc.get_ID(e.getIt()), tfNom.getText(), description.getText(), path.getText(), "localhost/coachini/uploads/" + bpath));
        JOptionPane.showMessageDialog(null, "Catégorie Modifiée!");

    }

}

