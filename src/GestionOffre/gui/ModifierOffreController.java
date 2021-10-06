/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionOffre.gui;

import GestionCompte.entities.EncapsulationComtpe;
import GestionOffre.models.Offre;
import GestionOffre.services.OffreService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ModifierOffreController implements Initializable {

    @FXML
    private TextField titre;
    @FXML
    private DatePicker date;
    @FXML
    private TextField description;
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_annuler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void back(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AfficherOffre.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void Modifoffre(ActionEvent event) {

        OffreService sp = new OffreService();
        LocalDate value1 = date.getValue();
        if (titre.getText().length() == 0) {
            titre.setStyle("-fx-border-color: red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(titre).play();
        } else {
            titre.setStyle(null);
        }

        if (date.getValue() == null) {
            date.setStyle("-fx-border-color: red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(date).play();
        } else {
            date.setStyle(null);
        }
        if (description.getText().length() == 0) {
            description.setStyle("-fx-border-color: red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(description).play();
        } else {
            description.setStyle(null);
        }
        if (titre.getText().trim().isEmpty() || date.getValue() == null || description.getText().trim().isEmpty()) {
            Image img = new Image("images/canc.png");
            Notifications notificationBuilder = Notifications.create()
                    .title("Ajout")
                    .text("l'attribut ne doit pas etre vide ")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);

            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {
            AfficherOffreController e = new AfficherOffreController();
            ChoisirCategorieController cr = new ChoisirCategorieController();
            EncapsulationComtpe en = new EncapsulationComtpe();
            System.out.println(new Offre(Integer.parseInt(e.getS()), titre.getText(), Date.valueOf(value1), description.getText(), Integer.valueOf(en.getId())));
            sp.modifier(new Offre(Integer.parseInt(e.getS()), titre.getText(), Date.valueOf(value1), description.getText(), Integer.valueOf(en.getId()),Integer.valueOf(cr.getId())));

            Image img = new Image("images/images.png");
            Notifications notificationBuilder = Notifications.create()
                    .title("Modifier offre")
                    .text("offre modifiée")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);

            notificationBuilder.darkStyle();
            notificationBuilder.show();

        }
    }

    @FXML
    private void Annuler_offre(ActionEvent event) {
        Stage stage = (Stage) btn_annuler.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void SetTextField1(String a) {
        this.titre.setText(a);
    }

    public void SetTextField2(String a) {
        this.description.setText(a);
    }

    public void DatePicker1(LocalDate d) {
        this.date.setValue(d);
    }
}
