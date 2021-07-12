/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guiLouay;

import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.io.websocket.WebSocket;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.entities.Message;
import com.mycompany.myapp.services.MessageServices;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Louay
 */
public class ChatBox extends Form {

    Form current;
    private String myUsername = EncapsulationCompte.getNomDutilisateur();
    private String channel = null;

    public ChatBox(Form previous, String friend) {
        current = createForm(previous, friend);
    }

    private Form createForm(Form previous, String friend) {
        Form tempForm = new Form();
        tempForm.setTitle("Chat " + friend);
        tempForm.setLayout(new BorderLayout());
        Container chatContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container notifContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container mainContainer=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        //tempForm.addComponent(BorderLayout.NORTH, notifContainer);
        //chatContainer.setScrollableY(true);
        ArrayList<Message> messages = MessageServices.getInstance().getAllMessage();
        for (Message m : messages) {
            String destinataire = m.getDestinataire();
            String expediteur = m.getExpediteur();
            if ((destinataire.equals(myUsername) && expediteur.equals(friend))
                    || (destinataire.equals(friend) && expediteur.equals(myUsername))) {
                SpanLabel labelTemp = new SpanLabel();
                labelTemp.setText(expediteur + " : " + m.getContenuMessage());
                Button boutonEditer=new Button("Editer");
                boutonEditer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        new EditerMessage(previous,m,friend).show();
                    }
                });
                Container messageContainer=new Container(new BoxLayout(BoxLayout.X_AXIS));
                messageContainer.addComponent(labelTemp);
                if(expediteur.equals(myUsername)){
                   messageContainer.addComponent(boutonEditer); 
                }
                chatContainer.add(messageContainer);
                tempForm.revalidate();
                chatContainer.scrollComponentToVisible(labelTemp);
            }
        }
        if (stringCompare(friend, myUsername) > 0) {
            channel = friend + myUsername;
        } else {
            channel = myUsername + friend;
        }

        WebSocket sock = new WebSocket("ws://127.0.0.1:8080") {

            @Override
            protected void onOpen() {
                System.out.println("In onOpen");
            }

            @Override
            protected void onClose(int statusCode, String reason) {

            }

            @Override
            protected void onMessage(final String message) {
                String messageToDisplay = null;
                String userFrom = null;
                try {
                    JSONParser j = new JSONParser();
                    Map<String, Object> messageJson = j.parseJSON(new CharArrayReader(message.toCharArray()));
                    messageToDisplay = messageJson.get("message").toString();
                    userFrom = messageJson.get("user").toString();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                String tempMessageToDisplay = messageToDisplay;
                String tempUserFrom = userFrom;
                Display.getInstance().callSerially(new Runnable() {
                    String friendRun = tempUserFrom;
                    String messageToDisplayRun = tempMessageToDisplay;

                    public void run() {
                        if (chatContainer == null) {
                            return;
                        }
                        SpanLabel label = new SpanLabel();
                        label.setText(friendRun + " : " + messageToDisplayRun);
                        if (friendRun.equals("ChatBot")) {
                            notifContainer.addComponent(label);
                            tempForm.revalidate();
                        } else {   
                            chatContainer.addComponent(label);
                            tempForm.revalidate();
                            mainContainer.scrollComponentToVisible(label);
                            chatContainer.animateHierarchy(100);
                        }

                    }

                });
            }

            @Override
            protected void onError(Exception ex) {
                ex.printStackTrace();
                System.out.println("in onError");
            }

            @Override
            protected void onMessage(byte[] message) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        System.out.println("Sending connect");
        sock.connect();
        try {
            Thread.sleep(750);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        sock.send("{\"me\":\"" + myUsername + "\", \"friend\":\"" + friend + "\", \"message\":\"none\", "
                + "\"action\":\"subscribe\",\"channel\":\"" + channel + "\",\"user\":\"" + myUsername + "\"}");
        Container sendingContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField inputTextField = new TextField("", "message", 30, TextField.USERNAME);
        inputTextField.getAllStyles().setFgColor(0x000000);
        Button boutonEnvoyer = new Button("Envoyer");
        boutonEnvoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (inputTextField.getText().equals("")) {
                    Dialog.show("ERROR", "Vous devez saisir un texte ", new Command("OK"));
                } else {
                    sock.send("{\"me\":\"" + myUsername + "\", \"friend\":\"" + friend + "\", \"message\":\"" + inputTextField.getText() + "\", "
                            + "\"action\":\"message\",\"channel\":\"" + channel + "\",\"user\":\"" + myUsername + "\"}");
                    String tempContenuMsg=inputTextField.getText();
                    inputTextField.setText("");
                    Message m=new Message(friend, myUsername,tempContenuMsg );
                    MessageServices.getInstance().addMessage(m);
                }
            }
        });
        sendingContainer.addAll(inputTextField, boutonEnvoyer);

        
        mainContainer.add(notifContainer);
        mainContainer.add(chatContainer);
        mainContainer.setScrollableY(true);
        tempForm.addComponent(BorderLayout.CENTER, mainContainer);
        tempForm.addComponent(BorderLayout.SOUTH, sendingContainer);
        tempForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new MesAmis(previous));
        tempForm.show();
        return tempForm;
    }

    public int stringCompare(String str1, String str2) {
        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
        if (l1 != l2) {
            return l1 - l2;
        } else {
            return 0;
        }
    }

}
