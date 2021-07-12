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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Offre;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asus
 */
public class OffreService {
    public ArrayList<Offre> offres;

    public static OffreService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private OffreService() {
        req = new ConnectionRequest();
    }

    public static OffreService getInstance() {
        if (instance == null) {
            instance = new OffreService();
        }
        return instance;
    }

    public boolean resultOk;

    public boolean addOffre(Offre o) {
        String url = Statics.BASE_URL + "/fronthome/offre/newOffreJSON?titre=" + o.getTitre() + "&date=" + o.getDate() + "&description=" + o.getDescription() + "&id_categorie=" + o.getId_categorie() + "&idCompte=" + o.getIdCompte();
        ConnectionRequest req = new ConnectionRequest(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean deleteOffre(Offre o) {
        String url = Statics.BASE_URL + "/fronthome/offre/DeleteOffreJson/" + o.getId();
        ConnectionRequest req = new ConnectionRequest(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean editOffre(Offre o) {
        String url = Statics.BASE_URL + "/fronthome/offre/editOffreJSON/" + o.getId();
        ConnectionRequest req = new ConnectionRequest(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public ArrayList<Offre> parseOffre(String jsonText) throws ParseException {
        try {
            offres = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> offreListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) offreListJson.get("root");

            for (Map<String, Object> obj : list) {
//                String date = obj.get("date").toString();
                Offre o = new Offre();
                float id = Float.parseFloat(obj.get("id").toString());
                o.setId((int) id);
                o.setTitre(obj.get("titre").toString());
                o.setDescription(obj.get("description").toString());
//                String sDate1 = String.valueOf(obj.get("date")).toString();
//                SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
//                Date date1 = formatter1.parse(sDate1);
//               String datett =String.valueOf(date1);
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dutyDay = (java.util.Date) simpleDateFormat.parse((String) obj.get("date"));
                o.setDate(dutyDay);
//                float id_categorie = Float.parseFloat(obj.get("id_categorie").toString());
//                o.setId_categorie((int) id_categorie);
//                float idCompte = Float.parseFloat(obj.get("idCompte").toString());
//
//                o.setIdCompte((int) idCompte);

                offres.add(o);
            }

        } catch (IOException ex) {

        }
        return offres;
    }

    public ArrayList<Offre> getAllOffre() {
        String url = Statics.BASE_URL + "/fronthome/offre/getAllOffreJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    offres = parseOffre(new String(req.getResponseData()));
                } catch (ParseException ex) {
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return offres;
    }
}
