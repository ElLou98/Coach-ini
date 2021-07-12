/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiHedi;

import com.codename1.ext.filechooser.FileChooser;
import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Actualite;
import com.mycompany.myapp.services.ServiceActualite;
import com.mycompany.myapp.services.ServiceProfile;

/**
 *
 * @author Espace Info
 */
public class ModifierActualite extends Form {
        String imagename="";
    String filename="";
    Actualite actualite;
    public ModifierActualite(Actualite actualite ,Form previous){
        this.actualite=actualite;
         setTitle("Modifier Actualite");
        setLayout(BoxLayout.y());
        
        TextField tfstatut = new TextField("","Staut:");
         tfstatut.getUnselectedStyle().setBorder(Border.createUnderlineBorder(1, 0x99CCC));
         
        Button tfimage= new Button( "Image ");
         tfimage.getAllStyles().setBgColor(0x99CCC);
        tfimage.getAllStyles().setBgTransparency(255);
        tfimage.getUnselectedStyle().setFgColor(0xFFFFFF);
        tfimage.getAllStyles().setMarginBottom(20);
        Button tffichier= new Button( "Fichier");
         tffichier.getAllStyles().setBgColor(0x99CCC);
        tffichier.getAllStyles().setBgTransparency(255);
        tffichier.getUnselectedStyle().setFgColor(0xFFFFFF);
        TextField tfbio= new TextField("", "Bio: ");
         tfbio.getUnselectedStyle().setBorder(Border.createUnderlineBorder(1, 0x99CCC));
        TextField tfcompetence= new TextField("", "Competence: ");
                 tfcompetence.getUnselectedStyle().setBorder(Border.createUnderlineBorder(1, 0x99CCC));

        TextField tflikepub= new TextField("", "Like: ");
                 tflikepub.getUnselectedStyle().setBorder(Border.createUnderlineBorder(1, 0x99CCC));

           tfstatut.getAllStyles().setFgColor(0x000000);
          tfbio.getAllStyles().setFgColor(0x000000);
        tfcompetence.getAllStyles().setFgColor(0x000000);
        tflikepub.getAllStyles().setFgColor(0x000000);
        tfstatut.setText(actualite.getStatut());
        
        tfbio.setText(actualite.getBio());
        tfcompetence.setText(actualite.getCompétence());
        tflikepub.setText(actualite.getLikepub()+"");

        Button btnValider = new Button("Modifier Actualite");
        btnValider.getAllStyles().setBgColor(0x0000ff);
        btnValider.getAllStyles().setBgTransparency(255);
        btnValider.getUnselectedStyle().setFgColor(0xFFFFFF);
        btnValider.getAllStyles().setMarginBottom(20);
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfstatut.getText().length()==0)||(tfimage.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try{ 
                        actualite.setStatut(tfstatut.getText());
                       actualite.setImage(imagename);
                        actualite.setFichier(filename);

                        actualite.setBio(tfbio.getText());
                        actualite.setCompétence(tfcompetence.getText());
                        actualite.setLikepub(Integer.parseInt(tflikepub.getText()));
                        if( ServiceActualite.getInstance().modifierActualite(actualite)){
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                                 previous.showBack();
//                          new AfficheActualite(previous).show();
                        }                          
                       else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
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
    FileChooser.showOpenDialog(".jpeg, .jpg, ", e2-> {
        String file = (String)e2.getSource();
        if (file == null) {
//            hi.add("No file was selected");
//            hi.revalidate();
        } else {
          imagename=file.substring(file.lastIndexOf("/")+1 );
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
    FileChooser.showOpenDialog(".pdf, ", e2-> {
        String file = (String)e2.getSource();
        if (file == null) {
//            hi.add("No file was selected");
//            hi.revalidate();
        } else {
          filename=file.substring(file.lastIndexOf("/")+1 );
        }
        
//        hi.revalidate();
    });
}
        }
        
        
           
         });

        addAll(tfstatut,tfimage,tffichier,tfbio,tfcompetence,tflikepub,btnValider);
    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK , e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
    }
    
    
