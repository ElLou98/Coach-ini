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
public interface ICoach<T> extends ICompte {
    public void ajouterCoach(T t);
    public void supprimerCoach(T t);
    public void modifierCoach(T t);
    public List<T> coachsList();
}
