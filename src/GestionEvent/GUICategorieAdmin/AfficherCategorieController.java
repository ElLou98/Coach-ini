/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.GUICategorieAdmin;

import gui.AccueilAdminController;
import GestionEvent.Models.Categorie_Event;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import rest.file.uploader.tn.FileUploader;
import GestionEvent.Services.Service_Categorie_Event;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author kagha
 */
public class AfficherCategorieController implements Initializable {

    public static javafx.scene.image.Image getLoad_image() {
        return load_image;
    }

    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private BorderPane border_id;
    @FXML
    private Button detail_id;
    @FXML
    private Button modifier_id;
    @FXML
    private Button supprimer_id;
    @FXML
    private TextField lookup;
    @FXML
    private ListView event_list;
    ObservableList<Categorie_Event> data = FXCollections.observableArrayList();
    static String s;
    static javafx.scene.image.Image load_image;
    @FXML
    private ImageView picture;
    @FXML
    private Button detail_id1;
    public static ListView L;

    public static ListView getL() {
        return L;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        L = event_list;
        loadData();
        int i;
        List<Categorie_Event> e = new ArrayList<Categorie_Event>();
        for (i = 0; i < list.size(); i++) {
            e.add(new Categorie_Event((String) list.get(i)));
            data.add(e.get(i));
        }
    }

        public void seeMe(ListView l) {
        Service_Categorie_Event sc = new Service_Categorie_Event();
        list.removeAll(l);
        list.addAll(sc.afficher_nom());
        System.out.println(list);
        l.setItems(list);
    }
    
    private void loadData() {
        //String  selected =event_list.getSelectionModel().getSelectedItem(); 
        Service_Categorie_Event sc = new Service_Categorie_Event();
        list.removeAll(event_list);
        list.addAll(sc.afficher_nom());
        event_list.setItems(list);
    }

    @FXML
    private void Detail(ActionEvent event) {
        Service_Categorie_Event ce = new Service_Categorie_Event();
        String selected = s;
        AfficherCategorieController e = new AfficherCategorieController();
        if (selected == null || selected.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Séléctionner un événement a détaillée");
            return;
        }
        list.removeAll(event_list);
        list.clear();
        list.addAll(ce.fetch(String.valueOf(ce.get_ID(s))));
        //System.out.println(ce.fetch(selected));
        try{
        event_list.getSelectionModel().clearSelection();
        String s1 = "Nom Catégorie: " + list.get(0).toString();
        String s2 = "Description Catégorie: " + list.get(1).toString();
        String s3 = "Photo Catégorie:" + list.get(2).toString();
        Service_Categorie_Event sc = new Service_Categorie_Event();
        String Db_picture = sc.get_Db_picture(sc.get_ID(e.getIt()));
        System.out.println(Db_picture);
        File pic = new File(Db_picture);

        URL url = new URL("http://" + Db_picture);
        BufferedImage image = javax.imageio.ImageIO.read(url);
        javafx.scene.image.Image ig = SwingFXUtils.toFXImage(image, null);
        //javafx.scene.image.Image image = new javafx.scene.image.Image(pic.toURI().toString());
        picture.setImage(ig);
        list.clear();
        list.addAll(s1, s2, s3);
        event_list.setItems(list);
        event_list.setMouseTransparent(false);
        event_list.setFocusTraversable(false);
        }catch( Exception ee ){
            try {
                System.out.println("empty");
                Back(null);
            } catch (IOException ex) {
                Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @FXML
    private void Modifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierCategorie.fxml"));
            Parent root = loader.load();
            ModifierCategorieController cr = loader.getController();
            Service_Categorie_Event sc = new Service_Categorie_Event();
            String selected = String.valueOf(sc.get_ID(s));
            if (selected == null || selected.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Séléctionner un événement a Modifier");
                return;
            }
            List<String> lm = sc.fetch(selected);
            cr.setTfNom((lm.get(0)));
            cr.setDescription((lm.get(1)));
            cr.setPath((lm.get(2)));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            //cr.setImage(new ImageView(lm.get(3)));
        } catch ( Exception ee ){
            try {
                System.out.println("empty");
                Back(null);
            } catch (IOException ex) {
                Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @FXML
    private void Supprimer(ActionEvent event) {
        try {
            Service_Categorie_Event sc = new Service_Categorie_Event();
            String selected = s;
            if (selected == null || selected.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Séléctionner un événement a Supprimer");
                return;
            }
            AfficherCategorieController e = new AfficherCategorieController();
            FileUploader fu = new FileUploader("http://localhost/coachini/");
            String Db_picture = sc.get_Db_picture(sc.get_ID(e.getIt()));
            fu.delete(Db_picture.substring(Db_picture.indexOf("f")));
            sc.supprimer_id(new Categorie_Event(sc.get_ID(s)));
            //refresh
            list.removeAll(event_list);
            list.clear();
            event_list.getSelectionModel().clearSelection();
            event_list.setItems(list);
            picture.setImage(new Image("src/GestionEvent/Icons/main.png"));
            loadData();
        } catch ( Exception ee ){
            try {
                System.out.println("empty");
                Back(null);
            } catch (IOException ex) {
                Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @FXML
    private void LookUp(ActionEvent event) {
    }

    @FXML
    private void test(KeyEvent event) {
        //String  selected =getSelected();
        //System.out.println(selected);
        ListView<Categorie_Event> event_list = null;
        Service_Categorie_Event sp = new Service_Categorie_Event();
        list.addAll(sp.afficher_nom());
        //System.out.println(sp.afficher_nom());
        // System.out.println(list.get(1));
        //list.removeAll(this.event_list);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Categorie_Event> filteredData = new FilteredList<>(data, b -> true);
        //System.out.println(filteredData);

        // 2. Set the filter Predicate whenever the filter changes.
        lookup.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Categorie_Event -> {
                // If filter text is empty, display all 
                //System.out.println(newValue);
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name 
                String lowerCaseFilter = newValue.toLowerCase();

                if (Categorie_Event.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    //System.out.println(lowerCaseFilter);
                    return true; // Filter matches first name.
                } else {
                    return false; // Does not match.
                }

            });
        });

        SortedList<Categorie_Event> sortedData = new SortedList<>(filteredData);
        //sortedData.toString().substring(sortedData.lastIndexOf("Nom_event") + 1)
        //System.out.println(sortedData);
        //System.out.println(filteredData.toString().substring(filteredData.toString().indexOf("=", filteredData.toString().indexOf("=") + 1), 40) );
        ObservableList<String> observableList = FXCollections.observableList((List) sortedData);
        //System.out.println(observableList);
        this.event_list.setItems(observableList);

    }

    @FXML
    private void getSelected(MouseEvent event) {
        Categorie_Event e = new Categorie_Event(String.valueOf(event_list.getSelectionModel().getSelectedItem()));
        s = e.getNom();
        //System.out.println(s);
    }

    public static String getIt() {
        return s;
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml"));
        Pane root = loader.load();
        AccueilAdminController ac = new AccueilAdminController();
        BorderPane bb = ac.getBb();
        ac.setBorder(bb);
        ac.getBorder().setCenter(root);
    }

}
