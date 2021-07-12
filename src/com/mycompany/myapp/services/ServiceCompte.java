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
import static com.codename1.ui.events.ActionEvent.Type.Response;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Coach;
import com.mycompany.myapp.entities.Compte;
import com.mycompany.myapp.utils.Statics;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 *
 * @author masso
 */
public class ServiceCompte {

    public ArrayList<Compte> comptes;
    public Compte compte;
    public static ServiceCompte instance;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCompte() {
        req = new ConnectionRequest();
        compte = new Compte();
    }

    public static ServiceCompte getInstance() {
        if (instance == null) {
            instance = new ServiceCompte();
        }
        return instance;

    }

    public Compte login(String username, String password) {
        String url = Statics.BASE_URL + "/compte/authentifier?username=" + username + "&password=" + password;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                compte = findCompte(new String(req.getResponseData()));
                System.out.println(compte);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return compte;

    }

    public boolean addCompte(Compte c) {
        String url = Statics.BASE_URL + "/compte/addClient?age="+c.getAge()+"&nom=" + c.getNom() + "&username=" + c.getNomDutilisateur() + "&prenom="+c.getPrenom()+"&motDePasse="+encrypt(c.getMotDePasse())+"&email="+c.getAdresseMail()+"&numTel="+c.getNumTel();
        req.setUrl(url);
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
    
    public boolean addCoach(Compte c,String profession) {
        String url = Statics.BASE_URL + "/compte/addCoach?age="+c.getAge()+"&nom=" + c.getNom() + "&username=" + c.getNomDutilisateur() + "&prenom="+c.getPrenom()+"&motDePasse="+encrypt(c.getMotDePasse())+"&email="+c.getAdresseMail()+"&numTel="+c.getNumTel()+"&profession="+profession;
        req.setUrl(url);
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

    public boolean deleteCompte(Compte c) {
        String url = Statics.BASE_URL + "/compte/deleteCompte/" + c.getId();
        req.setUrl(url);
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
    
    public boolean deleteCoach(Compte c) {
        String url = Statics.BASE_URL + "/compte/deleteCoach/" + c.getId();
        req.setUrl(url);
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

    public boolean updateCompte(Compte c) {
        String url = Statics.BASE_URL + "/compte/updateCompte/" + c.getId() +"?age="+c.getAge()+"&nom=" + c.getNom() + "&username=" + c.getNomDutilisateur() + "&prenom="+c.getPrenom()+"&motDePasse="+encrypt(c.getMotDePasse())+"&email="+c.getAdresseMail()+"&numTel="+c.getNumTel();
        req.setUrl(url);
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
    
    public boolean updateCoach(Compte c,String profession) {
        String url = Statics.BASE_URL + "/compte/updateCoach/" + c.getId() +"?age="+c.getAge()+"&nom=" + c.getNom() + "&username=" + c.getNomDutilisateur() + "&prenom="+c.getPrenom()+"&motDePasse="+encrypt(c.getMotDePasse())+"&email="+c.getAdresseMail()+"&numTel="+c.getNumTel()+"&profession="+profession;
        req.setUrl(url);
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

    public ArrayList<Compte> parseComptes(String jsonText) {
        try {
            comptes = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> comptesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) comptesListJson.get("root");// Instanciation d'un objet JSONParser permettant le parsing du résultat json

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
//            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//            Map<String,Object> comptesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
//            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//            System.out.println(comptesListJson);
            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche.               
            
            Le format Json impose que l'objet soit définit sous forme
            de clé valeur avec la valeur elle même peut être un objet Json.
            Pour cela on utilise la structure Map comme elle est la structure la
            plus adéquate en Java pour stocker des couples Key/Value.
            
            Pour le cas d'un tableau (Json Array) contenant plusieurs objets
            sa valeur est une liste d'objets Json, donc une liste de Map
             */
            //List<Map<String,Object>> list = (List<Map<String,Object>>)comptesListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Compte c = new Compte();
                float age = Float.parseFloat(obj.get("age").toString());
                c.setAge((int) age);
                c.setNom(obj.get("nom").toString());
                c.setPrenom(obj.get("prenom").toString());
                c.setNomDutilisateur(obj.get("username").toString());
                comptes.add(c);
            }

        } catch (IOException ex) {

        }
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
         */
        return comptes;
    }

    public Compte findCompte(String jsonText) {

        try {
            
            JSONParser j = new JSONParser();
            j.setIncludeNulls(true);
            if(!jsonText.equals("null")){
                Map<String, Object> compteListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
  
                if(compteListJson.containsKey("age"))
                    compte.setAge((int) Float.parseFloat(compteListJson.get("age").toString()));
                if(compteListJson.containsKey("id"))
                    compte.setId( (int) Float.parseFloat(compteListJson.get("id").toString()));
               if(compteListJson.containsKey("numTel"))
                   compte.setNumTel((int) Float.parseFloat(compteListJson.get("numTel").toString()));
                if(compteListJson.containsKey("nom"))
                    compte.setNom(compteListJson.get("nom").toString());
                if(compteListJson.containsKey("prenom"))
                    compte.setPrenom(compteListJson.get("prenom").toString());
                if(compteListJson.containsKey("username"))
                    compte.setNomDutilisateur(compteListJson.get("username").toString());
                if(compteListJson.containsKey("adresseMail"))
                    compte.setAdresseMail(compteListJson.get("adresseMail").toString());
                if(compteListJson.containsKey("motDePasse"))
                    compte.setMotDePasse(compteListJson.get("motDePasse").toString());
                if(compteListJson.containsKey("type"))
                    compte.setType(compteListJson.get("type").toString());

            }  
        } catch (IOException ex) {
            return compte;
        }
        return compte;
    }

    public ArrayList<Compte> getAllComptes() {
        String url = Statics.BASE_URL + "/compte/liste/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                comptes = parseComptes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return comptes;
    }

    public Compte getCompte() {
        String url = Statics.BASE_URL + "/compte/find/160";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                compte = findCompte(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return compte;
    }

    public boolean retrievePassword(String email) {
        String url = Statics.BASE_URL + "/compte/retrievePassword/"+email;
        req.setUrl(url);
        
        
        
        resultOK=true;
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String jsonText= new String(req.getResponseData());
                System.out.println(jsonText);
                
                if(jsonText.contains("nulle"))
                    resultOK=false;
               req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;

    }

    public void sendSms() {

        Twilio.init("AC5110b71cce19bab16cde98a018778611", "b48e2f174cc3773ef8db3653def1854d");
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21626213651"),
                new com.twilio.type.PhoneNumber("+19282482909"),
                "159753")
                .create();

        System.out.println(message.getSid());
    }

    public static String encrypt(String  motDePasse){
    String result="";
    String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int i=0;
    while(i<motDePasse.length())
    {
        char c=motDePasse.charAt(i);
        int j=alphabet.indexOf(c);
        if(j!=-1)
        {
            if(i+j>51)
            {
                int k=i+j-52;
                result+= alphabet.charAt(k);
            }
            else
                result+= alphabet.charAt(i+j);
        }
        else
            result+= c;
        i++;
    }
    return  result;
    }

    public static String decrypt(String  motDePasse){
    String result="";
    String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int i=0;
    while(i<motDePasse.length())
    {
        char c=motDePasse.charAt(i);
        int j=alphabet.indexOf(c);
        if(j!=-1)
        {
            if(j-i<0)
            {
                int k=j-i+52;
                result+= alphabet.charAt(k);
            }
            else
                result+= alphabet.charAt(j-i);
        }
        else
            result+= c;
        i++;
    }
    return  result;
    }
    
}
