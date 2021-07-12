/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiHedi;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Profile;
import com.mycompany.myapp.services.ServiceProfile;

/**
 *
 * @author Espace Info
 */
public class AjoutProfile extends Form{

    public AjoutProfile(Form previous) {

        setTitle("Add a new Profile");
        setLayout(BoxLayout.y());
        
        TextField tfNom = new TextField("","Nom:");
        TextField tfPhoto= new TextField("", "Photo: ");
        TextField tfRating= new TextField("", "Rating: ");
        TextField tfDescription= new TextField("", "Description: ");
        TextField tfDétail= new TextField("", "Détail: ");
        TextField tfCatégorie= new TextField("", "Catégorie: ");
        TextField tfID_Compte= new TextField("", "ID_Compte: ");


        Button btnValider = new Button("Add Profile");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfNom.getText().length()==0)||(tfDescription.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Profile t = new Profile();
                        t.setNom(tfNom.getText());
                        t.setDescription(tfDescription.getText());
                        t.setRating(Integer.parseInt(tfRating.getText()));
                        t.setDétail(tfDétail.getText());
                        t.setCatégorie(tfCatégorie.getText());
                        t.setPhoto(tfPhoto.getText());
                        t.setID_Compte(Integer.parseInt(tfID_Compte.getText()));
                        if( ServiceProfile.getInstance().addProfile(t))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
//                addAll(tfNom,tfPhoto,tfRating,tfDescription,tfDétail,tfCatégorie,btnValider);

        
        addAll(tfNom,tfPhoto,tfRating,tfDescription,tfDétail,tfCatégorie,tfID_Compte,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK , e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
    
    
}