/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Louay
 */
public class TypeReclamation {
    private int idTypeReclamation;
    private String typeReclamation;

    public TypeReclamation(int idTypeReclamation, String typeReclamation) {
        this.idTypeReclamation = idTypeReclamation;
        this.typeReclamation = typeReclamation;
    }

    public TypeReclamation() {
        
    }

    public int getIdTypeReclamation() {
        return idTypeReclamation;
    }

    public void setIdTypeReclamation(int idTypeReclamation) {
        this.idTypeReclamation = idTypeReclamation;
    }

    public String getTypeReclamation() {
        return typeReclamation;
    }

    public void setTypeReclamation(String typeReclamation) {
        this.typeReclamation = typeReclamation;
    }

    @Override
    public String toString() {
        return "TypeReclamation{" + "idTypeReclamation=" + idTypeReclamation + ", typeReclamation=" + typeReclamation + '}';
    }
    
    
    
}
