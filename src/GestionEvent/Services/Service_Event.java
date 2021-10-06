/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Services;

import GestionEvent.Models.Event;
import GestionEvent.Utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kagha
 */
public class Service_Event implements IService<Event> {

    Connection cnx = DataSource.getInstance().getCnx();

    public void ajouter(Event e) {
        try {
            String requete = "INSERT INTO Event (Nom_event,Date_debut,Heure_debut,Date_fin,Heure_fin,Participation,Nb_participant,Description,id_Cat,db_map,id_Coach,Map) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, e.getNom_event());
            pst.setDate(2, (Date) e.getDate_debut());
            pst.setString(3, e.getHeure_debut());
            pst.setDate(4, (Date) e.getDate_fin());
            pst.setString(5, e.getHeure_fin());
            pst.setString(6, e.getParticipation());
            pst.setInt(7, e.getNb_participant());
            pst.setString(8, e.getDescription());
            pst.setInt(9, e.getId_Cat());
            pst.setString(10, e.getDb_map());
            pst.setInt(11, e.getId_Coach());
            pst.setString(12, e.getMapVal());
            pst.executeUpdate();
            System.out.println("Event Ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void supprimer_id(Event e) {
        try {
            String requete = "DELETE FROM Event Where id =?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, e.getId());
            pst.executeUpdate();
            System.out.println("Event Supprimée!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void supprimer_nom(Event e) {
        try {
            String requete = "DELETE FROM Event Where Nom_event =?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, e.getNom_event());
            pst.executeUpdate();
            System.out.println("Event Supprimée!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void modifier(Event e) {
        try {
            String requete = "UPDATE Event SET Nom_event=?,Date_debut=?,Heure_debut=?,Date_fin=?,Heure_fin=?,Participation=?,Nb_participant=?,Description=?,id_Cat=?,db_map=?,Map=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, e.getNom_event());
            pst.setDate(2, (Date) e.getDate_debut());
            pst.setString(3, e.getHeure_debut());
            pst.setDate(4, (Date) e.getDate_fin());
            pst.setString(5, e.getHeure_fin());
            pst.setString(6, e.getParticipation());
            pst.setInt(7, e.getNb_participant());
            pst.setString(8, e.getDescription());
            pst.setInt(9, e.getId_Cat());
            pst.setString(10, e.getDb_map());
            pst.setString(11,e.getMapVal());
            pst.setInt(12,e.getId());
            pst.executeUpdate();
            System.out.println("Event Modifié!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public List<Event> afficher() {
        List<Event> list = new ArrayList<>();
        try {
            String requete = "SELECT * FROM Event ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Event(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getDate(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9)));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public List<String> afficher_nom() {
        List<String> list = new ArrayList<>();
        try {
            String requete = "SELECT Nom_event FROM Event ";
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
        public List<String> afficher_nom(int id) {
        List<String> list = new ArrayList<>();
        try {
            String requete = "SELECT Nom_event FROM Event where id_Coach=? ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
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
            String requete = "SELECT * FROM Event where  id =?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, s);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(0, (Integer.toString(rs.getInt(1))));
                list.add(1, ("Nom : " + rs.getString(2)));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                list.add(2, ("Date debut : " + dateFormat.format(rs.getDate(3))));
                list.add(3, ("Heure debut : " + rs.getString(4)));
                list.add(4, ("Date fin : " + dateFormat.format(rs.getDate(5))));
                list.add(5, ("Heure fin : " + rs.getString(6)));
                list.add(6, ("Participation: " + rs.getString(7)));
                list.add(7, ("Nombre des participants: " + Integer.toString(rs.getInt(8))));
                list.add(8, ("Description: " + rs.getString(9)));
                list.add(9, (Integer.toString(rs.getInt(10))));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }
    
    
    public String get_Db_picture(int id) {
        String a = "";
        try {
            String requete = "SELECT db_map FROM Event where id=?";
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

    public int get_ID(String nom) {
        int a = 0;
        try {
            String requete = "SELECT id FROM Event where nom_Event=?";
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

    public int get_Last_ID() {
        int a = 0;
        try {
            String requete = "SELECT MAX(Id) FROM Event";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return a;

    }

    public String db_Image(int id) {
        String a = null ;
        try {
            String requete = "SELECT db_map FROM Event where id=?";
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
        public String db_CAT(int id) {
        String a = null ;
        try {
            String requete = "SELECT id_Cat FROM Event where id=?";
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
    public int Name_Exist(String name ) {
        int a =0 ;
        try {
            String requete = "SELECT 1 FROM Event WHERE Nom_event=? LIMIT 1";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return a;

    }
    
        public int Name_ExistTB(String name , int id_coach) {
        int a =0 ;
        try {
            String requete = "SELECT 1 FROM Event WHERE Nom_event=? and id_Coach =? LIMIT 1";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, name);
            pst.setInt(2, id_coach);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return a;

    }
    public int pic_Exist(String name) {
        int a =0 ;
        try {
            String requete = "SELECT 1 FROM Event WHERE db_map=? LIMIT 1";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return a;

    }

    public void modifier(Event event, String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
