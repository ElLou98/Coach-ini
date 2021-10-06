/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionProfile.services;

/**
 *
 * @author Espace Info
 */


import java.util.List;

public interface IntActualite<E> {
   void ajouter(E var1);
   int ajouter1(E var1);
   void supprimer(E var1);

   void modifier(E var1);
   
   void likepub (E var1);
   
   int getActualiteByStatut (String var1);
   
   List<E> afficher();
   
}