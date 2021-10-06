/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionAmis.services;

import GestionAmis.entite.Amis;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tools.MyConnection;

/**
 *
 * @author Louay
 */
public class AmisCRUD implements IserviceAmis<Amis>{
    
    Connection cnx = MyConnection.getInstance().getCnx();

    @Override
    public void ajouterDemandeAmis(Amis a) {
        try {
            String requete = "INSERT INTO amis (destinataire,expediteur,etatDemande) "
                    + "VALUES ('" + a.getDestinataire() + "','" + a.getExpediteur() + "','0');";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Demande amis envoye !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public void accepterDemandeAmis(Amis a) {
        try {
            String requete = "UPDATE amis "
                    + "SET etatDemande = 1 "
                    + "WHERE  destinataire like '"+a.getDestinataire()+"' "
                    + "AND expediteur like '"+a.getExpediteur()+"';";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Reclamation Client modifiée !! ");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerAmis(Amis a) {
        try {
            String requete = "DELETE FROM amis WHERE destinataire like '" + a.getDestinataire()+ 
                    "' AND expediteur like '"+a.getExpediteur()+"';";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Amis supprimée !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Amis> consulterAmis() {
        List<Amis> List = new ArrayList<>();
        try {
            String requete = "SELECT * FROM amis; ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()) {
            List.add(new Amis(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));// afficher date reclamation descrip et type
            }
            
            System.out.println("Liste d amis recupere !! ");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return List;
    }

    
}
