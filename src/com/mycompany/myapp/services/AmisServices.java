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
import com.mycompany.myapp.entities.Amis;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Louay
 */
public class AmisServices {
   
    public ArrayList<Amis> amis;
    public ArrayList<String> usernameComptes;
    
    public static AmisServices instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private AmisServices() {
         req = new ConnectionRequest();
    }

    public static AmisServices getInstance() {
        if (instance == null) {
            instance = new AmisServices();
        }
        return instance;
    }
    
    public ArrayList<String> parseCompte(String jsonText){
        try {
            usernameComptes=new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String,Object> comptesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)comptesListJson.get("root");
            
            for(Map<String,Object> obj : list){
                usernameComptes.add(obj.get("username").toString());
            }
            
            
        } catch (IOException ex) {
            
        }
        return usernameComptes;
    }
    
    public ArrayList<String> getAllCompte(){
        String url = Statics.BASE_URL+"/amis/getCompteJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                usernameComptes = parseCompte(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return usernameComptes;
    }
    
    public boolean addAmis(Amis a) {
        String url = Statics.BASE_URL + "/amis/ajoutAmisJSON?destinataire=" +a.getDestinataire()+"&expediteur="+a.getExpediteur(); 
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
    
    public boolean accepterAmis(Amis a) {
        String url = Statics.BASE_URL + "/amis/accepterAmisJSON/" +a.getIdAmis();
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
    
    public boolean deleteAmis(Amis a) {
        String url = Statics.BASE_URL + "/amis/deleteAmisJSON/" +a.getIdAmis(); 
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
    
    public ArrayList<Amis> parseAmis(String jsonText){
        try {
            amis=new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String,Object> amisListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)amisListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Amis a = new Amis();
                float id = Float.parseFloat(obj.get("idamis").toString());
                a.setIdAmis((int)id);
                a.setDestinataire(obj.get("destinataire").toString());
                a.setExpediteur(obj.get("expediteur").toString());
                float etat = Float.parseFloat(obj.get("etatdemande").toString());
                a.setEtatDemande((int)etat);
                amis.add(a);
            }
            
            
        } catch (IOException ex) {
            
        }
        return amis;
    }
    
    
    public ArrayList<Amis> getAllAmis(){
        String url = Statics.BASE_URL+"/amis/getAmisJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                amis = parseAmis(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return amis;
    } 

}
