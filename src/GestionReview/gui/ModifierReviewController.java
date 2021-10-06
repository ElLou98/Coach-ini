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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Adem
 */
public class ModifierReviewController implements Initializable {

    @FXML
    private Label current_cl_modifer_name;
    @FXML
    private Rating current_cl_modifer_rating;
    @FXML
    private JFXTextField current_cl_review;
    @FXML
    private JFXButton btn_current_cl_modifer;
    @FXML
    private JFXButton btnCancel_current_cl_modifer;
    
                EncapsulationComtpe ec = new EncapsulationComtpe();
        String nomclient = ec.getNomDutilisateur();
        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        current_cl_modifer_name.setText(nomclient);
        // TODO
    }    

    @FXML
    private void current_cl_modifer_text(ActionEvent event) {
    }

    @FXML
    private void Modifier_review_client(ActionEvent event) {

        if (current_cl_review.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a new Review! ");
            } else {
                ReviewCrud rc = new ReviewCrud();
                rc.modiferReviewClient2(new Review(current_cl_review.getText(), current_cl_modifer_name.getText(), 
                        (float)current_cl_modifer_rating.getRating()));
                JOptionPane.showMessageDialog(null, "Your Review have been successfully Modified! ");
                Stage stage = (Stage) btnCancel_current_cl_modifer.getScene().getWindow();
                stage.close();
//                AfficherReview2Controller arc = new AfficherReview2Controller();
//                arc.showtable();
                

            }
    }

    @FXML
    private void Cancel_current_cl_modifer(ActionEvent event) {
        Stage stage = (Stage) btnCancel_current_cl_modifer.getScene().getWindow();
        stage.close();
    }
    
    
    

    public void setCurrent_cl_modifer_name(String current_cl_modifer_name) {
        this.current_cl_modifer_name.setText(current_cl_modifer_name);
    }

    public void setCurrent_cl_modifer_rating(float current_cl_modifer_rating) {
        this.current_cl_modifer_rating.setRating(current_cl_modifer_rating);
    }

    public void setCurrent_cl_review(String current_cl_review) {
        this.current_cl_review.setText(current_cl_review);
    }
    
    
    
}
