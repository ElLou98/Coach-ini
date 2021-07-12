/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiGhassen;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Categorie_Event;
import com.mycompany.myapp.services.ServiceCategorieEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author bhk
 */
public class ListCategorieEventsForm extends Form {

    public ListCategorieEventsForm(Form previous,Resources res) {

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        //fab.addActionListener(e -> new AddCategorieEventForm(this).show());
        FloatingActionButton sub = fab.createSubFAB(FontImage.MATERIAL_ARROW_BACK, "");
        sub.addActionListener(ex -> new CatEventMenu(res).show());
        FloatingActionButton sub2 = fab.createSubFAB(FontImage.MATERIAL_ARTICLE, "");
        sub2.addActionListener(e -> new AddCategorieEventForm(this,res).show());
        FloatingActionButton sub3 = fab.createSubFAB(FontImage.MATERIAL_REFRESH, "");
        sub3.addActionListener(et -> new ListCategorieEventsForm(previous,res).show());
        fab.bindFabToContainer(this.getContentPane());
        Toolbar.setGlobalToolbar(true);
        ArrayList<Categorie_Event> e = ServiceCategorieEvent.getInstance().getAllEvents();
        Container name1 = new Container(BoxLayout.y());
       /* Button refresh = new Button("refresh                                ");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ListCategorieEventsForm ref = new ListCategorieEventsForm(previous);
                ref.show();
            }
        });*/

        Form hi = new Form(BoxLayout.y());
        //hi.setTitle("List tasks");
        // hi.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ex -> previous.showBack());
        hi.add(new InfiniteProgress());
        ArrayList<Categorie_Event> en = ServiceCategorieEvent.getInstance().getAllEvents();
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...
            //Contact[] cnts = Display.getInstance().getAllContacts(true, true, true, true, false, false);

            Display.getInstance().callSerially(() -> {
                hi.removeAll();
                for (Categorie_Event eyy : en) {
                    MultiButton m = new MultiButton();
                    m.setTextLine1("Nom: " + eyy.getNom());
                    m.setTextLine2("Description:" + eyy.getDescription());
                    Image placeholder = Image.createImage(60, 60, 0xbfc9d2); //square image set to 10% of screen width
                    EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
                    Date date = new Date();
                    long timeMilli = date.getTime();
                    Image pic = URLImage.createToStorage(encImage, "pic" + String.valueOf(eyy.getId()), "http://" + eyy.getDb_picture(), URLImage.RESIZE_SCALE);
                    if (pic != null) {
                        m.setIcon(pic);
                    } else {
                        //m.setIcon(finalDuke);
                    }
                    m.addActionListener((evt) -> {
                        updateCategorieEvent a = new updateCategorieEvent(previous, eyy.getNom(), eyy.getDescription(), eyy.getDb_picture().substring(27), String.valueOf(eyy.getId()),res);
                        a.show();
                    });
                    hi.add(m);
                }
                hi.revalidate();
            });
        });
        this.getToolbar().addSearchCommand(eyyo -> {
            String text = (String) eyyo.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : hi.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                hi.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : hi.getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    String line3 = mb.getTextLine3();
                    String line4 = mb.getTextLine4();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                hi.getContentPane().animateLayout(150);
            }
        }, 4);

        //hi.add(hi);
        //hi.show();
        //name1.add(hi);
        //add(refresh);
        //System.out.println("done");
        this.add(hi);

        /*for(int i=0; i<e.size();i++){ 
            Label id = new Label(String.valueOf(e.get(i).getId()));
            id.setHidden(true);
            Label nomE = new Label("Nom Catégorie Event="+e.get(i).getNom());
            Label desciprtion = new Label(e.get(i).getDescription());
            Label ab=new Label(e.get(i).getDb_picture());
            
            
            try{
            int deviceWidth = Display.getInstance().getDisplayWidth();
            Image placeholder = Image.createImage(70 , 70, 0xbfc9d2); //square image set to 10% of screen width
            EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
            Image img = URLImage.createToStorage(encImage, "pico"+i,"http://"+e.get(i).getDb_picture(), URLImage.RESIZE_SCALE);
            
            ImageViewer imgmap = new ImageViewer(img);
            System.out.println(e.get(i).getDb_picture());
            name1.add(id);
            name1.add(nomE);
            name1.add(desciprtion);
            name1.add(imgmap);
                        }catch(Exception ee){
                System.out.println("ok");
            }
            nomE.addPointerReleasedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                    updateCategorieEvent a = new updateCategorieEvent(previous,nomE.getText().substring(nomE.getText().lastIndexOf("=")+1),desciprtion.getText(),ab.getText().substring(27),id.getText());
                    a.show();
                   
                }
            });
           if(i==e.size()-1){
               add(refresh);
               add(name1);
               
               
           }
        }*/
    }

}
