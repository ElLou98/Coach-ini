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
import com.mycompany.myapp.services.AmisServices;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class DemandeEnvoye extends Form {
    
    Form current;
    private String myUsername = EncapsulationCompte.getNomDutilisateur();

    public DemandeEnvoye(Form previous) {
        current = createForm(previous);

    }

    private Form createForm(Form previous) {
        Form tempForm = new Form();
        tempForm.setTitle("Demandes d'amis envoyées");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        GridLayout gridLayout = new GridLayout(1, 2);

        ArrayList<Amis> amis = AmisServices.getInstance().getAllAmis();
        for (Amis a : amis) {
            if (a.getExpediteur().equals(myUsername) 
                    && a.getEtatDemande() == 0) {
                Container corpsContainer = new Container(gridLayout);
                Label usernameLabel = new Label(a.getDestinataire());
                Button boutonAnnuler = new Button("Annuler");
                boutonAnnuler.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if( AmisServices.getInstance().deleteAmis(a))
                        {
                            tempForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
                            Dialog.show("Success","Demande d'ami annulée ",new Command("OK"));
                            Form newForm=createForm(previous);
                        }   
                        else
                        {
                            Dialog.show("ERROR", "Demande d'ami non annulée", new Command("OK"));
                        }
                    }
                });
 
                corpsContainer.addAll(usernameLabel,boutonAnnuler);
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
