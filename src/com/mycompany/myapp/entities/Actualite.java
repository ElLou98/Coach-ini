/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;



/**
 *
 * @author Espace Info
 */
public class Actualite {
     private int IdAct;
    private String Statut;
    private String Image;
    private String Fichier;
    private String Compétence;
    private String Bio;
    private int likepub;
    
    private Date datepub;
    
    public Actualite(int IdAct, String Statut, String Image, String Fichier, String Compétence, String Bio, int likepub, Date datepub) {
        this.IdAct = IdAct;
        this.Statut = Statut;
        this.Image = Image;
        this.Fichier = Fichier;
        this.Compétence = Compétence;
        this.Bio = Bio;
        this.likepub = likepub;
        this.datepub=datepub;

    }
    
    public Actualite(int IdAct) {
        this.IdAct = IdAct;

    }

    public Actualite() {
    }
    
    public Actualite(int IdAct, String Statut, String Image, String Fichier, String Compétence, String Bio, int likepub) {
        this.IdAct = IdAct;
        this.Statut = Statut;
        this.Image = Image;
        this.Fichier = Fichier;
        this.Compétence = Compétence;
        this.Bio = Bio;
        this.likepub = likepub;
  

    }

    public Actualite(String Statut, String Image, String Fichier, String Compétence, String Bio, int likepub, Date datepub) {
        this.Statut = Statut;
        this.Image = Image;
        this.Fichier = Fichier;
        this.Compétence = Compétence;
        this.Bio = Bio;
        this.likepub = likepub;
        this.datepub=datepub;
    }

    public Date getDatepub() {
        return datepub;
    }

    public int getIdAct() {
        return this.IdAct;
    }

    public void setIdAct(int IdAct) {
        this.IdAct = IdAct;
    }

    public String getStatut() {
        return this.Statut;
    }

    public void setStatut(String Statut) {
        this.Statut = Statut;
    }

    public String getImage() {
        return this.Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getFichier() {
        return this.Fichier;
    }

    public void setFichier(String Fichier) {
        this.Fichier = Fichier;
    }

    public String getCompétence() {
        return this.Compétence;
    }

    public void setCompétence(String Compétence) {
        this.Compétence = Compétence;
    }

    public String getBio() {
        return this.Bio;
    }

    public void setBio(String Bio) {
        this.Bio = Bio;
    }

    public int getLikepub() {
        return likepub;
    }

    public void setLikepub(int likepub) {
        this.likepub = likepub;
    }

    @Override
    public String toString() {
        return "Actualite{" + "IdAct=" + IdAct + ", Statut=" + Statut + ", Image=" + Image + ", Fichier=" + Fichier + ", Comp\u00e9tence=" + Compétence + ", Bio=" + Bio + ", likepub=" + likepub + '}';
    }


    
}
