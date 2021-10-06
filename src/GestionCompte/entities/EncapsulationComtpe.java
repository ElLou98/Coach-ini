/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompte.entities;

import Messagerie.clientChat.ChatClient;

/**
 *
 * @author masso
 */
public class EncapsulationComtpe {
    
    protected static String nomDutilisateur;
    protected static int id;
    protected static String nom;
    protected static String prenom;
    protected static int age ;
    protected static String adresseMail;
    protected static String motDePasse;
    protected static int numTel;
    protected static String profession;
    protected static ChatClient client;

    public static ChatClient getClient() {
        return client;
    }

    public static void setClient(ChatClient client) {
        EncapsulationComtpe.client = client;
    }

    public static String getProfession() {
        return profession;
    }

    public static void setProfession(String profession) {
        EncapsulationComtpe.profession = profession;
    }
    

    public EncapsulationComtpe() {
    }
    
    
    public EncapsulationComtpe(int id,String nomDutilisateur, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel) {
        this.nomDutilisateur=nomDutilisateur;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
    }
    
    public EncapsulationComtpe(int id,String nomDutilisateur, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel, ChatClient client) {
        this.nomDutilisateur=nomDutilisateur;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
        this.client=client;
    }
    
    public EncapsulationComtpe(int id,String nomDutilisateur, String nom, String prenom, int age, String adresseMail, String motDePasse, int numTel, String profession) {
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
        EncapsulationComtpe.nomDutilisateur = nomDutilisateur;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        EncapsulationComtpe.id = id;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        EncapsulationComtpe.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        EncapsulationComtpe.prenom = prenom;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        EncapsulationComtpe.age = age;
    }

    public static String getAdresseMail() {
        return adresseMail;
    }

    public static void setAdresseMail(String adresseMail) {
        EncapsulationComtpe.adresseMail = adresseMail;
    }

    public static String getMotDePasse() {
        return motDePasse;
    }

    public static void setMotDePasse(String motDePasse) {
        EncapsulationComtpe.motDePasse = motDePasse;
    }

    public static int getNumTel() {
        return numTel;
    }

    public static void setNumTel(int numTel) {
        EncapsulationComtpe.numTel = numTel;
    }
    
    public static EncapsulationComtpe getEncapsulationCompte() {
     EncapsulationComtpe ec=new EncapsulationComtpe(id, nomDutilisateur, nom, prenom, age, adresseMail, motDePasse, numTel);
     return ec;
    }
    
    
    
}
