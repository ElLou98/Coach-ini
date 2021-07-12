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
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;

import com.codename1.ui.util.Resources;

import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Offre;
import com.mycompany.myapp.services.OffreService;

import java.util.ArrayList;

/**
 *
 * @author Shai Almog
 */
public class AfficherOffre extends SideMenuBaseForm {

    Form current;
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public AfficherOffre(Resources res) {
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
        GridLayout gridLayout = new GridLayout(1, 5);

//        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener(e -> new AddOffre(res).show());
        fab.bindFabToContainer(this.getContentPane());
        Toolbar.setGlobalToolbar(true);
        Form hi = new Form("Search", BoxLayout.y());
//        hi.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ex -> previous.showBack());
        hi.add(new InfiniteProgress());
        setTransitionOutAnimator(CommonTransitions.createEmpty());

        Container enteteConainter = new Container(gridLayout);

        formContainer.add(enteteConainter);
        ArrayList<Offre> offres = OffreService.getInstance().getAllOffre();
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...
            //Contact[] cnts = Display.getInstance().getAllContacts(true, true, true, true, false, false);

            Display.getInstance().callSerially(() -> {
                hi.removeAll();
                for (Offre eyy : offres) {
                    MultiButton m = new MultiButton();
                    m.setTextLine1("Nom: " + eyy.getTitre());
                    m.setTextLine2(eyy.getDescription());
                    String date2 = (new SimpleDateFormat("yyyy-MM-dd")).format(eyy.getDate());
                    m.setTextLine3("Date: " + date2);

                    hi.add(m);
                }
                hi.revalidate();
            });
        });

//        for (Offre r : offres) {
//
//            Container Conainter = new Container(gridLayout);
//            Conainter.add(new Label(r.getTitre()));
//            Conainter.add(new Label(r.getDescription()));
//            String date2 = (new SimpleDateFormat("yyyy-MM-dd")).format(r.getDate());
//            Label dateF = new Label(date2);
//            Conainter.add(dateF);
//            
//
//            Button bouton = new Button("Supprimer");
//            bouton.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent evt) {
//                    if (OffreService.getInstance().deleteOffre(r)) {
//                        Dialog.show("Success", "Suppression effectué ", new Command("OK"));
////                        Form newForm = createForm();
////                        newForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
////                        newForm.show();
//                    } else {
//                        Dialog.show("ERROR", "Suppression échoué", new Command("OK"));
//                    }
//                }
//            });
//
//            Conainter.add(bouton);
//            add(Conainter);
//
//        }
//        Button refresh = new Button("refresh");
//        refresh.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                ListOffre ref = new ListOffre(previous);
//                ref.show();
//            }
//        });
//        add(refresh);
        hi.getToolbar().addSearchCommand(eyy -> {
            String text = (String) eyy.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : hi.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                hi.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : hi.getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    String line3 = mb.getTextLine3();
                    String line4 = mb.getTextLine4();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1
                            || line3 != null && line3.toLowerCase().indexOf(text) > -1
                            || line4 != null && line4.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                hi.getContentPane().animateLayout(150);
            }
        }, 4);

        formContainer.add(hi);

//         SpanLabel sp = new SpanLabel();
//         sp.setText(OffreService.getInstance().getAllOffre().toString());
//         add(sp);
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        formContainer.addAll();

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
