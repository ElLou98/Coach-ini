/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReview.services;

/**
 *
 * @author Adem
 */
public interface IserviceSeance <T> {
    public void ajouterSeance(T t);
    public void modiferSeance(T t);
    public void supprimerSeance(T t);
    public void afficherSeance(T t);
    
}
