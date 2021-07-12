/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiGhassen;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceEvent;
import com.mycompany.myapp.entities.Event;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author bhk
 */
public class ListEventsFormClient extends Form {

    public ListEventsFormClient(Form previous, Resources res) {

        //ArrayList<Event> e = ServiceEvent.getInstance().getAllEvents();
        //Container name1 = new Container(BoxLayout.y());
        //Button refresh = new Button("refresh");
        /*  refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ListEventsForm ref = new ListEventsForm(previous);
                ref.show();
            }
        });*/
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        FloatingActionButton sub = fab.createSubFAB(FontImage.MATERIAL_ARROW_BACK, "");
        sub.addActionListener(ex -> new EventMenuClient(res).show());
        FloatingActionButton sub3 = fab.createSubFAB(FontImage.MATERIAL_REFRESH, "");
        sub3.addActionListener(et -> new ListEventsFormClient(previous, res).show());
        fab.bindFabToContainer(this.getContentPane());
        Toolbar.setGlobalToolbar(true);
        Form hi = new Form(BoxLayout.y());
        //hi.setTitle("List tasks");
        //hi.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ex -> previous.showBack());
        hi.add(new InfiniteProgress());
        ArrayList<Event> en = ServiceEvent.getInstance().getAllEvents();
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...
            //Contact[] cnts = Display.getInstance().getAllContacts(true, true, true, true, false, false);

            Display.getInstance().callSerially(() -> {
                hi.removeAll();
                for (Event eyy : en) {
                    MultiButton m = new MultiButton();
                    m.setTextLine1("Nom: " + eyy.getNom_event());
                    m.setTextLine2(eyy.getDescription() + eyy.getNb_participant() + " " + eyy.getParticipation() + " " + eyy.getMap());
                    String date1 = (new SimpleDateFormat("yyyy-MM-dd")).format(eyy.getDate_debut());
                    m.setTextLine3("DB: " + date1 + " HD: " + eyy.getHeure_debut());
                    String date2 = (new SimpleDateFormat("yyyy-MM-dd")).format(eyy.getDate_fin());
                    m.setTextLine4("DF: " + date2 + " HF: " + eyy.getHeure_fin());
                    //m.setText("Nb"+eyy.getNb_participant()+" "+eyy.getParticipation()+" "+eyy.getMap());
                    /*MultiButton mm = new MultiButton();
                    m.setTextLine2(eyy.getHeure_fin());
                    m.setTextLine3(eyy.getParticipation());
                    m.setTextLine4(String.valueOf(eyy.getNb_participant()));
                    m.setText(eyy.getDescription());*/
                    Image placeholder = Image.createImage(100, 100, 0xbfc9d2); //square image set to 10% of screen width
                    EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
                    Date date = new Date();
                    long timeMilli = date.getTime();
                    Image pic = URLImage.createToStorage(encImage, "pic" + eyy.getId(), "http://" + eyy.getDb_map(), URLImage.RESIZE_SCALE);
                    if (pic != null) {
                        m.setIcon(pic);
                    } else {
                        //m.setIcon(finalDuke);
                    }
                    hi.add(m);
                }
                hi.revalidate();
            });
        });

        this.getToolbar().addSearchCommand(eyy -> {
            String text = (String) eyy.getSource();
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
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1
                            || line3 != null && line3.toLowerCase().indexOf(text) > -1
                            || line4 != null && line4.toLowerCase().indexOf(text) > -1;
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
        this.add(hi);

        /*
        for (int i = 0; i < e.size(); i++) {
            Label id = new Label(String.valueOf(e.get(i).getId()));
            id.setHidden(true);
            Label nomE = new Label("Nom Event: " + e.get(i).getNom_event());
            String date1 = (new SimpleDateFormat("yyyy-MM-dd")).format(e.get(i).getDate_debut());
            Label dateD = new Label(date1);
            String date2 = (new SimpleDateFormat("yyyy-MM-dd")).format(e.get(i).getDate_fin());
            Label dateF = new Label(date2);
            Label heureD = new Label(e.get(i).getHeure_debut());
            Label heureF = new Label(e.get(i).getHeure_debut());
            Label participation = new Label(e.get(i).getParticipation());
            Label nbparticipant = new Label(String.valueOf(e.get(i).getNb_participant()));
            Label desciprtion = new Label(e.get(i).getDescription());
            Label location = new Label(e.get(i).getMap());
         */

 /*Date date = new Date();
                //This method returns the time in millis
                long timeMilli = date.getTime();

                int deviceWidth = Display.getInstance().getDisplayWidth();
                Image placeholder = Image.createImage(100, 100, 0xbfc9d2); //square image set to 10% of screen width
                EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
                Image img = URLImage.createToStorage(encImage, "pic" + timeMilli, "http://" + e.get(i).getDb_map(), URLImage.RESIZE_SCALE);
                System.out.println(e.get(i).getDb_map());
                ImageViewer imgmap = new ImageViewer(img);

                //System.out.println(e.get(i).getHeure_fin());
                name1.add(id);
                name1.add(nomE);
                name1.add("Date Debut: " + date1);
                name1.add("Date Fin: " + date2);
                name1.add("Heure Debut: " + heureD.getText());
                name1.add("Heure Fin: " + heureF.getText());
                name1.add("Participation: " + participation.getText());
                name1.add("Nombre: " + nbparticipant.getText());
                name1.add("Description: " + desciprtion.getText());
                name1.add("Location: " + location.getText());
                name1.add(imgmap);*/
        //name1.add(hi);
        //add(name1);
        /*
            nomE.addPointerReleasedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    updateEvent a = new updateEvent(previous, nomE.getText().substring(nomE.getText().lastIndexOf(":") + 1), nbparticipant.getText(), desciprtion.getText(), participation.getText(), dateD.getText(), dateF.getText(), heureD.getText(), heureF.getText(), id.getText());
                    a.show();

                }
            });
            if (i == e.size() - 1) {
                //add(refresh);
                
                

            }*/
    }

}
