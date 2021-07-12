/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiGhassen;


import com.codename1.components.MultiButton;
import com.codename1.l10n.ParseException;
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
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.ServiceCategorieEvent;
import com.mycompany.myapp.services.ServiceEvent;
import java.util.ArrayList;
import java.util.Date;

public class updateEvent extends Form {

    Form current;

    public updateEvent(Form previous, String a1, String a2, String a3, String a4, String date1, String date2, String h1, String h2, String id, String filo,int idCoach,Resources res) {
        current = this;
        try {
            /*Form hi = new Form("Browser", new BorderLayout());
            BrowserComponent browser = new BrowserComponent();
            browser.setURL("https://www.codenameone.com/");
            hi.add(BorderLayout.CENTER, browser);*/


            setTitle("Update Event");
            setLayout(BoxLayout.y());

            TextField tfName = new TextField("", "Nom de L'evnenement");
            TextField tfnb = new TextField("", "Nombre des participants", 20, TextField.NUMERIC);
            Button btnValider = new Button("Update Event");

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
            TextField imgName = new TextField("", "Image Name");
            Button btn = new Button("upload Image");
            Button del = new Button("Delete");
            imgName.setText(filo.substring(27));
            tfName.setText(a1);
            tfnb.setText(a2);
            description.setText(a3);
            if (a4.equals("On Ligne")) {
                rb1.setSelected(true);
            } else {
                rb2.setSelected(true);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dat = sdf.parse(date1);
            Date datt = sdf.parse(date2);
            p1.setDate(dat);
            p2.setDate(datt);

            t1.setTime(Integer.parseInt(h1.substring(0, h1.indexOf(":"))), Integer.parseInt(h1.substring(h1.lastIndexOf(':') + 1)));
            //System.out.println(h1);
            t2.setTime(Integer.parseInt(h2.substring(0, h2.indexOf(":"))), Integer.parseInt(h2.substring(h2.lastIndexOf(':') + 1)));

            String[] characters = new String[10];
            String[] desc = new String[10];
            String[] ids = new String[10];
            int size = Display.getInstance().convertToPixels(7);
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(size, size, 0xffcccccc), true);
            Image[] pictures = new Image[10];
            ArrayList<Categorie_Event> ep = ServiceCategorieEvent.getInstance().getAllEvents();
            for (int i = 0; i < ep.size(); i++) {
                //System.out.println(ep.get(i).getNom());
                characters[i] = ep.get(i).getNom();
                desc[i] = ep.get(i).getDescription();
                pictures[i] = URLImage.createToStorage(placeholder, "name" + i, "http://" + ep.get(i).getDb_picture());
                ids[i] = String.valueOf(ep.get(i).getId());
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
                    mb.setTextLine3(ids[iter]);
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
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

//                    Form hi = new Form();
//                    if (FileChooser.isAvailable()) {
//                        FileChooser.showOpenDialog(".jpg, .png, JPEG", e2 -> {
//                            String file = (String) e2.getSource();
//                            if (file == null) {
//                                hi.add("No file was selected");
//                                hi.revalidate();
//                            } else {
//                                String extension = null;
//                                if (file.lastIndexOf(".") > 0) {
//                                    extension = file.substring(file.lastIndexOf(".") + 1);
//                                }
//                                if ("txt".equals(extension)) {
//                                    FileSystemStorage fs = FileSystemStorage.getInstance();
//                                    try {
//                                        InputStream fis = fs.openInputStream(file);
//                                        hi.addComponent(new SpanLabel(Util.readToString(fis)));
//                                    } catch (Exception ex) {
//                                        System.out.println("erreur File Upload");
//                                    }
//                                } else {
//                                    hi.add("Selected file " + file);
//                                }
//                                
//                                System.out.println(file);
//                                MultipartRequest request = new MultipartRequest();
//                                String url = "http://127.0.0.1/upload.php";
//                                request.setUrl(url);
//                                request.setPost(true);
//                                //String path =FileSystemStorage.getInstance().getAppHomePath();
//                                String path = "file:///home//kagha/Pictures/1er.jpg";
//                                System.out.println(path);
//                                try {
//                                    request.addData("picture", path, "image/png");
//                                } catch (IOException ex) {
//                                    ex.printStackTrace();
//                                }
//                                NetworkManager.getInstance().addToQueue(request);
//                                
//                            }
//                            hi.revalidate();
//                        });
//                    }
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
                    if ((tfName.getText().length() == 0) || (tfnb.getText().length() == 0) || (description.getText().length() == 0) || (p1.getText().length() == 0) || (p2.getText().length() == 0) || (t1.getText().length() == 0) || (t2.getText().length() == 0)) {
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    } else {
                        try {
                            System.out.println(p1.getDate());
                            String date1 = (new SimpleDateFormat("yyyy-MM-dd")).format(p1.getDate());
                            System.out.println(date1);
                            String date2 = (new SimpleDateFormat("yyyy-MM-dd")).format(p2.getDate());
                            System.out.println("rigo2"+idCoach);
                            Event t = new Event(tfName.getText(), date1, t1.getText(), date2, t2.getText(), rb1.getText(), Integer.parseInt(tfnb.getText()), description.getText(), Integer.valueOf(id), GoogleMapsTestApp.getValMap(), idCoach, imgName.getText());
                            if (ServiceEvent.getInstance().updateEvent(t, id)) {
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
                    ServiceEvent.getInstance().deleteEvent(id);
                   Dialog.show("Action", "Event Deleted", "OK", "Cancel");
                }
            });

            addAll(tfName, tfnb, datedebut, p1, heuredebut, t1, dateFin, p2, heurefin, t2, b, Participation, rb1, rb2, description, imgName, btn2, btn, btnValider, del);
            ListEventsForm eb = new ListEventsForm(previous,res);
            getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                    e -> eb.showBack()); // Revenir vers l'interface précédente
        } catch (ParseException ex) {
            //Logger.getLogger(updateEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
