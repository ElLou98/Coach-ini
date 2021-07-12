/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiAdem;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Adem
 */
public class HomePlanning extends Form{
    public HomePlanning(Resources res) {
        Form current;
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Planning");
        setLayout(BoxLayout.y());

        add(new Label(" "));
//        Button btnAddSeance = new Button("Add seance");
        Button btnListSeance = new Button("My Agenda");

        //btnListSeance.addActionListener(e -> new ListSeanceCoach(current));
        btnListSeance.addActionListener(e -> new ListSeance(current,res));
        addAll( btnListSeance);

    }
    
}
