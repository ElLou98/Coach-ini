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
import com.mycompany.myapp.entities.CategorieSport;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asus
 */
public class CategorieService {
     public ArrayList<CategorieSport> categories;

    public static CategorieService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private CategorieService() {
        req = new ConnectionRequest();
    }

    public static CategorieService getInstance() {
        if (instance == null) {
            instance = new CategorieService();
        }
        return instance;
    }

    public boolean resultOk;

    public boolean addCategorie(CategorieSport c) {
        String url = Statics.BASE_URL + "/backhome/categoriesport/newCategorieJSON?nom=" + c.getNom() + "&description=" + c.getDescription() + "&photo=" + c.getPhoto();
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

    public boolean deleteCategorie(CategorieSport c) {
        String url = Statics.BASE_URL + "/backhome/categoriesport/DeleteCategorieJson/" + c.getId();
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

    public boolean editCategorie(CategorieSport c) {
        String url = Statics.BASE_URL + "/backhome/categoriesport/editCatgorieJSON/" + c.getId();
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

    public ArrayList<CategorieSport> parseCategorie(String jsonText) {
        try {
            categories = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> categorieListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) categorieListJson.get("root");
            for (Map<String, Object> obj : list) {
                CategorieSport c = new CategorieSport();
                float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int) id);
                c.setNom(obj.get("nom").toString());
                c.setDescription(obj.get("description").toString());
                c.setPhoto(obj.get("photo").toString());

                categories.add(c);
            }

        } catch (IOException ex) {

        }
        return categories;
    }

    public ArrayList<CategorieSport> getAllCategories() {
        String url = Statics.BASE_URL + "/backhome/categoriesport/getAllJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categories = parseCategorie(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }
}
