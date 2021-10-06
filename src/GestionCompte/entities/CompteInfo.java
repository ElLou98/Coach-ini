/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompte.entities;

import GestionCompte.services.CompteCRUD;

/**
 *
 * @author masso
 */
public class CompteInfo extends Compte{
    
    private String type;

    public CompteInfo() {
        
    }

    public CompteInfo(int id) {
        super(id);
        this.type=CompteCRUD.typeCompte(id);
    }

    public CompteInfo(int id, String nomDutilisateur, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel) {
        super(id, nomDutilisateur, nom, prenom, age, adresseMail, motDePasse, numTel);
        this.type=CompteCRUD.typeCompte(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    

}
