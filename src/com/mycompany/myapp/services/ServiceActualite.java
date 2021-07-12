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
import com.mycompany.myapp.entities.Actualite;

import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Espace Info
 */
public class ServiceActualite {

  
public ArrayList<Actualite> actualites;
    
    public static ServiceActualite instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceActualite() {
         req = new ConnectionRequest();
    }

    public static ServiceActualite getInstance() {
        if (instance == null) {
            instance = new ServiceActualite();
        }
        return instance;
    
    
}
     public ArrayList<Actualite> parseActualites(String jsonText){
        try {
            actualites=new ArrayList<>();
            JSONParser j = new JSONParser();
         
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
           
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
  
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Actualite t = new Actualite();
                //float id = Float.parseFloat(obj.get("id").toString());
                float IdAct = Float.parseFloat(obj.get("id").toString());
                t.setIdAct((int)IdAct);
                t.setStatut(obj.get("statut").toString());
                t.setImage(obj.get("image").toString());
                t.setFichier(obj.get("fichier").toString());
                t.setCompétence(obj.get("competence").toString());
                t.setBio(obj.get("bio").toString());
                t.setLikepub((int) Float.parseFloat(obj.get("likepub").toString()));
            
                //Ajouter la tâche extraite de la réponse Json à la liste
                actualites.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return actualites;
    }
         public boolean deleteActualite(Actualite r) {
        String url = Statics.BASE_URL + "/actualite/deleteActualiteJSON/" +r.getIdAct(); 
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
        public ArrayList<Actualite> getAllActualite(){
            actualites = new ArrayList<>();
        String url = Statics.BASE_URL+"/actualite/getActualiteJSON";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //System.out.println(new String(req.getResponseData()));
                actualites = parseActualites(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return actualites;
    }
        public boolean modifierActualite(Actualite a) {
            System.out.println(a);
        String url = Statics.BASE_URL + "/actualite/editActualiteJSON?id="+a.getIdAct()+"&statut=" + a.getStatut()+ "&image=" + a.getImage()+ "&fichier=" + a.getFichier()+ "&bio=" + a.getBio()+ "&competence=" + a.getCompétence()+ "&likepub=" + a.getLikepub(); //création de l'URL
            System.out.println(url);
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
          public boolean ajouteractualite(Actualite a) {
            System.out.println(a);
        String url = Statics.BASE_URL + "/actualite/addActualiteJSON?statut=" + a.getStatut()+ "&image=" + a.getImage()+ "&fichier=" + a.getFichier()+ "&bio=" + a.getBio()+ "&competence=" + a.getCompétence()+ "&likepub=" + a.getLikepub(); //création de l'URL
            System.out.println(url);
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
          public ArrayList<Actualite> searchActualite (String name){
              
            actualites = new ArrayList<>();
         String url = Statics.BASE_URL+"/actualite/searchActJSON/"+name;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //System.out.println(new String(req.getResponseData()));
                actualites = parseActualites(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
              System.out.println(actualites);
              return actualites;
          }
} 

  