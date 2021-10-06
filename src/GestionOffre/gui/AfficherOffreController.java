/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionOffre.gui;

import GestionOffre.models.Offre;
import GestionOffre.services.OffreService;
import GestionOffre.tools.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import GestionCompte.entities.EncapsulationComtpe;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherOffreController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private Button Ajouter_id;

    @FXML
    private Button Modifier_id;
    @FXML
    private ComboBox<String> triBox;
    @FXML
    private ListView<String> list_view;
    ObservableList<Offre> datalist = FXCollections.observableArrayList();
    ObservableList list = FXCollections.observableArrayList();
    ObservableList ob1 = FXCollections.observableArrayList();

    static String s;
    
    @FXML
    private Button idsupprim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String selected = list_view.getSelectionModel().getSelectedItem();
        loadData();
        List<Offre> e = new ArrayList<Offre>();
        int i;
        for (i = 0; i < list.size(); i++) {
            e.add(new Offre(String.valueOf(list.get(i))));
            datalist.add(e.get(i));

        }
        ObservableList<String> data = FXCollections.observableArrayList("Titre", "Date");
        triBox.setItems(data);
    }

    private void loadData() {
        OffreService sp = new OffreService();
        list.removeAll(list_view);
        EncapsulationComtpe en = new EncapsulationComtpe();
        list.addAll(sp.afficher1(en.getId()));
        list_view.getItems().addAll(list);
    }

    @FXML
    public void test(KeyEvent event) {
        ListView<Offre> list_view = null;
        OffreService sp = new OffreService();
        EncapsulationComtpe en = new EncapsulationComtpe();
        list.addAll(sp.afficher1(en.getId()));

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Offre> filteredData = new FilteredList<>(datalist, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Offre -> {
                // If filter text is empty, display all 

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first titre 
                String lowerCaseFilter = newValue.toLowerCase();

                if (Offre.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                    return true; // Filter matches titre
                } else {
                    return false; // Does not match.
                }

            });
        });
        SortedList<Offre> sortedData = new SortedList<>(filteredData);

        ObservableList<String> observableList = FXCollections.observableList((List) sortedData);
        //System.out.println(observableList);
        this.list_view.setItems(observableList);

    }

    public String GetSelected() {
        OffreService sp = new OffreService();
        return list_view.getSelectionModel().getSelectedItem();
    }
//public static String getIt(){
//        return s;
//    }

    @FXML
    private void Supprimer_offre(ActionEvent event) {

        OffreService sp = new OffreService();
        if (list_view.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a offre to delete");

        }

        String selected = String.valueOf(list_view.getSelectionModel().getSelectedItem());
          selected=selected.substring(selected.lastIndexOf("titre=")+6,selected.lastIndexOf(", date="));;

        System.out.println(selected);
        if (selected == null || selected.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Séléctionner un offre a Supprimer");
            return;
        }
        System.out.println(selected);
           s = String.valueOf(sp.getID(selected));
        //sp.supprimer(new Offre(selected));
        //System.out.println(Integer.parseInt(selected));
        System.out.println(s);
        sp.supprimer(Integer.parseInt(s));
        //refresh 
        list.removeAll(list_view);
        list.clear();
        list_view.getSelectionModel().clearSelection();
        list_view.setItems(list);
        loadData();

    }

    @FXML
    private void ModifierOffre(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ModifierOffre.fxml"));
            Parent root = loader.load();
            ModifierOffreController cr = loader.getController();
            OffreService sp = new OffreService();
            String selected = String.valueOf(list_view.getSelectionModel().getSelectedItem());
            System.out.println("select" + selected);
            //System.out.println("dddd" + selected.substring(selected.lastIndexOf("=") + 1));
            

            //selected = selected.substring(selected.lastIndexOf("=") + 1);
            if (selected == null || selected.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Séléctionner un offre a Modifier");
                return;

            }
            //System.out.println("selected="+selected);
            //System.out.println(sp.getIDOffre(Integer.valueOf(selected.trim())));
           // System.out.println("s="+selected);
            System.out.println(selected.substring(selected.lastIndexOf("titre=")+6,selected.lastIndexOf(", date=")));
            selected=selected.substring(selected.lastIndexOf("titre=")+6,selected.lastIndexOf(", date="));
            s = String.valueOf(sp.getID(selected));
            //System.out.println(sp.fetch(sp.getIDOffre(Integer.valueOf(selected.trim()))).get(1));
            //System.out.println(sp.fetch(sp.getIDOffre(Integer.valueOf(selected.trim()))).get(3));*
            //System.out.println(sp.fetch(sp.getIDOffre(Integer.valueOf(sp.getID(selected)))));
            System.out.println(sp.getIDOffre(Integer.valueOf(sp.getID(selected))));
            cr.SetTextField1(sp.fetch(sp.getIDOffre(Integer.valueOf(sp.getID(selected)))).get(1));
            cr.SetTextField2(sp.fetch(sp.getIDOffre(Integer.valueOf(sp.getID(selected)))).get(3));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate1 = LocalDate.parse(sp.fetch(sp.getIDOffre(Integer.valueOf(sp.getID(selected)))).get(2), formatter);

            cr.DatePicker1(localDate1);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            //loadData();
        } catch (IOException ex) {
            Logger.getLogger(AfficherOffreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Ajouter() throws IOException {

        Stage stage = (Stage) Ajouter_id.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AjoutOffre.fxml"));
        primaryStage.setTitle("Ajout");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void Modifier() throws IOException {

        Stage stage = (Stage) Modifier_id.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ModifierOffre.fxml"));
        primaryStage.setTitle("Modifier");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static String getS() {
        return s;
    }

    @FXML
    private void trier(ActionEvent event) {
        Connection cnx = DataSource.getInstance().getCnx();
        try {
            ob1.clear();
            EncapsulationComtpe cm = new EncapsulationComtpe();
            String requete = "SELECT * FROM Offre where idCompte=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, cm.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ob1.add(new Offre(rs.getInt("id"), rs.getString("titre"), rs.getDate("date"), rs.getString("description")));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        Comparator<Offre> comparator;
        if (triBox.getValue() == "ID") {
            comparator = Comparator.comparingInt(Offre::getId);

        } else if (triBox.getValue() == "Titre") {
            comparator = Comparator.comparing(Offre::getTitre);

        } else {
            comparator = Comparator.comparing(Offre::getDate);

        }
  
        FXCollections.sort(ob1, comparator);
        list_view.setItems(ob1);
        

    }

}
