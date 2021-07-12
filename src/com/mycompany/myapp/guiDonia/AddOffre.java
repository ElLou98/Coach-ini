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
package com.mycompany.myapp.guiDonia;

import com.codename1.uikit.materialscreens.*;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.CategorieSport;
import com.mycompany.myapp.entities.Compte;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Offre;
import com.mycompany.myapp.services.CategorieService;
import com.mycompany.myapp.services.OffreService;
import com.mycompany.myapp.services.ServiceCompte;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Shai Almog
 */
public class AddOffre extends SideMenuBaseForm {

    private int ID_Compte = EncapsulationCompte.getId();
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public AddOffre(Resources res) {
        super(new BorderLayout());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent
                = BorderLayout.north(
                        BorderLayout.west(menuButton)
                ).
                        add(BorderLayout.CENTER, space).
                        add(BorderLayout.SOUTH,
                                FlowLayout.encloseIn(
                                        new Label(EncapsulationCompte.getPrenom(), "WelcomeBlue"),
                                        new Label(" ", "WelcomeBlue"),
                                        new Label(EncapsulationCompte.getNom(), "WelcomeWhite")
                                ));
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);
        Container formContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Label titreLabel = new Label("Titre :");
//        titreLabel.getUnselectedStyle().setFont(fnt);
        titreLabel.getAllStyles().setFgColor(0x000000);

        TextField tftitre = new TextField("", "titre");
        tftitre.getUnselectedStyle().setBorder(Border.createUnderlineBorder(2, 0x99CCC));

        Label descriptionLabel = new Label("Description :");
        descriptionLabel.getAllStyles().setFgColor(0x000000);

        descriptionLabel.getUnselectedStyle().setFont(fnt);
        TextArea descriptionTextArea = new TextArea(4, 100);
        descriptionTextArea.setHint("Description");
        descriptionTextArea.getUnselectedStyle().setBorder(Border.createUnderlineBorder(2, 0x99CCC));

        Label tdateLabel = new Label("Date :");
        tdateLabel.getUnselectedStyle().setFont(fnt);
        tdateLabel.getAllStyles().setFgColor(0x000000);

        TextField tfDate = new TextField("", "Date");
        tfDate.getUnselectedStyle().setBorder(Border.createUnderlineBorder(2, 0x99CCC));

        Label typeLabel = new Label("Categorie sport :");

        typeLabel.getUnselectedStyle().setBorder(Border.createUnderlineBorder(2, 0x99CCC));

        typeLabel.getUnselectedStyle().setFont(fnt);
        Container c = new Container();
        ComboBox typeComboBox = new ComboBox();
        c.add(typeComboBox);
        ArrayList<CategorieSport> categories = CategorieService.getInstance().getAllCategories();
        for (CategorieSport t : categories) {
            typeComboBox.addItem(t.getNom());
        }
        Button btnValider = new Button("Add Offre");
        btnValider.getAllStyles().setBgColor(0x5BC0EB);
        btnValider.getAllStyles().setBgTransparency(255);
        btnValider.getUnselectedStyle().setFgColor(0xFFFFFF);
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tftitre.getText().length() == 0) || (descriptionTextArea.getText().length() == 0) || (tfDate.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Offre o = new Offre();
                        String sDate1 = tfDate.getText().toString();
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
//                        String selected = String.valueOf(typeComboBox.getSelectedItem())
                        o.setTitre(tftitre.getText());
                        o.setDate(date1);
                        o.setDescription(descriptionTextArea.getText());
                        if (typeComboBox.getSelectedItem().equals("Tennis")) {
                            o.setId_categorie(11);
                        } else {
                            if (typeComboBox.getSelectedItem().equals("BasketBall")) {
                                o.setId_categorie(12);
                            } else {
                                if (typeComboBox.getSelectedItem().equals("Box")) {
                                    o.setId_categorie(13);
                                }
                            }
//                            System.out.println(typeComboBox.getSelectedItem().equals("Cycling"));
                            if (typeComboBox.getSelectedItem().equals("Cycling")) {
                                o.setId_categorie(14);
                            } else {
                                if (typeComboBox.getSelectedItem().equals("FootBall")) {
                                    o.setId_categorie(16);
                                }
                            }
                        }
                        o.setIdCompte(263);
                        if (OffreService.getInstance().addOffre(o)) {
                            Dialog.show("Success", "Votre offre a été ajouté avec succés", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Erreur lors de l'ajout de votre offre ", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    } catch (ParseException ex) {
                    }

                }

            }
        });

        formContainer.addAll(titreLabel, tftitre, descriptionLabel, descriptionTextArea, tdateLabel, tfDate, typeLabel, c, btnValider);

        add(BorderLayout.CENTER, formContainer);

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
        for (int color : COLORS) {
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

        renderer.setMargins(new int[]{0, 0, 0, 0});
        renderer.setMarginsColor(0xffffff);

        renderer.setShowLegend(false);

        renderer.setXAxisMin(3);
        renderer.setXAxisMax(8);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(10);
        return renderer;
    }
}
