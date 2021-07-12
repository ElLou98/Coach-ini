/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author masso
 */
public class Compte {
    protected String nomDutilisateur;
    protected int id;
    protected String nom;
    protected String prenom;
    protected int age ;
    protected String adresseMail;
    protected String motDePasse;
    protected int numTel;
    protected Date date;
    protected String type;
    
    public Compte() {
        
    }

    public Compte(int id) {
        this.id = id;
    }

    public Compte(String nomDutilisateur, int id, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel, Date date, String type) {
        this.nomDutilisateur = nomDutilisateur;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
        this.date = date;
        this.type = type;
    }

    public Compte(String nomDutilisateur, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel, Date date, String type) {
        this.nomDutilisateur = nomDutilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
        this.date = date;
        this.type = type;
    }
    
    public Compte(int id,String nomDutilisateur, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel) {
        this.nomDutilisateur=nomDutilisateur;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
    }

    public Compte(String nomDutilsiateur ,String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel) {
        this.nomDutilisateur=nomDutilsiateur;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public int getNumTel() {
        return numTel;
    }

    public int getId() {
        return id;
    }

    public String getNomDutilisateur() {
        return nomDutilisateur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomDutilisateur(String nomDutilisateur) {
        this.nomDutilisateur = nomDutilisateur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Compte{" + "nomDutilisateur=" + nomDutilisateur + ", id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", age=" + age + ", adresseMail=" + adresseMail + ", motDePasse=" + motDePasse + ", numTel=" + numTel + ", date=" + date + ", type=" + type + '}';
    }
    
    
    
}
