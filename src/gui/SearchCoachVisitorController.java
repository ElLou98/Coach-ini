/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import GestionProfile.entities.Profile;
import GestionProfile.services.ProfileService;
import GestionReview.tests.ReviewGuiMainClient;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Adem
 */
public class SearchCoachVisitorController implements Initializable {
    
    @FXML
    private ListView listCoach;
    List<Profile> list;
    static String s;
    @FXML
    private TextField searchCoach;
    @FXML
    private Separator bar1;
    @FXML
    private Label name12;
    
    String cati;
    String deti;
    String desci;
    String name11;
    @FXML
    Rating rating1;
    int idCompte;
    int idCoach;
    String path;
    @FXML
    private Text desc11;
    
    @FXML
    private Text det11;
    @FXML
    private Text cat12;
    
    @FXML
    private Text rat12;
    @FXML
    private ImageView imageuser12;
    @FXML
    private Label desc12;
    @FXML
    private Label cat11;
    
    int rating12;
    @FXML
    private Label det12;
    @FXML
    private ImageView getout;
    @FXML
    private Button btn_revues_coach_ADEM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ProfileService sp = new ProfileService();
        list = sp.afficher();
        cat11.setVisible(false);
        det11.setVisible(false);
        desc12.setVisible(false);
        imageuser12.setVisible(false);
        name12.setVisible(false);
        rating1.setVisible(false);
        rat12.setVisible(false);
        det12.setVisible(false);
        desc11.setVisible(false);
        cat12.setVisible(false);
        bar1.setVisible(false);
        btn_revues_coach_ADEM.setVisible(false);
        
        listCoach.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent click) {
                
                if (click.getClickCount() == 2) {

                    //Use ListView's getSelected Item
                    final int selectedIdx = listCoach.getSelectionModel().getSelectedIndex();
                    
                    cati = list.get(selectedIdx).getCatégorie();
                    deti = list.get(selectedIdx).getDétail();
                    desci = list.get(selectedIdx).getDescription();
                    name11 = list.get(selectedIdx).getNom();
                    rating12 = list.get(selectedIdx).getRating();
                    idCompte = list.get(selectedIdx).getID_Compte();
                    idCoach = list.get(selectedIdx).getID_Coach();
                    path = "file:/E:/web&mobile/webCoachini/public/ProfileImages/"+ list.get(selectedIdx).getPhoto();
                    
                    cat11.setVisible(true);
                    det11.setVisible(true);
                    desc12.setVisible(true);
                    imageuser12.setVisible(true);
                    name12.setVisible(true);
                    rating1.setVisible(true);
                    rat12.setVisible(true);
                    det12.setVisible(true);
                    desc11.setVisible(true);
                    cat12.setVisible(true);
                    btn_revues_coach_ADEM.setVisible(true);
                    
                    bar1.setVisible(true);
                    
                    cat11.setText(cati);
                    
                    det12.setText(deti);
                    
                    desc12.setText(desci);
                    
                    name12.setText(name11);
                    
                    Image image1 = new Image(path);
                    imageuser12.setImage(image1);
                    
                    rating1.setRating((double) rating12);
                    rating1.setVisible(true);
                    rating1.setDisable(true);
                    
                }
                
            }
            
        });
        
        List imageList = new ArrayList();
        
        for (int i = 0; i < list.size(); i++) {
            listCoach.getItems().add(list.get(i));
            imageList.add("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+list.get(i).getPhoto());
        }
        
        listCoach.setCellFactory(param -> new ListCell<Profile>() {
            Label l = new Label();            

            @Override
            public void updateItem(Profile name, boolean empty) {
                
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    
                    try {
                        
                        l.setGraphic(new ImageView(new Image("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+name.getPhoto(), 100, 100, false, false)));
                        l.setText(name.getNom());
                        
                        setGraphic(l);
                        
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    
                }
            }
        });
    }    
    
    @FXML
    private void search(KeyEvent event) {
    }
    
    @FXML
    private void searchbyName(MouseEvent event) {
        ProfileService sp = new ProfileService();
        list = sp.search(searchCoach.getText());
        listCoach.getItems().clear();
        List imageList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            listCoach.getItems().add(list.get(i));
            imageList.add("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+list.get(i).getPhoto());
            
        }
        
        listCoach.setCellFactory(param -> new ListCell<Profile>() {

            //private ImageView imageView = new ImageView();
            Label l = new Label();            

            @Override
            public void updateItem(Profile name, boolean empty) {
                
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    
                    try {
                        
                        l.setGraphic(new ImageView(new Image("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+name.getPhoto(), 100, 100, false, false)));
                        l.setText(name.getNom());
                        
                        setGraphic(l);
                        
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    
                }
            }
        });
    }
    
    @FXML
    private void getout(MouseEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccueilVisiteur.fxml"));
        Parent root = loader.load();
        
        name12.getScene().setRoot(root);
    }
    
    @FXML
    private void Handel_revues_coach_ADEM(ActionEvent event) {
        try {
            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            new ReviewGuiMainClient().start(s);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
