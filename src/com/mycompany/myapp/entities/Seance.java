/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Adem
 */
public class Seance {

    private String Summary; 
    private String user_name; 
    private String Description;
    private String Date;
    private String Starts_at;
    private String Finishs_at;
    private String Localisation;

    public Seance() {
    }

    public Seance(String Summary, String Description, String Date, String Starts_at, String Finishs_at, String Localisation) {
        this.Summary = Summary;
        this.Description = Description;
        this.Date = Date;
        this.Starts_at = Starts_at;
        this.Finishs_at = Finishs_at;
        this.Localisation = Localisation;
    }

    public Seance(String user_name, String Summary, String Description, String Date, String Starts_at, String Finishs_at, String Localisation) {
        this.Summary = Summary;
        this.user_name = user_name;
        this.Description = Description;
        this.Date = Date;
        this.Starts_at = Starts_at;
        this.Finishs_at = Finishs_at;
        this.Localisation = Localisation;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getStarts_at() {
        return Starts_at;
    }

    public void setStarts_at(String Starts_at) {
        this.Starts_at = Starts_at;
    }

    public String getFinishs_at() {
        return Finishs_at;
    }

    public void setFinishs_at(String Finishs_at) {
        this.Finishs_at = Finishs_at;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public void setLocalisation(String Localisation) {
        this.Localisation = Localisation;
    }

    @Override
    public String toString() {
        return "Seance{" + "Summary= " + Summary + ", user_name= " + user_name + ", Description= " + Description + ", Date= " + Date 
                + ", Starts_at= " + Starts_at + ", Finishs_at= " + Finishs_at + ", Localisation= " + Localisation + '}';
    }

    
    

}

