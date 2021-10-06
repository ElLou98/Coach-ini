/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompte.entities;

/**
 *
 * @author masso
 */
public class Coach extends Compte{

    private String profession;
    
    public Coach(String nomDutilsiateur ,String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel,String profession) {
        super( nomDutilsiateur, nom, prenom, age, adresseMail, motDePasse, numTel);
        this.profession=profession ;
    }
    
    public Coach(String nom)
    {
        this.nom=nom;
    }

    public Coach() {
        
    }

    public Coach(int id, String nomDutilsiateur ,String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel,String profession) {
        super(id, nomDutilsiateur, nom, prenom, age, adresseMail, motDePasse, numTel);
        this.profession=profession ;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return super.toString()+" "+this.profession ;
    }
    
}
