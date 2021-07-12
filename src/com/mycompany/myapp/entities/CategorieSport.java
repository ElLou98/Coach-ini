/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author asus
 */
public class CategorieSport {
     private int id;
    private String nom;
    private String description;
    private String photo;

    public CategorieSport(int id, String nom, String description, String photo) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.photo = photo;
    }

    public CategorieSport(String nom, String description, String photo) {
        this.nom = nom;
        this.description = description;
        this.photo = photo;
    }

    public CategorieSport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "CategorieSport{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", photo=" + photo + '}';
    }
    
}
