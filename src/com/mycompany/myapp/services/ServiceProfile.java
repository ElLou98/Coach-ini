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
import com.mycompany.myapp.entities.Profile;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Espace Info
 */
public class ServiceProfile {

  
public ArrayList<Profile> profiles;
    
    public static ServiceProfile instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceProfile() {
         req = new ConnectionRequest();
    }

    public static ServiceProfile getInstance() {
        if (instance == null) {
            instance = new ServiceProfile();
        }
        return instance;
    }
     public boolean addProfile(Profile t) {
        String url = Statics.BASE_URL + "/profile/addProfileJSON?description=" + t.getDescription()+ "&nom=" + t.getNom()+ "&photo=" + t.getPhoto()+ "&categorie=" + t.getCatégorie()+ "&rating=" + t.getRating()+ "&detail=" + t.getDétail()+"&idcompte="+t.getID_Compte(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    
    
}
     public ArrayList<Profile> parseProfiles(String jsonText){
        try {
            profiles=new ArrayList<>();
            JSONParser j = new JSONParser();
         
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
           
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
  
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Profile t = new Profile();
                float id = Float.parseFloat(obj.get("id").toString());
//                int ID_Coach = Integer.parseInt(obj.get("id").toString());
                t.setID_Coach((int)id);
               t.setNom(obj.get("nom").toString());
                t.setPhoto(obj.get("photo").toString());
                t.setRating((int)Float.parseFloat(obj.get("rating").toString()));
                t.setCatégorie(obj.get("categorie").toString());
                t.setDescription(obj.get("description").toString());
                t.setDétail(obj.get("detail").toString());
                t.setID_Compte((int)Float.parseFloat(obj.get("id_compte").toString()));
                //Ajouter la tâche extraite de la réponse Json à la liste
                profiles.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return profiles;
    }
        public ArrayList<Profile> getAllProfile(){
            profiles = new ArrayList<>();
        String url = Statics.BASE_URL+"/profile/getProfileJSON";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //System.out.println(new String(req.getResponseData()));
                profiles = parseProfiles(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return profiles;
        
        
        
    }
        public ArrayList<Profile> getProfileByIdCompte(int id){
            profiles = new ArrayList<>();
            System.out.println(id+"**********");
        String url = Statics.BASE_URL+"/profile/getProfileByIdCompteJSON/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //System.out.println(new String(req.getResponseData()));
                profiles = parseProfiles(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return profiles;
}
}