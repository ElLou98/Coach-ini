package com.mycompany.myapp.entities;

/**
 *
 * @author Louay
 */
public class UtilisateurBloque {
    private int idBloque;
    private String utilisateur;
    private String abloque;

    public UtilisateurBloque(int idBloque, String utilisateur, String abloque) {
        this.idBloque = idBloque;
        this.utilisateur = utilisateur;
        this.abloque = abloque;
    }
    public UtilisateurBloque(String utilisateur, String abloque) {
        this.utilisateur = utilisateur;
        this.abloque = abloque;
    }

    public UtilisateurBloque() {
    }

    public int getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(int idBloque) {
        this.idBloque = idBloque;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getAbloque() {
        return abloque;
    }

    public void setAbloque(String abloque) {
        this.abloque = abloque;
    }

    @Override
    public String toString() {
        return "UtilisateurBloque{" + "idBloque=" + idBloque + ", utilisateur=" + utilisateur + ", abloque=" + abloque + '}';
    }
    
    
    
}
