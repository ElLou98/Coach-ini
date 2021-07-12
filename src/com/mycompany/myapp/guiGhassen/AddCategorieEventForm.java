/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiGhassen;


import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Categorie_Event;
import com.mycompany.myapp.services.ServiceCategorieEvent;

/**
 *
 * @author bhk
 */
public class AddCategorieEventForm extends Form {

    Form current;

    public static Image getScreenshot() {
        Form form = Display.getInstance().getCurrent();
        if (form != null) {
            Image screenshot = Image.createImage(form.getWidth(), form.getHeight());
            form.paintComponent(screenshot.getGraphics(), true);
            return screenshot;
        } else {
            return null;
        }
    }

    public AddCategorieEventForm(Form previous,Resources res) {

        /*MultipartRequest request = new MultipartRequest();
        request.setUrl("http://127.0.0.1:8000/image.php");
        System.out.println(FileSystemStorage.getInstance().getAppHomePath() + "kagha/Pictures/dossier.jpg");
        MultipartRequest cr = new MultipartRequest();
        cr.setPost(true);
        String mime = "image/jpeg";
        cr.addData("file", FileSystemStorage.getInstance().getAppHomePath() + "kagha/Pictures/dossier.jpg", mime);
        cr.setFilename("file", "MyImage.jpg");//any unique name you want
        
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        cr.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(cr);*/
        
        current = this;
        setTitle("Add a new Catégorie Event");
        setLayout(BoxLayout.y());
        TextField tfName = new TextField("", "Nom du Catégorie");
        TextField imgname = new TextField("", "Nom img BD");
        Button btnValider = new Button("Add Catégorie Event");
        TextArea description = new TextArea(3, 8);
        description.setHint("Description");
        Button btn = new Button("upload Image");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                Form fm_brw = new Form("Upload file");
                fm_brw.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> current.showBack());
                fm_brw.setLayout(new BorderLayout());
                BrowserComponent browser = new BrowserComponent();
                browser.setURL("http://127.0.0.1:80/test.html");
                
                fm_brw.addComponent(BorderLayout.CENTER, browser);
                fm_brw.show();
                
            }
        });
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (description.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Categorie_Event t = new Categorie_Event(tfName.getText(), description.getText(), "localhost/coachini/uploads/" + imgname.getText());
                        if (ServiceCategorieEvent.getInstance().addCategorieEvent(t)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }
                
            }
        });
        addAll(tfName, imgname, description, btn, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> previous.showBack()); // Revenir vers l'interface précédente

    }

}
