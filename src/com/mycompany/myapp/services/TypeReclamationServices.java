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
import com.mycompany.myapp.entities.TypeReclamation;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Louay
 */
public class TypeReclamationServices {

    public ArrayList<TypeReclamation> typeReclamations;

    public static TypeReclamationServices instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private TypeReclamationServices() {
        req = new ConnectionRequest();
    }

    public static TypeReclamationServices getInstance() {
        if (instance == null) {
            instance = new TypeReclamationServices();
        }
        return instance;
    }


    public ArrayList<TypeReclamation> parseTypeReclamations(String jsonText) {
        try {
            typeReclamations = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> typesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) typesListJson.get("root");

            for (Map<String, Object> obj : list) {
                TypeReclamation t = new TypeReclamation();
                float id = Float.parseFloat(obj.get("idtypereclamation").toString());
                t.setIdTypeReclamation((int) id);
                t.setTypeReclamation(obj.get("typereclamation").toString());
                typeReclamations.add(t);
            }

        } catch (IOException ex) {

        }
        return typeReclamations;
    }

    public ArrayList<TypeReclamation> getAllTypeReclamations() {
        String url = Statics.BASE_URL + "/typereclamation/getTypeJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                typeReclamations = parseTypeReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return typeReclamations;
    }
}
