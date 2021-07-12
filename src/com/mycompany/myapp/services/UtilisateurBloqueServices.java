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
import com.mycompany.myapp.entities.UtilisateurBloque;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Louay
 */
public class UtilisateurBloqueServices {
   
    public ArrayList<UtilisateurBloque> utilisateurBloques;
    
    public static UtilisateurBloqueServices instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private UtilisateurBloqueServices() {
         req = new ConnectionRequest();
    }

    public static UtilisateurBloqueServices getInstance() {
        if (instance == null) {
            instance = new UtilisateurBloqueServices();
        }
        return instance;
    }
    
    public boolean addBloque(UtilisateurBloque u) {
        String url = Statics.BASE_URL + "/utilisateurbloque/ajoutBloqueJSON?utilisateur=" +u.getUtilisateur()+"&abloque="+u.getAbloque(); 
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
    
    public boolean deleteBloque(UtilisateurBloque u) {
        String url = Statics.BASE_URL + "/utilisateurbloque/deleteBloqueJSON/" +u.getIdBloque(); 
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
    
    public ArrayList<UtilisateurBloque> parseUtilisateurBloque(String jsonText){
        try {
            utilisateurBloques=new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String,Object> utilisateurBloqueListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)utilisateurBloqueListJson.get("root");
            
            for(Map<String,Object> obj : list){
                UtilisateurBloque u = new UtilisateurBloque();
                float id = Float.parseFloat(obj.get("idbloque").toString());
                u.setIdBloque((int)id);
                u.setUtilisateur(obj.get("utilisateur").toString());
                u.setAbloque(obj.get("abloque").toString());
                utilisateurBloques.add(u);
            }
            
            
        } catch (IOException ex) {
            
        }
        return utilisateurBloques;
    }
    
    
    public ArrayList<UtilisateurBloque> getAllUtilisateurBloque(){
        String url = Statics.BASE_URL+"/utilisateurbloque/getBlockJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                utilisateurBloques = parseUtilisateurBloque(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return utilisateurBloques;
    } 

//    public boolean editReclamation(Amis a) {
//        String url = Statics.BASE_URL + "/reclamation/editReclamationJSON/" +r.getIdReclamation(); 
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200; 
//                req.removeResponseListener(this);   
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }
}
