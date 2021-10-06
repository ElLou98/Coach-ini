/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Models;

import java.sql.Date;

/**
 *
 * @author kagha
 */
public class Event {

    private int id;
    private String Nom_event;
    private Date Date_debut;
    private String Heure_debut;
    private Date Date_fin;
    private String Heure_fin;
    private String Participation;
    private int Nb_participant;
    private String Description;
    private int id_Cat;
    private String db_map;
    private int id_Coach;
    private String mapVal;


    private int i;

    public Event(int id, String Nom_event, Date Date_debut, String Heure_debut, Date Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description) {
        this.id = id;
        this.Nom_event = Nom_event;
        this.Date_debut = Date_debut;
        this.Heure_debut = Heure_debut;
        this.Date_fin = Date_fin;
        this.Heure_fin = Heure_fin;
        this.Participation = Participation;
        this.Nb_participant = Nb_participant;
        this.Description = Description;
    }

    public Event(int id, String Nom_event, Date Date_debut, String Heure_debut, Date Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description, int id_Cat, String db_map) {
        this.id = id;
        this.Nom_event = Nom_event;
        this.Date_debut = Date_debut;
        this.Heure_debut = Heure_debut;
        this.Date_fin = Date_fin;
        this.Heure_fin = Heure_fin;
        this.Participation = Participation;
        this.Nb_participant = Nb_participant;
        this.Description = Description;
        this.id_Cat = id_Cat;
        this.db_map = db_map;
    }
     public Event(int id, String Nom_event, Date Date_debut, String Heure_debut, Date Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description, int id_Cat, String db_map, String mapVal) {
        this.id = id;
        this.Nom_event = Nom_event;
        this.Date_debut = Date_debut;
        this.Heure_debut = Heure_debut;
        this.Date_fin = Date_fin;
        this.Heure_fin = Heure_fin;
        this.Participation = Participation;
        this.Nb_participant = Nb_participant;
        this.Description = Description;
        this.id_Cat = id_Cat;
        this.db_map = db_map;
        this.mapVal=mapVal;
    }

    public Event(String Nom_event, Date Date_debut, String Heure_debut, Date Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description) {
        this.Nom_event = Nom_event;
        this.Date_debut = Date_debut;
        this.Heure_debut = Heure_debut;
        this.Date_fin = Date_fin;
        this.Heure_fin = Heure_fin;
        this.Participation = Participation;
        this.Nb_participant = Nb_participant;
        this.Description = Description;
    }

    public Event(String Nom_event, Date Date_debut, String Heure_debut, Date Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description, int id_Cat, String db_map) {
        this.Nom_event = Nom_event;
        this.Date_debut = Date_debut;
        this.Heure_debut = Heure_debut;
        this.Date_fin = Date_fin;
        this.Heure_fin = Heure_fin;
        this.Participation = Participation;
        this.Nb_participant = Nb_participant;
        this.Description = Description;
        this.id_Cat = id_Cat;
        this.db_map = db_map;
    }

    public Event(String Nom_event, Date Date_debut, String Heure_debut, Date Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description, int id_Cat, String db_map, int id_Coach) {
        this.Nom_event = Nom_event;
        this.Date_debut = Date_debut;
        this.Heure_debut = Heure_debut;
        this.Date_fin = Date_fin;
        this.Heure_fin = Heure_fin;
        this.Participation = Participation;
        this.Nb_participant = Nb_participant;
        this.Description = Description;
        this.id_Cat = id_Cat;
        this.db_map = db_map;
        this.id_Coach=id_Coach;
    }


    
    public Event(String Nom_event, Date Date_debut, String Heure_debut, Date Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description, int id_Cat, String db_map, int id_Coach, String mapVal) {
        this.Nom_event = Nom_event;
        this.Date_debut = Date_debut;
        this.Heure_debut = Heure_debut;
        this.Date_fin = Date_fin;
        this.Heure_fin = Heure_fin;
        this.Participation = Participation;
        this.Nb_participant = Nb_participant;
        this.Description = Description;
        this.id_Cat = id_Cat;
        this.db_map = db_map;
        this.id_Coach = id_Coach;
        this.mapVal = mapVal;
    }
    
  
        public String getMapVal() {
        return mapVal;
    }

    public void setMapVal(String mapVal) {
        this.mapVal = mapVal;
    }


    public int getId_Coach() {
        return id_Coach;
    }

    public void setId_Coach(int id_Coach) {
        this.id_Coach = id_Coach;
    }

    public String getDb_map() {
        return db_map;
    }

    public void setDb_map(String db_map) {
        this.db_map = db_map;
    }

    public int getId_Cat() {
        return id_Cat;
    }

    public void setId_Cat(int id_Cat) {
        this.id_Cat = id_Cat;
    }

    public Event(int id) {
        this.id = id;
        //System.out.println("ordre de suppression");
    }

    public Event(String nom) {
        this.Nom_event = nom;
        //System.out.println("ordre de suppression");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_event() {
        return Nom_event;
    }

    public void setNom_event(String Nom_event) {
        this.Nom_event = Nom_event;
    }

    public Date getDate_debut() {
        return Date_debut;
    }

    public void setDate_debut(Date Date_debut) {
        this.Date_debut = Date_debut;
    }

    public String getHeure_debut() {
        return Heure_debut;
    }

    public void setHeure_debut(String Heure_debut) {
        this.Heure_debut = Heure_debut;
    }

    public Date getDate_fin() {
        return Date_fin;
    }

    public void setDate_fin(Date Date_fin) {
        this.Date_fin = Date_fin;
    }

    public String getHeure_fin() {
        return Heure_fin;
    }

    public void setHeure_fin(String Heure_fin) {
        this.Heure_fin = Heure_fin;
    }

    public String getParticipation() {
        return Participation;
    }

    public void setParticipation(String Participation) {
        this.Participation = Participation;
    }

    public int getNb_participant() {
        return Nb_participant;
    }

    public void setNb_participant(int Nb_participant) {
        this.Nb_participant = Nb_participant;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String toString(boolean x) {
        return "Réclamation {" + "id=" + id + " Nom_event=" + Nom_event + " Date_debut=" + Date_debut + " Heure_debut=" + Heure_debut + " Date_fin=" + Date_fin + " Heure_fin=" + Heure_fin + " Participation=" + Participation + " Nb_participant=" + Nb_participant + " Description=" + Description;
    }

    public String toString() {
        return Nom_event;
    }
}
