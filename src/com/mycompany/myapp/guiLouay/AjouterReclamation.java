/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiLouay;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.TypeReclamation;
import com.mycompany.myapp.services.ReclamationServices;
import com.mycompany.myapp.services.TypeReclamationServices;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class AjouterReclamation extends Form {
    private String myUsername=EncapsulationCompte.getNomDutilisateur();
    
    public AjouterReclamation(Form previous,Resources res){
        setTitle("Ajouter réclamation");
        setLayout(BoxLayout.y());
        Font fnt= Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        
        Label typeLabel=new Label("Type de la réclamation :");
        typeLabel.getUnselectedStyle().setFont(fnt);
        Container c=new Container();
        ComboBox typeComboBox=new ComboBox();
        c.add(typeComboBox);
        ArrayList<TypeReclamation>typeReclamations=TypeReclamationServices.getInstance().getAllTypeReclamations();
        for (TypeReclamation t : typeReclamations){
            typeComboBox.addItem(t.getTypeReclamation());
        }
        
        Label descriptionLabel=new Label("Description :");
        descriptionLabel.getUnselectedStyle().setFont(fnt);
        TextArea descriptionTextArea = new TextArea(4, 100);
        Button btnValider = new Button("Valider");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((descriptionTextArea.getText().length()==0))
                    Dialog.show("Alerte", "Veuillez remplir le champ : Description", new Command("OK"));
                else
                {
                    try {
                        Reclamation r = new Reclamation();
                        r.setLogin(myUsername);
                        r.setDescriptionReclamation(descriptionTextArea.getText());
                        if(typeComboBox.getSelectedItem().equals("Attitude négative"))
                        {
                            r.setIdTypeReclamation(1);
                        }
                        else {
                            if(typeComboBox.getSelectedItem().equals("Problème technique"))
                            {
                                r.setIdTypeReclamation(3);
                            }
                            else {
                                if(typeComboBox.getSelectedItem().equals("Autres"))
                                {
                                    r.setIdTypeReclamation(4);
                                }
                            }
                        }
                        if( ReclamationServices.getInstance().addReclamation(r))
                        {
                            Dialog.show("Success","Votre reclamation a été ajouté avec succés",new Command("OK"));
                            descriptionTextArea.setText("");
                            typeComboBox.setSelectedIndex(0);
                        }
                        else
                        {
                            Dialog.show("ERROR", "Erreur lors de l'ajout de votre réclamation ", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                
                
            }
        });
        
        addAll(typeLabel,c,descriptionLabel,descriptionTextArea,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> new AfficherReclamationClient(res).show());
        
    }
    
}
