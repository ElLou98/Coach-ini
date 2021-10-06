/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Services;

import GestionEvent.Models.myMap;
import GestionEvent.Utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kagha
 */
public class Service_Map {
    
    Connection cnx =DataSource .getInstance().getCnx();

    public void ajouter(myMap m) {
        try {
            String requete = "INSERT INTO Map (id,Latitude,Longtitude,Lieu) VALUES ('" + m.getId() + "','" + m.getLatitude() + "',' " + m.getLongitude() + "','" +m.getLieu()+ "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println(" Map ajoutée! ");
        } catch (SQLException ex) {
            System.out.println(" erreur d'ajout! ");
            System.err.println(ex.getMessage());
        }
    }

    public void modifier(myMap m) {
        try {
            String requete = "UPDATE Map SET Latitude=?, Longtitude=?, Lieu=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(4, m.getId());
            pst.setFloat(1, m.getLatitude());
            pst.setFloat(2, m.getLongitude());
            pst.setString(3, m.getLieu());
            pst.executeUpdate();
            System.out.println(" Map Modifiée! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de modification! ");
            System.err.println(ex.getMessage());
        }
    }

    public void supprimer(int m) {
        try {
            String requete = "DELETE FROM Map WHERE   id=?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, m);
            pst.executeUpdate();
            System.out.println(" Map supprimé! ");
        } catch (SQLException ex) {
            System.out.println(" erreur de suppression! ");
            System.err.println(ex.getMessage());
        }
    }

    
    public List<myMap> afficher() {
        List<myMap> list = new ArrayList<>();
        try {
            String requete = "SELECT * FROM Map";
            Statement pst = cnx.createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new myMap(rs.getInt(1), rs.getFloat(2), rs.getFloat(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;

    }



    public List<String> fetch(String s) {

        List<String> list = new ArrayList<>();
        try {
            String requete = "SELECT * FROM Map where  id =?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, Integer.valueOf(s));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(0, (String.valueOf(rs.getInt(1))));
                list.add(1, (String.valueOf(rs.getFloat(2))));
                list.add(2, (String.valueOf(rs.getFloat(3))));
                list.add(3, (rs.getString(4)));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }
    
   public List<String> detail(String s) {

        List<String> list = new ArrayList<>();
        try {
            String requete = "SELECT * FROM Map where  id =?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, Integer.valueOf(s));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(0, ("id:"+String.valueOf(rs.getInt(1))));
                list.add(1, ("Latitude:"+String.valueOf(rs.getFloat(2))));
                list.add(2, ("Longitude:"+String.valueOf(rs.getFloat(3))));
                list.add(3, ("Lieu:"+rs.getString(4)));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
   
}

    

