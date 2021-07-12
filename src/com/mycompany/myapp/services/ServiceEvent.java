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
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceEvent {

    public ArrayList<Event> events;
    
    public static ServiceEvent instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceEvent() {
         req = new ConnectionRequest();
    }

    public static ServiceEvent getInstance() {
        if (instance == null) {
            instance = new ServiceEvent();
        }
        return instance;
    }

    public boolean addEvent(Event t) {
     
        String url = Statics.BASE_URL + "/event/addevent?" +"nomEvent="+ t.getNom_event()+ "&dateDebut=" + t.getDate_debut()+ "&heureDebut=" + t.getHeure_debut()+ "&dateFin=" + t.getDate_fin()+ "&heureFin=" + t.getHeure_fin()+ "&participation=" + t.getParticipation()+ "&nbParticipant=" + t.getNb_participant()+ "&description=" + t.getDescription()+ "&map=" + t.getDb_map()+ "&dbMap="+t.getDbimg()+"&idCat=" + t.getId_Cat()+"&idCoach=263";//+ t.getIdCoach()
        System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
        public boolean updateEvent(Event t, String id) {
     
        String url = Statics.BASE_URL + "/event/updateEvent/"+id+"?" +"nomEvent="+ t.getNom_event()+ "&dateDebut=" + t.getDate_debut()+ "&heureDebut=" + t.getHeure_debut()+ "&dateFin=" + t.getDate_fin()+ "&heureFin=" + t.getHeure_fin()+ "&participation=" + t.getParticipation()+ "&nbParticipant=" + t.getNb_participant()+ "&description=" + t.getDescription()+ "&map=" + t.getDb_map()+ "&dbMap="+t.getDbimg()+ "&idCat=" + t.getId_Cat()+"&idCoach="+ t.getIdCoach();
        System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
        
            public boolean deleteEvent(String id) {
     
        String url = Statics.BASE_URL + "/event/deleteEvent/" +id;
        System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Event> parseEvents(String jsonText){
        try {
            events=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
            */
            Map<String,Object> eventListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
              /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche.               
            
            Le format Json impose que l'objet soit définit sous forme
            de clé valeur avec la valeur elle même peut être un objet Json.
            Pour cela on utilise la structure Map comme elle est la structure la
            plus adéquate en Java pour stocker des couples Key/Value.
            
            Pour le cas d'un tableau (Json Array) contenant plusieurs objets
            sa valeur est une liste d'objets Json, donc une liste de Map
            */
            List<Map<String,Object>> list = (List<Map<String,Object>>)eventListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Event t = new Event();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setNom_event(obj.get("nomEvent").toString());
                t.setDate_debut(obj.get("dateDebut").toString());
                t.setHeure_debut(obj.get("heureDebut").toString());
                t.setDate_fin(obj.get("dateFin").toString());
                t.setHeure_fin(obj.get("heureFin").toString());
                t.setParticipation(obj.get("participation").toString());
                t.setNb_participant((int) (double) obj.get("nbParticipant"));
                t.setIdCoach((int)(double)obj.get("idCoach"));
                t.setDb_map(obj.get("dbMap").toString());
                //t.setId_Cat(Integer.valueOf(obj.get("idCat").toString()));
                t.setMap(obj.get("map").toString());
                t.setDescription(obj.get("description").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                events.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return events;
    }
    
    public ArrayList<Event> getAllEvents(){
        String url = Statics.BASE_URL+"/event/listallevents";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
    
}
