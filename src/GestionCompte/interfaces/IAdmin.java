/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompte.interfaces;

import java.util.List;

/**
 *
 * @author masso
 */
public interface IAdmin<T> extends ICompte {
    public void ajouterAdmin(T t);
    public void supprimerAdmin(T t);
    public void modifierAdmin(T t);
    public List<T> adminsList();  
}