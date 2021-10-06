/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionOffre.tests;


import GestionCategorieSport.services.CategorieService;
import GestionOffre.services.OffreService;
import GestionOffre.tools.DataSource;
import java.sql.SQLException;

/**
 *
 * @author asus
 */
public class MainService {
  /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        DataSource ds = DataSource.getInstance();
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
       // OffreService sp = new OffreService();
        //test ajout 
        //sp.ajouter( new Offre(1, "titre1", sqlDate, "testing"));
        //sp.ajouter(new Offre(3, "offre2", sqlDate, "sport"););
        //sp.ajouter(new Offre(4, "offre3", sqlDate, "I have a corporate perspective "));
        //sp.ajouter(new Offre( "offre3", sqlDate, "I have a corporate perspective "));
        // sp.rechercher().forEach(System.out::println);
//       sp.rechercherParTitre("offre2");
//       sp.afficher().forEach(System.out::println);
        //sp.rechercherParDate("2021-02-26").forEach(System.out::println);
        // sp.trie().forEach(System.out::println);
        //sp.afficher().forEach(System.out::println);
        //test de modification 

       // sp.modifier1(new Offre(1, "test", sqlDate, "testtt"));
        //test de suppression 
        //sp.supprimer( new Offre(1, "test", sqlDate, "testtt"));
        //sp.afficher().forEach(System.out::println);

       // System.out.println(sp.filtrer(62));
       CategorieService sp = new CategorieService();
       System.out.println(sp.filtrer1("chess"));
    }   
}
