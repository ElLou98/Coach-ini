/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiLouay;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.myapp.entities.Amis;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.UtilisateurBloque;
import com.mycompany.myapp.services.AmisServices;
import com.mycompany.myapp.services.UtilisateurBloqueServices;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class AjouterAmis extends Form {

    Form current;
    private String myUsername = EncapsulationCompte.getNomDutilisateur();

    public AjouterAmis(Form previous) {
        current = createForm(previous);

    }

    private Form createForm(Form previous) {
        Form tempForm = new Form();

        tempForm.setTitle("Ajouter un(e) ami(e)");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        GridLayout gridLayout = new GridLayout(1, 2);

        ArrayList<Amis> amis = AmisServices.getInstance().getAllAmis();
        ArrayList<String> usernameCompte = AmisServices.getInstance().getAllCompte();
        int n = amis.size();
        for (String s : usernameCompte) {
            boolean test = false;
            int i = 0;
            if (!(s.equals(myUsername))) {
                while (i < n && !test) {
                    Amis a = amis.get(i);
                    if ((s.equals(a.getDestinataire()) && a.getExpediteur().equals(myUsername))
                            || (s.equals(a.getExpediteur()) && a.getDestinataire().equals(myUsername))) {
                        test = true;
                    }
                    i++;
                }
                if (!test) {
                    Container corpsContainer = new Container(gridLayout);
                    Label usernameLabel = new Label(s);
                    Button boutonAjouter = new Button("Ajouter");
                    boutonAjouter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            Amis a = new Amis(s, myUsername);
                            if (AmisServices.getInstance().addAmis(a)) {
                                tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                                Dialog.show("Success", "Demande d'ajout envoyé ", new Command("OK"));
                                Form newForm = createForm(previous);
                            } else {
                                Dialog.show("ERROR", "Demande d'ajout échoué", new Command("OK"));
                            }
                        }
                    });
                    corpsContainer.addAll(usernameLabel, boutonAjouter);
                    tempForm.add(corpsContainer);
                }
            }

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
