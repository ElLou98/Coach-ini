/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiLouay;

import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.materialscreens.ProfileForm;
import com.codename1.uikit.materialscreens.SideMenuBaseForm;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ReclamationServices;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class AfficherReclamationClient extends SideMenuBaseForm {

    Form current;
    private String myUsername = EncapsulationCompte.getNomDutilisateur();
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public AfficherReclamationClient(Resources res) {
        super(new BorderLayout());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        //menuButton.addActionListener(e -> getToolbar().openSideMenu());
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
                getToolbar().openSideMenu();
                
            }
        });
        
        Button settingsButton = new Button("");
        settingsButton.setUIID("Title");
        FontImage.setMaterialIcon(settingsButton, FontImage.MATERIAL_SETTINGS);

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

        setTransitionOutAnimator(CommonTransitions.createEmpty());
        GridLayout gridLayout = new GridLayout(1, 5);

        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Label labelDescription = new Label("Description");
        Label labelType = new Label("Type");
        Label labelDate = new Label("Date");
        Label labelEtat = new Label("Etat");
        Label labelAction = new Label("Action");
        labelDescription.getUnselectedStyle().setFont(fnt);
        labelType.getUnselectedStyle().setFont(fnt);
        labelDate.getUnselectedStyle().setFont(fnt);
        labelEtat.getUnselectedStyle().setFont(fnt);
        labelAction.getUnselectedStyle().setFont(fnt);

        Container enteteConainter = new Container(gridLayout);
        enteteConainter.add(labelDescription);
        enteteConainter.add(labelType);
        enteteConainter.add(labelDate);
        enteteConainter.add(labelEtat);
        enteteConainter.add(labelAction);
        formContainer.add(enteteConainter);
        ArrayList<Reclamation> Reclamations = ReclamationServices.getInstance().getAllReclamations();
        for (Reclamation r : Reclamations) {
            if (r.getLogin().equals(myUsername)) {
                String etat = null;
                if (r.getEnCours() == 0 && r.getTraite() == 0) {
                    etat = "-";
                } else if (r.getEnCours() == 1 && r.getTraite() == 0) {
                    etat = "En cours";
                } else if (r.getEnCours() == 0 && r.getTraite() == 1) {
                    etat = "Traité";
                }
                Container corpsConainter = new Container(gridLayout);
                corpsConainter.add(new SpanLabel(r.getDescriptionReclamation()));
                corpsConainter.add(new SpanLabel(r.getTypeReclamation()));
                corpsConainter.add(new Label(r.getDateReclamation().substring(0, 10)));
                corpsConainter.add(new Label(etat));
                Button bouton = new Button("Supprimer");
                bouton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (ReclamationServices.getInstance().deleteReclamation(r)) {
                            Dialog.show("Success", "Suppression effectué ", new Command("OK"));
                            new AfficherReclamationClient(res).show();
//                            Form newForm = createForm();
//                            newForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
//                            newForm.show();
                        } else {
                            Dialog.show("ERROR", "Suppression échoué", new Command("OK"));
                        }
                    }
                });

                corpsConainter.add(bouton);
                formContainer.add(corpsConainter);
            }
        }
        Button boutonAjout = new Button("Ajouter une réclamation");
        Form tempForm=this;
        boutonAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
                new AjouterReclamation(tempForm,res).show();
                setTransitionOutAnimator(CommonTransitions.createEmpty());
            }
        });
        formContainer.add(boutonAjout);

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
