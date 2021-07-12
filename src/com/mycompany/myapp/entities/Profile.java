/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Espace Info
 */
public class Profile {
     private String Photo;
    private String Description;
    private int Rating;
    private String Catégorie;
    private String Détail;
    private String Nom;
    private int ID_Coach;
    private int ID_Compte;

    public Profile(String Description, String Nom) {
        this.Description = Description;
        this.Nom = Nom;
    }

    public Profile(String Photo, String Description, int Rating, String Catégorie, String Détail, String Nom,int ID_Compte, int ID_Coach) {
        this.Photo = Photo;
        this.Description = Description;
        this.Rating = Rating;
        this.Catégorie = Catégorie;
        this.Détail = Détail;
        this.Nom = Nom;
        this.ID_Coach = ID_Coach;
        this.ID_Compte = ID_Compte;
    }

    public Profile(String Photo, String Description, int Rating, String Catégorie, String Détail, String Nom,int ID_Compte) {
        this.Photo = Photo;
        this.Description = Description;
        this.Rating = Rating;
        this.Catégorie = Catégorie;
        this.Détail = Détail;
        this.Nom = Nom;
        this.ID_Compte = ID_Compte;
    }

    public Profile() {
    }


    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int Rating) {
        this.Rating = Rating;
    }

    public String getCatégorie() {
        return Catégorie;
    }

    public void setCatégorie(String Catégorie) {
        this.Catégorie = Catégorie;
    }

    public String getDétail() {
        return Détail;
    }

    public Profile(int ID_Coach) {
        this.ID_Coach = ID_Coach;
    }

    public void setDétail(String Détail) {
        this.Détail = Détail;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public int getID_Coach() {
        return ID_Coach;
    }

    public void setID_Coach(int ID_Coach) {
        this.ID_Coach = ID_Coach;
    }

    public int getID_Compte() {
        return ID_Compte;
    }

    public void setID_Compte(int ID_Compte) {
        this.ID_Compte = ID_Compte;
    }

    @Override
    public String toString() {
        return "Profile{" + "Photo=" + Photo + ", Description=" + Description + ", Rating=" + Rating + ", Cat\u00e9gorie=" + Catégorie + ", D\u00e9tail=" + Détail + ", Nom=" + Nom + ", ID_Coach=" + ID_Coach + ", ID_Compte=" + ID_Compte + '}';
    }

    

    
}
