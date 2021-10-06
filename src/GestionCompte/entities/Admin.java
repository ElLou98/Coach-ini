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
public class Admin  extends Compte{
    
    private String role;

    public Admin(int id,String nomDutilsiateur ,String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel, String role) {
        super(id, nomDutilsiateur ,nom, prenom, age, adresseMail, motDePasse, numTel);
        this.role=role ;
    }
    
    public Admin(String nomDutilsiateur ,String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel, String role) {
        super( nomDutilsiateur ,nom, prenom, age, adresseMail, motDePasse, numTel);
        this.role= role;
    }

    public Admin() {
        
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Admin{" + "role=" + role + '}';
    }
    
    
    
    
}

