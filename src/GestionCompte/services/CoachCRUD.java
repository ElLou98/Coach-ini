/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompte.services;

import GestionCompte.entities.Coach;
import GestionCompte.entities.Compte;
import tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author masso
 */
public class CoachCRUD extends CompteCRUD {

    public void ajouterCoach(Coach co) {
        Compte c = new Compte();
        c = co;
        ajouterCompte(c);

        try {
            String requeteId = "SELECT max(id) FROM compte";
            Statement stId = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = stId.executeQuery(requeteId);
            int i = 0;
            while (rs.next()) {
                i = rs.getInt(1);
            }
            String requete = "INSERT INTO `coach` (`idc`,`profession`) VALUES ( '" + i + "','"+co.getProfession()+"')";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("Coach ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerCoach(Coach t) {
        try {
            int id = t.getId();
            String requete = "DELETE FROM coach WHERE idc=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            requete = "DELETE FROM compte WHERE id=?";
            pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("Coach supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierCoach(Coach t) {
        try {
            String requete = " UPDATE compte SET nom=?, prenom=?,age=?,adresse_mail=?,mot_de_passe=?,num_tel=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getPrenom());
            pst.setInt(3, t.getAge());
            pst.setString(4, t.getAdresseMail());
            pst.setString(5, t.getMotDePasse());
            pst.setInt(6, t.getNumTel());
            pst.setInt(7, t.getId());
            pst.executeUpdate();
            requete = " UPDATE coach SET profession=? WHERE id=?";
            pst.setString(1, t.getProfession());
            pst.setInt(2, t.getId());
            pst.executeUpdate();
            System.out.println("compte modifié!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Coach rechercherCoach(int id) {
        if (CompteCRUD.existe(id, "compte")) {
            try {
                String requete = "SELECT * FROM compte,coach where id='" + id + "'";
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                Coach c = new Coach();
                while (rs.next()) {
                    c.setId(id);
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString(3));
                    c.setAge(rs.getInt("age"));
                    c.setAdresseMail(rs.getString("adresse_mail"));
                    c.setMotDePasse(rs.getString("mot_de_passe"));
                    c.setNumTel(rs.getInt("num_tel"));
                    c.setProfession(rs.getString("profession"));
                    return c;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return null;
    }

    public List<Coach> coachsList() {
        List<Coach> myList = new ArrayList<>();
        try {

            String requete = "SELECT * FROM compte,coach where compte.id=coach.id";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Coach c = new Coach();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString(3));
                c.setAge(rs.getInt("age"));
                c.setAdresseMail(rs.getString("adresse_mail"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
                c.setNumTel(rs.getInt("num_tel"));
                c.setProfession(rs.getString("profession"));                
                myList.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
}
