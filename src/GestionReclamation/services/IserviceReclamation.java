package GestionReclamation.services;

import java.util.List;

/**
 *
 * @author Adem
 */
public interface IserviceReclamation<T> {
    public void ajouterReclamation(T t);  // pour client / coach 
    public void supprimerReclamation(T t);  // pour client 
    public void modiferReclamation(T t); // pour le client
    public List<T> afficherReclamation(); // pour client et admin

}
