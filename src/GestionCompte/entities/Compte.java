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
public class Compte {
  protected String nomDutilisateur;
    protected int id;
    protected String nom;
    protected String prenom;
    protected int age ;
    protected String adresseMail;
    protected String motDePasse;
    protected int numTel;
    
    public Compte() {
        
    }

    public Compte(int id) {
        this.id = id;
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
    
    @Override
    public String toString() {
        return "Compte{" + "Nom= " + nom + "Prenom= " + prenom + "Age= " + age + "Adresse mail= " + adresseMail + "Mot de passe= " + motDePasse + "Numero de telephone= " + numTel     + "}";
    }
}
