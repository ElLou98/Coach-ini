/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiAdem;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Review;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.L10NManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Display;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.services.ReviewTask;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Adem
 */
public class AddReview extends Form {

    L10NManager l10n = L10NManager.getInstance();
    String sysdate2 = l10n.formatDateTimeShort(new Date()).toString();

    private String date_review = sysdate2; //"2021-05-03 17:50:40"
    private String myUsername = EncapsulationCompte.getNomDutilisateur();

    ArrayList<Float> myList = new ArrayList<>();

    public AddReview(Form previous , Resources res) {
        myList.add((float) 0);
        myList.add((float) 1);
        myList.add((float) 2);
        myList.add((float) 3);
        myList.add((float) 4);
        myList.add((float) 5);

        setTitle("Add a Review");
        setLayout(BoxLayout.y());
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

        TextField tfClientname = new TextField("", myUsername);
        tfClientname.setText(myUsername);
        tfClientname.setEditable(false);
        

        TextArea tfDescReview = new TextArea(4, 100);
        tfDescReview.setHint("Your Description...");
        TextField tfCoachname = new TextField("", "Your Coach Username...");
        TextField tfdatereview = new TextField("", date_review);
        tfdatereview.setText(date_review);
        tfdatereview.setEditable(false);
        

        ComboBox RatingCombo = new ComboBox();
        
            for (int i = 0; i < myList.size(); i++) {
                RatingCombo.addItem(myList.get(i));
            }
            
//        LocalNotification n = new LocalNotification();
//        n.setId("demo-notification");
//        n.setAlertBody("It's time to take a break and look at me");
//        n.setAlertTitle("Break Time!");
//        n.setAlertSound("/notification_sound_bells.mp3"); //file name must begin with notification_sound


        Button btnValider = new Button("Add Review");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfDescReview.getText().length() == 0) || (tfCoachname.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", "OK", null);
                } else {
                    try {
                        Review r = new Review();

                        r.setNom_client_review(tfClientname.getText());
                        r.setDescription_review(tfDescReview.getText());
                        r.setNom_coach_review(tfCoachname.getText());
                        r.setDate_review(tfdatereview.getText());
                        r.setRating((float) RatingCombo.getSelectedItem());
                        if( ReviewTask.getInstance().addReview(r)){
                            //Display.getInstance().scheduleLocalNotification(n, System.currentTimeMillis() + 10 * 1000, LocalNotification.REPEAT_NONE);
                             Dialog.show("Success", "Connection accepted", "OK", null);
                             tfDescReview.setText("");
                             tfCoachname.setText("");
                             }
                             
                        else
                             Dialog.show("Error", "Server ERROR", "OK", null);
                         

                    } catch (NumberFormatException e) {
                        Dialog.show("Alert", "Please fill all the fields", "OK", null);
                    }

                }

            }
        });
        addAll(tfClientname, tfDescReview, tfCoachname, RatingCombo, tfdatereview, btnValider);
        setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                 e -> new ListReview(previous,res)); // Revenir vers l'interface précédente

    }

}
