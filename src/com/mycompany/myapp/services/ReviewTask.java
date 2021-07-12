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
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adem
 */
public class ReviewTask {

    public ArrayList<Review> reviewslist;

    public static ReviewTask instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    private ArrayList<Review> Reviews;

    public ReviewTask() {
        req = new ConnectionRequest();
    }

    public static ReviewTask getInstance() {
        if (instance == null) {
            instance = new ReviewTask();
        }
        return instance;
    }

    public boolean addReview(Review r) {
        //127.0.0.1:8000/review/client/addreviewclientid/new?descriptionReview=test mobile
        //&nomClientReview=ademclient&nomCoachReview=ademcoach&dateReview=2021-05-03 20:20:20&rating=4

        String url = Statics.BASE_URL + "/review/client/addreviewclientid/new?descriptionReview=" + r.getDescription_review()
                + "&nomClientReview=" + r.getNom_client_review()
                + "&nomCoachReview=" + r.getNom_coach_review()
                + "&dateReview=" + r.getDate_review()
                + "&rating=" + r.getRating();

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

    public ArrayList<Review> parseReviews(String jsonText) {

        try {
            Reviews = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> reviewListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) reviewListJson.get("root");
            for (Map<String, Object> obj : list) {
                Review r = new Review();
                float id = Float.parseFloat(obj.get("idReview").toString());
                r.setId_review((int) id);
                r.setDescription_review(obj.get("descriptionReview").toString());
                r.setNom_client_review(obj.get("nomClientReview").toString());
                r.setNom_coach_review(obj.get("nomCoachReview").toString());
                r.setDate_review(obj.get("dateReview").toString());
                float rate = Float.parseFloat(obj.get("rating").toString());
                r.setRating((float) rate);
                Reviews.add(r);
            }

        } catch (IOException ex) {

        }
        return Reviews;
    }

    public ArrayList<Review> getAllReviews() {
        String url = Statics.BASE_URL + "/review/client/allreviewsjson/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Reviews = parseReviews(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Reviews;
    }

    public boolean deleteReview(Review r) {
        String url = Statics.BASE_URL + "/review/client/deletereviewclientJSON/" + r.getId_review();
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

    public boolean editReview(Review r) {
        String url = Statics.BASE_URL + "/review/client/updatereviewclientJSON/" + r.getId_review()
                +"?descriptionReview="+r.getDescription_review()
                + "&nomClientReview=" + r.getNom_client_review()
                + "&nomCoachReview=" + r.getNom_coach_review()
                + "&dateReview=" + r.getDate_review()
                + "&rating=" + r.getRating();
        
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
