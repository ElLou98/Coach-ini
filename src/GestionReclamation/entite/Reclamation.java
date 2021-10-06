/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReclamation.entite;

//import java.text.SimpleDateFormat;
//import java.util.Calendar;


/**
 *
 * @author Adem
 */
public class Reclamation {
    private int idReclamation;
    private String login;
    private String descriptionReclamation;
    private String typeReclamation;
    java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");    //2021-02-24 20:48:12
    private String dateReclamation = sdf.format(dt);
    private int enCours;
    private int traite;
    private String enCoursString;
    private String traiteString;
    private String etat;
    
    

    public Reclamation() {
    }

    public Reclamation(int idReclamation, String login, String descriptionReclamation, String typeReclamation, String dateReclamation, int enCours, int traite) {
        this.idReclamation = idReclamation;
        this.login = login;
        this.descriptionReclamation = descriptionReclamation;
        this.typeReclamation = typeReclamation;
        this.dateReclamation=dateReclamation;
        this.enCours = enCours;
        this.traite = traite;
    }
    
    public Reclamation(String login, String descriptionReclamation, String typeReclamation, String dateReclamation, String enCoursString, String traiteString) {
        this.login = login;
        this.descriptionReclamation = descriptionReclamation;
        this.typeReclamation = typeReclamation;
        this.dateReclamation=dateReclamation;
        this.enCoursString = enCoursString;
        this.traiteString = traiteString;
    }
    
    public Reclamation(String login,String descriptionReclamation, String typeReclamation, String dateReclamation, int enCours, int traite) {
        this.login = login;
        this.descriptionReclamation = descriptionReclamation;
        this.typeReclamation = typeReclamation;
        this.dateReclamation=dateReclamation;
        if(enCours==1)
        {
            this.enCoursString="Oui";
            this.traiteString="Non";
        }
        else
        {
            if(traite==1)
            {
                this.traiteString="Oui";
                this.enCoursString="Non";
            }
            else
            {
                this.traiteString="Non";
                this.enCoursString="Non";
            }
        }
    }
    
    public Reclamation(String login,String descriptionReclamation, String typeReclamation, String dateReclamation) {
        this.login = login;
        this.descriptionReclamation = descriptionReclamation;
        this.typeReclamation = typeReclamation;
        this.dateReclamation=dateReclamation;
        this.enCours = 0;
        this.traite = 0;
    }
    
    public Reclamation(String login,String dateReclamation) {
        this.login = login;
        this.dateReclamation=dateReclamation;
    }



    public Reclamation(String dateReclamation, String descriptionReclamation, String etat) {
        this.dateReclamation = dateReclamation;
        this.descriptionReclamation = descriptionReclamation;
        this.etat = etat;
    }

    public String getEnCoursString() {
        return enCoursString;
    }

    public void setEnCoursString(String enCoursString) {
        this.enCoursString = enCoursString;
    }

    public String getTraiteString() {
        return traiteString;
    }

    public void setTraiteString(String traiteString) {
        this.traiteString = traiteString;
    }
    

    public int getIdReclamation() {
        return idReclamation;
    }

    public String getDateReclamation() {
        return dateReclamation;
    }

    public String getDescriptionReclamation() {
        return descriptionReclamation;
    }

    public String getTypeReclamation() {
        return typeReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public void setDateReclamation(String dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public void setDescriptionReclamation(String descriptionReclamation) {
        this.descriptionReclamation = descriptionReclamation;
    }

    public void setTypeReclamation(String typeReclamation) {
        this.typeReclamation = typeReclamation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getEnCours() {
        return enCours;
    }

    public void setEnCours(int enCours) {
        this.enCours = enCours;
    }

    public int getTraite() {
        return traite;
    }

    public void setTraite(int traite) {
        this.traite = traite;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    
    @Override
    public String toString() {
        return "Reclamation :" + " idReclamation = " + idReclamation + " , dateReclamation = " + dateReclamation + " , descriptionReclamation = " + descriptionReclamation + ", typeReclamation = " + typeReclamation + ' ';
    }
    
    
}
