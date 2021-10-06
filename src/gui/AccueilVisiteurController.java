/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class AccueilVisiteurController implements Initializable {

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
    
    
    
    
    

    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
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
    private void authentifier(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionCompteGui/Sauthentifier.fxml"));
            Parent root = loader.load();
            hBox.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    


    @FXML
    private void signUpClient(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionCompteGui/SinscrireClient.fxml"));
            Parent root = loader.load();
            hBox.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    

    @FXML
    private void searchCoach(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchCoachVisitor.fxml"));
        Parent root = loader.load();

        imageView.getScene().setRoot(root);
    }

    @FXML
    private void coachSignUp(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionCompteGui/SinscrireCoach.fxml"));
            Parent root = loader.load();
            hBox.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    


   

    
}
