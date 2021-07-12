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
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Louay
 */
public class ReclamationServices {
   
    public ArrayList<Reclamation> Reclamations;
    
    public static ReclamationServices instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ReclamationServices() {
         req = new ConnectionRequest();
    }

    public static ReclamationServices getInstance() {
        if (instance == null) {
            instance = new ReclamationServices();
        }
        return instance;
    }
    
    public boolean addReclamation(Reclamation r) {
        String url = Statics.BASE_URL + "/reclamation/addReclamationsJSON?login=" +r.getLogin()+"&description="+r.getDescriptionReclamation()+"&typereclamation="+r.getIdTypeReclamation(); 
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
    
    public boolean deleteReclamation(Reclamation r) {
        String url = Statics.BASE_URL + "/reclamation/deleteReclamationsJSON/" +r.getIdReclamation(); 
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
    
    public ArrayList<Reclamation> parseReclamations(String jsonText){
        try {
            Reclamations=new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String,Object> reclamationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)reclamationsListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Reclamation r = new Reclamation();
                float id = Float.parseFloat(obj.get("idreclamation").toString());
                r.setIdReclamation((int)id);
                r.setLogin(obj.get("login").toString());
                r.setDescriptionReclamation(obj.get("descriptionreclamation").toString());
                r.setTypeReclamation(obj.get("typereclamation").toString());
                r.setDateReclamation(obj.get("datereclamation").toString());
                float encours = Float.parseFloat(obj.get("encours").toString());
                r.setEnCours((int)encours);
                float traite = Float.parseFloat(obj.get("traite").toString());
                r.setTraite((int)traite);
                Reclamations.add(r);
            }
            
            
        } catch (IOException ex) {
            
        }
        return Reclamations;
    }
    
    
    public ArrayList<Reclamation> getAllReclamations(){
        String url = Statics.BASE_URL+"/reclamation/getReclamationsJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Reclamations = parseReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Reclamations;
    } 

    public boolean editReclamation(Reclamation r) {
        String url = Statics.BASE_URL + "/reclamation/editReclamationJSON/" +r.getIdReclamation(); 
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
}
