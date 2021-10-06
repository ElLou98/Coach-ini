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
public interface ICompte<T> {
    
    public void ajouterCompte(T t);
    public void supprimerCompte(T t);
    public void modifierCompte(T t);
    public List<T> comptesList();
    
}
