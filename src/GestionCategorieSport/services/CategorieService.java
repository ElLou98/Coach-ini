/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCategorieSport.services;

import GestionCategorieSport.models.CategorieSport;
import GestionOffre.services.IService;
import GestionOffre.tools.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asus
 */
public class CategorieService implements IService<CategorieSport> {
     Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(CategorieSport t) {

        try {

            String requete = "INSERT INTO CategorieSport (nom,description,photo) VALUES (?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(requete);

            ps.setString(1, t.getNom());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getPhoto());
            ps.executeUpdate();
            System.out.println(" done! ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }
    }

    @Override
    public void modifier(CategorieSport t) {
        try {
            String requete = "UPDATE CategorieSport SET nom=?, description=?, photo=? WHERE nom=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(4, t.getNom());
            pst.setString(1, t.getNom());
            pst.setString(2, t.getDescription());
            pst.setString(3, t.getPhoto());
            pst.executeUpdate();
            System.out.println(" Categorie du sport Modifiée! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de modification! ");
            System.err.println(ex.getMessage());
        }
    }
    
    public void modifier1(CategorieSport t) {
        try {
            String requete = "UPDATE CategorieSport SET nom=?, description=?, photo=? WHERE ( id = ?)";
            PreparedStatement ast = cnx.prepareStatement(requete);
            ast.setInt(4, t.getId());
            ast.setString(1, t.getNom());
            ast.setString(2, t.getDescription());
            ast.setString(3, t.getPhoto());
            
            ast.executeUpdate();
            System.out.println(" Modifié ");
        } catch (SQLException ex) {
            System.out.println(" erreur de modification! ");
            System.err.println(ex.getMessage());
        }
    }
    
     public int get_ID(String nom)
        {
            int a = 0;
         try {
             String requete ="SELECT id FROM CategorieSport where nom=?";
             PreparedStatement pst =cnx.prepareStatement(requete);
             pst.setString(1, nom);
             ResultSet rs=pst.executeQuery();
             while (rs.next())
                {a=rs.getInt(1);}
         } catch (SQLException ex) {
         }
         return a;
        
        }
    
    
    public void supprimer1(CategorieSport t) {
        try {
            String requete = "DELETE FROM CategorieSport WHERE   id=" + t.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println(" Categorie supprimé! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de suppression! ");
            System.err.println(ex.getMessage());
        }
    }
    public void supprimer2(CategorieSport t) {
        try {
             String requete = "DELETE FROM CategorieSport WHERE ( nom = '" + t.getNom() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println(" Categorie supprimé! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de suppression! ");
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<CategorieSport> afficher() {
        List<CategorieSport> list = new ArrayList<>();
        try {
            String requete = "SELECT * FROM CategorieSport";
            Statement pst = cnx.createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new CategorieSport(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;

    }

    @Override
    public void supprimer(int t) {
        try {
            String requete = "DELETE FROM CategorieSport WHERE   id=?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            System.out.print("t : " + t);
            pst.setInt(1, t);
            pst.executeUpdate();
            System.out.println(" categorie supprimé! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de suppression! ");
            System.err.println(ex.getMessage());
        }
    }

    public List<CategorieSport> rechercherParNom(String nom) throws SQLException {
        List<CategorieSport> list = new ArrayList<>();

        try {

            String requete = "SELECT * FROM CategorieSport where nom='" + nom + "'";

            Statement pst = cnx.createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new CategorieSport(rs.getInt(1), rs.getString(2), rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;

    }
    
     public List<Integer> filtrer1(String nom) throws SQLException {

     
      
           List<Integer> list = new ArrayList<>();
           String requete = "SELECT id FROM CategorieSport where  nom =?";
           PreparedStatement pst = cnx.prepareStatement(requete);
           pst.setString(1, nom);
           ResultSet rs = pst.executeQuery();
           while (rs.next()) {
               list.add(0, rs.getInt(1));
            
           }
           return list;
  
    }   
    
}
