package GestionEvent.GUIAccueilCoach;

import GestionEvent.Models.Event;
import gui.AccueilCoachController;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import GestionEvent.Services.Service_Event;
import GestionEvent.Services.Service_Map;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.BadElementException;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import rest.file.uploader.tn.FileUploader;
import GestionEvent.Services.Service_Categorie_Event;
import GestionCompte.entities.EncapsulationComtpe;

/**
 * FXML Controller class
 *
 * @author kagha
 */
public class AfficherEventCoachController implements Initializable {

    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private ListView<String> event_list;
    @FXML
    private Button modifier_id;
    @FXML
    private Button supprimer_id;
    @FXML
    private Button detail_id;
    @FXML
    private BorderPane border_id;
    @FXML
    private TextField lookup;
    ObservableList<Event> dataList = FXCollections.observableArrayList();
    static String s;
    @FXML
    private ImageView image;
    static BufferedImage img;
    @FXML
    private Label space;
    @FXML
    private Button Back;
    public static ListView L;

    public static ListView getL() {
        return L;
    }

    public ObservableList getList() {
        return list;
    }

    public ListView<String> getEvent_list() {
        return event_list;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String selected = s;
        loadData();
        List<Event> e = new ArrayList<Event>();
        int i;
        for (i = 0; i < list.size(); i++) {
            e.add(new Event((String) list.get(i)));
            dataList.add(e.get(i));
        }
        L = event_list;
    }

    public void initialize() {
        loadData();
    }

    public void seeMe(ListView l) {
        Service_Event sp = new Service_Event();
        list.removeAll(l);
        EncapsulationComtpe cm = new EncapsulationComtpe();
        list.addAll(sp.afficher_nom(cm.getId()));
        System.out.println(list);
        l.setItems(list);
    }

    public void loadData() {
        //String  selected =event_list.getSelectionModel().getSelectedItem(); 
        Service_Event sp = new Service_Event();
        list.removeAll(event_list);
        EncapsulationComtpe cm = new EncapsulationComtpe();
        list.addAll(sp.afficher_nom(cm.getId()));
        System.out.println(cm.getId());
        event_list.setItems(list);
    }

    @FXML
    private void Detail(ActionEvent event) {
        try {
            Service_Event sp = new Service_Event();
            Service_Map mp = new Service_Map();
            String selected = s;
            AfficherEventCoachController e = new AfficherEventCoachController();
            if (selected == null || selected.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Séléctionner un événement a détaillée");
                return;
            }
            //System.out.println(selected);
            list.removeAll(event_list);
            list.clear();
            list.addAll(sp.fetch(String.valueOf(sp.get_ID(s))));
            list.remove(0);
            list.addAll(mp.detail(String.valueOf(sp.get_ID(s))));
            list.remove(9);
            //list.remove(10);
            // Create the ListView for the fruits
            Service_Categorie_Event sv = new Service_Categorie_Event();
            int a = Integer.parseInt((String) list.get(8));
            list.set(8, "Catégorie Event :" + sv.get_Nom(a));
            System.out.println(sp.fetch(selected));
            event_list.getSelectionModel().clearSelection();
            event_list.setItems(list);
            event_list.setMouseTransparent(true);
            event_list.setFocusTraversable(false);

            String fr = new String(list.toString().getBytes(), "UTF-8");
            //QR code
            //System.out.println(fr);
            String qrCodeData = fr;
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = null;
            try {
                matrix = new MultiFormatWriter().encode(
                        new String(qrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 200, 200, hintMap);
            } catch (WriterException ex) {
                Logger.getLogger(AfficherEventCoachController.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedImage bitimage = MatrixToImageWriter.toBufferedImage(matrix);
            Image image = SwingFXUtils.toFXImage(bitimage, null);
            this.image.setImage(image);
            img = bitimage;
            System.out.println("QR Code image created successfully!");

        } catch (IOException ex) {
            Logger.getLogger(AfficherEventCoachController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Supprimer(ActionEvent event) throws IOException {

        Service_Event sp = new Service_Event();
        Service_Map mp = new Service_Map();
        String selected = s;
        if (selected == null || selected.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Séléctionner un événement a Supprimer");
            return;
        }
        System.out.println(selected);
        FileUploader fu = new FileUploader("http://localhost/coachini/");
        String Db_picture = sp.db_Image(sp.get_ID(selected));
        System.out.println(Db_picture);
        if (Db_picture != null) {
            fu.delete(Db_picture.substring(Db_picture.indexOf("f")));
        }
        mp.supprimer(sp.get_ID(selected));
        sp.supprimer_nom(new Event(selected));
        //fu.delete(sp.db_Image(sp.get_ID(selected)).substring(sp.db_Image(sp.get_ID(selected)).indexOf("f")));
        //System.out.println(sp.db_Image(sp.get_ID(selected)).substring(sp.db_Image(sp.get_ID(selected)).indexOf("f")));
        //refresh 
        list.removeAll(event_list);
        list.clear();
        event_list.getSelectionModel().clearSelection();
        event_list.setItems(list);
        loadData();

    }

    @FXML
    private void Modifier(ActionEvent event) {
        try {
            /*FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/Version_4_Ajouter_Event.fxml"));
            Version_3Controller cr =loader.getController();
            cr.SetTextField1("testing");*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUIAccueilCoach/ModifierEventCoach.fxml"));
            Parent root = loader.load();
            ModifierEventCoachController cr = loader.getController();
            Service_Event sp = new Service_Event();
            Service_Map mp = new Service_Map();
            String selected = String.valueOf(sp.get_ID(s));
            //System.out.println(sp.get_ID(s));
            if (selected == null || selected.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Séléctionner un événement a Modifier");
                return;

            }
            List<String> lm = mp.fetch(selected);
            System.out.println(lm.get(1));
            System.out.println(lm.get(2));
            cr.setLocation("(" + lm.get(1) + "," + lm.get(2) + ")" + lm.get(3));
            cr.SetTextField1(sp.fetch(selected).get(1).substring(sp.fetch(selected).get(1).lastIndexOf(":") + 1).trim());
            //System.out.println(sp.fetch(selected).get(1).substring(sp.fetch(selected).get(1).lastIndexOf(":") + 1).trim());
            //System.out.println(sp.fetch(selected).get(1).substring(sp.fetch(selected).get(1).lastIndexOf(":") + 1));
            //cr.TextField1Disable();
            cr.SetTextField2(sp.fetch(selected).get(7).substring(sp.fetch(selected).get(7).lastIndexOf(":") + 1).trim());
            cr.SetTextField3(sp.fetch(selected).get(8).substring(sp.fetch(selected).get(8).lastIndexOf(":") + 1).trim());
            cr.SetComboBox1(sp.fetch(selected).get(3).substring(sp.fetch(selected).get(3).indexOf(":") + 1, sp.fetch(selected).get(3).length()).trim());
            cr.SetComboBox2(sp.fetch(selected).get(5).substring(sp.fetch(selected).get(5).indexOf(":") + 1, sp.fetch(selected).get(5).length()).trim());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate1 = LocalDate.parse(sp.fetch(selected).get(2).substring(sp.fetch(selected).get(2).lastIndexOf(":") + 2), formatter);
            LocalDate localDate2 = LocalDate.parse(sp.fetch(selected).get(4).substring(sp.fetch(selected).get(4).lastIndexOf(":") + 2), formatter);
            cr.DatePicker1(localDate1);
            cr.DatePicker2(localDate2);
            switch (sp.fetch(selected).get(6).substring(sp.fetch(selected).get(6).lastIndexOf(":") + 2).trim()) {
                case "En Ligne":
                    cr.SetRadio1();
                    break;
                case "En Personne":
                    cr.SetRadio2();
                    break;
            }
            Service_Categorie_Event cat = new Service_Categorie_Event();
            cr.setBtn_Cat(cat.get_Nom(Integer.valueOf(sp.fetch(selected).get(9))));
            //System.out.println(sp.fetch(selected).get(6).substring(sp.fetch(selected).get(1).lastIndexOf(":") + 2));
            cr.setNom(sp.fetch(selected).get(1).substring(sp.fetch(selected).get(1).lastIndexOf(":") + 1).trim());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            //loadData();
        } catch (IOException ex) {
            Logger.getLogger(AfficherEventCoachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void LookUp(ActionEvent event) {
    }

    @FXML
    private void test(KeyEvent event) {
        //String  selected =getSelected();
        //System.out.println(selected);
        ListView<Event> event_list = null;
        Service_Event sp = new Service_Event();
        EncapsulationComtpe cm = new EncapsulationComtpe();
        list.addAll(sp.afficher_nom(cm.getId()));
        //System.out.println(sp.afficher_nom());
        //list.removeAll(this.event_list);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Event> filteredData = new FilteredList<>(dataList, b -> true);
        //System.out.println(dataList);
        //System.out.println(filteredData);

        // 2. Set the filter Predicate whenever the filter changes.
        lookup.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Event -> {
                // If filter text is empty, display all 
                //System.out.println(newValue);

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name 
                String lowerCaseFilter = newValue.toLowerCase();

                if (Event.getNom_event().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    //System.out.println(lowerCaseFilter);
                    return true; // Filter matches first name.
                } else {
                    return false; // Does not match.
                }

            });
        });
        SortedList<Event> sortedData = new SortedList<>(filteredData);
        //sortedData.toString().substring(sortedData.lastIndexOf("Nom_event") + 1)
        //System.out.println(sortedData);
        //System.out.println(filteredData.toString().substring(filteredData.toString().indexOf("=", filteredData.toString().indexOf("=") + 1), 40) );
        ObservableList<String> observableList = FXCollections.observableList((List) sortedData);
        //System.out.println(observableList);
        this.event_list.setItems(observableList);

    }

    @FXML
    private void getSelected(MouseEvent event) {
        //ListView<Event> event_list = null;
        Event e = new Event(String.valueOf(event_list.getSelectionModel().getSelectedItem()));
        s = e.toString();
        //System.out.println(e);
    }

    public static String getIt() {
        return s;
    }

    @FXML
    private void download(MouseEvent event) {
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                File fileToSave = fileChooser.getSelectedFile();
                ImageIO.write(img, "png", fileToSave);
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(AfficherEventCoachController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void pdf_Download(MouseEvent event) throws FileNotFoundException, DocumentException, IOException {

        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());

            String file_name = fileToSave.getAbsolutePath();
            Document document = new Document();
            //simple paragraph
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file_name));
            writer.setPageEvent(new PDFBackground());
            //PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();
            //Paragraph para = new Paragraph("testing");
            Service_Event sp = new Service_Event();
            Service_Map mp = new Service_Map();
            String selected = String.valueOf(sp.get_ID(s));

            FixText(sp.fetch(selected).get(1), 120, 600, writer, 26, (new BaseColor(40, 140, 172)));
            FixText(sp.fetch(selected).get(7), 180, 560, writer, 20, (new BaseColor(99, 241, 255)));
            FixText(sp.fetch(selected).get(3), 180, 480, writer, 20, BaseColor.WHITE);
            FixText(sp.fetch(selected).get(5), 180, 440, writer, 20, BaseColor.WHITE);
            FixText(sp.fetch(selected).get(2), 180, 400, writer, 20, (new BaseColor(99, 241, 255)));
            FixText(sp.fetch(selected).get(4), 180, 360, writer, 20, BaseColor.WHITE);
            FixText(sp.fetch(selected).get(6), 180, 320, writer, 20, BaseColor.WHITE);
            FixText(sp.fetch(selected).get(8), 180, 520, writer, 20, BaseColor.WHITE);
            List<String> lm = mp.fetch(selected);
            FixText("Localisation=(" + lm.get(1) + "," + lm.get(2) + ")", 180, 280, writer, 20, BaseColor.WHITE);
            Service_Categorie_Event sc = new Service_Categorie_Event();
            System.out.println(Integer.valueOf(sp.fetch(selected).get(0).trim()));
            FixText("Catégorie: " + sc.get_Nom(Integer.valueOf(sp.fetch(selected).get(9))), 180, 240, writer, 20, BaseColor.WHITE);
            com.itextpdf.text.Image image1 = com.itextpdf.text.Image.getInstance("src/GestionEvent/Icons/main.png");
            image1.setAbsolutePosition(240f, 640f);
            image1.scaleAbsolute(100, 100);
            document.add(image1);

            com.itextpdf.text.Image image2 = com.itextpdf.text.Image.getInstance("http://" + sp.get_Db_picture(Integer.valueOf(sp.fetch(selected).get(0))));
            image2.setAbsolutePosition(160f, 110f);
            image2.scaleAbsolute(120, 120);
            document.add(image2);

            com.itextpdf.text.Image image3 = com.itextpdf.text.Image.getInstance("http://" + sc.get_Db_picture(sc.get_ID(sc.get_Nom(Integer.valueOf(sp.fetch(selected).get(9))))));
            image3.setAbsolutePosition(150f, 238f);
            image3.scaleAbsolute(20, 20);
            document.add(image3);

            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to add a Picture to pdf?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                //System.out.println("No button clicked");

            } else if (response == JOptionPane.YES_OPTION) {

                JFileChooser c = new JFileChooser();
                FileFilter imageFilter = new FileNameExtensionFilter(
                        "Image files", ImageIO.getReaderFileSuffixes());
                c.setFileFilter(imageFilter);
                int rVal = c.showOpenDialog(new JPanel());
                File file = c.getSelectedFile();
                com.itextpdf.text.Image image4 = com.itextpdf.text.Image.getInstance(file.getAbsolutePath());
                image4.setAbsolutePosition(310f, 110f);
                image4.scaleAbsolute(120, 120);
                document.add(image4);
                document.close();

            } else if (response == JOptionPane.CLOSED_OPTION) {
                System.out.println("JOptionPane closed");
            }

            if (response == JOptionPane.NO_OPTION) {
                document.close();
            }
        }
    }

    public static void FixText(String text, int x, int y, PdfWriter writer, int size, BaseColor bc) throws IOException {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, size);
            cb.setColorFill(bc);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherEventCoach.fxml"));
        Pane root = loader.load();
        AccueilCoachController ac = new AccueilCoachController();
        BorderPane bb = ac.getBb();
        ac.setBorder(bb);
        ac.getBorder().setCenter(root);
    }

}
