/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiHedi;

import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.materialscreens.ProfileForm;
import com.codename1.uikit.materialscreens.SideMenuBaseForm;
import com.mycompany.myapp.entities.Actualite;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.services.ServiceActualite;
import com.mycompany.myapp.services.ServiceProfile;
import java.util.ArrayList;

/**
 *
 * @author Espace Info
 */
public class AfficheActualite extends Form {
 Form current;
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public AfficheActualite(Form previous) {
                    current = createForm(previous);

    }
 private Form createForm(Form previous) {
        Form tempForm = new Form();

        tempForm.setTitle("Liste des Actualite");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
         GridLayout gridLayout = new GridLayout(1, 8);
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Label labelStatut = new Label("Statut");
        Label labelImage = new Label("Image");
        Label labelFichier = new Label("Fichier");
        Label labelCompetence = new Label("Compétence");
        Label labelBio = new Label("Bio");
        Label labelLike = new Label("Like ");
        Label labelAction = new Label("Action");
        labelStatut.getUnselectedStyle().setFont(fnt);
        labelImage.getUnselectedStyle().setFont(fnt);
        labelFichier.getUnselectedStyle().setFont(fnt);
        labelCompetence.getUnselectedStyle().setFont(fnt);
        labelBio.getUnselectedStyle().setFont(fnt);
        labelLike.getUnselectedStyle().setFont(fnt);
        labelAction.getUnselectedStyle().setFont(fnt);

        Container enteteConainter = new Container(gridLayout);
        enteteConainter.add(labelStatut);
        enteteConainter.add(labelImage);
        enteteConainter.add(labelFichier);
        enteteConainter.add(labelCompetence);
        enteteConainter.add(labelBio);
        enteteConainter.add(labelLike);
        enteteConainter.add(labelAction);
        tempForm.add(enteteConainter);
        ArrayList<Actualite> Actualites = ServiceActualite.getInstance().getAllActualite();
        for (Actualite r : Actualites) {

            Container corpsConainter = new Container(gridLayout);
            corpsConainter.add(new Label(r.getStatut()));
            corpsConainter.add(new Label(r.getImage()));
            corpsConainter.add(new Label(r.getFichier()));
            corpsConainter.add(new Label(r.getCompétence()));
            corpsConainter.add(new Label(r.getBio()));
            corpsConainter.add(new Label(r.getLikepub() + ""));
            Button bouton = new Button("Supprimer");
             bouton.getAllStyles().setBgColor(0xFF0000);
        bouton.getAllStyles().setBgTransparency(255);
        bouton.getUnselectedStyle().setFgColor(0xFFFFFF);
                        bouton.getAllStyles().setMargin(5,5,5,5);

            Button boutonmodif = new Button("Modifier");
             boutonmodif.getAllStyles().setBgColor(0x00ff00);
        boutonmodif.getAllStyles().setBgTransparency(255);
        boutonmodif.getUnselectedStyle().setFgColor(0xFFFFFF);
                boutonmodif.getAllStyles().setMargin(5,5,5,5);


            bouton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (ServiceActualite.getInstance().deleteActualite(r)) {
                        Dialog.show("Success", "Suppression effectué ", new Command("OK"));

//                            Form newForm=createForm();
//                            newForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
//                            
//                            newForm.show();
                        corpsConainter.removeAll();
                        ArrayList<Actualite> Actualites = ServiceActualite.getInstance().getAllActualite();
                        for (Actualite r : Actualites) {

                            Container corpsConainter = new Container(gridLayout);
                            corpsConainter.add(new Label(r.getStatut()));
                            corpsConainter.add(new Label(r.getImage()));
                            corpsConainter.add(new Label(r.getFichier()));
                            corpsConainter.add(new Label(r.getCompétence()));
                            corpsConainter.add(new Label(r.getBio()));
                            corpsConainter.add(new Label(r.getLikepub() + ""));
                            Button bouton = new Button("Supprimer");
                            Button boutonmodif = new Button("Modifier");
                        }

                    } else {
                        Dialog.show("ERROR", "Suppression échoué", new Command("OK"));
                    }
                }
            });

            boutonmodif.addActionListener(e -> new ModifierActualite(r, previous).showBack());
            corpsConainter.add(bouton);
            corpsConainter.add(boutonmodif);
            tempForm.add(corpsConainter);
        }
            tempForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
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
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                tempForm.getContentPane().animateLayout(150);
            }
        }, 4);
            
       
            
      

 
      tempForm.show();
        return tempForm;
  
 }
 
}