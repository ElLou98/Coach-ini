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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
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
public class updateCategorieEvent extends Form {

    public updateCategorieEvent(Form previous, String a1, String a2,String a3,String id,Resources res) {

        /*Form hi = new Form("Browser", new BorderLayout());
        BrowserComponent browser = new BrowserComponent();
        browser.setURL("https://www.codenameone.com/");
        hi.add(BorderLayout.CENTER, browser);*/
        setTitle("Update Catégorie Event");
        setLayout(BoxLayout.y());
        TextField tfName = new TextField("", "Nom de L'evnenement");
        TextField tfnb = new TextField("", "File name");
        Button btnValider = new Button("Update Catégorie Event");
        TextArea description = new TextArea(3, 8);
        description.setHint("Description");
        tfName.setText(a1);
        tfnb.setText(a3);
        description.setText(a2);
        Button btn = new Button("Upload Image");
        Button del = new Button("Delete");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form fm_brw = new Form("Upload file");
                HomeCategorieEventForm h = new HomeCategorieEventForm(res);
                 fm_brw.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> h.getCurrent().showBack());
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
                if ((tfName.getText().length() == 0||(description.getText().length() == 0))) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Categorie_Event t = new Categorie_Event(tfName.getText(), description.getText(),"localhost/coachini/uploads/"+tfnb.getText() );
                        if (ServiceCategorieEvent.getInstance().updateCEvent(t, id)) {
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
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println(id);
                ServiceCategorieEvent.getInstance().deleteCEvent(id);
                Dialog.show("Action", "Catégorie Event Deleted", "OK", "Cancel");
            }
        });
        addAll(tfName, tfnb, description, btn, btnValider, del);
        ListCategorieEventsForm eb = new ListCategorieEventsForm(previous,res);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> eb.showBack()); // Revenir vers l'interface précédente

    }

}
