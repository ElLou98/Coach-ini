/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionAmis.entite;

public class Amis {
    private int idAmis;
    private String destinataire;
    private String expediteur;
    private int etatDemande;

    public Amis() {
    }

    public Amis(int idAmis, String destinataire, String expediteur, int etatDemande) {
        this.idAmis = idAmis;
        this.destinataire = destinataire;
        this.expediteur = expediteur;
        this.etatDemande = etatDemande;
    }

    public Amis(String destinataire, String expediteur) {
        this.destinataire = destinataire;
        this.expediteur = expediteur;
    }

    public int getIdAmis() {
        return idAmis;
    }

    public void setIdAmis(int idAmis) {
        this.idAmis = idAmis;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public int getEtatDemande() {
        return etatDemande;
    }

    public void setEtatDemande(int etatDemande) {
        this.etatDemande = etatDemande;
    }
    
    
    
}
