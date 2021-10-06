/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompte.services;

import GestionCompte.entities.Admin;
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
public class AdminCRUD extends CompteCRUD {

    public void ajouterAdmin(Admin a) {
        Compte c = new Compte();
        c = a;
        ajouterCompte(c);

        try {
            String requeteId = "SELECT max(id) FROM compte";
            Statement stId = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = stId.executeQuery(requeteId);
            int i = 0;
            while (rs.next()) {
                i = rs.getInt(1);
            }
            String requete = "INSERT INTO `admin` ( `id`,`role`) VALUES ( '" + i + "','" + a.getRole() + "') ";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("Admin ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerAdmin(Admin t) {
        try {
            int id = t.getId();
            String requete = "DELETE FROM admin WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            requete = "DELETE FROM compte WHERE id=?";
            pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("Admin supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Admin rechercherAdmin(int id) {
        if (CompteCRUD.existe(id, "compte")) {
            try {
                String requete = "SELECT * FROM compte,admin where (compte.id='" + id + "' and admin.id='" + id + "')";
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                Admin c = new Admin();
                while (rs.next()) {
                    c.setId(id);
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString(3));
                    c.setAge(rs.getInt("age"));
                    c.setAdresseMail(rs.getString("adresse_mail"));
                    c.setMotDePasse(rs.getString("mot_de_passe"));
                    c.setNumTel(rs.getInt("num_tel"));
                    c.setRole(rs.getString("role"));
                    return c;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return null;
    }

    public void modifierAdmin(Admin t) {
        try {
            String requeteA = " UPDATE admin SET role=? WHERE id=?";
            PreparedStatement pstA = MyConnection.getInstance().getCnx().prepareStatement(requeteA);
            pstA.setString(1, t.getRole());
            pstA.setInt(2, t.getId());
            pstA.executeUpdate();
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
            System.out.println("Admin modifié!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Admin> adminsList() {
        List<Admin> myList = new ArrayList<>();
        try {

            String requete = "SELECT * FROM compte,admin where compte.id=admin.id";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Admin c = new Admin();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString(3));
                c.setAge(rs.getInt("age"));
                c.setAdresseMail(rs.getString("adresse_mail"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
                c.setNumTel(rs.getInt("num_tel"));
                c.setRole(rs.getString("role"));
                System.out.println(rs.getString("role"));
                myList.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

}
