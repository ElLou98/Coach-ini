/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReview.gui;

import GestionCompte.entities.EncapsulationComtpe;
import GestionReview.entities.Review;
import GestionReview.services.ReviewCrud;
import GestionReview.tools.ReviewConnection;
import com.jfoenix.controls.JFXButton;
import gui.ClientviewProfileCoachController;
import gui.RechercheCoachController;
import gui.SearchCoachVisitorController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WritableFloatValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Adem
 */
public class AfficherReview2Controller implements Initializable { // pour le client 
    // pour l admin copier coller w supprimer les btns ajouter et modifer kahoi ez 

    @FXML
    private JFXButton Ajouter_new_review;
    @FXML
    private JFXButton btn_supprimer_current_clt;
    @FXML
    public TableView<Review> reviewtable;
    @FXML
    private TableColumn<Review, String> nom_client;
    @FXML
    private TableColumn<Review, String> date_review;
    @FXML
    private TableColumn<Review, String> descrip_review;
    @FXML
    private TableColumn<Review, Float> rating;

    static String s;
    @FXML
    private JFXButton Modifer_new_review1;
    @FXML
    private JFXButton btn_refresh;

    @FXML
    private TextField tf_search;
    ObservableList list = FXCollections.observableArrayList();
    ObservableList<Review> dataList = FXCollections.observableArrayList();

    ObservableList<Review> obl = FXCollections.observableArrayList();
    
        
        EncapsulationComtpe ec = new EncapsulationComtpe();
        String nomclient = ec.getNomDutilisateur();

//    Review selectedReview;
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

        // sorting with date
//        date_review.setSortType(TableColumn.SortType.DESCENDING);
//        reviewtable.getSortOrder().add(date_review);
//        reviewtable.sort();
//        // sorting with Client name
//        date_review.setSortType(TableColumn.SortType.DESCENDING);
//        reviewtable.getSortOrder().add(nom_client);
//        reviewtable.sort();
//        
//        // sorting with rating
//        date_review.setSortType(TableColumn.SortType.DESCENDING);
//        reviewtable.getSortOrder().add(rating);
//        reviewtable.sort();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ClientviewProfileCoach.fxml"));
//        ClientviewProfileCoachController cvpc = new ClientviewProfileCoachController();
//        
//        cvpc.name1.setText("adem");

        showtable();
        search_review();

    }

    public void showtable() {
        nom_client.setCellValueFactory(new PropertyValueFactory<Review, String>("nom_client_review"));
        date_review.setCellValueFactory(new PropertyValueFactory<Review, String>("date_review"));
        descrip_review.setCellValueFactory(new PropertyValueFactory<Review, String>("description_review"));
        rating.setCellValueFactory(new PropertyValueFactory<Review, Float>("rating"));
        rating.setCellFactory(table -> new TableCell<Review, Float>() {

            private final Rating rating;

            private final ChangeListener<Number> ratingChangeListener;

            {
                rating = new Rating(5);// from jfoenix lib ( the stars ) 

                // listener for changes in rating
                ratingChangeListener = (observable, oldValue, newValue) -> {
                    TableColumn<Review, Float> column = getTableColumn(); // listen to column rating value changing !!! 

                    // get the property used for this column (has to be WritableDoubleProperty)
                    WritableFloatValue value = (WritableFloatValue) column.getCellValueFactory()
                            .call(new TableColumn.CellDataFeatures(getTableView(), column, getTableRow().getItem()));

                    value.set(newValue.floatValue());
                };

            }

            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);

                rating.ratingProperty().removeListener(ratingChangeListener);

                if (empty) {
                    setGraphic(null); // default no rating 
                } else {
                    rating.setRating(item.floatValue());

                    // only listen to changes done later through user interaction
                    rating.ratingProperty().addListener(ratingChangeListener);
                    setGraphic(rating);
                    rating.ratingProperty().removeListener(ratingChangeListener); // unset the user new changing ! 

                }
            }

        });
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterReview.fxml"));
//
//            Parent root = loader.load();
//            AjouterReviewController cr = loader.getController();

        try {
//            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../ClientviewProfileCoach.fxml"));
//
//            ClientviewProfileCoachController cvpc = loader2.getController();
//
//            String nomcoach = cvpc.name1.getText();
// new added integration 5ra 
            RechercheCoachController hedi = new RechercheCoachController();
            String hedinomcoach = hedi.getNomcoachadem();
            System.out.println(hedinomcoach);
            ObservableList<Review> reviewlist = ReviewCrud.getAllRecordsCoach(hedinomcoach);
            //was ObservableList<Review> reviewlist = ReviewCrud.getAllRecords();

            populateTable(reviewlist);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherReview2Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AfficherReview2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void search_review() {
        nom_client.setCellValueFactory(new PropertyValueFactory<>("nom_client_review"));
        date_review.setCellValueFactory(new PropertyValueFactory<>("date_review"));
        descrip_review.setCellValueFactory(new PropertyValueFactory<>("description_review"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        try {

            String requete = "SELECT * FROM  review_client";
            Statement st = ReviewConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                obl.add(new Review(rs.getString("description_review"), rs.getString("nom_client_review"), rs.getFloat("rating")));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        dataList = obl;
        reviewtable.setItems(dataList);
        FilteredList<Review> filteredData = new FilteredList<>(dataList, b -> true);
        tf_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(review -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (review.getNom_client_review().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
                } else if (review.getDescription_review().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (String.valueOf(review.getRating()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else {
                    return false; // Does not match.
                }
            });
        });

        reviewtable.setItems(filteredData);

    }

    @FXML
    private void Ajouter_new_review(ActionEvent event) throws ClassNotFoundException, SQLException { // disable le button ajouter ici car l admin ne peut pas ajouter une review 
        //Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterReview.fxml"));

            Parent root = loader.load();
            AjouterReviewController cr = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

//            ObservableList<Review> reviewlist;
//            reviewlist = ReviewCrud.getAllRecords();
//            System.out.println(reviewlist);
//            reviewtable.refresh();
            stage.showAndWait();
            showtable();

//            try {
//                reviewlist = ReviewCrud.getAllRecords();
//                reviewtable.refresh();
//                
//                
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(AfficherReview2Controller.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SQLException ex) {
//                Logger.getLogger(AfficherReview2Controller.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (IOException ex) {
            Logger.getLogger(AfficherReview2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void supprimer_current_client_review(ActionEvent event) {

        ReviewCrud rc = new ReviewCrud();

        if (reviewtable.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a review to delete");

        } else {

            String selected = reviewtable.getSelectionModel().getSelectedItem().getNom_client_review();
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this review?", "Delete Review Confirmation", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                Review r = new Review(selected);
                rc.supprimerReviewClient(r);
                reviewtable.getItems().removeAll(reviewtable.getSelectionModel().getSelectedItem());
                reviewtable.refresh();
                reviewtable.getSelectionModel().clearSelection();
                JOptionPane.showMessageDialog(null, "Your Review have been deleted");

            } else {
                JOptionPane.showMessageDialog(null, "Deleting Review Canceled!");

            }
        }

    }

    private void populateTable(ObservableList<Review> reviewlist) {
        reviewtable.setItems(reviewlist);

    }

    @FXML
    private void Modifer_new_review(ActionEvent event) {
        //Parent root1 = null;
        ReviewCrud rc1 = new ReviewCrud();
        if (reviewtable.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a review to Modify! ");

        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ModifierReview.fxml"));
                Parent root1 = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initModality(Modality.APPLICATION_MODAL);

                ModifierReviewController mr = loader.getController();
                mr.setCurrent_cl_modifer_rating(reviewtable.getSelectionModel().getSelectedItem().getRating());
                mr.setCurrent_cl_review(reviewtable.getSelectionModel().getSelectedItem().getDescription_review());

                stage.showAndWait();

//            Review modifReview = Mo.getCustomer();//Get the selected customer from the Search Controller. HAVE A LOOK AT THE SearchPanelController!
//            //Set the selected customer to the TextFields
//                reviewtable.getSelectionModel().clearSelection();
//                reviewtable.refresh();
                showtable();

            } catch (IOException ex) {
                Logger.getLogger(AfficherReview2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void refresh_data_table(ActionEvent event) {

        showtable();
//        date_review.setSortType(TableColumn.SortType.DESCENDING);
//        reviewtable.getSortOrder().add(date_review);
//        reviewtable.sort();

    }

}
