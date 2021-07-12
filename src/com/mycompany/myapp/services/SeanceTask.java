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
import com.mycompany.myapp.entities.Review;
import com.mycompany.myapp.entities.Seance;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adem
 */
public class SeanceTask {

    public ArrayList<Review> reviewslist;

    public static SeanceTask instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    private ArrayList<Seance> Seances;

    public SeanceTask() {
        req = new ConnectionRequest();
    }

    public static SeanceTask getInstance() {
        if (instance == null) {
            instance = new SeanceTask();
        }
        return instance;
    }

    public boolean addSeance(Seance s) {
        //http://127.0.0.1:8000/seance/planning/addseanceclientid/new?userName=ademmobile&summary=test mobile
        //&description=json mobile&date=2021-03-21&startsAt=16:58:23&finishsAt=17:58:23&localisation=sousse

        String url = Statics.BASE_URL + "/seance/planning/addseanceclientid/new?userName=" + s.getUser_name()
                + "&summary=" + s.getSummary()
                + "&description=" + s.getDescription()
                + "&date=" + s.getDate()
                + "&startsAt=" + s.getStarts_at()
                + "&finishsAt=" + s.getFinishs_at()
                + "&localisation=" + s.getLocalisation();

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

    public ArrayList<Seance> parseSeances(String jsonText) {

        try {
            Seances = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> seanceListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) seanceListJson.get("root");
            for (Map<String, Object> obj : list) {
                Seance s = new Seance();

                s.setUser_name(obj.get("userName").toString());
                s.setSummary(obj.get("summary").toString());
                s.setDescription(obj.get("description").toString());
                s.setDate(obj.get("date").toString());
                s.setStarts_at(obj.get("startsAt").toString());
                s.setFinishs_at(obj.get("finishsAt").toString());
                s.setLocalisation(obj.get("localisation").toString());

                Seances.add(s);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
        return Seances;
    }

    public ArrayList<Seance> getAllSeances() {
        String url = Statics.BASE_URL + "/seance/planning/allplanningjson/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Seances = parseSeances(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Seances;
    }

    public boolean deleteSeance(Seance s) {
        String url = Statics.BASE_URL + "/seance/planning/deleteseanceclientJSON?userName=" + s.getUser_name();
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
        public boolean editSeance(Seance s) {
        String url = Statics.BASE_URL + "/seance/planning/updateseanceclientJSON?userName=" + s.getUser_name()
                +"&summary="+s.getSummary()
                + "&description=" + s.getDescription()
                + "&date=" + s.getDate()
                + "&startsAt=" + s.getStarts_at()
                + "&finishsAt=" + s.getFinishs_at()
                + "&localisation=" + s.getLocalisation();
        
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
