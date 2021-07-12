/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author masso
 */
public class Client extends Compte{

    public Client(String nomDutilsiateur ,String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel) {
        super(nomDutilsiateur ,nom, prenom, age, adresseMail, motDePasse, numTel);
    }
    
    public Client(int id,String nomDutilsiateur ,String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel) {
        super(id,nomDutilsiateur ,nom, prenom, age, adresseMail, motDePasse, numTel);
    }
    
    public Client(String nom)
    {
        this.nom=nom;
    }
    
    public Client(int id)
    {
        this.id=id;
    }

    public Client() {
    }
    
    
}
