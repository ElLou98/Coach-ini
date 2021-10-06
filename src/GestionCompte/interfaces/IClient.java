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
public interface IClient<T> extends ICompte {
    public void ajouterClient(T t);
    public void supprimerClient(T t);
    public void modifierClient(T t);
    public List<T> clientsList();
}
