/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Compte;
import com.mycompany.myapp.services.ServiceCompte;

/**
 *
 * @author masso
 */
public class Sms extends Form {
    public Sms(Resources theme,Compte c,String profession) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        TextField login = new TextField("", "code", 20, TextField.USERNAME) ;
        
        TextField erreur= new TextField("");
        
        erreur.getAllStyles().setFgColor(0xff0000);
        login.getAllStyles().setMargin(LEFT, 0);
        
        erreur.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        
        
        Button loginButton = new Button("submit");
        loginButton.setUIID("LoginButton");
        Button resendButton = new Button("resend");
        resendButton.setUIID("LoginButton");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
             
              if(login.getText().equals("159753"))
              {
                  if( ServiceCompte.getInstance().addCoach(c,profession));
                     new LoginForm(theme).show();
              }
              else
                  erreur.setText("invalid code!");
            }
        });
        
        resendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
             ServiceCompte.getInstance().sendSms();
            }
        });
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                
                
                spaceLabel,
                BorderLayout.center(erreur),
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                
                loginButton,
                resendButton
                
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
