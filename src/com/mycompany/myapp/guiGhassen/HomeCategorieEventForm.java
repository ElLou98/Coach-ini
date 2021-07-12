
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiGhassen;


import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author bhk
 */
public class HomeCategorieEventForm extends Form {

    Form current;
    /*Garder traçe de la Form en cours pour la passer en paramètres 
    aux interfaces suivantes pour pouvoir y revenir plus tard en utilisant
    la méthode showBack*/

    public Form getCurrent() {
        return current;
    }
    
    public HomeCategorieEventForm(Resources res) {
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Home Catégorie Event");
        setLayout(BoxLayout.y());

        add(new Label("Choose an option"));
        Button btnAddTask = new Button("Add Categorie Event");
        Button btnListTasks = new Button("List Categorie Events");

        btnAddTask.addActionListener(e -> new AddCategorieEventForm(current,res).show());
        btnListTasks.addActionListener(e -> new ListCategorieEventsForm(current,res).show());
        addAll(btnAddTask, btnListTasks);

    }

}
