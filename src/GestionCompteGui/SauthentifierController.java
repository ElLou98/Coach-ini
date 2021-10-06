/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompteGui;

import GestionCompte.entities.Compte;
import GestionCompte.entities.EncapsulationComtpe;
import GestionCompte.services.ClientCRUD;
import GestionCompte.services.CompteCRUD;
import GestionCompte.tools.JavaMailutil;
import gui.AccueilAdminController;
import gui.AccueilClientController;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.util.Duration;
import javax.naming.*;
import javax.naming.directory.*;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author masso
 */
public class SauthentifierController implements Initializable {

    @FXML
    private TextField in_userName;
    @FXML
    private TextField in_password;
    @FXML
    private Button btn_login;
    @FXML
    private Label mdp_message;
    @FXML
    private Label userName_message;

    private int count = 0;
    @FXML
    private PasswordField in_secret;
    @FXML
    private Label MOPOublié;
    @FXML
    private Label l_mdp;
    @FXML
    private Button btn_signInCoach;
    @FXML
    private Button btn_signInClient;
    @FXML
    private ImageView icon_show;
    @FXML
    private ImageView icon_hide;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        in_password.setPromptText("Mot de passe");
        in_secret.setPromptText("Mot de passe");
        in_userName.setPromptText("Nom d'utilisateur");
        icon_hide.setVisible(false);

        in_secret.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                in_password.setText(in_secret.getText());
            }
        });

        in_password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                in_secret.setText(in_password.getText());
            }
        });

    }

    private void delay() throws InterruptedException {

        Thread.sleep(5000);

    }

    ;

    @FXML
    private void inscrireClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SinscrireClient.fxml"));
            Parent root = loader.load();
            in_userName.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void authentifier(ActionEvent event) throws InterruptedException, IOException {
        count++;
        if (in_userName.getText().length() == 0) {
            in_userName.setStyle("-fx-border-color: red; -fx-borfrt-width: 2px;");
            new animatefx.animation.RubberBand(in_userName).play();
        } else {
            in_userName.setStyle(null);
        }

        if (in_password.getText().length() == 0) {
            in_password.setStyle("-fx-border-color: red; -fx-borfrt-width: 2px;");
            new animatefx.animation.RubberBand(in_password).play();
        } else {
            in_password.setStyle(null);
        }
        if (in_secret.getText().length() == 0) {
            in_secret.setStyle("-fx-border-color: red; -fx-borfrt-width: 2px;");
            new animatefx.animation.RubberBand(in_secret).play();
        } else {
            in_secret.setStyle(null);
        }
        String userName = in_userName.getText();
        if (CompteCRUD.existe(userName)) {
            String password = in_password.getText();
            password = CompteCRUD.encrypt(password);
            if (CompteCRUD.CompteExiste(userName, password) != 0) {
                int id = CompteCRUD.CompteExiste(userName, password);
                if (CompteCRUD.isAdmin(id)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AccueilAdmin.fxml"));
                    try {
                        Parent root = loader.load();
                        in_userName.getScene().setRoot(root);
                        AccueilAdminController aac = loader.getController();
                        aac.setId(id);
                        //EncapsulationComtpe encapsulationCompte= new EncapsulationComtpe(CompteCRUD.rechercherCompte(in_userName.getText()));

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else if (CompteCRUD.typeCompte(id) == "client") {
                    Compte c = CompteCRUD.rechercherCompte(id);
                    System.out.println(id);
                    EncapsulationComtpe encapsulationCompte = new EncapsulationComtpe(id, userName, c.getNom(), c.getPrenom(), c.getAge(), c.getAdresseMail(), c.getMotDePasse(), c.getNumTel());
                    System.out.println(EncapsulationComtpe.getId() );
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AccueilClient.fxml"));
                    try {
                        Parent root = loader.load();
                        in_userName.getScene().setRoot(root);
                        AccueilClientController acc = loader.getController();
                        acc.setId(id);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    Compte c = CompteCRUD.rechercherCompte(id);
                    EncapsulationComtpe encapsulationCompte = new EncapsulationComtpe(id, userName, c.getNom(), c.getPrenom(), c.getAge(), c.getAdresseMail(), c.getMotDePasse(), c.getNumTel());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AccueilCoach.fxml"));
                    try {
                        Parent root = loader.load();
                        in_userName.getScene().setRoot(root);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } else {
//                JOptionPane.showMessageDialog(null, "Ce mot de passe est incorrecte");
                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.SLIDE;
                tray.setAnimationType(type);
                tray.setTitle("sign in");
                tray.setMessage("Ce mot de passe est incorrecte!");
                tray.setNotificationType(NotificationType.ERROR);
                tray.showAndDismiss(Duration.millis(3000));
                if (count % 3 == 0) {
                    delay();
                }
            }
        } else {
//            JOptionPane.showMessageDialog(null, "Ce nom d'utilisateur n'existe pas!");
                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.SLIDE;
                tray.setAnimationType(type);
                tray.setTitle("authentifier");
                tray.setMessage("Ce nom d'utilisateur n'existe pas!");
                tray.setNotificationType(NotificationType.ERROR);
                tray.showAndDismiss(Duration.millis(3000));
        }
    }

    @FXML
    private void MDPOublié(MouseEvent event) {

        if (in_userName.getText().trim().isEmpty()) {
            l_mdp.setText("Vous devez saisir votre nom d'utilisateur.");
            l_mdp.setTextFill(Color.rgb(210, 39, 30));
            l_mdp.setLayoutX(524);
        } else if (!CompteCRUD.existe(in_userName.getText())) {
            l_mdp.setText("Ce nom d'utilisateur n'existe pas.");
            l_mdp.setTextFill(Color.rgb(210, 39, 30));
            l_mdp.setLayoutX(546);

        } else {
            Compte c = CompteCRUD.rechercherCompte(in_userName.getText());
            JavaMailutil.sendMail(c.getAdresseMail(), CompteCRUD.decrypt(c.getMotDePasse()));
            TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.POPUP;
                tray.setAnimationType(type);
                tray.setTitle("sign in");
                tray.setMessage("Verifier votre adresse mail!");
                tray.setNotificationType(NotificationType.INFORMATION);
                tray.showAndDismiss(Duration.millis(3000));
        }

    }

    @FXML
    private void inscrireCoach(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SinscrireCoach.fxml"));
            Parent root = loader.load();
            in_userName.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void hide(MouseEvent event) {
        in_secret.setVisible(true);
        in_password.setVisible(false);
        icon_hide.setVisible(false);
        icon_show.setVisible(true);

    }

    @FXML
    private void show(MouseEvent event) {
        in_secret.setVisible(false);
        in_password.setVisible(true);
        icon_hide.setVisible(true);
        icon_show.setVisible(false);
    }

}
