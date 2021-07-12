/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date ;

/**
 *
 * @author kagha
 */
public class Event 
{
    
    
    private int id;
    private String Nom_event;
    private String Date_debut;
    private String Heure_debut;
    private String Date_fin;
    private String Heure_fin;
    private String Participation;
    private int Nb_participant;
    private String Description;
    private int id_Cat;
    private String db_map;
    private String dbimg;
    private String map;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getDbimg() {
        return dbimg;
    }

    public void setDbimg(String dbimg) {
        this.dbimg = dbimg;
    }
    private int idCoach;

    public Event(String Nom_event, String Date_debut, String Heure_debut, String Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description, int id_Cat, String db_map, int idCoach,String dbimg) {
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
        this.dbimg = dbimg;
        this.idCoach = idCoach;
    }
        public Event(String Nom_event, String Date_debut, String Heure_debut, String Date_fin, String Heure_fin, String Participation, int Nb_participant, String Description, int id_Cat, String db_map, int idCoach) {
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
        this.idCoach = idCoach;
    }

    
    public Event() {
        
    }

    public String getDate_debut() {
        return Date_debut;
    }

    public void setDate_debut(String Date_debut) {
        this.Date_debut = Date_debut;
    }

    public String getDate_fin() {
        return Date_fin;
    }

    public void setDate_fin(String Date_fin) {
        this.Date_fin = Date_fin;
    }

    
    public String getDb_map() {
        return db_map;
    }

    public void setDb_map(String db_map) {
        this.db_map = db_map;
    }
    private int i;


    public int getIdCoach() {
        return idCoach;
    }

    public void setIdCoach(int idCoach) {
        this.idCoach = idCoach;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

   
        public int getId_Cat() {
        return id_Cat;
    }

    public void setId_Cat(int id_Cat) {
        this.id_Cat = id_Cat;
    }

    public Event(int id) 
    {
        this.id = id;
        //System.out.println("ordre de suppression");
    }
        public Event(String nom) 
    {
        this.Nom_event = nom;
        //System.out.println("ordre de suppression");
    }


        public int getId() 
        {
            return id;
        }

        public void setId(int id) 
        {
            this.id = id;
        }

        public String getNom_event() 
        {
            return Nom_event;
        }

        public void setNom_event(String Nom_event) 
        {
            this.Nom_event = Nom_event;
        }



        public String getHeure_debut() 
        {
            return Heure_debut;
        }

        public void setHeure_debut(String Heure_debut) 
        {
            this.Heure_debut = Heure_debut;
        }


        public String getHeure_fin() 
        {
            return Heure_fin;
        }

        public void setHeure_fin(String Heure_fin) 
        {
            this.Heure_fin = Heure_fin;
        }

        public String getParticipation() 
        {
            return Participation;
        }

        public void setParticipation(String Participation) 
        {
            this.Participation = Participation;
        }

        public int getNb_participant() 
        {
            return Nb_participant;
        }

        public void setNb_participant(int Nb_participant) 
        {
            this.Nb_participant = Nb_participant;
        }

        public String getDescription() 
        {
            return Description;
        }

        public void setDescription(String Description) 
        {
            this.Description = Description;
        }

        public String toString()
        {
            return "id="+id+" Nom_event="+Nom_event+" Date_debut="+Date_debut+" Heure_debut="+Heure_debut+" Date_fin="+Date_fin+" Heure_fin="+Heure_fin+" Participation="+Participation+" Nb_participant="+Nb_participant+" Description="+Description;
        }
        
}
