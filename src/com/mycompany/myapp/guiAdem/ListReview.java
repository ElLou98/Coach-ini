/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiAdem;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
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
public class ListReview extends Form {

    Form current;
    private String Username = EncapsulationCompte.getNomDutilisateur();

    public ListReview(Form previous,Resources res) {

        current = createForm(previous,res);

    }

    public Form createForm(Form previous,Resources res) {
        Form tempForm = new Form();

        tempForm.setTitle("My Reviews");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
        GridLayout gridLayout = new GridLayout(1, 6);

        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Label labelCoachname = new Label("Coach Username");
        Label labelDate = new Label("Date");
        Label labelRating = new Label("Rating");
        Label labelDescription = new Label("Description");
        Label labelAction = new Label("Action");
        Label labelEdit = new Label(" ");
        labelCoachname.getUnselectedStyle().setFont(fnt);
        labelDate.getUnselectedStyle().setFont(fnt);
        labelRating.getUnselectedStyle().setFont(fnt);
        labelDescription.getUnselectedStyle().setFont(fnt);
        labelAction.getUnselectedStyle().setFont(fnt);

        Container HeadConainter = new Container(gridLayout);
        HeadConainter.add(labelCoachname);
        HeadConainter.add(labelDate);
        HeadConainter.add(labelRating);
        HeadConainter.add(labelDescription);
        HeadConainter.add(labelAction);
        HeadConainter.add(labelEdit);
//        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
//                 e -> new HomeReview());
        tempForm.add(HeadConainter);

        ArrayList<Review> Reviews = ReviewTask.getInstance().getAllReviews();
        for (Review r : Reviews) {
            if (r.getNom_client_review().equals(Username)) {
                Container BodyConainter = new Container(gridLayout);
                BodyConainter.add(new Label(r.getNom_coach_review()));
                BodyConainter.add(new Label(r.getDate_review()));
                String rate = String.valueOf(r.getRating());
                BodyConainter.add(new Label(rate));
                BodyConainter.add(new Label(r.getDescription_review()));
                Button deletebtn = new Button("Delete");

                deletebtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (ReviewTask.getInstance().deleteReview(r)) {
                            Dialog.show("Success", "Review has been deleted! ", "Ok", null);
                            Form NewForm = createForm(previous,res);
                            NewForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                            NewForm.show();
                        } else {
                            Dialog.show("ERROR", "Error , Could NOT delete Review", "Ok", null);
                        }
                    }

                });

                Button editbtn = new Button("Edit");

                editbtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        tempForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
                        new EditReview(tempForm, r, res).show();
                        tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                    }
                });
                BodyConainter.add(editbtn);
                BodyConainter.add(deletebtn);
                tempForm.add(BodyConainter);
            }

        }
        Button AddReview = new Button("Add a Review");
        AddReview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tempForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
                new AddReview(tempForm , res).show();
                tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
            }
        });
        Container cnAdd = new Container(gridLayout);
        
        
        cnAdd.add(AddReview);
        tempForm.add(cnAdd);
        

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
                    if(!(mb.getComponentAt(0) instanceof Button)){
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
                }
                tempForm.getContentPane().animateLayout(150);
            }
        }, 4);

        tempForm.show();
        tempForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> new HomeMenuReview(res).showBack()); // Revenir vers l'interface précédente
        return tempForm;

    }

}
