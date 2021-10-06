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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class RechercheCoachController implements Initializable {

    @FXML
    private ListView listCoach;
    List<Profile> list;
    static String s;
    @FXML
    private TextField searchCoach;
    @FXML
    private Text desc1;
    @FXML
    private Text cat1;
    @FXML
    private Text det1;
    @FXML
    private Text rat1;
    @FXML
    private ImageView imageuser;
    @FXML
    private Rating rating;
    @FXML
    private Label name1;
    @FXML
    private ImageView backk;
    @FXML
    private Label desc;
    @FXML
    private Label det;
    @FXML
    private Label cat;
    @FXML
    private Separator bar1;

    String cati;
    String deti;
    String desci;
    String name11;
    int rating1;
    int idCompte;
    int idCoach;
    String path;
    @FXML
    private Button btn_revues_coach_client_ADEM;
    /**
     * Initializes the controller class.
     */
    // ajouter par adem 
    public static String nomcoachadem;

    public static String getNomcoachadem() {
        return nomcoachadem;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProfileService sp = new ProfileService();
        list = sp.afficher();

        cat.setVisible(false);
        det.setVisible(false);
        desc.setVisible(false);
        imageuser.setVisible(false);
        name1.setVisible(false);
        rating.setVisible(false);
        rat1.setVisible(false);
        det1.setVisible(false);
        desc1.setVisible(false);
        cat1.setVisible(false);
        bar1.setVisible(false);
        btn_revues_coach_client_ADEM.setVisible(false);
        listCoach.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    btn_revues_coach_client_ADEM.setVisible(true);
                    //Use ListView's getSelected Item
                    final int selectedIdx = listCoach.getSelectionModel().getSelectedIndex();

                    cati = list.get(selectedIdx).getCatégorie();
                    deti = list.get(selectedIdx).getDétail();
                    desci = list.get(selectedIdx).getDescription();
                    name11 = list.get(selectedIdx).getNom();

                    // ajouter par adem
                    nomcoachadem = name11;
                    //fin ajouter adem 
                    rating1 = list.get(selectedIdx).getRating();
                    idCompte = list.get(selectedIdx).getID_Compte();
                    idCoach = list.get(selectedIdx).getID_Coach();

                    path ="file:/E:/web&mobile/webCoachini/public/ProfileImages/"+ list.get(selectedIdx).getPhoto();

                    rat1.setVisible(true);
                    det1.setVisible(true);
                    desc1.setVisible(true);
                    cat1.setVisible(true);
                    bar1.setVisible(true);

                    cat.setText(cati);
                    cat.setVisible(true);

                    det.setText(deti);
                    det.setVisible(true);

                    desc.setText(desci);
                    desc.setVisible(true);

                    name1.setText(name11);
                    name1.setVisible(true);

                    Image image1 = new Image(path);
                    imageuser.setImage(image1);
                    imageuser.setVisible(true);

                    //rating.setRating((double)rating1);
                    rating.setVisible(true);

                }

            }

        });

        ProfileService ps = new ProfileService();
        rating.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.err.println(path + "\n" + desci + "\n" + newValue.intValue() + "\n" + cati + "\n" + deti + "\n" + name11 + "\n" + idCompte + "\n" + idCoach);
                ps.modifier(new Profile(path, desci, newValue.intValue(), cati, deti, name11, idCompte, idCoach));
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
    private void back(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccueilClient.fxml"));
        Parent root = loader.load();

        name1.getScene().setRoot(root);
    }

    @FXML
    private void Handel_revues_coach_client_ADEM(ActionEvent event) {
        try {
            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            new ReviewGuiMainClient().start(s);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
