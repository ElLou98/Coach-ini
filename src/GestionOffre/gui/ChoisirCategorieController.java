/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionOffre.gui;

import GestionCategorieSport.models.CategorieSport;
import GestionCategorieSport.services.CategorieService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ChoisirCategorieController implements Initializable {

    @FXML
    private TextField search;
   @FXML
    private TableView<CategorieSport> tableView;
    @FXML
    private TableColumn<CategorieSport, String> nom;
    @FXML
    private TableColumn<CategorieSport, String> description;
    @FXML
    private TableColumn<CategorieSport, String> photo;
    int indexx = -1;
    CategorieService cs = new CategorieService();
    ObservableList<CategorieSport> list = FXCollections.observableArrayList(cs.afficher());
    static String s;
    static int id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   nom.setCellValueFactory(new PropertyValueFactory<CategorieSport, String>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<CategorieSport, String>("description"));
        photo.setCellValueFactory(new PropertyValueFactory<CategorieSport, String>("photo"));
        //System.out.println(list.get(0).getPhoto());


        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));

        photo.setCellFactory(tc -> {
            System.out.println(indexx);
            //System.out.println(Paths.get("../../../uploads/"+photo.getCellData(indexx)));
            System.out.println(System.getProperty("user.dir")+"/src/uploads"+photo.getCellData(indexx));
                    File f = new File(System.getProperty("user.dir")+"/src/uploads/"+photo.getCellData(indexx));
            indexx+=1;

            final Image activeImage = new Image(f.toURI().toString(), 100, 150, false, false);
            //final Image passiveImage = new Image("logo.png");

            TableCell<CategorieSport, String> cell = new TableCell<CategorieSport, String>() {
                private ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String nom, boolean empty) {
                    super.updateItem(nom, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {

                        imageView.setImage(activeImage);
                        setGraphic(imageView);
                    }
                }
            };
            return cell;
        });

        tableView.setItems(list);
        FilteredList<CategorieSport> filteredData = new FilteredList<>(list, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(CategorieSport -> {
                // If filter text is empty, display all 

                if (newValue == null || newValue.isEmpty()) {
                    
                    return true;
                }

                // Compare first titre 
                String lowerCaseFilter = newValue.toLowerCase();

                if (CategorieSport.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                    return true; // Filter matches titre
                    
                } else {
                    return false; // Does not match.
                }

            });
        });
        SortedList<CategorieSport> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        //System.out.println(observableList);
        tableView.setItems(sortedData);

    }

    public static int getId() {
        return id;
    }
 

    public void choisircategorie(ActionEvent event) {
        id =tableView.getSelectionModel().getSelectedItem().getId();
        System.out.println(id);
        
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
        
    }

    
}
