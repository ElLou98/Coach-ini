/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.GUICategorieAdmin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kagha
 */
public class AccueilAdminController implements Initializable {

    @FXML
    private ImageView logo_view;
    @FXML
    private ComboBox<?> gestionCompteClientComboBox;
    @FXML
    private ComboBox<?> gestionCompteClientComboBox1;
    @FXML
    private ComboBox<?> gestionCompteClientComboBox2;
    @FXML
    private ComboBox<?> gestionCompteClientComboBox3;
    @FXML
    private ComboBox GestCatEv;
    @FXML
    private Label id;
    @FXML
    private ComboBox GestCat;
    @FXML
    private BorderPane border;
    ObservableList<String> optionsEvent1 = FXCollections.observableArrayList("Ajouter", "Traiter");
    ObservableList<String> optionsEvent2 = FXCollections.observableArrayList("Traiter");
    static BorderPane bb;

    public void setBorder(BorderPane border) {
        this.border = border;
    }

    public BorderPane getBorder() {
        return border;
    }

    public static BorderPane getBb() {
        return bb;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        GestCatEv.getItems().addAll(optionsEvent1);
        GestCat.getItems().addAll(optionsEvent2);
    }

    @FXML
    private void Gest_Event(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUIAccueilAdmin/AfficherEventAdmin.fxml"));
        Pane root = loader.load();
        border.setCenter(root);
        border.setLayoutX(400);
        border.setLayoutY(60);
        bb = border;

    }

    @FXML
    private void Gest_Cat(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterCategorie.fxml"));
        Pane root = loader.load();
        border.setLayoutX(450);
        border.setLayoutY(80);
        border.setCenter(root);

    }

    @FXML
    private void Trai_cat(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml"));
        Pane root = loader.load();
        border.setLayoutX(400);
        border.setLayoutY(60);
        border.setCenter(root);
        bb = border;
    }

}
