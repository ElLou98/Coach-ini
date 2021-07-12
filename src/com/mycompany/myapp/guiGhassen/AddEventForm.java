/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiGhassen;


import com.codename1.components.MultiButton;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Categorie_Event;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.ServiceCategorieEvent;
import com.mycompany.myapp.services.ServiceEvent;
import java.util.ArrayList;

/**
 *
 * @author bhk
 */
public class AddEventForm extends Form {

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

    public AddEventForm(Form previous,Resources res) {

        current = this;
        setTitle("Add a new Event");
        setLayout(BoxLayout.y());

        TextField tfName = new TextField("", "Nom de L'evnenement");
        TextField tfnb = new TextField("", "Nombre des participants", 20, TextField.NUMERIC);
        Button btnValider = new Button("Add Event");

        TextArea description = new TextArea(3, 8);
        description.setHint("Description");
        Label datedebut = new Label("Date de Debut:");
        Picker p1 = new Picker();
        p1.setType(Display.PICKER_TYPE_DATE);
        Label dateFin = new Label("Date de Fin:");
        Picker p2 = new Picker();
        p1.setType(Display.PICKER_TYPE_DATE);
        Label heuredebut = new Label("Heure de Debut:");
        Picker t1 = new Picker();
        t1.setType(Display.PICKER_TYPE_TIME);
        Label heurefin = new Label("Heure de Fin:");
        Picker t2 = new Picker();
        t2.setType(Display.PICKER_TYPE_TIME);
        Label Participation = new Label("Participation:");
        RadioButton rb1 = new RadioButton("On Ligne");
        RadioButton rb2 = new RadioButton("En Personne");
        new ButtonGroup(rb1, rb2);
        GoogleMapsTestApp gp = new GoogleMapsTestApp();
        TextField imgName = new TextField("", "Image Name");
        Button btn = new Button("upload Image");

        /*String[] characters = {"Tyrion Lannister", "Jaime Lannister", "Cersei Lannister", "Daenerys Targaryen",
            "Jon Snow" };

        Picker p = new Picker();
        p.setStrings(characters);
        p.setSelectedString(characters[0]);
        p.addActionListener(e -> ToastBar.showMessage("You picked " + p.getSelectedString(), FontImage.MATERIAL_INFO));
        ArrayList<CategorieEventEntities.Categorie_Event> e = ServiceCategorieEvent.getInstance().getAllEvents();
        for (int i = 0; i < e.size(); i++) {
             characters[i]=e.get(i).getNom(); 

        }*/
        String[] characters = new String[10];
        String[] desc = new String[10];
        String[] id = new String[10];
        int size = Display.getInstance().convertToPixels(7);
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(size, size, 0xffcccccc), true);
        Image[] pictures = new Image[10];
        ArrayList<Categorie_Event> ep = ServiceCategorieEvent.getInstance().getAllEvents();
        for (int i = 0; i < ep.size(); i++) {
            //System.out.println(ep.get(i).getNom());
            characters[i] = ep.get(i).getNom();
            desc[i] = ep.get(i).getDescription();
            pictures[i] = URLImage.createToStorage(placeholder, "name" + i, "http://" + ep.get(i).getDb_picture());
            id[i] = String.valueOf(ep.get(i).getId());
        }
        MultiButton b = new MultiButton("Pick categorie...");
        b.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for (int iter = 0; iter < characters.length; iter++) {
                MultiButton mb = new MultiButton(characters[iter]);
                mb.setTextLine2(desc[iter]);
                mb.setIcon(pictures[iter]);
                mb.setTextLine3(id[iter]);
                d.add(mb);
                mb.addActionListener(ee -> {
                    b.setTextLine1(mb.getTextLine1());
                    b.setTextLine2(mb.getTextLine2());
                    b.setTextLine3(mb.getTextLine3());
                    b.setIcon(mb.getIcon());
                    d.dispose();
                    b.revalidate();
                });
            }
            d.showPopupDialog(b);
        });


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
        Button btn2 = new Button("Map");
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                /* Form fm_brw = new Form("Select Location");
                fm_brw.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> current.showBack());
                fm_brw.setLayout(new BorderLayout());
                BrowserComponent browser = new BrowserComponent();
                browser.setURL("http://127.0.0.1:80/map.html");
                fm_brw.addComponent(BorderLayout.CENTER, browser);
                Button cap = new Button("capture");
                fm_brw.setLayout(new BorderLayout());
                fm_brw.addComponent(BorderLayout.EAST, cap);
                fm_brw.show();

                cap.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                            try {
                                String pathToBeStored = FileSystemStorage.getInstance().getAppHomePath() + System.currentTimeMillis() + ".jpg";
                                Image img = getScreenshot();
                                OutputStream os = FileSystemStorage.getInstance().openOutputStream(pathToBeStored);
                                System.out.println(pathToBeStored);
                                ImageIO.getImageIO().save(img, os, ImageIO.FORMAT_JPEG, 0.9f);
                                os.close();
                            } catch (Exception e) {
                                System.out.println("ok");
                            }
                        }
                });*/
                //MapContainer googleMap = new MapContainer();
                try {
                    GoogleMapsTestApp g = new GoogleMapsTestApp();
                    g.start(current);
                } catch (Exception e) {
                    System.out.println("done");
                }

            }
        });

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfnb.getText().length() == 0) || (description.getText().length() == 0) || (p1.getText().length() == 0) || (p2.getText().length() == 0) || (t1.getText().length() == 0) || (t2.getText().length() == 0) || !rb1.isSelected() && !rb2.isSelected()) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        System.out.println(p1.getDate());
                        String date1 = (new SimpleDateFormat("yyyy-MM-dd")).format(p1.getDate());
                        System.out.println(date1);
                        String date2 = (new SimpleDateFormat("yyyy-MM-dd")).format(p2.getDate());
                        String res;
                        if (rb1.isSelected()) {
                            res = rb1.getText();
                        } else {
                            res = rb2.getText();
                        }
                        System.out.println(Integer.valueOf(b.getTextLine3()));
                        int id = EncapsulationCompte.getId();
                        Event t = new Event(tfName.getText(), date1, t1.getText(), date2, t2.getText(), res, Integer.parseInt(tfnb.getText()), description.getText(), Integer.valueOf(b.getTextLine3()), GoogleMapsTestApp.getValMap(), 263, imgName.getText());
                        if (ServiceEvent.getInstance().addEvent(t)) {
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

        addAll(tfName, tfnb, datedebut, p1, heuredebut, t1, dateFin, p2, heurefin, t2, b, Participation, rb1, rb2, description, imgName, btn2, btn, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ee -> previous.showBack()); // Revenir vers l'interface précédente

    }

}
