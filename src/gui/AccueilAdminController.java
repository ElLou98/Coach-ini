/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import GestionCategorieSport.tests.MainCategorie;
import GestionCompte.entities.CompteInfo;
import GestionCompte.services.CompteCRUD;
import GestionReview.tests.ReviewGuiMainAdmin;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import tools.MyConnection;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class AccueilAdminController implements Initializable {

    @FXML
    private ImageView logo_view;
    @FXML
    private ComboBox<?> gestionCompteClientComboBox;
    @FXML
    private ComboBox<?> gestionCompteClientComboBox1;
    @FXML
    private ComboBox Combo_GestionCategorieAdmin;
    @FXML
    private TableView<CompteInfo> tableau_compte;

    ObservableList<CompteInfo> obl = FXCollections.observableArrayList();

    @FXML
    private TableColumn<CompteInfo, String> cl_userName;
    @FXML
    private TableColumn<CompteInfo, String> cl_nom;
    @FXML
    private TableColumn<CompteInfo, String> cl_prenom;
    @FXML
    private TableColumn<CompteInfo, String> cl_age;
    @FXML
    private TableColumn<CompteInfo, String> cl_email;
    @FXML
    private TableColumn<CompteInfo, String> cl_password;
    @FXML
    private TableColumn<CompteInfo, String> cl_numTel;
    @FXML
    private TableColumn<CompteInfo, String> cl_type;
    @FXML
    private Label id;
    @FXML
    private ComboBox Combo_GestionRevuesAdmin;
    @FXML
    private ComboBox GestiondesEvent;
    @FXML
    private BorderPane border;
    private static BorderPane bb;
    @FXML
    private ComboBox GestionCatEvent;
    @FXML
    private Button btn_csv;
    @FXML
    private LineChart<?, ?> chart1;
    @FXML
    private PieChart chartPie;

    public BorderPane getBb() {
        return bb;
    }

    public BorderPane getBorder() {
        return border;
    }

    public void setBorder(BorderPane border) {
        this.border = border;
    }

    public int getId() {
        return Integer.parseInt(id.getText());
    }

    public void setId(int id) {
        this.id.setText(Integer.toString(id));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        int count=CompteCRUD.pourcentageAccountClient();
        ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(
        new PieChart.Data("Clients", count),
        new PieChart.Data("Coach", 100-count));
        chartPie.getData().addAll(pieChartData);
        
        ArrayList<Integer> list=CompteCRUD.signninStatisque();
        
        
        XYChart.Series S = new XYChart.Series<>();
        S.setName("test");
        S.getData().add(new XYChart.Data<>("1",list.get(1)));
        S.getData().add(new XYChart.Data<>("2",list.get(2)));
        S.getData().add(new XYChart.Data<>("3",list.get(3)));
        S.getData().add(new XYChart.Data<>("4",list.get(4)));
        
        chart1.getData().addAll(S);

        List<String> myListReview = new ArrayList<>();
        myListReview.add("Gestion des Revues des Clients");

        List<String> myListCateg = new ArrayList<>();
        myListCateg.add("Gestion des Categories sport");

        List<String> myListeEvent = new ArrayList<>();
        myListeEvent.add("Gestion Event");

        List<String> myListeCatEvent = new ArrayList<>();
        myListeCatEvent.add("Ajouter Catégorie Event");
        myListeCatEvent.add("Traiter Catégorie Event");

        Combo_GestionRevuesAdmin.getItems().addAll(myListReview.get(0));
        Combo_GestionCategorieAdmin.getItems().addAll(myListCateg.get(0));
        GestiondesEvent.getItems().addAll(myListeEvent.get(0));
        GestionCatEvent.getItems().addAll(myListeCatEvent.get(0));
        GestionCatEvent.getItems().addAll(myListeCatEvent.get(1));
        cl_userName.setCellValueFactory(new PropertyValueFactory<>("nomDutilisateur"));
        cl_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cl_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cl_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        cl_email.setCellValueFactory(new PropertyValueFactory<>("adresseMail"));
        cl_password.setCellValueFactory(new PropertyValueFactory<>("motDePasse"));
        cl_numTel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        cl_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        try {

            String requete = "SELECT * FROM compte";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                obl.add(new CompteInfo(rs.getInt("id"), rs.getString("userName"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("age"), rs.getString("adresse_mail"), rs.getString("mot_de_passe"), rs.getInt("num_tel")));
                tableau_compte.setItems(obl);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Handel_combo_gestionRevue_Admin(ActionEvent event) {
        try {
            Combo_GestionRevuesAdmin.getSelectionModel().isSelected(0);
            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            new ReviewGuiMainAdmin().start(s);

        } catch (IOException ex) {
            Logger.getLogger(AccueilAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Handel_combo_gestionCateg_Admin(ActionEvent event) {
        Combo_GestionCategorieAdmin.getSelectionModel().isSelected(0);
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        try {
            new MainCategorie().start(s);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Handel_combo_gestionEvent_Admin(ActionEvent event) throws IOException {
        GestiondesEvent.getSelectionModel().isSelected(0);
        chartPie.setVisible(false);
        btn_csv.setVisible(false);
        Color c = Color.web("0xEEEEEE", 1.0);
        border.setBackground(new Background(new BackgroundFill(c, null, null)));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionEvent/GUIAccueilAdmin/AfficherEventAdmin.fxml"));
        Pane root = loader.load();
        border.setCenter(root);
        border.setLayoutX(300);
        border.setLayoutY(15);
        bb = border;
        //System.out.println("test");
        Platform.runLater(() -> GestiondesEvent.setValue("Gestion des Evénements"));
    }

    @FXML
    private void Handel_combo_gestionCatEvent_Admin(ActionEvent event) {
        if (GestionCatEvent.getValue() == "Ajouter Catégorie Event") {

            try {
                // System.out.println("Ajouter Catégorie Event");
                Platform.runLater(() -> GestionCatEvent.setValue("Gestion des Catégorie Event"));
                chartPie.setVisible(false);
                btn_csv.setVisible(false);
                Color c = Color.web("0xEEEEEE", 1.0);
                border.setBackground(new Background(new BackgroundFill(c, null, null)));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionEvent/GUICategorieAdmin/AjouterCategorie.fxml"));
                Pane root = loader.load();
                border.setLayoutX(310);
                border.setLayoutY(40);
                border.setCenter(root);
            } catch (IOException ex) {
                Logger.getLogger(AccueilAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (GestionCatEvent.getValue() == "Traiter Catégorie Event") {

            try {
                //System.out.println("Traiter Catégorie Event");
                Platform.runLater(() -> GestionCatEvent.setValue("Gestion des Catégorie Event"));
                chartPie.setVisible(false);
                btn_csv.setVisible(false);
                Color c = Color.web("0xEEEEEE", 1.0);
                border.setBackground(new Background(new BackgroundFill(c, null, null)));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionEvent/GUICategorieAdmin/AfficherCategorie.fxml"));
                Pane root = loader.load();
                border.setLayoutX(300);
                border.setLayoutY(10);
                border.setCenter(root);
                bb = border;
            } catch (IOException ex) {
                Logger.getLogger(AccueilAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void dirigerReclamation(MouseEvent event) throws IOException {
//        chartPie.setVisible(false);
//        btn_csv.setVisible(false);
//        border.setVisible(true);
//        Color c = Color.web("0xEEEEEE",1.0);
//        border.setBackground(new Background(new BackgroundFill(c, null, null)));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionReclamation/gui/ReclamationBack.fxml"));
//        Pane root = loader.load();
//        border.setCenter(root);
                chartPie.setVisible(false);
                btn_csv.setVisible(false);
                Color c = Color.web("0xEEEEEE", 1.0);
                border.setBackground(new Background(new BackgroundFill(c, null, null)));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionReclamation/gui/ReclamationBack.fxml"));
                Pane root = loader.load();
                border.setLayoutX(310);
                border.setLayoutY(40);
                border.setCenter(root);
    }

    @FXML
    private void writeCSV(ActionEvent event) throws FileNotFoundException {
        try {

            String requete = "SELECT * FROM compte";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            PrintWriter writer= new PrintWriter("data.txt");
            writer.print("");
            writer.close();
            while (rs.next()) {
                saveRecord(Integer.toString(rs.getInt("id")), rs.getString("userName"), rs.getString("nom"), rs.getString("prenom"), Integer.toString(rs.getInt("age")), rs.getString("adresse_mail"), rs.getString("mot_de_passe"), Integer.toString(rs.getInt("id")),"data.txt");
                
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
    }
    
    public static void saveRecord(String ID, String nomDutilisateur,String nom,String prenom,String age,String adresseMail,String motDePasse,String numTel,String filepath)
    {
        try
        {
           FileWriter fw = new FileWriter(filepath,true);
           BufferedWriter bw = new BufferedWriter(fw) ;
           PrintWriter pw = new PrintWriter(bw);

           pw.println(ID+","+nomDutilisateur+","+nom+","+prenom+","+age+","+adresseMail+","+motDePasse+","+numTel);
           pw.flush();
           pw.close();

            
        }
        catch(Exception E)
        {
            JOptionPane.showMessageDialog(null, "Record not saved");
        }
    }
    
    
    

    
}