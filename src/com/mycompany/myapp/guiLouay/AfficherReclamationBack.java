package com.mycompany.myapp.guiLouay;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ReclamationServices;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Louay
 */
public class AfficherReclamationBack {

    Form current;
    private String myUsername = "Louay";

    public AfficherReclamationBack() {
        current = createForm();

    }

    public Form createForm() {
        Form tempForm = new Form();
        tempForm.setTitle("Réclamations");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
        GridLayout gridLayout = new GridLayout(1, 6);

        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Label labelUsername = new Label("Username");
        Label labelDescription = new Label("Description");
        Label labelType = new Label("Type");
        Label labelDate = new Label("Date");
        Label labelEtat = new Label("Etat");
        Label labelAction = new Label("Action");
        labelUsername.getUnselectedStyle().setFont(fnt);
        labelDescription.getUnselectedStyle().setFont(fnt);
        labelType.getUnselectedStyle().setFont(fnt);
        labelDate.getUnselectedStyle().setFont(fnt);
        labelEtat.getUnselectedStyle().setFont(fnt);
        labelAction.getUnselectedStyle().setFont(fnt);

        Container enteteConainter = new Container(gridLayout);
        enteteConainter.add(labelUsername);
        enteteConainter.add(labelDescription);
        enteteConainter.add(labelType);
        enteteConainter.add(labelDate);
        enteteConainter.add(labelEtat);
        enteteConainter.add(labelAction);
        tempForm.add(enteteConainter);
        ArrayList<Reclamation> Reclamations = ReclamationServices.getInstance().getAllReclamations();
        for (Reclamation r : Reclamations) {

            String etat = null;
            if (r.getEnCours() == 0 && r.getTraite() == 0) {
                etat = "-";
            } else if (r.getEnCours() == 1 && r.getTraite() == 0) {
                etat = "En cours";
            } else if (r.getEnCours() == 0 && r.getTraite() == 1) {
                etat = "Traité";
            }
            Container corpsConainter = new Container(gridLayout);
            corpsConainter.add(new Label(r.getLogin()));
            corpsConainter.add(new SpanLabel(r.getDescriptionReclamation()));
            corpsConainter.add(new SpanLabel(r.getTypeReclamation()));
            corpsConainter.add(new Label(r.getDateReclamation().substring(0, 10)));
            corpsConainter.add(new Label(etat));
            Button bouton = new Button("Modifier");
            if(!etat.equals("Traité"))
            {
            bouton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (ReclamationServices.getInstance().editReclamation(r)) {
                        Dialog.show("Success", "Changement d'état effectué ", new Command("OK"));
                        Form newForm = createForm();
                        newForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                        newForm.show();
                    } else {
                        Dialog.show("ERROR", "Changement d'état échoué", new Command("OK"));
                    }
                }
            });
            }

            corpsConainter.add(bouton);
            tempForm.add(corpsConainter);
        }
        

        tempForm.show();
        return tempForm;
    }

}
