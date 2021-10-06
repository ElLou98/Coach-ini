/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import GestionCompte.entities.EncapsulationComtpe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Adem
 */
public class ClientviewProfileCoachController implements Initializable {

    @FXML
    private AnchorPane detail;
    @FXML
    private TextField nom;
    @FXML
    private TextArea Description;
    @FXML
    private ChoiceBox<?> categorie;
    @FXML
    private TextArea Detail;
    @FXML
    private Button photo;
    @FXML
    private ImageView imageuser;
    @FXML
    private Rating rating;
    @FXML
    private Button btn_view_coach_rating;
    @FXML
    public Label name1;


    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

         name1.setText("ghassen");
        
        // TODO
    }    

    @FXML
    private void OpenPhoto(ActionEvent event) {
    }

    @FXML
    private void capture(MouseEvent event) {
    }

    @FXML
    private void view_coach_rating(ActionEvent event) { // ici adem faire la laision avec son travaille 
                                                        // le but c'est que quand un client click sur ce bouton l interface des review de adem se declanche 
                                                        
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ModifierReview.fxml"));
//                Parent root1 = loader.load();
//
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root1));
//                stage.initModality(Modality.APPLICATION_MODAL);                                                    
//                                                        
    }

    @FXML
    private void back(MouseEvent event) {
    }
    
}
