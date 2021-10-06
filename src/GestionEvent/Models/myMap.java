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
public class myMap {

    private float Latitude;
    private float Longitude;
    private String Lieu;
    private int id;

    public myMap(float Latitude,float  Longitude, String Lieu) {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Lieu = Lieu;
    }


    public myMap(int id,float Latitude, float Longitude, String Lieu) {
        this.id=id;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Lieu = Lieu;
    }

        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(int Latitude) {
        this.Latitude = Latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(int Longitude) {
        this.Longitude = Longitude;
    }

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String Lieu) {
        this.Lieu = Lieu;
    }

    @Override
    public String toString() {
        return "Map{" + "Latitude=" + Latitude + ", Longitude=" + Longitude + ", Lieu=" + Lieu + '}';
    }

}
