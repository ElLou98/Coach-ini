/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionProfile.services;

import GestionProfile.entities.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tools.MyConnection;

/**
 *
 * @author Espace Info
 */
public class ProfileService implements IntProfile<Profile> {

    Connection cnx = MyConnection.getInstance().getCnx();

    @Override
    public int ajouter(Profile var1) {
        int id = 0;
        try {
            String requete = "INSERT INTO Profile (Photo,Description,Rating,Catégorie,Détail, Nom,ID_Compte) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(requete);

            ps.setString(1, var1.getPhoto());
            ps.setString(2, var1.getDescription());
            ps.setInt(3, var1.getRating());
            ps.setString(4, var1.getCatégorie());
            ps.setString(5, var1.getDétail());
            ps.setString(6, var1.getNom());
            ps.setInt(7, var1.getID_Compte());

            ps.executeUpdate();
            String requete1 = "SELECT MAX(ID_Coach) FROM profile";
             PreparedStatement ps1 = cnx.prepareStatement(requete1);
             
             ResultSet ac = ps1.executeQuery();
             if(ac.next()){
                 id = (int)ac.getLong(1);
                 System.out.println("id ->>"+ id);
             }
            System.out.println(" done! ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return id;
    }

    @Override
    public void supprimer(Profile var1) {
        try {
            System.out.println("id coach supp     "+var1.getID_Coach());
            String requete = "DELETE FROM Profile WHERE ID_Coach = " + var1.getID_Coach();
            Statement ast = this.cnx.createStatement();
            ast.executeUpdate(requete);
            System.out.println("Profile Supprimée !! ");
        } catch (SQLException var4) {
            System.err.println(var4.getMessage());
        }
    }

    @Override
    public void modifier(Profile var1) {
        try {
            String requete = "UPDATE Profile SET Photo = ?, Description= ?, Rating= ?,Catégorie = ?,Détail = ? ,Nom = ?,ID_Compte = ? WHERE ( ID_Coach = ?)";
            PreparedStatement ast = cnx.prepareStatement(requete);
            ast.setInt(8, var1.getID_Coach());
            ast.setString(1, var1.getPhoto());
            ast.setString(2, var1.getDescription());
            ast.setInt(3, var1.getRating());
            ast.setString(4, var1.getCatégorie());
            ast.setString(5, var1.getDétail());
            ast.setString(6, var1.getNom());
            ast.setInt(7, var1.getID_Compte());
            ast.executeUpdate();
            System.out.println(" Modifié aaaaaa");
        } catch (SQLException ex) {
            System.out.println(" erreur de modification! ");
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Profile> afficher() {

        ArrayList list = new ArrayList();

        try {
            String requete = "SELECT * FROM Profile ";
            PreparedStatement pst = this.cnx.prepareStatement(requete);
            ResultSet ac = pst.executeQuery();

            while (ac.next()) {
               
                list.add(new Profile(ac.getString(2), ac.getString(3), ac.getInt(4), ac.getString(5), ac.getString(6), ac.getString(7), ac.getInt(8),ac.getInt(1)));
            }
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }

        return list;
    }

    @Override
    public Profile getProfileByCompteId(int id) {
       Profile p = new Profile();
          try {
            String requete = "SELECT * FROM Profile WHERE ( ID_Compte = ?)";
            PreparedStatement pst = this.cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet ac = pst.executeQuery();

            while (ac.next()) {
                p.setID_Coach(ac.getInt(1));
                p.setCatégorie(ac.getString(5));
                p.setDescription(ac.getString(3));
                p.setDétail(ac.getString(6));
                p.setNom(ac.getString(7));
                p.setPhoto(ac.getString(2));
                p.setRating(ac.getInt(4));
                p.setID_Compte(ac.getInt(8));
               
            }
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }

          System.out.println("hobbbaaaa"+p);
        return p;
    }

    @Override
    public List<Profile> search(String nom) {
        System.out.println("nom : "+nom);
        ArrayList list = new ArrayList();

        try {
            String requete = "SELECT * FROM Profile WHERE (Nom LIKE ?)";
            PreparedStatement pst = this.cnx.prepareStatement(requete);
            pst.setString(1,nom + "%");

            ResultSet ac = pst.executeQuery();

            while (ac.next()) {
                list.add(new Profile(ac.getString(2), ac.getString(3), ac.getInt(4), ac.getString(5), ac.getString(6), ac.getString(7), ac.getInt(8), ac.getInt(1)));
                
                
            }
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }
        
        for(int i=  0;i<list.size();i++){
          System.out.println("list search : "+list.get(i));  
        }
        
        return list;
    }
}