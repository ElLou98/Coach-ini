/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReview.services;

import java.util.List;

/**
 *
 * @author Adem
 */
public interface IservicePlaning<T> {
    public void AjouterSeance(T t);  
   
    public void ModifierSeance(T t);  
    

    public void SupprimerSeance(T t); 

    public List<T> afficherSeanceClient(); 
    
}
