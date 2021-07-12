/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiAdem;

import com.codename1.l10n.L10NManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Review;
import com.mycompany.myapp.services.ReviewTask;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Adem
 */
public class EditReview extends Form {

    L10NManager l10n = L10NManager.getInstance();
    String sysdate2 = l10n.formatDateTimeShort(new Date()).toString();

    private String date_review = sysdate2; //"2021-05-03 17:50:40"
    private String myUsername = EncapsulationCompte.getNomDutilisateur();

    ArrayList<Float> myList = new ArrayList<>();

    public EditReview(Form previous, Review r , Resources res) {
        myList.add((float) 0);
        myList.add((float) 1);
        myList.add((float) 2);
        myList.add((float) 3);
        myList.add((float) 4);
        myList.add((float) 5);
        
        setTitle("Edit My  Review");
        setLayout(BoxLayout.y());
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        
        TextField tfClientname = new TextField("", myUsername);
        tfClientname.setText(myUsername);
        tfClientname.setVisible(false);
        //tfClientname.setEditable(false);
        

        TextArea tfDescReview = new TextArea(4, 100);
        tfDescReview.setText(r.getDescription_review());
        TextField tfCoachname = new TextField(r.getNom_coach_review());
        tfCoachname.setEditable(false);
        TextField tfdatereview = new TextField("", date_review);
        tfdatereview.setText(date_review);
        tfdatereview.setEditable(false);
        

        ComboBox RatingCombo = new ComboBox();
        
            for (int i = 0; i < myList.size(); i++) {
                RatingCombo.addItem(myList.get(i));
            }
            
            Button btnValider = new Button("Edit Review");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfDescReview.getText().length() == 0) || (tfCoachname.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", "OK", null);
                } else {
                    try {

                        r.setDescription_review(tfDescReview.getText());
                        
                        r.setDate_review(tfdatereview.getText());
                        r.setRating((float) RatingCombo.getSelectedItem());
                        if( ReviewTask.getInstance().editReview(r)){
                             Dialog.show("Success", "Connection accepted", "OK", null);
                             
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
                 e -> new ListReview(previous,res));
            
            
            
    }
    

}
