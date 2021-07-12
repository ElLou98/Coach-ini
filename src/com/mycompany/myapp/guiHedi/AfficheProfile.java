/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiHedi;

import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.materialscreens.ProfileForm;
import com.codename1.uikit.materialscreens.SideMenuBaseForm;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.services.ServiceProfile;

/**
 *
 * @author Espace Info
 */
    public class AfficheProfile extends SideMenuBaseForm{
          private int ID_Compte = EncapsulationCompte.getId();

        private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    
public AfficheProfile(Resources res) {
    
        super(new BorderLayout());
        
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        

                                Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(100, 100);
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());
          Container remainingTasks = BoxLayout.encloseY(
                        new Label("12", "CenterTitle"),
                        new Label("remaining tasks", "CenterSubTitle")
                );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                        new Label("32", "CenterTitle"),
                        new Label("completed tasks", "CenterSubTitle")
        );
        
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

       
        
        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent = 
                BorderLayout.north(
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
        Container formContainer=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        
       
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        
        System.out.println(ID_Compte);
        //
//        SpanLabel sp = new SpanLabel();
//        sp.setText(ServiceProfile.getInstance().getAllProfile().toString());
//        formContainer.add(sp);
//     getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

        //

  

        
        Container flowLayout1 = FlowLayout.encloseCenter(new Label("Nom : "), 
        new Label(ServiceProfile.getInstance().getAllProfile().get(0).getNom())); 
        
          Container flowLayout2 = FlowLayout.encloseCenter(new Label("Déscription : "), 
        new Label(ServiceProfile.getInstance().getAllProfile().get(0).getDescription())); 
          
            Container flowLayout3 = FlowLayout.encloseCenter(new Label("Détail : "), 
        new Label(ServiceProfile.getInstance().getAllProfile().get(0).getDétail())); 
            
            
              Container flowLayout4 = FlowLayout.encloseCenter(new Label("Rating : "), 
        new Label(String.valueOf(ServiceProfile.getInstance().getAllProfile().get(0).getRating()))); 
              
              
                Container flowLayout5 = FlowLayout.encloseCenter(new Label("Catégorie : "), 
        new Label(ServiceProfile.getInstance().getAllProfile().get(0).getCatégorie()));
           formContainer.add(profilePic);
     
formContainer.add(flowLayout1);
formContainer.add(flowLayout2);
formContainer.add(flowLayout3);
formContainer.add(flowLayout4);
formContainer.add(flowLayout5);
        
        
        add(BorderLayout.CENTER,formContainer);
        
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
//    public AfficheProfile(Form previous) {
//        setTitle("List Proflies");
//        
//        SpanLabel sp = new SpanLabel();
//        sp.setText(ServiceProfile.getInstance().getProfileByIdCompte(ID_Compte).toString());
//        add(sp);
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
//    }

 
    
    
}


