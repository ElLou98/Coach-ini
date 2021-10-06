/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import GestionCompte.entities.Compte;
import GestionCompte.entities.EncapsulationComtpe;
import GestionCompte.services.CompteCRUD;
import GestionProfile.services.ProfileService;
import Messagerie.clientChat.ChatClient;
import static gui.AccueilClientController.pass;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class AccueilCoachController implements Initializable {

    private List<String> list = new ArrayList<String>();
    private Image images[];
    int j = 0;
    double orgCliskSceneX, orgReleaseSceneX;
    Button lbutton, rButton;
    @FXML
    private ImageView imageView;
    @FXML
    private GridPane root;
    @FXML
    private HBox hBox;
    @FXML
    private Label id;
    @FXML
    private BorderPane border;
    static BorderPane bb;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img3;
    @FXML
    private Label txt1;
    @FXML
    private Label txt2;
    @FXML
    private Label txt3;
    @FXML
    private Label txt4;
    @FXML
    private Label txt5;
    @FXML
    private Label txt6;
    @FXML
    private ImageView img6;
    @FXML
    private ImageView img5;
    public static String pass;
    public static String clientoucoach="z";



    public static String getPass() {
        return pass;
    }

    public BorderPane getBorder() {
        return border;
    }

    public void setBorder(BorderPane border) {
        this.border = border;
    }

    public static BorderPane getBb() {
        return bb;
    }

    public int getId() {
        return Integer.parseInt(this.id.getText());
    }

    public void setId(int id) {
        this.id.setId(Integer.toString(id));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientoucoach="a";
        border.setVisible(false);
        id.setVisible(false);
        try {
            list.add("../img/1.jpg");
            list.add("../img/2.jpg");
            list.add("../img/3.jpg");

            root.setAlignment(Pos.CENTER);

            lbutton = new Button("<");
            rButton = new Button(">");

            images = new Image[list.size()];
            for (int i = 0; i < list.size(); i++) {
                images[i] = new javafx.scene.image.Image(getClass().getResource(list.get(i)).toExternalForm());
            }

            imageView = new ImageView(images[j]);
            imageView.setCursor(Cursor.CLOSED_HAND);
            imageView.setOnMouseReleased(e -> {
                orgReleaseSceneX = e.getSceneX();
                if (orgCliskSceneX > orgReleaseSceneX) {
                    lbutton.fire();
                } else {
                    rButton.fire();
                }
            });

            rButton.setOnAction(e -> {
                j = j + 1;
                if (j == list.size()) {
                    j = 0;
                }
                imageView.setImage(images[j]);

            });
            lbutton.setOnAction(e -> {
                j = j - 1;
                if (j == 0 || j > list.size() + 1 || j == -1) {
                    j = list.size() - 1;
                }
                imageView.setImage(images[j]);

            });
            imageView.setFitHeight(400);
            imageView.setFitWidth(600);
            hBox.setSpacing(15);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(imageView);
            root.add(hBox, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        j = j + 1;
                        if (j == list.size()) {
                            j = 0;
                        }
                        imageView.setImage(images[j]);
                    }
                }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    @FXML
    private void GestionCompte(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionCompteGui/GestionCompteCoach.fxml")); // kanit GestionCompteClient.fxml , now it is : GestionCompteCoach.fxml
            Parent root = loader.load();
            hBox.getScene().setRoot(root);

        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void logOut(MouseEvent event) {
        try {
            EncapsulationComtpe encapsulationCompte = new EncapsulationComtpe();
            ChatClient chatClient=encapsulationCompte.getClient();
            chatClient.logoff();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccueilVisiteur.fxml"));
            encapsulationCompte = new EncapsulationComtpe(0, "", "", "", 0, "", "", 0, "");
            Parent root = loader.load();
            hBox.getScene().setRoot(root);

        } catch (IOException ex) {
            ex.getMessage();
        }

    }

    @FXML
    private void ProfileCoach(MouseEvent event) {
        EncapsulationComtpe ec = new EncapsulationComtpe();

        ProfileService ps = new ProfileService();

        System.out.println(" lmlmlmlmlmlm " + ps.getProfileByCompteId(ec.getId()).getID_Coach());

        if (ps.getProfileByCompteId(ec.getId()).getID_Coach() == 0) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutProfile.fxml"));
                Parent root = loader.load();
                hBox.getScene().setRoot(root);

            } catch (IOException ex) {
                ex.getMessage();
            }
        } else {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutActualite.fxml"));
                Parent root = loader.load();
                hBox.getScene().setRoot(root);
                AjoutActualiteController dcp = loader.getController();
                dcp.setDescription(ps.getProfileByCompteId(ec.getId()).getDescription());
                dcp.setCategorie(ps.getProfileByCompteId(ec.getId()).getCatégorie());
                dcp.setDetail(ps.getProfileByCompteId(ec.getId()).getDétail());
                dcp.setRating(ps.getProfileByCompteId(ec.getId()).getRating());
                dcp.setimageuser1path(ps.getProfileByCompteId(ec.getId()).getPhoto());
                System.out.println("tatatatata  : " + ps.getProfileByCompteId(ec.getId()).getID_Coach());
                dcp.setIdCoach(ps.getProfileByCompteId(ec.getId()).getID_Coach());
                dcp.setIdCompte(ps.getProfileByCompteId(ec.getId()).getID_Compte());

            } catch (IOException ex) {
                ex.getMessage();
            }
        }

    }

    @FXML
    private void TraiterEvent(MouseEvent event) throws IOException {
        border.setVisible(true);
        Color c = Color.web("0xEEEEEE", 1.0);
        border.setBackground(new Background(new BackgroundFill(c, null, null)));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionEvent/GUIAccueilCoach/AfficherEventCoach.fxml"));
        Pane root = loader.load();
        border.setCenter(root);
        bb = border;
    }

    @FXML
    private void CreeEvent(MouseEvent event) throws IOException {
        border.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionEvent/GUIAccueilCoach/AjouterEventCoach.fxml"));
        Pane root = loader.load();
        border.setCenter(root);

    }

//    @FXML
//    private void basketball(MouseEvent event) throws IOException {
//        pass = txt2.getText();
//        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
//        primaryStage.setTitle("Ajouter une catégorie");
//        primaryStage.setScene(new Scene(root));
//
//        primaryStage.show();
//    }
//
//    @FXML
//    private void football(MouseEvent event) throws IOException {
//        pass = txt6.getText();
//        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
//        primaryStage.setTitle("Ajouter une catégorie");
//        primaryStage.setScene(new Scene(root));
//
//        primaryStage.show();
//    }
//
//    @FXML
//    private void athteltisme(MouseEvent event) throws IOException {
//        pass = txt1.getText().trim();
//        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
//        primaryStage.setTitle("Ajouter une catégorie");
//        primaryStage.setScene(new Scene(root));
//
//        primaryStage.show();
//    }
//
//    @FXML
//    private void natation(MouseEvent event) throws IOException {
//        pass = txt5.getText();
//        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
//        primaryStage.setTitle("Ajouter une catégorie");
//        primaryStage.setScene(new Scene(root));
//
//        primaryStage.show();
//    }
//
//    @FXML
//    private void tennis(MouseEvent event) throws IOException {
//        pass = txt4.getText();
//        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
//        primaryStage.setTitle("Ajouter une catégorie");
//        primaryStage.setScene(new Scene(root));
//
//        primaryStage.show();
//    }
//
//    @FXML
//    private void musculation(MouseEvent event) throws IOException {
//        pass = txt3.getText();
//        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
//        primaryStage.setTitle("Ajouter une catégorie");
//        primaryStage.setScene(new Scene(root));
//
//        primaryStage.show();
//    }
    @FXML
    private void basketball(MouseEvent event) throws IOException {
        pass = txt2.getText();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
        primaryStage.setTitle("Ajouter une catégorie");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    @FXML
    private void football(MouseEvent event) throws IOException {
        pass = txt6.getText();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
        primaryStage.setTitle("Ajouter une catégorie");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    @FXML
    private void athteltisme(MouseEvent event) throws IOException {
        pass = txt1.getText().trim();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
        primaryStage.setTitle("Ajouter une catégorie");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    @FXML
    private void natation(MouseEvent event) throws IOException {
        pass = txt5.getText();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
        primaryStage.setTitle("Ajouter une catégorie");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    @FXML
    private void tennis(MouseEvent event) throws IOException {
        pass = txt4.getText();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
        primaryStage.setTitle("Ajouter une catégorie");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    @FXML
    private void musculation(MouseEvent event) throws IOException {
        pass = txt3.getText();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../GestionOffre/gui/AccueilOffre.fxml"));
        primaryStage.setTitle("Ajouter une catégorie");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    public Object getClientoucoach() {
        return clientoucoach;
    }

    @FXML
    private void dirigerReclamation(MouseEvent event) throws IOException {
        border.setVisible(true);
        Color c = Color.web("0xEEEEEE",1.0);
        border.setBackground(new Background(new BackgroundFill(c, null, null)));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionReclamation/gui/ReclamationClient.fxml"));
        Pane root = loader.load();
        border.setCenter(root);
    }

    @FXML
    private void dirigerMessagerieEtAmis(MouseEvent event) throws IOException {
        border.setVisible(true);
        Color c = Color.web("0xEEEEEE",1.0);
        border.setBackground(new Background(new BackgroundFill(c, null, null)));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../MessagerieEtAmi/gui/MessagerieEtAmiClient.fxml"));
        Pane root = loader.load();
        border.setCenter(root);
    }

}
