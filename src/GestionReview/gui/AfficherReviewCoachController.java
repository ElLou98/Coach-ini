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
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Adem
 */
public class AfficherReviewCoachController implements Initializable { // pour le coach

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
    private JFXButton btn_refresh;
    
    
    

     ObservableList list = FXCollections.observableArrayList();
    ObservableList<Review> dataList = FXCollections.observableArrayList();
    
     ObservableList<Review> obl = FXCollections.observableArrayList();
    @FXML
    private TextField tf_search_coach;
    
        EncapsulationComtpe ec = new EncapsulationComtpe();
        String nomcoach = ec.getNomDutilisateur();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        showtable2();
        search_review2();
         // sorting with date
        date_review.setSortType(TableColumn.SortType.DESCENDING);
        reviewtable.getSortOrder().add(date_review);
        reviewtable.sort();
        
        // sorting with Client name
        date_review.setSortType(TableColumn.SortType.DESCENDING);
        reviewtable.getSortOrder().add(nom_client);
        reviewtable.sort();
        
        // sorting with rating
        date_review.setSortType(TableColumn.SortType.DESCENDING);
        reviewtable.getSortOrder().add(rating);
        reviewtable.sort();
    }    
        private void showtable2() {
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

        try {
            
            ObservableList<Review> reviewlist = ReviewCrud.getAllRecordsCoach(nomcoach);

            populateTable(reviewlist); 

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherReview2Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AfficherReview2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void search_review2() {
        nom_client.setCellValueFactory(new PropertyValueFactory<>("nom_client_review"));
        date_review.setCellValueFactory(new PropertyValueFactory<>("date_review"));
        descrip_review.setCellValueFactory(new PropertyValueFactory<>("description_review"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        try {

            String requete = "SELECT * FROM  review_client WHERE( nom_coach_review = '"+nomcoach+"')";
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
        tf_search_coach.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(review -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (review.getNom_client_review().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
                } else if (review.getDescription_review().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                }  else if (String.valueOf(review.getRating()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                }  else {
                    return false; // Does not match.
                }
            });
        });

        reviewtable.setItems(filteredData);
        
    
    }
    private void populateTable(ObservableList<Review> reviewlist) {
        reviewtable.setItems(reviewlist);
    }
    @FXML
    private void refresh_data_table(ActionEvent event) {
        showtable2();
    }


    
}
