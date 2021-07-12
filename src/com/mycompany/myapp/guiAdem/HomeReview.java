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
public class HomeReview extends Form {

    public HomeReview(Resources res) {
        Form current;
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Reviews");
        setLayout(BoxLayout.y());

        add(new Label(" "));
//        Button btnAddReview = new Button("Add Review");
        Button btnListReview = new Button("My Reviews");

        //btnAddReview.addActionListener(e -> new AddReview(current).show());
        //btnListReview.addActionListener(e -> new ListReviewCoach(current));
        btnListReview.addActionListener(e -> new ListReview(current , res));
        addAll(btnListReview);

    }

}
