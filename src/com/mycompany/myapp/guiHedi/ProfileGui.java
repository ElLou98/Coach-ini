/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiHedi;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Espace Info
 */
public class ProfileGui extends Form {
    
    Form current;

    public ProfileGui() {
        current = createForm();
    }

    public Form createForm() {
        Form tempForm = new Form();
        tempForm.setTitle("Profile et Actualite");
        tempForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        Button boutonMonprofile=new Button("Mon Profile");
        Button boutonMesactualite=new Button("Mes Actualite");
        Button boutonAjouterProfile=new Button("Ajouter un profile");
        Button boutonAjouterActualite=new Button("Ajouter des actualite");
        Button boutonModifProfile=new Button("Modifier Mon Profile");
        Button boutonSuppProfile=new Button("Supprimer Mon profile");



//        boutonMonprofile.addActionListener(e -> new AfficheProfile(res).show());
//         boutonMesactualite.addActionListener(e -> new AfficheActualite(current).show());
                                boutonAjouterProfile.addActionListener(e -> new AjoutProfile(current).show());


        
        tempForm.add(boutonMonprofile);
        tempForm.add(boutonMesactualite);
        tempForm.add(boutonAjouterProfile);
        tempForm.add(boutonAjouterActualite);
        tempForm.add(boutonSuppProfile);
        tempForm.add(boutonModifProfile);


        tempForm.show();
        return tempForm;
    }
    
}
