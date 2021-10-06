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
public interface IserviceReview<T> {
    public void ajouterReviewClient(T t);  

   
    public void supprimerReviewClient(T t);  
    

    public void modiferReviewClient(T t); 
    public void modiferReviewClient2(T t); 

    public List<T> afficherReviewClient(); 
    public List<T> afficherReviewClient2();

    
}
