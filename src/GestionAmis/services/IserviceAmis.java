package GestionAmis.services;

import java.util.List;
/**
 *
 * @author Louay
 */
public interface IserviceAmis<A> {
    
    public void ajouterDemandeAmis(A a);
    public void accepterDemandeAmis(A a);
    public void supprimerAmis(A a);
    public List<A> consulterAmis();
    
    
}
