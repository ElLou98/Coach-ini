/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReview.services;

import GestionReview.entities.Seance;
import GestionReview.tools.ReviewConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adem
 */
public class SeanceCrud implements IservicePlaning<Seance> {

    Connection cnxx = ReviewConnection.getInstance().getCnx();

    @Override
    public void AjouterSeance(Seance t) {
        try {
            String requete = "INSERT INTO seance_planning (user_name,Summary,Description,Date,Starts_at,Finishs_at,Localisation) "
                    + "VALUES ('" + t.getUser_name() + "','" + t.getSummary() + "','" + t.getDescription() + "','" + t.getDate() + "','"
                    + t.getStarts_at() + "','" + t.getFinishs_at() + "','"+t.getLocalisation()+ "')";
            Statement st = cnxx.createStatement(); 
            st.executeUpdate(requete);
            System.out.println("Seance Ajouteé !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void ModifierSeance(Seance t) {
    }

    @Override
    public void SupprimerSeance(Seance t) {
    }

    @Override
    public List<Seance> afficherSeanceClient() {
        List<Seance> List = new ArrayList<>();

        return List;
    }

}
