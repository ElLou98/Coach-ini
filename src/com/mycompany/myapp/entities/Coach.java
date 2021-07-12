/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author masso
 */
public class Coach {
    
    private int id;
    private int idc;
    private String profession;

    public Coach(int id, int idc, String profession) {
        this.id = id;
        this.idc = idc;
        this.profession = profession;
    }

    public Coach() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "Coach{" + "id=" + id + ", idc=" + idc + ", profession=" + profession + '}';
    }
    
    
    
}
