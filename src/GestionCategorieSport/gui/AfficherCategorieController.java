/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCategorieSport.gui;

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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherCategorieController implements Initializable {

      @FXML
    private TextField search;
    @FXML
    private Button idajout;

    @FXML
    private Button idModifier;
    @FXML
    private Button idsupprim;
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

    @FXML
    private void Modifier_Categorie(ActionEvent event) {
        Parent root1 = null;
        CategorieService rc1 = new CategorieService();
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a categorie to Modify! ");

        } else {
            String selected = String.valueOf(tableView.getSelectionModel().getSelectedItem().getNom());
            s = selected;

            //System.out.println(s);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ModifierCategorie.fxml"));
                root1 = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();

                ModifierCategorieController mr = loader.getController();
                mr.SetTextField1(tableView.getSelectionModel().getSelectedItem().getNom());
                mr.SetTextField2(tableView.getSelectionModel().getSelectedItem().getDescription());
                mr.setimagepath(tableView.getSelectionModel().getSelectedItem().getPhoto());
                tableView.refresh();
                System.out.println(mr.getImgview());
            } catch (IOException ex) {
                Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void supprimer_categorie(ActionEvent event) {

        CategorieService rc = new CategorieService();

        if (tableView.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a categorie to delete");

        } else {

            String selected = String.valueOf(tableView.getSelectionModel().getSelectedItem().getNom());
            //s=selected;

            // System.out.println(s);
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this categorie?", "Delete caategorie Confirmation", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                CategorieSport r = new CategorieSport(selected);
                //System.out.println(new CategorieSport(selected));
                rc.supprimer2(r);
                list.removeAll(tableView.getSelectionModel().getSelectedItem());
                list.clear();
                tableView.getSelectionModel().clearSelection();
                tableView.setItems(list);
                tableView.refresh();

                JOptionPane.showMessageDialog(null, "Your categorie have been deleted");

            } else {
                JOptionPane.showMessageDialog(null, "Deleting categorie Canceled!");

            }
        }
    }

    @FXML
    public void AjouterCategorie() throws IOException {

        
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AjoutCategorie.fxml"));
        primaryStage.setTitle("Ajouter une catégorie");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
