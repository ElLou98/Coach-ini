/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompte.services;

import GestionCompte.entities.Client;
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
public class ClientCRUD extends CompteCRUD {

    public void ajouterClient(Client a) {
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
            String requete = "INSERT INTO `client` (`idc`) VALUES ( '" + i + "') ";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("Client ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerClient(Client t) {
        try {
            int id = t.getId();
            String requete = "DELETE FROM client WHERE idc=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            requete = "DELETE FROM compte WHERE id=?";
            pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("Client supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierClient(Compte t) {
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
            System.out.println("compte modifié!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Client rechercherClient(int id) {
        if (CompteCRUD.existe(id, "compte")) {
            try {
                String requete = "SELECT * FROM compte where id='" + id + "'";
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                Client c = new Client();
                while (rs.next()) {
                    c.setId(id);
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString("prenom"));
                    c.setAge(rs.getInt("age"));
                    c.setAdresseMail(rs.getString("adresse_mail"));
                    c.setMotDePasse(rs.getString("mot_de_passe"));
                    c.setNumTel(rs.getInt("num_tel"));
                    c.setNomDutilisateur(rs.getString("userName"));
                    return c;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return null;
    }
    
    
    public static Client rechercherClient(String userName) {
        if (CompteCRUD.existe(userName)) {
            try {
                String requete = "SELECT * FROM compte where userName='" + userName + "'";
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                Client c = new Client();
                while (rs.next()) {
                    c.setId(rs.getInt("id"));
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString("prenom"));
                    c.setAge(rs.getInt("age"));
                    c.setAdresseMail(rs.getString("adresse_mail"));
                    c.setMotDePasse(rs.getString("mot_de_passe"));
                    c.setNumTel(rs.getInt("num_tel"));
                    c.setNomDutilisateur(rs.getString("userName"));
                    return c;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return null;
    }

    public List<Client> clientsList() {
        List<Client> myList = new ArrayList<>();
        try {

            String requete = "SELECT * FROM compte,client where compte.id=client.id";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAge(rs.getInt("age"));
                c.setAdresseMail(rs.getString("adresse_mail"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
                c.setNumTel(rs.getInt("num_tel"));
                c.setNomDutilisateur(rs.getString("userName"));
                myList.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

}

