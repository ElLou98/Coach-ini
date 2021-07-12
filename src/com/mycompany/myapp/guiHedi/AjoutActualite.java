/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiHedi;

import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
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
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.materialscreens.ProfileForm;
import com.codename1.uikit.materialscreens.SideMenuBaseForm;
import com.mycompany.myapp.entities.Actualite;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.services.ServiceActualite;
import java.io.InputStream;

/**
 *
 * @author Espace Info
 */

public class AjoutActualite extends SideMenuBaseForm {

    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};
    String imagename = "";
    String filename = "";

    public AjoutActualite(Resources res) {

        super(new BorderLayout());
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

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

        TextField tfstatut = new TextField("", "Staut:");
        tfstatut.getUnselectedStyle().setBorder(Border.createUnderlineBorder(2, 0x99CCC));
        Button tfimage = new Button("Image ");
        tfimage.getAllStyles().setBgColor(0x99CCC);
        tfimage.getAllStyles().setBgTransparency(255);
        tfimage.getUnselectedStyle().setFgColor(0xFFFFFF);
        tfimage.getAllStyles().setMarginBottom(20);
        Button tffichier = new Button("Fichier");
        tffichier.getAllStyles().setBgColor(0x99CCC);
        tffichier.getAllStyles().setBgTransparency(255);
        tffichier.getUnselectedStyle().setFgColor(0xFFFFFF);
        TextField tfbio = new TextField("", "Bio: ");
        tfbio.getUnselectedStyle().setBorder(Border.createUnderlineBorder(2, 0x99CCC));

        TextField tfcompetence = new TextField("", "Competence: ");
        tfcompetence.getUnselectedStyle().setBorder(Border.createUnderlineBorder(2, 0x99CCC));

        TextField tflikepub = new TextField("", "Like: ");
        tflikepub.getUnselectedStyle().setBorder(Border.createUnderlineBorder(2, 0x99CCC));

        tfstatut.getAllStyles().setFgColor(0x000000);
        tfbio.getAllStyles().setFgColor(0x000000);
        tfcompetence.getAllStyles().setFgColor(0x000000);
        tflikepub.getAllStyles().setFgColor(0x000000);

        Button btnValider = new Button("Ajouter Actualite");
        btnValider.getAllStyles().setBgColor(0x0000ff);
        btnValider.getAllStyles().setBgTransparency(255);
        btnValider.getUnselectedStyle().setFgColor(0xFFFFFF);
        btnValider.getAllStyles().setMarginBottom(20);
        Button btnAct = new Button("Mes Actualite");
        btnAct.getAllStyles().setBgColor(0x008080);
        btnAct.getAllStyles().setBgTransparency(255);
        btnAct.getUnselectedStyle().setFgColor(0xFFFFFF);
        btnAct.addActionListener(e -> new AfficheActualite(this));
        btnAct.getAllStyles().setMarginBottom(50);

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfstatut.getText().length() == 0) || (tfimage.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Actualite actualite = new Actualite();

                        actualite.setStatut(tfstatut.getText());
                        actualite.setImage(imagename);
                        actualite.setFichier(filename);
                        actualite.setBio(tfbio.getText());
                        actualite.setCompétence(tfcompetence.getText());
                        actualite.setLikepub(Integer.parseInt(tflikepub.getText()));
                        if (ServiceActualite.getInstance().ajouteractualite(actualite)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                            tfstatut.setText("");
                            tfbio.setText("");
                            tfcompetence.setText("");
                            tflikepub.setText("");

                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "LIKE must be a number", new Command("OK"));
                    }

                }

            }
        });

        tfimage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (FileChooser.isAvailable()) {
                    FileChooser.showOpenDialog(".jpeg, .jpg, ", e2 -> {
                        String file = (String) e2.getSource();
                        if (file == null) {
//            hi.add("No file was selected");
//            hi.revalidate();
                        } else {
                            imagename = file.substring(file.lastIndexOf("/") + 1);
                        }

//        hi.revalidate();
                    });
                }
            }

        });

        tffichier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (FileChooser.isAvailable()) {
                    FileChooser.showOpenDialog(".pdf, ", e2 -> {
                        String file = (String) e2.getSource();
                        if (file == null) {
//            hi.add("No file was selected");
//            hi.revalidate();
                        } else {
                            filename = file.substring(file.lastIndexOf("/") + 1);
                        }

//        hi.revalidate();
                    });
                }
            }

        });

        formContainer.addAll(btnAct, tfstatut, tfimage, tffichier, tfbio, tfcompetence, tflikepub, btnValider);

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
//    String imagename="";
//    String filename="";
//    
//
//    public AjoutActualite(Form previous){
//
//
//         setTitle("Ajouter Actualite");
//        setLayout(BoxLayout.y());
//        
//        
//        TextField tfstatut = new TextField("","Staut:");
//        tfstatut.setWidth(20);
//        Button tfimage= new Button( "Image ");
//        Button tffichier= new Button( "Fichier");
//        TextField tfbio= new TextField("", "Bio: ");
//        TextField tfcompetence= new TextField("", "Competence: ");
//        TextField tflikepub= new TextField("", "Like: ");
//        
//
//        Button btnValider = new Button("Ajouter Actualite");
//        
//        btnValider.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                if ((tfstatut.getText().length()==0)||(tfimage.getText().length()==0))
//                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
//                else
//                {
//                    try{ 
//                            Actualite actualite=new Actualite();
//
//                        actualite.setStatut(tfstatut.getText());
//                        actualite.setImage(imagename);
//                        actualite.setFichier(filename);
//                        actualite.setBio(tfbio.getText());
//                        actualite.setCompétence(tfcompetence.getText());
//                        actualite.setLikepub(Integer.parseInt(tflikepub.getText()));
//                        if( ServiceActualite.getInstance().ajouteractualite(actualite)){
//                            Dialog.show("Success","Connection accepted",new Command("OK"));
//                           previous.showBack();
//                   
//                        }                          
//                       else
//                            Dialog.show("ERROR", "Server error", new Command("OK"));
//                    } catch (NumberFormatException e) {
//                        Dialog.show("ERROR", "LIKE must be a number", new Command("OK"));
//                    }
//                    
//                }
//                
//                
//            }
//        });
//        
//        tfimage.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent evt) {
//if (FileChooser.isAvailable()) {
//    FileChooser.showOpenDialog(".jpeg, .jpg, ", e2-> {
//        String file = (String)e2.getSource();
//        if (file == null) {
////            hi.add("No file was selected");
////            hi.revalidate();
//        } else {
//          imagename=file.substring(file.lastIndexOf("/")+1 );
//        }
//        
////        hi.revalidate();
//    });
//}
//        }
//        
//        
//           
//         });
//        
//               tffichier.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent evt) {
//if (FileChooser.isAvailable()) {
//    FileChooser.showOpenDialog(".pdf, ", e2-> {
//        String file = (String)e2.getSource();
//        if (file == null) {
////            hi.add("No file was selected");
////            hi.revalidate();
//        } else {
//          filename=file.substring(file.lastIndexOf("/")+1 );
//        }
//        
////        hi.revalidate();
//    });
//}
//        }
//        
//        
//           
//         });
//              
//        addAll(tfstatut,tfimage,tffichier,tfbio,tfcompetence,tflikepub,btnValider);
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK , e-> previous.showBack()); // Revenir vers l'interface précédente
//                
//    }

}
