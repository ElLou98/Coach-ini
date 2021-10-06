/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Services;

import GestionEvent.Models.Categorie_Event;
import GestionEvent.Models.TableCategorie;
import GestionEvent.Utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author kagha
 */
public class Service_Categorie_Event implements IService<Categorie_Event> {

    Connection cnx = DataSource.getInstance().getCnx();

    public void ajouter(Categorie_Event t) {

        try {

            String requete = "INSERT INTO CategorieEvent (nom,description,photo,db_picture) VALUES (?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(requete);

            ps.setString(1, t.getNom());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getPhoto());
            ps.setString(4, t.getDb_picture());
            ps.executeUpdate();
            System.out.println(" done! ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }
    }

    public void modifier(Categorie_Event t) {
        try {
            String requete = "UPDATE CategorieEvent SET nom=?, description=?, photo=? , db_picture=? WHERE nom=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(5, t.getNom());
            pst.setString(1, t.getNom());
            pst.setString(2, t.getDescription());
            pst.setString(3, t.getPhoto());
            pst.setString(4, t.getDb_picture());
            pst.executeUpdate();
            System.out.println(" Categorie du sport Modifiée! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de modification! ");
            System.err.println(ex.getMessage());
        }
    }

    public void modifier1(Categorie_Event t) {
        try {
            String requete = "UPDATE CategorieEvent SET nom=?, description=?, photo=? ,db_picture=? WHERE ( id = ?)";
            PreparedStatement ast = cnx.prepareStatement(requete);
            ast.setInt(5, t.getId());
            ast.setString(1, t.getNom());
            ast.setString(2, t.getDescription());
            ast.setString(3, t.getPhoto());
            ast.setString(4, t.getDb_picture());

            ast.executeUpdate();
            System.out.println(" Modifié ");
        } catch (SQLException ex) {
            System.out.println(" erreur de modification! ");
            System.err.println(ex.getMessage());
        }
    }

    public int get_ID(String nom) {
        int a = 0;
        try {
            String requete = "SELECT id FROM CategorieEvent where nom=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, nom);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return a;

    }
    
   public String get_Nom(int id) {
        String a = null;
        try {
            String requete = "SELECT nom FROM CategorieEvent where id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a = rs.getString(1);
            }
        } catch (SQLException ex) {
        }
        return a;

    }    
    

    public String get_Db_picture(int id) {
        String a = "";
        try {
            String requete = "SELECT db_picture FROM CategorieEvent where id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a = rs.getString(1);
            }
        } catch (SQLException ex) {
        }
        return a;

    }

    public void supprimer_id(Categorie_Event t) {
        try {
            String requete = "DELETE FROM CategorieEvent WHERE   id=" + t.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println(" Categorie supprimé! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de suppression! ");
            System.err.println(ex.getMessage());
        }
    }

    public void supprimer_nom(Categorie_Event t) {
        try {
            String requete = "DELETE FROM CategorieEvent WHERE ( nom = '" + t.getNom() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println(" Categorie supprimé! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de suppression! ");
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie_Event> afficher() {
        List<Categorie_Event> list = new ArrayList<>();
        try {
            String requete = "SELECT * FROM CategorieEvent";
            Statement pst = cnx.createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new Categorie_Event(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;

    }

    public void supprimer(int t) {
        try {
            String requete = "DELETE FROM CategorieEvent WHERE   id=?";

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

    public List<Categorie_Event> rechercherParNom(String nom) throws SQLException {
        List<Categorie_Event> list = new ArrayList<>();

        try {

            String requete = "SELECT * FROM CategorieEvent where nom='" + nom + "'";

            Statement pst = cnx.createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new Categorie_Event(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;

    }

    public List<String> afficher_nom() {
        List<String> list = new ArrayList<>();
        try {
            String requete = "SELECT nom FROM CategorieEvent ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public List<String> fetch(String s) {

        List<String> list = new ArrayList<>();
        try {
            String requete = "SELECT * FROM CategorieEvent where  id =?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, s);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                //list.add(0,(rs.getString(1)));
                list.add(0, (rs.getString(2)));
                list.add(1, (rs.getString(3)));
                list.add(2, (rs.getString(4)));
                //list.add(2,(rs.getString(4)));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public ObservableList<TableCategorie> fillCategorie() throws SQLException {
        ObservableList<TableCategorie> list = FXCollections.observableArrayList();

        try {

            String requete = "SELECT * FROM CategorieEvent";

            Statement pst = cnx.createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                ImageView img = new ImageView("http://" + rs.getString(5));
                img.setFitHeight(80);
                img.setFitWidth(80);
                list.add(new TableCategorie(rs.getString(2), rs.getString(3), img));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }


}
