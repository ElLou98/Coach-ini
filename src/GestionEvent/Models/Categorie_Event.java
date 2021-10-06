/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Models;

/**
 *
 * @author kagha
 */
public class Categorie_Event {

    private int id;
    private String nom;
    private String description;
    private String photo;
    private String db_picture;

    public String getDb_picture() {
        return db_picture;
    }

    public void setDb_picture(String db_picture) {
        this.db_picture = db_picture;
    }

    public Categorie_Event(String nom) {
        this.nom = nom;
    }

    public Categorie_Event(int id) {
        this.id = id;
    }

    public Categorie_Event(int id, String nom, String description, String db_picture) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.db_picture=db_picture;
    }

    public Categorie_Event(String nom, String description, String photo, String db_picture) {
        this.nom = nom;
        this.description = description;
        this.photo = photo;
        this.db_picture=db_picture;
    }

    public Categorie_Event(int id, String nom, String description, String photo, String db_picture) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.photo = photo;
        this.db_picture=db_picture;
    }

    public Categorie_Event() {
    }

    public int getId() {
        return id;
    }

    public void setId_categorie(int id) {
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
    public String toString(){
    return this.nom;
}

}
