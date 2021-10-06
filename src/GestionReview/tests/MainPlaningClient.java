/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReview.tests;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

/**
 *
 * @author Adem
 */
public class MainPlaningClient extends Application { // planing Client 
    
    @Override
    public void start(Stage primaryStage) {
        VCalendar mainVCalendar = new VCalendar()
                .withProductIdentifier(ICalendarAgenda.DEFAULT_PRODUCT_IDENTIFIER).withVersion();

        BorderPane root = new BorderPane();
        ICalendarAgenda agenda = new ICalendarAgenda(mainVCalendar);
        root.setCenter(agenda);

        Button increaseweek = new Button(" Week >> ");
        Button decreaseweek = new Button(" << Week");
        Button yearplus = new Button(" Year >>");
        Button yearminus = new Button(" << Year ");
        Button importbtn = new Button(" Import Calender ");
        //Button Exportbtn = new Button(" Export Calender ");
        //HBox buttonHboxTime = new HBox(yearminus, yearplus,decreaseweek, increaseweek,importbtn,Exportbtn);
        HBox buttonHboxTime = new HBox(yearminus, yearplus, decreaseweek, increaseweek, importbtn);
        buttonHboxTime.setSpacing(5);
        root.setTop(buttonHboxTime);

        //weeks
        increaseweek.setOnAction((e)
                -> {
            LocalDateTime newDisplayedLocalDateTime = agenda.getDisplayedLocalDateTime().plus(Period.ofWeeks(1));
            agenda.setDisplayedLocalDateTime(newDisplayedLocalDateTime);
        });
        decreaseweek.setOnAction((e)
                -> {
            LocalDateTime newDisplayedLocalDateTime = agenda.getDisplayedLocalDateTime().minus(Period.ofWeeks(1));
            agenda.setDisplayedLocalDateTime(newDisplayedLocalDateTime);
        });

        //years 
        yearplus.setOnAction((e)
                -> {
            LocalDateTime newDisplayedLocalDateTime = agenda.getDisplayedLocalDateTime().plus(Period.ofYears(1));
            agenda.setDisplayedLocalDateTime(newDisplayedLocalDateTime);
        });
        yearminus.setOnAction((e)
                -> {
            LocalDateTime newDisplayedLocalDateTime = agenda.getDisplayedLocalDateTime().minus(Period.ofYears(1));
            agenda.setDisplayedLocalDateTime(newDisplayedLocalDateTime);
        });

        //import ics file
        FileChooser filechooser = new FileChooser();
        filechooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("ics files", "*.ics"));
        importbtn.setOnAction(e -> 
        {
        File file = filechooser.showOpenDialog(primaryStage);
            if (file!=null && file.toString().lastIndexOf("ics")>0) {
                try {
                    // process ITIP and log exceptions
                    final List <String> log = new ArrayList<>();
                    VCalendar iTIPMessage = VCalendar.parseICalendarFile(file.toPath());
                    Thread.setDefaultUncaughtExceptionHandler((thread,exception) -> log.add(exception.getMessage()));
                    List <String> messagelog = mainVCalendar.processITIPMessage(iTIPMessage);
                    log.addAll(messagelog);
                    log.forEach(System.out::println);
                    JOptionPane.showMessageDialog(null, "Your Calender has been successfully Imported! \n Please refrech your calender! \n"
                            + "by Increase and decreasing weeks just one time! ");
                    
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Invalide file :" + file.getName() + ". Please Select a valide ics file");
                throw new IllegalArgumentException("Invalide file :" + file + ". Please Select a valide ics file");
            }
        // try to disable the editing if you had time left 
        });
        Scene scene = new Scene(root, 1366, 786);
        primaryStage.setScene(scene);
        primaryStage.setTitle("test calendarAgenda , Planing");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
