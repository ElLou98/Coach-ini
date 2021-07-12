/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.codename1.uikit.materialscreens;

import com.codename1.components.ToastBar;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.guiAdem.HomeMenuReview;
import com.mycompany.myapp.guiAdem.HomeMenuReviewCoach;
import com.mycompany.myapp.guiDonia.AfficherOffre;
import com.mycompany.myapp.guiGhassen.CatEventMenu;
import com.mycompany.myapp.guiGhassen.EventMenu;
import com.mycompany.myapp.guiGhassen.EventMenuClient;
import com.mycompany.myapp.guiHedi.AfficheActualite;
import com.mycompany.myapp.guiHedi.AfficheProfile;
import com.mycompany.myapp.guiHedi.AjoutActualite;
import com.mycompany.myapp.guiLouay.AfficherReclamationClient;
import com.mycompany.myapp.guiLouay.GestionAmis;
import com.mycompany.myapp.guiAdem.HomeMenuPlanning;

/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseForm extends Form {

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {

        Label profilePicLabel = new Label(EncapsulationCompte.getNomDutilisateur(), "SideMenuTitle");

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        if (EncapsulationCompte.getTypeCompte().equals("client")) {
            getToolbar().addComponentToSideMenu(sidemenuTop);

            getToolbar().addMaterialCommandToSideMenu("  Amis & Messagerie", FontImage.MATERIAL_PERSON_ADD, e -> new GestionAmis(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Réclamation", FontImage.MATERIAL_CREATE, e -> new AfficherReclamationClient(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Offre", FontImage.MATERIAL_STYLE, e -> new AfficherOffre(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Event", FontImage.MATERIAL_EVENT, e -> new EventMenuClient(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Review", FontImage.MATERIAL_STAR_RATE, e -> new HomeMenuReview(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Plannig", FontImage.MATERIAL_CALENDAR_TODAY, e -> new HomeMenuPlanning(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, e -> new StatsForm(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new LoginForm(res).show());

        } else if (EncapsulationCompte.getTypeCompte().equals("coach")) {
            getToolbar().addComponentToSideMenu(sidemenuTop);

            getToolbar().addMaterialCommandToSideMenu("  Profil", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> new AfficheProfile(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Actuality", FontImage.MATERIAL_ARTICLE, e -> new AjoutActualite(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Amis & Messagerie", FontImage.MATERIAL_PERSON_ADD, e -> new GestionAmis(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Réclamation", FontImage.MATERIAL_CREATE, e -> new AfficherReclamationClient(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Offre", FontImage.MATERIAL_STYLE, e -> new AfficherOffre(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Event", FontImage.MATERIAL_EVENT, e -> new EventMenu(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Catégorie Event", FontImage.MATERIAL_DASHBOARD_CUSTOMIZE, e -> new CatEventMenu(res).show());

            getToolbar().addMaterialCommandToSideMenu("  Reviews", FontImage.MATERIAL_STAR_RATE, e -> new HomeMenuReviewCoach(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Plannig", FontImage.MATERIAL_CALENDAR_TODAY, e -> new HomeMenuPlanning(res).show());

            getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, e -> new StatsForm(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new LoginForm(res).show());

        }

    }

    protected abstract void showOtherForm(Resources res);
}
