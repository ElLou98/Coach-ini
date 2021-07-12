/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiAdem;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Review;
import com.mycompany.myapp.services.ReviewTask;
import java.util.ArrayList;

/**
 *
 * @author Adem
 */
public class ListReviewCoach extends Form {
        Form current;
    private String Username = EncapsulationCompte.getNomDutilisateur();

    public ListReviewCoach(Form previous,Resources res) {

        current = createForm(res);

    }

    private Form createForm(Resources res) {
        Form tempForm = new Form();

        tempForm.setTitle("My Reviews");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
        GridLayout gridLayout = new GridLayout(1, 4);

        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Label labelClientname = new Label("Client Username");
        Label labelDate = new Label("Date");
        Label labelRating = new Label("Rating");
        Label labelDescription = new Label("Description");
        
        labelClientname.getUnselectedStyle().setFont(fnt);
        labelDate.getUnselectedStyle().setFont(fnt);
        labelRating.getUnselectedStyle().setFont(fnt);
        labelDescription.getUnselectedStyle().setFont(fnt);
        

        Container HeadConainter = new Container(gridLayout);
        HeadConainter.add(labelClientname);
        HeadConainter.add(labelDate);
        HeadConainter.add(labelRating);
        HeadConainter.add(labelDescription);
        
//        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
//                 e -> new HomeReview());
        tempForm.add(HeadConainter);
        
                ArrayList<Review> Reviews = ReviewTask.getInstance().getAllReviews();
        for (Review r : Reviews) {
            if (r.getNom_coach_review().equals(Username)) {
                Container BodyConainter = new Container(gridLayout);
                BodyConainter.add(new Label(r.getNom_client_review()));
                BodyConainter.add(new Label(r.getDate_review()));
                String rate = String.valueOf(r.getRating());
                BodyConainter.add(new Label(rate));
                BodyConainter.add(new Label(r.getDescription_review()));


               
                tempForm.add(BodyConainter);
            }

        }
        tempForm.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : tempForm.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                tempForm.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : tempForm.getContentPane()) {
                    Container mb = (Container) cmp;
                    
                    Label label1 = (Label) mb.getComponentAt(0);
                    String line1 = label1.getText();
                    Label label2 = (Label) mb.getComponentAt(1);
                    String line2 = label2.getText();
                    Label label3 = (Label) mb.getComponentAt(2);
                    String line3 = label3.getText();
                    Label label4 = (Label) mb.getComponentAt(3);
                    String line4 = label4.getText();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                    line2 != null && line2.toLowerCase().indexOf(text) > -1 ||
                    line3 != null && line3.toLowerCase().indexOf(text) > -1 ||
                    line4 != null && line4.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                    
                }
                tempForm.getContentPane().animateLayout(150);
            }
        }, 4);
        
        tempForm.show();
        tempForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> new HomeMenuReviewCoach(res).showBack()); // Revenir vers l'interface précédente
        return tempForm;
        
    }
    
}
