/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionOffre.models;

import java.sql.Date;
import java.util.Comparator;

/**
 *
 * @author asus
 */
public class Offre implements Comparator<Offre> {
    private int id;
    private String titre;
    private Date date;
    private String description;
    private int id_coach;
    private int id_categorie;
    private int idCompte;

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    
    
    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }
public Offre(int id, String titre, Date date, String description, int id_coach, int id_categorie,int idCompte) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.id_coach = id_coach;
        this.id_categorie = id_categorie;
         this.idCompte = idCompte;
    }
    public Offre(int id, String titre, Date date, String description, int id_coach, int id_categorie) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.id_coach = id_coach;
        this.id_categorie = id_categorie;
    }

    
    public Offre(int id,int id_coach,int id_categorie,String titre, Date date, String description, int idCompte) {
        this.id = id;
        this.id_coach = id_coach;
        this.id_categorie = id_categorie;
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.idCompte=idCompte;
        
    }
    public Offre(String titre, Date date, String description, int id_coach, int id_categorie,int idCompte) {
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.id_coach = id_coach;
        this.id_categorie = id_categorie;
        
    }
    public Offre(String titre, Date date, String description, int id_categorie, int idCompte) {
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.idCompte = idCompte;
        this.id_categorie = id_categorie;
    }
    

    public Offre(String titre, Date date, String description, int id_coach) {
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.id_coach = id_coach;
    }

    public Offre(int id, String titre, Date date, String description, int id_coach) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.description = description;
        this.id_coach = id_coach;
    }

    public void setId_coach(int id_coach) {
        this.id_coach = id_coach;
    }

    public int getId_coach() {
        return id_coach;
    }

    public Offre(int id) {
        this.id = id;
    }

    public Offre(String titre) {
        this.titre = titre;
    }

    public Offre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "titre=" + titre + ", date=" + date + ", description=" + description ;
    }

 
   
 
    public int compare(Offre a, Offre b) {
        return a.getDate().compareTo(b.getDate());
    } 
}
