/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.GUIAccueilCoach;

import static GestionEvent.GUICategorieAdmin.AfficherCategorieController.getIt;
import GestionEvent.GUICategorieAdmin.CategorieController;
import GestionEvent.GUIMap.MapController;
import GestionEvent.GUIMap.WebViewCaptureMap;
import GestionEvent.Models.Event;
import GestionEvent.Models.myMap;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import rest.file.uploader.tn.FileUploader;
import GestionEvent.Services.Service_Categorie_Event;
import GestionEvent.Services.Service_Event;
import GestionEvent.Services.Service_Map;
import javafx.scene.input.MouseEvent;
import GestionCompte.entities.EncapsulationComtpe;

/**
 * FXML Controller class
 *
 * @author kagha
 */
public class AjouterEventCoachController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private DatePicker date_Debut;
    @FXML
    private DatePicker date_Fin;
    @FXML
    private TextArea description;
    @FXML
    private TextField nb_Participants;
    @FXML
    private ComboBox combo_Box;
    @FXML
    private ComboBox combo_Box1;
    @FXML
    private Button btn_annuler;
    @FXML
    private ToggleGroup tg;
    @FXML
    private Text Location;
    @FXML
    private RadioButton radio_Ligne;
    @FXML
    private RadioButton radio_Personne;
    @FXML
    private Button btn_valider;
    @FXML
    private Button btn_Cat;
    @FXML
    private Button btn_localiser;
    private String nom;
    @FXML
    private ImageView mapImage;
    @FXML
    private ImageView CatImage;
    static String map_path;
    private static String img_path;

    @FXML
    private void End_event(ActionEvent event) {
        Stage stage = (Stage) btn_annuler.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void select(ActionEvent event) {
        String s = combo_Box.getSelectionModel().getSelectedItem().toString();
        //label.setText(s);
    }

    public void SetTextField1(String a) {
        this.tfNom.setText(a);
    }

    public void setBtn_Cat(String btn_Cat) {
        this.btn_Cat.setText(btn_Cat);
    }

    public void TextField1Disable() {
        this.tfNom.setDisable(true);
    }

    public void SetTextField2(String a) {
        this.nb_Participants.setText(a);
    }

    public void SetTextField3(String a) {
        this.description.setText(a);
    }

    public void DatePicker1(LocalDate d) {
        this.date_Debut.setValue(d);
    }

    public void DatePicker2(LocalDate d) {
        this.date_Fin.setValue(d);
    }

    public void SetRadio1() {
        this.radio_Ligne.setSelected(true);
    }

    public void SetRadio2() {
        this.radio_Personne.setSelected(true);
    }

    public void SetComboBox1(String a) {
        this.combo_Box.setValue(a);
    }

    public void SetComboBox2(String a) {
        this.combo_Box1.setValue(a);
    }

    public void setLocation(String Location) {
        this.Location.setText(Location);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] quarterHours = {"00", "15", "30", "45"};
        List<String> times = new ArrayList<String>();

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 4; j++) {
                String time = i + ":" + quarterHours[j];
                if (i < 10) {
                    time = "0" + time;
                }
                times.add(time);
            }
        }
        ObservableList<String> list = FXCollections.observableArrayList(times);
        combo_Box.setItems(list);
        combo_Box1.setItems(list);
        nom = tfNom.getText().toString();
        /*
        Service_Categorie_Event cr = new Service_Categorie_Event();
        Service_Event se = new Service_Event();
        AfficherEventCoachController ae = new AfficherEventCoachController();
        int id_img = Integer.valueOf(se.db_CAT(se.get_ID(ae.getIt())));
        String Db_picture = cr.get_Db_picture(id_img);
        System.out.println(Db_picture);
        File pic = new File(Db_picture);
        Image ig = new Image("http://" + Db_picture);
        CatImage.setImage(ig);
         */
    }

    @FXML
    public void Ajouter_Event(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {

        String res = null;
        radio_Ligne.setToggleGroup(tg);
        radio_Personne.setToggleGroup(tg);
        switch (tg.getToggles().indexOf(tg.getSelectedToggle())) {
            case 0:
                res = ((RadioButton) tg.getSelectedToggle()).getText();
                break;
            case 1:
                res = ((RadioButton) tg.getSelectedToggle()).getText();
                break;
        }

        String s = "";
        boolean isNumeric = nb_Participants.getText().trim().chars().allMatch(Character::isDigit);
        /*if (!(btn_localiser.isDisabled())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Problem dans l'insertion ");
            alert.setHeaderText("Erreur");
            alert.setContentText("tu dois capturer l'image du map pour la stocker");
            alert.showAndWait();
            return;
        }*/
        if (tfNom.getText().trim().isEmpty() || nb_Participants.getText().trim().isEmpty() || description.getText().trim().isEmpty() || date_Debut.getValue() == null || date_Fin.getValue() == null || combo_Box.getSelectionModel().isEmpty() || combo_Box1.getSelectionModel().isEmpty() || tg.getSelectedToggle() == null || !isNumeric || Location.getText().trim().isEmpty() || btn_Cat.getText().equals("Choisir Catégorie") || btn_Cat.getText().isEmpty()) {
            if (!isNumeric) {
                s = s + "Participants doit être un entier ";
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Problem dans l'insertion ");
            alert.setHeaderText("Erreur");
            alert.setContentText("Attribue doit être  ni nulle ni vide \n" + s);
            alert.showAndWait();
            return;
        }

        Service_Event se = new Service_Event();
        //System.out.println(se.Name_Exist(tfNom.getText()));
        EncapsulationComtpe cm = new EncapsulationComtpe();
        boolean b = false;
        if (se.Name_ExistTB(tfNom.getText(),cm.getId()) >= 1) {
            b = true;
        } else {
            b = false;
        }
        if (b) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Problem dans l'insertion ");
            alert.setHeaderText("Erreur");
            alert.setContentText("Event existe déja " + s);
            alert.showAndWait();
            return;
        }
        LocalDate value1 = date_Debut.getValue();
        LocalDate value2 = date_Fin.getValue();
        CategorieController ct = new CategorieController();
        Service_Categorie_Event cat = new Service_Categorie_Event();
        WebViewCaptureMap wc = new WebViewCaptureMap();
        if (wc.getDb_path() == null) {
            System.out.println(cm.getId());
            se.ajouter(new Event(tfNom.getText(), Date.valueOf(value1), (String) combo_Box.getValue(), Date.valueOf(value2), (String) combo_Box1.getValue(), res, Integer.parseInt(nb_Participants.getText()), description.getText(), cat.get_ID(btn_Cat.getText()), map_path,cm.getId(),Location.getText()));
            
        }
        if (wc.getDb_path() != null) {
            System.out.println(cm.getId());
            se.ajouter(new Event(tfNom.getText(), Date.valueOf(value1), (String) combo_Box.getValue(), Date.valueOf(value2), (String) combo_Box1.getValue(), res, Integer.parseInt(nb_Participants.getText()), description.getText(), cat.get_ID(btn_Cat.getText()), wc.getDb_path(),cm.getId(),Location.getText()));
        }
        JOptionPane.showMessageDialog(null, "Event ajoutée !");

        Service_Map sm = new Service_Map();
        String lati = Location.getText().substring(Location.getText().indexOf("(") + 1, Location.getText().indexOf(",")).trim();
        String longi = Location.getText().substring(Location.getText().indexOf(",") + 1, Location.getText().indexOf(")")).trim();
        String lieu = Location.getText().substring(Location.getText().indexOf(")") + 1).trim();
        if (lieu == "") {
            lieu = "SCAN QR";
        }

        sm.ajouter(new myMap(se.get_Last_ID(), Float.parseFloat(lati), Float.parseFloat(longi), lieu));
        //pdf

        AfficherEventCoachController ev = new AfficherEventCoachController();
        String userDirectory = new File("").getAbsolutePath() + "/test.pdf";
        System.out.println(userDirectory);
        String file_name = userDirectory;
        Document document = new Document();
        //simple paragraph
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file_name));
        writer.setPageEvent(new PDFBackground());
        //PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        //Paragraph para = new Paragraph("testing");
        ev.FixText("Nom Event: " + tfNom.getText().toString(), 120, 600, writer, 26, (new BaseColor(40, 140, 172)));
        ev.FixText("Nombre des Participants: " + nb_Participants.getText().toString(), 180, 560, writer, 20, (new BaseColor(99, 241, 255)));
        ev.FixText("Description: " + description.getText().toString(), 180, 520, writer, 20, BaseColor.WHITE);
        ev.FixText("Heure Début : " + combo_Box.getValue().toString(), 180, 480, writer, 20, BaseColor.WHITE);
        ev.FixText("Heure Fin : " + combo_Box1.getValue().toString(), 180, 440, writer, 20, (new BaseColor(99, 241, 255)));
        ev.FixText("Date Début : " + date_Debut.getValue().toString(), 180, 400, writer, 20, BaseColor.WHITE);
        ev.FixText("Date fin : " + date_Fin.getValue().toString(), 180, 360, writer, 20, BaseColor.WHITE);
        ev.FixText("Participation : " + res, 180, 320, writer, 20, BaseColor.WHITE);
        ev.FixText("Catégorie: " + btn_Cat.getText(), 180, 240, writer, 20, BaseColor.WHITE);
        ev.FixText(Location.getText(), 180, 280, writer, 20, BaseColor.WHITE);

        com.itextpdf.text.Image image1 = com.itextpdf.text.Image.getInstance("src/GestionEvent/Icons/main.png");
        image1.setAbsolutePosition(240f, 640f);
        image1.scaleAbsolute(100, 100);
        document.add(image1);
        WebViewCaptureMap w = new WebViewCaptureMap();
        //System.out.println(w.getDb_path());
        if (w.getDb_path() != null) {
            com.itextpdf.text.Image image2 = com.itextpdf.text.Image.getInstance("http://" + w.getDb_path());
            image2.setAbsolutePosition(240f, 110f);
            image2.scaleAbsolute(120, 120);
            document.add(image2);
        }
        Service_Categorie_Event sc = new Service_Categorie_Event();
        com.itextpdf.text.Image image3 = com.itextpdf.text.Image.getInstance("http://" + sc.get_Db_picture(sc.get_ID(btn_Cat.getText())));
        image3.setAbsolutePosition(150f, 238f);
        image3.scaleAbsolute(20, 20);
        document.add(image3);
        document.close();
        //javaMail.sendMail("");
        //reset
        tfNom.clear();
        date_Debut.setValue(null);
        date_Fin.setValue(null);
        radio_Personne.setSelected(false);
        radio_Ligne.setSelected(false);
        description.clear();
        nb_Participants.clear();
        Location.setText("");
        combo_Box.getSelectionModel().selectFirst();
        combo_Box1.getSelectionModel().selectFirst();
        btn_Cat.setText("Choisir Catégorie");
        btn_localiser.setDisable(false);
        File file1 = new File("src/GestionEvent/Icons/map.png");
        File file2 = new File("src/GestionEvent/Icons/categorie.png");
        Image img_cat = new Image(file2.toURI().toString());
        Image img_map = new Image(file1.toURI().toString());
        CatImage.setImage(img_cat);
        mapImage.setFitHeight(61);
        mapImage.setFitWidth(56);
        mapImage.setX(0);
        mapImage.setY(0);
        mapImage.setImage(img_map);

        //combo_Box.getButtonCell().setText("");
        //combo_Box1.getButtonCell().setText("");   
    }

    public void Modifier_Event(ActionEvent event) throws IOException {
        String res = null;
        radio_Ligne.setToggleGroup(tg);
        radio_Personne.setToggleGroup(tg);
        switch (tg.getToggles().indexOf(tg.getSelectedToggle())) {
            case 0:
                res = ((RadioButton) tg.getSelectedToggle()).getText();
                break;
            case 1:
                res = ((RadioButton) tg.getSelectedToggle()).getText();
                break;
        }

        Service_Event se = new Service_Event();
        /*if (!(btn_localiser.isDisabled())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Problem dans l'insertion ");
            alert.setHeaderText("Erreur");
            alert.setContentText("tu dois capturer l'image du map pour la stocker");
            alert.showAndWait();
            return;
        }*/

        Service_Event sp = new Service_Event();
        FileUploader fu = new FileUploader("http://localhost/coachini/");
        String Db_picture = sp.db_Image(sp.get_ID(nom));
        System.out.println(Db_picture);

        Service_Map mp = new Service_Map();
        String lati = Location.getText().substring(Location.getText().indexOf("(") + 1, Location.getText().indexOf(",")).trim();
        String longi = Location.getText().substring(Location.getText().indexOf(",") + 1, Location.getText().indexOf(")")).trim();
        String lieu = Location.getText().substring(Location.getText().indexOf(")") + 1).trim();
        mp.modifier(new myMap(se.get_ID(AfficherEventCoachController.getIt()), Float.valueOf(lati), Float.valueOf(longi), lieu));
        AfficherEventCoachController e = new AfficherEventCoachController();
        LocalDate value1 = date_Debut.getValue();
        LocalDate value2 = date_Fin.getValue();
        CategorieController ct = new CategorieController();
        Service_Categorie_Event cat = new Service_Categorie_Event();
        WebViewCaptureMap wc = new WebViewCaptureMap();
        if (!btn_localiser.isDisabled()) {
            se.modifier(new Event(se.get_ID(AfficherEventCoachController.getIt()), tfNom.getText(), Date.valueOf(value1), (String) combo_Box.getValue(), Date.valueOf(value2), (String) combo_Box1.getValue(), res, Integer.valueOf(nb_Participants.getText().trim()), description.getText(), Integer.valueOf(cat.get_ID(btn_Cat.getText())), sp.get_Db_picture(se.get_ID(AfficherEventCoachController.getIt()))));
        }
        if (btn_localiser.isDisabled()) {
            se.modifier(new Event(se.get_ID(AfficherEventCoachController.getIt()), tfNom.getText(), Date.valueOf(value1), (String) combo_Box.getValue(), Date.valueOf(value2), (String) combo_Box1.getValue(), res, Integer.valueOf(nb_Participants.getText().trim()), description.getText(), Integer.valueOf(cat.get_ID(btn_Cat.getText())), wc.getDb_path()));
            fu.delete(Db_picture.substring(Db_picture.indexOf("f")));
        }
        JOptionPane.showMessageDialog(null, "Event Modifiée!");
        Stage stage = (Stage) tfNom.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                // try {
                System.out.println("Stage is closing");
                /* FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("Main_GUI.fxml"));
                    Main_GUIController mg = loader.load();
                    mg.Traiter(new ActionEvent());
                } catch (IOException ex) {
                    Logger.getLogger(Version_3Controller.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        });

    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @FXML
    private void Localiser(ActionEvent event) throws Exception {
        //try {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("map.fxml"));
            Parent root = loader.load();
            MapController cr = loader.getController();*/
        WebViewCaptureMap w = new WebViewCaptureMap();
        Stage stage = new Stage();
        w.start(stage);
        /*stage.setScene(new Scene(root));
            MainGUICategorie m = new MainGUICategorie();
            m.Map(stage);
            stage.show();*/

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                MapController mp = new MapController();
                Location.setText(mp.getMap_value());
                if (!"".equals(Location.getText())) {
                    btn_localiser.setDisable(true);
                    WebViewCaptureMap w = new WebViewCaptureMap();
                    String m = "http://" + w.getDb_path();
                    img_path = m;
                    mapImage.setImage(new Image(m));
                    mapImage.setFitHeight(150);
                    mapImage.setFitWidth(150);
                    mapImage.setX(-40);
                    mapImage.setY(-30);
                    //System.out.println("done");
                }

            }
        });

        /*} catch (IOException ex) {
            Logger.getLogger(Version_3Controller.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public static String readQRCode(String filePath, String charset, Map hintMap)
            throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new FileInputStream(filePath)))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
        return qrCodeResult.getText();
    }

    @FXML
    public void QR(ActionEvent event) throws IOException {

        JFileChooser dialogue = new JFileChooser();
        PrintWriter sortie = null;
        File fichier;

        if (dialogue.showOpenDialog(null)
                == JFileChooser.APPROVE_OPTION) {
            fichier = dialogue.getSelectedFile();
            sortie = new PrintWriter(new FileWriter(fichier.getPath(), true));
            //System.out.println(fichier.getPath());
            try {
                String filePath = fichier.getPath();
                String charset = "UTF-8";
                Hashtable hints = new Hashtable();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                //System.out.println("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
                //System.out.println(readQRCode(filePath, charset, hintMap));
                String s1 = readQRCode(filePath, charset, hintMap);
                String replace = s1.replace("[", "");
                replace = replace.replace("]", "");
                replace = replace.replace("Nom : ", "");
                replace = replace.replace("Date debut : ", "");
                replace = replace.replace("Date fin : ", "");
                replace = replace.replace("Heure debut : ", "");
                replace = replace.replace("Heure fin : ", "");
                replace = replace.replace("Participation: ", "");
                replace = replace.replace("Nombre des participants: ", "");
                replace = replace.replace("Description:", "");
                replace = replace.replace("Catégorie Event :", "");
                replace = replace.replace("Latitude:", "");
                replace = replace.replace("Longitude:", "");
                replace = replace.replace("Catégorie Event :", "");
                //System.out.println(replace);
                List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(",")));
                tfNom.setText(myList.get(0).trim());
                System.out.println(myList);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate1 = LocalDate.parse(myList.get(1).trim(), formatter);
                LocalDate localDate2 = LocalDate.parse(myList.get(3).trim(), formatter);
                date_Debut.setValue(localDate1);
                date_Fin.setValue(localDate2);
                combo_Box.setValue(myList.get(2).trim());
                combo_Box1.setValue(myList.get(4).trim());
                switch (myList.get(5).trim()) {
                    case "En Ligne":
                        SetRadio1();
                        break;
                    case "En Personne":
                        SetRadio2();
                        break;
                }

                nb_Participants.setText(myList.get(6).trim());
                description.setText(myList.get(7));
                btn_Cat.setText(myList.get(8).trim());
                Location.setText("(" + myList.get(9) + "," + myList.get(10) + ")");
                Service_Categorie_Event cr = new Service_Categorie_Event();
                Service_Event se = new Service_Event();
                AfficherEventCoachController ae = new AfficherEventCoachController();
                int id_img = Integer.valueOf(cr.get_ID(btn_Cat.getText()));
                String Db_picture = cr.get_Db_picture(id_img);
                System.out.println(Db_picture);
                File pic = new File(Db_picture);
                Image ig = new Image("http://" + Db_picture);
                CatImage.setImage(ig);
                Service_Event sv = new Service_Event();
                System.out.println(sv.get_Db_picture(sv.get_ID(myList.get(0).trim())));
                mapImage.setImage(new Image("http://" + sv.get_Db_picture(sv.get_ID(myList.get(0).trim()))));
                img_path = "http://" + sv.get_Db_picture(sv.get_ID(myList.get(0).trim()));
                System.out.println(sv.get_Db_picture(sv.get_ID(myList.get(0).trim())));           
                if (!"".equals(sv.get_Db_picture(sv.get_ID(myList.get(0).trim())))){
                map_path = sv.get_Db_picture(sv.get_ID(myList.get(0).trim()));
                mapImage.setFitHeight(150);
                mapImage.setFitWidth(150);
                mapImage.setX(-20);
                mapImage.setY(-30);}
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        //sortie.close();
    }

    @FXML
    private void Categorie(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUICategorieAdmin/Categorie.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        //stage.close();  
        CategorieController ct = new CategorieController();
        Service_Categorie_Event sc = new Service_Categorie_Event();
        Service_Event se = new Service_Event();
        //System.out.println(ct.getCat());
        btn_Cat.setText(ct.getCat());
        String img = "http://" + sc.get_Db_picture(sc.get_ID(ct.getCat()));
        System.out.println(img);
        CatImage.setImage(new Image(img));

    }

    @FXML
    private void zoom(MouseEvent event) {
        zoomer z = new zoomer();
        Stage stage = new Stage();
        z.start(stage, img_path);
    }
}
