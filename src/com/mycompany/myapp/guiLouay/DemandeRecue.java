/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiLouay;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
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
public class DemandeRecue extends Form {
    
    Form current;
    private String myUsername = EncapsulationCompte.getNomDutilisateur();

    public DemandeRecue(Form previous) {
        current = createForm(previous);

    }

    private Form createForm(Form previous) {
        Form tempForm = new Form();
        tempForm.setTitle("Demandes d'amis reçues");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        GridLayout gridLayout = new GridLayout(1, 2);

        ArrayList<Amis> amis = AmisServices.getInstance().getAllAmis();
        for (Amis a : amis) {
            if (a.getDestinataire().equals(myUsername) 
                    && a.getEtatDemande() == 0) {
                Container corpsContainer = new Container(gridLayout);
                Label usernameLabel = new Label(a.getExpediteur());
                Button boutonAccepter = new Button("Accepter");
                boutonAccepter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if( AmisServices.getInstance().accepterAmis(a))
                        {
                            tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                            Dialog.show("Success","Demande d'ami acceptée ",new Command("OK"));
                            Form newForm=createForm(previous);
                        }   
                        else
                        {
                            Dialog.show("ERROR", "Demande d'ami non acceptée", new Command("OK"));
                        }
                    }
                });
 
                corpsContainer.addAll(usernameLabel,boutonAccepter);
                tempForm.add(corpsContainer);
            }
        }

        tempForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> previous.showBack());
        
        tempForm.show();
        return tempForm;
    }
    
}
