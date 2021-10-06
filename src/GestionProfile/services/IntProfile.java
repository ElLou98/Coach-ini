/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionProfile.services;

import GestionProfile.entities.Profile;
import java.util.List;

/**
 *
 * @author Espace Info
 * @param <E>
 */
public interface IntProfile<E> {

    int ajouter(E var1);
    
    Profile getProfileByCompteId(int id);

    void supprimer(E var1);

    void modifier(E var1);

    List<E> afficher();
    
    List<E> search(String nom);
}
