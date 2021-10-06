/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReview.gui;

import GestionCompte.entities.EncapsulationComtpe;
import GestionReview.entities.Review;
import GestionReview.services.ReviewCrud;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Adem
 */
public class AjouterReviewController implements Initializable {

    @FXML
    private Label current_client;
    @FXML
    private Rating coach_rating;
    @FXML
    private JFXTextField current_client_review;
    @FXML
    private JFXButton btn_current_client;
    @FXML
    private ComboBox Combo_current_cl_coach;
    @FXML
    private JFXButton btn_cancel_current_clt;
    
            EncapsulationComtpe ec = new EncapsulationComtpe();
        String nomclient = ec.getNomDutilisateur();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        RequiredFieldValidator validator = new RequiredFieldValidator();
//        current_client_review.getValidators().add(validator);
//        validator.setMessage("Please enter a review!");
//        current_client_review.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                if (!newValue) {
//                    current_client_review.validate();
//                }
//            }
//        });
        current_client.setText(nomclient);
        List<String> myList = new ArrayList<>();
        myList.add("ademCoach");
        myList.add("elyes");
        myList.add("ghassen");
        myList.add("doniaCoach");
        myList.add("louay");
        myList.add("hedi");
        myList.add("mohamed el hedii");
        myList.add("medhedi ben");
        myList.add("2");

        Combo_current_cl_coach.getItems().addAll(myList.get(0), myList.get(1), 
                myList.get(2), myList.get(3), myList.get(4), myList.get(5)
        ,myList.get(6) , myList.get(7), myList.get(8));
        //.getItems().addAll(myList.get(0));

    }

    @FXML
    public void current_client_review_text(ActionEvent event) {
    }

    @FXML
    public void Ajouter_review_current_client(ActionEvent event) {
        Stage stage = (Stage) btn_current_client.getScene().getWindow();
        if (Combo_current_cl_coach.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Please select your Coach! ");

        } else {
            if (current_client_review.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, " Please enter a Review ");

            } else {
                try {
                ReviewCrud rc = new ReviewCrud();   
                rc.ajouterReviewClient(new Review(current_client_review.getText(), current_client.getText(),
                        Combo_current_cl_coach.getValue().toString(), (float) coach_rating.getRating()));
        // en combo box lezem irecupérie el nom du coach eli dakhalou el client ! par exemple ena dekhalt li coach ismou hedi donc le nom du coach est auto hedi
                JOptionPane.showMessageDialog(null, " Your Review have been successfully added! ");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AfficherReview2.fxml"));
                //try {
                    Parent root = loader.load();
                    AfficherReview2Controller arc = loader.getController();
                    arc.reviewtable.refresh();
                } catch (IOException ex) {
                    Logger.getLogger(AjouterReviewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage.close();

            }
        }

    }

    @FXML
    public void btn_Cancel_add_review(ActionEvent event) {
        Stage stage = (Stage) btn_cancel_current_clt.getScene().getWindow();
        stage.close();
    }

}
