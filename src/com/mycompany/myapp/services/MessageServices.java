/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Message;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Louay
 */
public class MessageServices {
   
    public ArrayList<Message> messages;
    public ArrayList<String> usernameComptes;
    
    public static MessageServices instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private MessageServices() {
         req = new ConnectionRequest();
    }

    public static MessageServices getInstance() {
        if (instance == null) {
            instance = new MessageServices();
        }
        return instance;
    }
    
    
    public boolean addMessage(Message m) {
        String url = Statics.BASE_URL + "/msg/addMessageJSON?destinataire="+m.getDestinataire()+"&expediteur="+m.getExpediteur()+"&contenumessage="+m.getContenuMessage(); 
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);   
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean editerMessage(Message m) {
        String url = Statics.BASE_URL + "/msg/editMessageJSON/"+m.getIdMessage()+"?contenumessage="+m.getContenuMessage();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);   
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean deleteMessage(Message m) {
        String url = Statics.BASE_URL + "/msg/deleteMessageJSON/" +m.getIdMessage(); 
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);   
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<Message> parseMessage(String jsonText){
        try {
            messages=new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String,Object> messageListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)messageListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Message m = new Message();
                float id = Float.parseFloat(obj.get("idmessage").toString());
                m.setIdMessage((int)id);
                m.setDestinataire(obj.get("destinataire").toString());
                m.setExpediteur(obj.get("expediteur").toString());
                m.setContenuMessage(obj.get("contenumessage").toString());
                messages.add(m);
            }
            
            
        } catch (IOException ex) {
            
        }
        return messages;
    }
    
    
    public ArrayList<Message> getAllMessage(){
        String url = Statics.BASE_URL+"/msg/getMessageJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                messages = parseMessage(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return messages;
    } 

}
