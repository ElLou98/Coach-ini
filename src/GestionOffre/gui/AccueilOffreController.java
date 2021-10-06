/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionOffre.gui;

import GestionCategorieSport.services.CategorieService;
import GestionCompte.entities.EncapsulationComtpe;
import GestionOffre.models.Offre;
import GestionOffre.services.OffreService;
import GestionOffre.tools.DataSource;
import gui.AccueilClientController;
import gui.AccueilCoachController;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AccueilOffreController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> triBox;
    @FXML
    private ListView<String> list_view;
    ObservableList<Offre> datalist = FXCollections.observableArrayList();
    ObservableList list = FXCollections.observableArrayList();
    ObservableList ob1 = FXCollections.observableArrayList();

    static String s;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    String selected = list_view.getSelectionModel().getSelectedItem();
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(AccueilOffreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Offre> e = new ArrayList<Offre>();
        int i;
        for (i = 0; i < list.size(); i++) {
            e.add(new Offre(String.valueOf(list.get(i))));
            datalist.add(e.get(i));
            
            
            
        }
        ObservableList<String> data = FXCollections.observableArrayList("Titre", "Date");
            triBox.setItems(data);
    }

    private void loadData() throws SQLException {
        OffreService sp = new OffreService();
        CategorieService cp = new CategorieService();
        
         
         
        AccueilClientController g = new AccueilClientController();
        System.out.println("Client="+g.getPass());
         if(g.getClientoucoach().equals("b")){
                
        System.out.println(cp.filtrer1(g.getPass()).get(0));
        System.out.println("1="+cp.filtrer1(g.getPass()).get(0));
        System.out.println("2="+sp.filtrer(cp.filtrer1(g.getPass()).get(0)));
        sp.filtrer(cp.filtrer1(g.getPass()).get(0));
        list.removeAll(list_view);
        EncapsulationComtpe en = new EncapsulationComtpe();
        System.out.println("size="+sp.filtrer(cp.filtrer1(g.getPass()).get(0)).size());
        System.out.println("val1="+sp.afficher1(sp.filtrer(cp.filtrer1(g.getPass()).get(0)).get(0)));
        for (int i=1; i<sp.filtrer(cp.filtrer1(g.getPass()).get(0)).size();i++){
          list.addAll(sp.afficher1(sp.filtrer(cp.filtrer1(g.getPass()).get(0)).get(i)));  
        }
        list_view.getItems().addAll(list);
        list.removeAll(list_view);
        AccueilCoachController ok= new AccueilCoachController();
        
        }
        AccueilCoachController aco= new AccueilCoachController();
         System.out.println("coach="+aco.getClientoucoach());
       if (aco.getClientoucoach().equals("a")){

        System.out.println(cp.filtrer1(aco.getPass()).get(0));
        System.out.println("1="+cp.filtrer1(aco.getPass()).get(0));
        System.out.println("2="+sp.filtrer(cp.filtrer1(aco.getPass()).get(0)));
        sp.filtrer(cp.filtrer1(aco.getPass()).get(0));
        
        list.removeAll(list_view);
        System.out.println("size="+sp.filtrer(cp.filtrer1(aco.getPass()).get(0)).size());
        for (int i=1; i<sp.filtrer(cp.filtrer1(aco.getPass()).get(0)).size();i++){
          list.addAll(sp.afficher1(sp.filtrer(cp.filtrer1(aco.getPass()).get(0)).get(i)));
                }
        
        list_view.getItems().addAll(list);
        list.removeAll(list_view);
         AccueilClientController g2 = new AccueilClientController();
        
        
    }
        
    }


    
  

    @FXML
    private void test(KeyEvent event) {
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

    @FXML
    private void trier(ActionEvent event) {
Connection cnx = DataSource.getInstance().getCnx();
        try {
            ob1.clear();
            EncapsulationComtpe cm = new EncapsulationComtpe();
            String requete = "SELECT * FROM Offre where id_coach=?";
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


    

