/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiLouay;

import com.codename1.components.MultiButton;
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
import com.mycompany.myapp.entities.Amis;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.UtilisateurBloque;
import com.mycompany.myapp.services.AmisServices;
import com.mycompany.myapp.services.UtilisateurBloqueServices;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class MesAmis extends Form {

    Form current;
    private String myUsername = EncapsulationCompte.getNomDutilisateur();

    public MesAmis(Form previous) {

        current = createForm(previous);

    }

    private Form createForm(Form previous) {
        Form tempForm = new Form();
        tempForm.setTitle("Mes Amis");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        GridLayout gridLayout = new GridLayout(1, 4);

        ArrayList<Amis> amis = AmisServices.getInstance().getAllAmis();
        for (Amis a : amis) {
            if ((a.getDestinataire().equals(myUsername)
                    || a.getExpediteur().equals(myUsername))
                    && a.getEtatDemande() == 1) {
                String friendUsername = null;
                if (!a.getDestinataire().equals(myUsername)) {
                    friendUsername = a.getDestinataire();
                } else if (!a.getExpediteur().equals(myUsername)) {
                    friendUsername = a.getExpediteur();
                }
                Container corpsContainer = new Container(gridLayout);
                Label usernameLabel = new Label(friendUsername);
                Button boutonSupprimer = new Button("Supprimer");
                boutonSupprimer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (AmisServices.getInstance().deleteAmis(a)) {
                            tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                            Dialog.show("Success", "Suppression effectué ", new Command("OK"));
                            Form newForm = createForm(previous);
                        } else {
                            Dialog.show("ERROR", "Suppression échoué", new Command("OK"));
                        }
                    }
                });
                Button boutonChat = new Button("Chat");
                String tempfriendUsername=friendUsername;
                boutonChat.addActionListener(e -> new ChatBox(previous, tempfriendUsername));
                
                ArrayList<UtilisateurBloque> utilisateurBloques = UtilisateurBloqueServices.getInstance().getAllUtilisateurBloque();
                String etatBloque = "none";
                UtilisateurBloque uIBlocked = new UtilisateurBloque();
                for (UtilisateurBloque u : utilisateurBloques) {
                    if (u.getUtilisateur().equals(myUsername)
                            && u.getAbloque().equals(friendUsername)) {
                        etatBloque = "IBlocked";
                        uIBlocked = u;
                    } else if (u.getUtilisateur().equals(friendUsername)
                            && u.getAbloque().equals(myUsername)) {
                        etatBloque = "BlockedMe";
                    }
                }
                if (etatBloque.equals("IBlocked")) {
                    Button boutonDeblock = new Button("Débloquer");
                    UtilisateurBloque tempBloque = uIBlocked;
                    boutonDeblock.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            if (UtilisateurBloqueServices.getInstance().deleteBloque(tempBloque)) {
                                tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                                Dialog.show("Success", "Ami(e) débloqué(e) ", new Command("OK"));
                                Form newForm = createForm(previous);

                            } else {
                                Dialog.show("ERROR", "Ami(e) non débloqué(e)", new Command("OK"));
                            }
                        }
                    });
                    corpsContainer.addAll(usernameLabel, boutonSupprimer, boutonDeblock);
                } else if (etatBloque.equals("BlockedMe")) {
                    corpsContainer.addAll(usernameLabel, boutonSupprimer);
                } else {
                    Button boutonBlock = new Button("Bloquer");
                    UtilisateurBloque tempBloque = new UtilisateurBloque(myUsername, friendUsername);
                    boutonBlock.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            if (UtilisateurBloqueServices.getInstance().addBloque(tempBloque)) {
                                tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                                Dialog.show("Success", "Ami(e) bloqué(e) ", new Command("OK"));
                                Form newForm = createForm(previous);
                            } else {
                                Dialog.show("ERROR", "Ami(e) non bloqué(e)", new Command("OK"));
                            }
                        }
                    });
                    corpsContainer.addAll(usernameLabel, boutonSupprimer, boutonChat, boutonBlock);
                }

                tempForm.add(corpsContainer);
            }
        }

        tempForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        tempForm.show();
        return tempForm;
    }

}
