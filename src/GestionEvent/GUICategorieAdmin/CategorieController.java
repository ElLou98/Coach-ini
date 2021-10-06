/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.GUICategorieAdmin;


import GestionEvent.Models.TableCategorie;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import GestionEvent.Services.Service_Categorie_Event;

/**
 * FXML Controller class
 *
 * @author kagha
 */
public class CategorieController implements Initializable {

    @FXML
    private TableView<TableCategorie> table;
    @FXML
    private TableColumn<TableCategorie, String> col_Nom;
    @FXML
    private TableColumn<TableCategorie, String> col_description;
    @FXML
    private TableColumn<TableCategorie, Image> col_Image;
    ObservableList<TableCategorie> oblist = FXCollections.observableArrayList();
    static String cat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_Image.setCellValueFactory(new PropertyValueFactory<>("img"));
        Service_Categorie_Event cr = new Service_Categorie_Event();
        try {
            table.setItems(cr.fillCategorie());
            table.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        cat = table.getSelectionModel().getSelectedItem().getNom();
                        Stage stage = (Stage) table.getScene().getWindow();
                        stage.close();
                        //Version_3Controller vt = new Version_3Controller();
                        //vt.setBtn_Cat(cat);
                        //System.out.println(cat);
                    }
                }
            });

            col_description.setCellFactory(tc -> {
                TableCell<TableCategorie, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(col_description.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            });
            col_Nom.setCellFactory(tc -> {
                
                TableCell<TableCategorie, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(col_Nom.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                cell.setTextFill(Color.AQUA);
                return cell;
            });


        } catch (SQLException ex) {
            Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getCat() {
        return cat;
    }

}
