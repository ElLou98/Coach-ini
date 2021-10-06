/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Services;

import GestionEvent.Models.Event;
import java.util.List;

/**
 *
 * @author kagha
 * @param <E>
 */
public interface IService <E> 
{
    public void ajouter(E e);
    public void supprimer_id(E e);
    public void supprimer_nom(E e);
    public void modifier(E e);
    public List<E> afficher(); 
}
