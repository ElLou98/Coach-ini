/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Tests;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kagha
 */
public class MainGUIEvent extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../GUIAccueilCoach/AccueilCoach.fxml"));
        Parent root= loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Ajouter un Event");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    public void Map (Stage primaryStage) throws IOException{
            Parent root = FXMLLoader.load(getClass().getResource("../GUI/map.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
