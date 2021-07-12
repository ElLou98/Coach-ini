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

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Compte;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.services.ServiceCompte;

/**
 *
 * @author Shai Almog
 */
public class StatsForm extends SideMenuBaseForm {
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public StatsForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        
        
        
        
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Button settingsButton = new Button("");
        settingsButton.setUIID("Title");
        FontImage.setMaterialIcon(settingsButton, FontImage.MATERIAL_SETTINGS);
        
        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent = 
                BorderLayout.north(
                    BorderLayout.west(menuButton).add(BorderLayout.EAST, settingsButton)
                ).
                add(BorderLayout.CENTER, space).
                add(BorderLayout.SOUTH, 
                        FlowLayout.encloseIn(
                                new Label(EncapsulationCompte.getPrenom(), "WelcomeBlue"),
                                new Label(EncapsulationCompte.getNom(), "WelcomeWhite")
                        ));
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);
        
        TextField login = new TextField(EncapsulationCompte.getNomDutilisateur(), "username", 20, TextField.USERNAME) ;
        TextField lnom = new TextField(EncapsulationCompte.getNom(), "Last name", 20, TextField.USERNAME) ;
        TextField lprenom = new TextField(EncapsulationCompte.getPrenom(), "First name", 20, TextField.USERNAME) ;
        TextField lemail = new TextField(EncapsulationCompte.getAdresseMail(), "email", 20, TextField.USERNAME) ;
        TextField lage = new TextField(Integer.toString(EncapsulationCompte.getAge()), "age", 20, TextField.USERNAME) ;
        TextField lnumTel = new TextField(Integer.toString(EncapsulationCompte.getNumTel()), "phone number", 20, TextField.USERNAME) ;
        TextField password = new TextField(ServiceCompte.decrypt(EncapsulationCompte.getMotDePasse()), "Password", 20, TextField.USERNAME) ;
        login.getAllStyles().setFgColor(0x000000);
        lnom.getAllStyles().setFgColor(0x000000);
        lprenom.getAllStyles().setFgColor(0x000000);
        lemail.getAllStyles().setFgColor(0x000000);
        lage.getAllStyles().setFgColor(0x000000);
        lnumTel.getAllStyles().setFgColor(0x000000);
        password.getAllStyles().setFgColor(0x000000);
        
        
        Button loginButton = new Button("EDIT");
        loginButton.setUIID("LoginButton");
        Button deleteButton = new Button("DELETE");
        
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               
               Compte c= new Compte(EncapsulationCompte.getId(),login.getText(), lnom.getText(), lprenom.getText(),Integer.parseInt(lage.getText()), lemail.getText(), password.getText(), Integer.parseInt(lnumTel.getText()));
               ServiceCompte.getInstance().updateCompte(c); 
                   
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               
               Compte c= new Compte(EncapsulationCompte.getId());
               ServiceCompte.getInstance().deleteCompte(c); 
               new LoginForm(res).show();
                   
            }
        });
        
        
        
        
        Container by = BoxLayout.encloseY(
                
                login,
                lnom,
                lprenom,
                lage,
                lemail,
                lnumTel,
                password,
                loginButton,
                deleteButton
                
                
                
        );
       
        add(BorderLayout.CENTER, 
                by);
        
        setupSideMenu(res);
    }

    private Image colorCircle(int color) {
        int size = Display.getInstance().convertToPixels(3);
        Image i = Image.createImage(size, size, 0);
        Graphics g = i.getGraphics();
        g.setColor(color);
        g.fillArc(0, 0, size, size, 0, 360);
        return i;
    }
    
    @Override
    protected void showOtherForm(Resources res) {
        new ProfileForm(res).show();
    }

    private XYMultipleSeriesRenderer createChartMultiRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        for(int color : COLORS) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
            r.setFillPoints(false);
            XYSeriesRenderer.FillOutsideLine outline = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BELOW);
            outline.setColor(color);
            r.addFillOutsideLine(outline);
            r.setLineWidth(5);
        }
        renderer.setPointSize(5f);
        renderer.setLabelsColor(0);
        renderer.setBackgroundColor(0xffffffff);
        renderer.setApplyBackgroundColor(true);
        renderer.setAxesColor(COLORS[0]);

        renderer.setXTitle("");
        renderer.setYTitle("");
        renderer.setAxesColor(0xcccccc);
        renderer.setLabelsColor(0);
        renderer.setXLabels(5);
        renderer.setYLabels(5);
        renderer.setShowGrid(true);
        
        renderer.setMargins(new int[] {0, 0, 0, 0});
        renderer.setMarginsColor(0xffffff);

        renderer.setShowLegend(false);
        
        renderer.setXAxisMin(3);
        renderer.setXAxisMax(8);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(10);
        return renderer;
    }
}
