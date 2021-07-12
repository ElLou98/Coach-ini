/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author asus
 */
public class Offre {
    private int id;
    private String titre;
    private Date date;
    private String description;
    private int id_coach;
    private int id_categorie;
    private int idCompte;

    public Offre(int id, String titre, Date date, String description, int id_coach, int id_categorie, int idCompte) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.id_coach = id_coach;
        this.id_categorie = id_categorie;
        this.idCompte = idCompte;
    }

    public Offre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_coach() {
        return id_coach;
    }

    public void setId_coach(int id_coach) {
        this.id_coach = id_coach;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie)
    {
        this.id_categorie = id_categorie;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    @Override
    public String toString() {
        return "Offre{" + "id=" + id + ", titre=" + titre + ", date=" + date + ", description=" + description + ", id_coach=" + id_coach + ", id_categorie=" + id_categorie + ", idCompte=" + idCompte + '}';
    }

    public Offre(int id, String titre, Date date, String description) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.description = description;
    }

    public Offre(String titre, Date date, String description) {
        this.titre = titre;
        this.date = date;
        this.description = description;
    }

    public Offre(String titre, Date date, String description, int id_categorie) {
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.id_categorie = id_categorie;
    }

    public Offre(String titre, String description, Date date, int id_categorie, int idCompte) {

        this.titre = titre;
        this.description = description;
        this.date = date;
        this.id_categorie = id_categorie;
        this.idCompte = idCompte;

    }

}
