/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author kagha
 */
public class TableCategorie {
    String nom,description;
    ImageView img;

    public TableCategorie(String nom, String description, ImageView img) {
        this.nom = nom;
        this.description = description;
        this.img = img;
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

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
    
}
