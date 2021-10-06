/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.GUIAccueilClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author kagha
 */
public class AccueilClientController implements Initializable {

    @FXML
    private GridPane root;
    @FXML
    private BorderPane border;
    static BorderPane bb;

    public  void setBorder(BorderPane border) {
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

    }

    @FXML
    private void TrouverClient(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficher_Event_Client.fxml"));
        Pane root = loader.load();
        border.setCenter(root);
        bb=border;
    }

}
