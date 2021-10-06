/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import GestionCompte.entities.EncapsulationComtpe;
import GestionProfile.entities.Actualite;
import GestionProfile.services.ActualiteService;
import GestionProfile.services.ProfileService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Espace Info
 */
public class ProfileController implements Initializable {

    String imagepath;
    String imageuserpath;
    String fichierpath;
    String competence, biographie;
    boolean isClicked = false;
//    @FXML
//    private ListView liste;
    @FXML
    private Text nom;
    @FXML
    private Text description;
    @FXML
    private Text detail;
    @FXML
    private Text categorie;
    @FXML
    private Text rating;

    @FXML
    private ImageView like;
    @FXML
    private ImageView delete;
    @FXML
    private ImageView imageuser2;
    private ImageView imageView = new ImageView();
    private String pub;
    int id;

    int likenumber = 0;
    List<Actualite> list;
    int indexx = 0;
  @FXML
    private ListView list2;
    @FXML
    private Label nome;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         EncapsulationComtpe ec=new EncapsulationComtpe();
         nome.setText(ec.getNom()+" "+ec.getPrenom());
       
        ActualiteService sp = new ActualiteService();
        list = sp.afficher();
        System.out.println(list);
        List imageList = new ArrayList();
        
         

        for (int i = 0; i < list.size(); i++) {

//            Image image = new Image("file:/"+list.get(i).getImage());
//        imageView.setImage(image);
            list2.getItems().add(list.get(i));

            // File file = new File(list.get(i).getImage());
            imageList.add(list.get(i).getImage());

//                      liste.getItems().add("Statut: "+list.get(i).getStatut()+"\n Compétence:"+list.get(i).getCompétence()+"\n Biographie: "+list.get(i).getBio()); 
        }
        System.out.println("image 1 " + imageList.size());
//           list2.setCellFactory(param -> new ListCell<Label>()
        list2.setCellFactory(param -> new ListCell<Actualite>() {

            //private ImageView imageView = new ImageView();
Label l = new Label();  
            @Override
            public void updateItem(Actualite name, boolean empty) {

                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {

                    try {
                        l.setGraphic(new ImageView(new Image("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+ name.getImage(), 100, 100, false, false)));
                        l.setText("Statut: " + name.getStatut() + "\n Date : " + name.getDatepub());
                                         
                        setGraphic(l);

                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }

                }
            }
        });
//        
//        liste.setCellFactory(param -> new ListCell<String>() {
//            
//            private ImageView imageView = new ImageView();
//            @Override
//            public void updateItem(String name, boolean empty) {
//               
//            super.updateItem(name, empty);
//            if (empty) {
//            setText(null);
//            setGraphic(null);
//            } else {
//               
//               try{
//                  imageView.setImage(new Image("file:/"+list.get(indexx).getImage(), 100, 100, false, false));
//                              indexx=indexx+ ;
//
//                  System.out.println("hahahawwww"+indexx);
//                    setGraphic(imageView);
//                    setText(name);
//            
//               }catch(Exception e){
//                   System.err.println(e.getMessage());
//               }
//                
//}
//}
//});
//        

        like.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                int index = list2.getSelectionModel().getSelectedIndex();
                System.out.println(index);
                int idd = list.get(index).getIdAct();
                String publication = list.get(index).getStatut();
                String imageP = list.get(index).getImage();
                String fichierP = list.get(index).getFichier();
                String comp = list.get(index).getCompétence();
                String Biog = list.get(index).getBio();
                Date dateP = (Date) list.get(index).getDatepub();
                System.out.println(idd);
                if (!isClicked) {

                    ActualiteService sp = new ActualiteService();
                    //id = sp.getActualiteByStatut(pub.getText());
                    System.out.println("Like");

                    sp.modifier(new Actualite(idd, publication, imageP, fichierP, comp, Biog, 1, dateP));
                    event.consume();
                    isClicked = true;
                } else {

                    ActualiteService sp = new ActualiteService();
                    //id = sp.getActualiteByStatut(pub.getText());
                    System.out.println("Like");
                    System.out.println(id);
                    sp.modifier(new Actualite(idd, publication, imageP, fichierP, comp, Biog, 0, dateP));
                    event.consume();
                    isClicked = false;
                }

            }
        });
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom.setText(nom);
    }

    public void setimageuser2(String path) {
        this.imageuserpath = path;
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmm");
        System.out.println(imageuserpath);
        Image image = new Image("file:/E:/web&mobile/webCoachini/public/ProfileImages/"+imageuserpath);
        imageuser2.setImage(image);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setDetail(String detail) {
        this.detail.setText(detail);
    }

    public void setCategorie(String categorie) {
        this.categorie.setText(categorie);
    }

    public void setRating(String rating) {
        this.rating.setText(rating);
    }

    public void setPub(String description1) {
        this.pub = description1;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setFichierpath(String fichierpath) {
        this.fichierpath = fichierpath;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    @FXML
    private void btndelete(MouseEvent event) throws IOException {
        ActualiteService sp = new ActualiteService();
        final int selectedIdx = list2.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIdx);

        int idd = list.get(selectedIdx).getIdAct();
        System.out.println(idd);
        sp.supprimer(new Actualite(idd));

        if (selectedIdx != -1) {
          

            final int newSelectedIdx
                    = (selectedIdx == list2.getItems().size() - 1) ? selectedIdx - 1 : selectedIdx;

            list2.getItems().remove(selectedIdx);
            list.remove(selectedIdx);
            list2.getSelectionModel().select(newSelectedIdx);

            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    // update message property
                    updateMessage("Statut Supprimer !! ");
                    return null;
                }

            };

// display message changes as notifications
            task.messageProperty().addListener((observable, oldMessage, newMessage)
                    -> Notifications.create().title("Notification").text(newMessage).darkStyle().show());

// execute long running task on background thread
            new Thread(task).start();
        }

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutActualite.fxml"));
//        Parent root = loader.load();
//
//        nom.getScene().setRoot(root);
//        AjoutActualiteController dcp = loader.getController();
//        dcp.setNom(nom.getText());
//        dcp.setDescription(description.getText());
//        dcp.setCategorie(categorie.getText());
//        dcp.setDetail(detail.getText());
//        dcp.setRating(Integer.parseInt(rating.getText()));
//        dcp.setimageuser1path(imageuserpath);
    }

    @FXML
    private void back(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutActualite.fxml"));
        Parent root = loader.load();

        nom.getScene().setRoot(root);
        AjoutActualiteController dcp = loader.getController();
        dcp.setNom(nom.getText());
        dcp.setDescription(description.getText());
        dcp.setCategorie(categorie.getText());
        dcp.setDetail(detail.getText());
        dcp.setRating(Integer.parseInt(rating.getText()));
        dcp.setimageuser1path(imageuserpath);

    }

}
