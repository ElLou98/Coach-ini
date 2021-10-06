package Messagerie.entite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Louay
 */
public class Message {
    private int idMessage;
    private String destinataire;
    private String expediteur;
    private String contenuMessage;
    private Date dateEnvoie;

    
    public Message() {
    }
    
    
    public Message(String destinataire, String expediteur, String contenuMessage) {
        this.destinataire = destinataire;
        this.expediteur = expediteur;
        this.contenuMessage = contenuMessage;
        //this.dateEnvoie = LocalDateTime.now();
    }
    public Message(int idMessage,String destinataire, String expediteur, String contenuMessage,Date dateEnvoie) {
        this.idMessage = idMessage;
        this.destinataire = destinataire;
        this.expediteur = expediteur;
        this.contenuMessage = contenuMessage;
        this.dateEnvoie = dateEnvoie;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
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

    public String getContenuMessage() {
        return contenuMessage;
    }

    public void setContenuMessage(String contenuMessage) {
        this.contenuMessage = contenuMessage;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    
    

    
    
    
    
}
