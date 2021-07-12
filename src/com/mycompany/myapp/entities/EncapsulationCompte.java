/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;


public class EncapsulationCompte {
    
    protected static String nomDutilisateur;
    protected static int id;
    protected static String nom;
    protected static String prenom;
    protected static int age ;
    protected static String adresseMail;
    protected static String motDePasse;
    protected static int numTel;
    protected static String profession;
    protected static String typeCompte;

    public static String getProfession() {
        return profession;
    }

    public static void setProfession(String profession) {
        EncapsulationCompte.profession = profession;
    }
    

    public EncapsulationCompte() {
    }
    
    
    public EncapsulationCompte(int id,String nomDutilisateur, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel) {
        this.nomDutilisateur=nomDutilisateur;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
    }
    
    public EncapsulationCompte(int id,String nomDutilisateur, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel, String profession) {
        this.nomDutilisateur=nomDutilisateur;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
        this.profession=profession;
    }

    public static String getNomDutilisateur() {
        return nomDutilisateur;
    }

    public static void setNomDutilisateur(String nomDutilisateur) {
        EncapsulationCompte.nomDutilisateur = nomDutilisateur;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        EncapsulationCompte.id = id;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        EncapsulationCompte.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        EncapsulationCompte.prenom = prenom;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        EncapsulationCompte.age = age;
    }

    public static String getAdresseMail() {
        return adresseMail;
    }

    public static void setAdresseMail(String adresseMail) {
        EncapsulationCompte.adresseMail = adresseMail;
    }

    public static String getMotDePasse() {
        return motDePasse;
    }

    public static void setMotDePasse(String motDePasse) {
        EncapsulationCompte.motDePasse = motDePasse;
    }

    public static int getNumTel() {
        return numTel;
    }

    public static void setNumTel(int numTel) {
        EncapsulationCompte.numTel = numTel;
    }

    public static String getTypeCompte() {
        return typeCompte;
    }

    public static void setTypeCompte(String typeCompte) {
        EncapsulationCompte.typeCompte = typeCompte;
    }
    
    
    
    public static void setCompte(Compte compte )
    {
        EncapsulationCompte.id = compte.getId();
        EncapsulationCompte.nomDutilisateur= compte.getNomDutilisateur();
        EncapsulationCompte.nom = compte.getNom();
        EncapsulationCompte.prenom = compte.getPrenom();
        EncapsulationCompte.motDePasse = compte.getMotDePasse();
        EncapsulationCompte.adresseMail = compte.getAdresseMail();
        EncapsulationCompte.age = compte.getAge();
        EncapsulationCompte.numTel = compte.getNumTel();
        EncapsulationCompte.typeCompte=compte.getType();
    }
    
    public static EncapsulationCompte getEncapsulationCompte() {
     EncapsulationCompte ec=new EncapsulationCompte(id, nomDutilisateur, nom, prenom, age, adresseMail, motDePasse, numTel);
     return ec;
    }
    
    
    
    
}