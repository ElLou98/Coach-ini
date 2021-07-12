/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import com.codename1.l10n.L10NManager;
import java.util.Date;



/**
 *
 * @author Adem
 */
public class Review {

    private int id_review;
//    private Date date_reclamation;
//    private String date_reclamation = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    private String description_review;
    private String nom_client_review;
    private String nom_coach_review;
    private float rating;
    
        L10NManager l10n = L10NManager.getInstance();
    String sysdate2 = l10n.formatDateTimeShort(new Date()).toString();



    private String date_review = sysdate2; //"2021-05-03 17:50:40"

    public Review() {
    }

    public Review(int id_review, String description_review, String nom_client_review, String nom_coach_review, String date_review, float rating) {
        this.id_review = id_review;
        this.description_review = description_review;
        this.nom_client_review = nom_client_review;
        this.nom_coach_review = nom_coach_review;
        this.date_review = date_review;
        this.rating = rating;
    }

    public Review(String description_review, String nom_client_review, String nom_coach_review, String date_review, float rating) {
        this.description_review = description_review;
        this.nom_client_review = nom_client_review;
        this.nom_coach_review = nom_coach_review;
        this.date_review = sysdate2; //*************************************************************************************
        this.rating = rating;
    }

    public Review( String description_review, String nom_client_review, String nom_coach_review, float rating) {
        this.description_review = description_review;
        this.nom_client_review = nom_client_review;
        this.rating = rating;
        this.nom_coach_review = nom_coach_review;
        this.date_review = date_review;
    }


    public Review(String nom_client_review) {
        this.nom_client_review = nom_client_review;
    }

    public Review(String description_review, String nom_client_review, float rating) {
        this.description_review = description_review;
        this.nom_client_review = nom_client_review;
        this.rating = rating;
    }

    public Review(String nom_client_review, String date_review) {

        this.date_review = date_review;
        this.nom_client_review = nom_client_review;

    }

    public int getId_review() {
        return id_review;
    }

    public String getDescription_review() {
        return description_review;
    }

    public String getNom_client_review() {
        return nom_client_review;
    }

    public String getDate_review() {
        return date_review;
    }

    public String getNom_coach_review() {
        return nom_coach_review;
    }

    public float getRating() {
        return rating;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public void setDescription_review(String description_review) {
        this.description_review = description_review;
    }

    public void setNom_client_review(String nom_client_review) {
        this.nom_client_review = nom_client_review;
    }

    public void setDate_review(String date_review) {
        this.date_review = date_review;
    }

    public void setNom_coach_review(String nom_coach_review) {
        this.nom_coach_review = nom_coach_review;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" + "id_review=" + id_review + ", description_review=" + description_review + ", nom_client_review=" + nom_client_review + ", nom_coach_review=" + nom_coach_review + ", rating=" + rating + ", date_review=" + date_review + '}';
    }

}
