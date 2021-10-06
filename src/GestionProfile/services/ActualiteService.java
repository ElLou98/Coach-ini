/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionProfile.services;

import GestionProfile.entities.Actualite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tools.MyConnection;

/**
 *
 * @author Espace Info
 */
public class ActualiteService implements IntActualite<Actualite> {
   Connection cnx = MyConnection.getInstance().getCnx();
   

   @Override
   public void ajouter(Actualite e) {
      try {
         String requete = "INSERT INTO Actualite (Statut,Image,Fichier,Compétence,Bio,datepub) VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(requete);
           
            ps.setString(1, e.getStatut());
            ps.setString(2, e.getImage());
            ps.setString(3, e.getFichier());
            ps.setString(4, e.getCompétence());
            ps.setString(5, e.getBio());
            ps.setDate(6, (java.sql.Date) e.getDatepub());

            
            ps.executeUpdate();
            System.out.println(" done! ");
        } catch (SQLException ex)
            {
            System.err.println(ex.getMessage());
        
        }
   }
   
   @Override
   public int ajouter1(Actualite e) {
       int numero=0;
       int id = 0;
      try {
         String requete = "INSERT INTO Actualite (Statut,Image,Fichier,Compétence,Bio,datepub) VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
           
            ps.setString(1, e.getStatut());
            ps.setString(2, e.getImage());
            ps.setString(3, e.getFichier());
            ps.setString(4, e.getCompétence());
            ps.setString(5, e.getBio());
            ps.setDate(6, (java.sql.Date) e.getDatepub());

            
            numero = ps.executeUpdate();
            String requete1 = "SELECT MAX(idAct) FROM Actualite";
          PreparedStatement ps1 = cnx.prepareStatement(requete1);

          ResultSet ac = ps1.executeQuery();
          if (ac.next()) {
              id = (int) ac.getLong(1);
              System.out.println("id ->>" + id);
          }
              
        
            
         
             
             
             
            
             
            System.out.println(" Done ");
        } catch (SQLException ex)
            {
            System.err.println(ex.getMessage());
        
        }
      System.out.println(numero+" PUBLIER");
      return id;
   }
   @Override
   public void supprimer(Actualite e) {
      try {
         String requete = "DELETE FROM Actualite WHERE ( IdAct = '"+e.getIdAct()+"')" ;
         Statement ast = this.cnx.createStatement();
         ast.executeUpdate(requete);
         System.out.println("Actualite Supprimée !! ");
      } catch (SQLException var4) {
         System.err.println(var4.getMessage());
      }

   }

   @Override
   public void modifier(Actualite e) {
      try {
         String requete = "UPDATE Actualite SET Statut = ? , Image= ? , Fichier= ? ,Compétence = ? ,Bio = ? , likepub = ?, datepub = ? WHERE ( IdAct = ?)";
         PreparedStatement ast = cnx.prepareStatement(requete); 
         ast.setInt(8, e.getIdAct());
         ast.setString(1, e.getStatut());
         ast.setString(2, e.getImage());
         ast.setString(3, e.getFichier());
         ast.setString(4, e.getCompétence());
         ast.setString(5, e.getBio());     
         ast.setInt(6, e.getLikepub());
         ast.setDate(7, (java.sql.Date) e.getDatepub());

         ast.executeUpdate();
            System.out.println(" LIKE ");
        } catch (SQLException ex) {
            System.out.println(" erreur de modification! ");
            System.err.println(ex.getMessage());
        }    }

   @Override
   public List<Actualite> afficher() {
      ArrayList list = new ArrayList();

      try {
         String requete = "SELECT * FROM Actualite ORDER BY datepub DESC ";
         PreparedStatement pst = this.cnx.prepareStatement(requete);
         ResultSet ac = pst.executeQuery();

         while(ac.next()) {
            list.add(new Actualite(ac.getInt(1),ac.getString(2), ac.getString(3), ac.getString(4), ac.getString(5), ac.getString(6),ac.getInt(7),ac.getDate(8)));
         }
      } catch (SQLException var5) {
         System.out.println(var5.getMessage());
      }

      return list;
   }
    @Override
   public void likepub (Actualite e) {
      try {
         String requete = "UPDATE Actualite SET Statut = ? , Image= ? , Fichier= ? ,Compétence = ? ,Bio = ?,likepub = ? WHERE ( IdAct = ?)";
  PreparedStatement ast = cnx.prepareStatement(requete); 
         ast.setInt(7, e.getIdAct());
         ast.setString(1, e.getStatut());
         ast.setString(2, e.getImage());
         ast.setString(3, e.getFichier());
         ast.setString(4, e.getCompétence());
         ast.setString(5, e.getBio());
         ast.setInt(6, e.getLikepub());
         ast.executeUpdate();
            System.out.println(" Modifié ");
        } catch (SQLException ex) {
            System.out.println(" erreur de modification! ");
            System.err.println(ex.getMessage());
        }    }

    @Override
    public int getActualiteByStatut(String var1) {
       int id = 1;
                
      try {
         String requete = "SELECT IdAct FROM Actualite WHERE statut = ? ";
         PreparedStatement pst = this.cnx.prepareStatement(requete);
         pst.setString(1, var1);
         ResultSet ac = pst.executeQuery();

     
         id = ac.getInt(1);
      
      } catch (SQLException var5) {
         System.out.println(var5.getMessage());
      }

      return id;
   }

    

}