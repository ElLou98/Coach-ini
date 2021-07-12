/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiLouay;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Message;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.TypeReclamation;
import com.mycompany.myapp.services.MessageServices;
import com.mycompany.myapp.services.ReclamationServices;
import com.mycompany.myapp.services.TypeReclamationServices;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class EditerMessage extends Form {

    private String myUsername = "Louay";

    public EditerMessage(Form First, Message m, String friend) {
        setTitle("Editer mon message");
        setLayout(BoxLayout.y());
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

        Label messageLabel = new Label("Mon message :");
        messageLabel.getUnselectedStyle().setFont(fnt);
        TextField messageTextField = new TextField(m.getContenuMessage());
        Button btnModifier = new Button("Modifier");
        Button btnSupprimer = new Button("Supprimer");
        Container boutonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        boutonContainer.add(btnModifier);
        boutonContainer.add(btnSupprimer);
        btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((messageTextField.getText().length() == 0)) {
                    Dialog.show("Alerte", "Veuillez remplir le champ : Mon message", new Command("OK"));
                } else {
                    m.setContenuMessage(messageTextField.getText());
                    if (MessageServices.getInstance().editerMessage(m)) {
                        Dialog.show("Success", "Votre message a été modifié avec succés", new Command("OK"));
                        new ChatBox(First, friend);
                    } else {
                        Dialog.show("ERROR", "Erreur lors de la modification de votre message ", new Command("OK"));
                        new ChatBox(First, friend);
                    }
                }

            }
        });

        btnSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (MessageServices.getInstance().deleteMessage(m)) {
                    Dialog.show("Success", "Votre message a été supprimé avec succés", new Command("OK"));
                    new ChatBox(First, friend);
                } else {
                    Dialog.show("ERROR", "Erreur lors de la suppression de votre message ", new Command("OK"));
                    new ChatBox(First, friend);
                }

            }
        });

        addAll(messageLabel, messageTextField, boutonContainer);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ChatBox(First, friend));
    }

}
