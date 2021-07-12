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
public class signInCoach extends Form {
    public signInCoach(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        TextField login = new TextField("", "username", 20, TextField.USERNAME) ;
        TextField lnom = new TextField("", "Last name", 20, TextField.USERNAME) ;
        TextField lprenom = new TextField("", "First name", 20, TextField.USERNAME) ;
        TextField lemail = new TextField("", "email", 20, TextField.USERNAME) ;
        TextField lage = new TextField("", "age", 20, TextField.USERNAME) ;
        TextField lnumTel = new TextField("", "phone number", 20, TextField.USERNAME) ;
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
        TextField lprofession = new TextField("", "profession", 20, TextField.USERNAME) ;
        TextField erreur= new TextField("");
        
        erreur.getAllStyles().setFgColor(0xff0000);
        login.getAllStyles().setMargin(LEFT, 0);
        lnom.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        erreur.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("SIGN IN");
        loginButton.setUIID("LoginButton");
       
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               Compte c= new Compte(login.getText(), lnom.getText(), lprenom.getText(),Integer.parseInt(lage.getText()), lemail.getText(), password.getText(), Integer.parseInt(lnumTel.getText()));
                ServiceCompte.getInstance().sendSms();
               new Sms(theme, c, lprofession.getText()).show();
                   
            }
        });
        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("CreateNewAccountButton");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            
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
                login,
                lnom,
                lprenom,
                lage,
                lemail,
                lnumTel,
                password,
                lprofession,
                loginButton,
                createNewAccount
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
