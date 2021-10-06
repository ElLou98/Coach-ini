package gui;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SlideShowImage  {

    private List<String> list = new ArrayList<String>();
    private Image images[];
    int j = 0;
    double orgCliskSceneX, orgReleaseSceneX;
    Button lbutton, rButton;
    ImageView imageView;


//public void start(Stage primaryStage,SubScene primarySubScene) {
public void start(Stage primaryStage) {
    // images in src folder.
    try {
        list.add("../img/1.jpg");
        list.add("../img/2.jpg");
        list.add("../img/3.jpg");

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);

        lbutton = new Button("<");
        rButton = new Button(">");

        images = new Image[list.size()];
        for (int i = 0; i < list.size(); i++) {
            images[i] = new javafx.scene.image.Image(getClass().getResource(list.get(i)).toExternalForm());
        }

        imageView = new ImageView(images[j]);
        imageView.setCursor(Cursor.CLOSED_HAND);

        imageView.setOnMousePressed(circleOnMousePressedEventHandler);

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

        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);
        // hBox.getChildren().addAll(lbutton, imageView, rButton);
        hBox.getChildren().addAll(imageView);

        root.add(hBox, 1, 1);
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
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

EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

    @Override
    public void handle(MouseEvent t) {
        orgCliskSceneX = t.getSceneX();
    }
};



public static void main(String[] args) {
    launch(args);
}
}