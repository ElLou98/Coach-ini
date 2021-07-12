/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiAdem;

import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Seance;
import com.mycompany.myapp.services.SeanceTask;
import java.util.ArrayList;

/**
 *
 * @author Adem
 */
public class ListSeanceCoach extends Form {
        private String Username = EncapsulationCompte.getNomDutilisateur();

    Form current;

    public ListSeanceCoach(Form previous,Resources res) {

        current = createForm(res);

    }

    private Form createForm(Resources res) {
        Form tempForm = new Form();

        tempForm.setTitle("My Agenda");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
        GridLayout gridLayout = new GridLayout(1, 7);

//        private String Summary; 
//    private String user_name; 
//    private String Description;
//    private String Date;
//    private String Starts_at;
//    private String Finishs_at;
//    private String Localisation;
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Label labelClientName = new Label("Client User");
        Label labelSummary = new Label("Summary");
        Label labelDescription = new Label("Description");
        Label labelDate = new Label("Date");
        Label labelStarts_at = new Label("Starts_at");
        Label labelFinishs_at = new Label("Finishs_at");
        Label labelLocalisation = new Label("Localisation");

        labelClientName.getUnselectedStyle().setFont(fnt);
        labelSummary.getUnselectedStyle().setFont(fnt);
        labelDescription.getUnselectedStyle().setFont(fnt);
        labelDate.getUnselectedStyle().setFont(fnt);
        labelStarts_at.getUnselectedStyle().setFont(fnt);
        labelFinishs_at.getUnselectedStyle().setFont(fnt);
        labelLocalisation.getUnselectedStyle().setFont(fnt);

        Container HeadConainter = new Container(gridLayout);
        
        HeadConainter.add(labelClientName);        
        HeadConainter.add(labelSummary);
        HeadConainter.add(labelDescription);
        HeadConainter.add(labelDate);
        HeadConainter.add(labelStarts_at);
        HeadConainter.add(labelFinishs_at);
        HeadConainter.add(labelLocalisation);

        tempForm.add(HeadConainter);
        ArrayList<Seance> Seances = SeanceTask.getInstance().getAllSeances();
        for (Seance s : Seances) {
            
                Container BodyConainter = new Container(gridLayout);
                BodyConainter.add(new Label(s.getUser_name()));
                BodyConainter.add(new Label(s.getSummary()));
                BodyConainter.add(new Label(s.getDescription()));

                BodyConainter.add(new Label(s.getDate()));
                BodyConainter.add(new Label(s.getStarts_at()));
                BodyConainter.add(new Label(s.getFinishs_at()));
                BodyConainter.add(new Label(s.getLocalisation()));

                tempForm.add(BodyConainter);
            

        }

        tempForm.show();
        tempForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> new HomeMenuPlanning(res).showBack());

        return tempForm;
    }
    
}
