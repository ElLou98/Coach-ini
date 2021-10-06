/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionOffre.services;

import java.util.List;

/**
 *
 * @author asus
 */
public interface IService<T> {

    public void ajouter(T t);

    public void modifier(T t);

    public void supprimer(int t);

    public List<T> afficher();
}
