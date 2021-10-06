/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReview.services;

import GestionReview.entities.Review;
import GestionReview.tools.ReviewConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Adem
 */
public class ReviewCrud implements IserviceReview<Review> {

    Connection cnx = ReviewConnection.getInstance().getCnx();

    @Override
    public void ajouterReviewClient(Review t) {
        try {
            String requete = "INSERT INTO review_client (description_review,nom_client_review,nom_coach_review,date_review,rating) "
                    + "VALUES ('" + t.getDescription_review() + "','" + t.getNom_client_review() + "','" + t.getNom_coach_review() + "','"
                    + t.getDate_review() + "','" + t.getRating() + "')";
            Statement st = cnx.createStatement(); // descp nomclient nomCoach date rating
            st.executeUpdate(requete);
            System.out.println("Review Client Ajouteé !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerReviewClient(Review t) {
        try {
            String requete = "DELETE FROM review_client WHERE ( nom_client_review = '" + t.getNom_client_review() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Review Client Supprimée !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modiferReviewClient(Review t) {
        try {
            String requete = "UPDATE review_client SET "
                    + "date_review = '" + t.getDate_review() + "', description_review= '" + t.getDescription_review()
                    + "', nom_client_review= '" + t.getNom_client_review() + "', nom_coach_review=  '" + t.getNom_coach_review() + "', rating= '" + t.getRating()
                    + "' WHERE ( nom_client_review = '" + t.getNom_client_review() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Review Client modifiée !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Review> afficherReviewClient() { //(description_review,nom_client_review,date_review)
        List<Review> List = new ArrayList<>();
        try {
            String requete = "SELECT * FROM review_client ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                List.add(new Review(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6))); // afficher date Review descrip et type
            }

            System.out.println("Reclamation Client affichée !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return List;
    }

    @Override
    public List<Review> afficherReviewClient2() {
        List<Review> List = new ArrayList<>();
        try {
            String requete = "SELECT * FROM review_client ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                List.add(new Review(rs.getString(3), rs.getString(3), rs.getString(5), rs.getString(2), rs.getFloat(6))); // afficher date Review descrip et type
            }// descp date nom rating

            System.out.println("Reclamation Client affichée !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return List;

    }

    public static ObservableList<Review> getAllRecords2(String nomCoach) throws ClassNotFoundException, SQLException {

        Connection cnnx = ReviewConnection.getInstance().getCnx();
        //            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../ClientviewProfileCoach.fxml"));
//
//            ClientviewProfileCoachController cvpc = loader2.getController();
//
//            String nomcoach = cvpc.name1.getText();

        String requetenomcoach = "SELECT * FROM review_client WHERE( nom_coach_review = 'ghassen')"; //+ cvpc.nom1.getText() + "')";
        String requete = "SELECT * FROM review_client ";
        try {
            Statement st = cnnx.createStatement();
            ResultSet rs = st.executeQuery(requetenomcoach); //requete

            ObservableList<Review> reviewList = getReviewObjects(rs);
            return reviewList;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    public static ObservableList<Review> getAllRecords() throws ClassNotFoundException, SQLException {

        Connection cnnx = ReviewConnection.getInstance().getCnx();
        String requete = "SELECT * FROM review_client";
        try {
            Statement st = cnnx.createStatement();
            ResultSet rs = st.executeQuery(requete);

            ObservableList<Review> reviewList = getReviewObjects(rs);
            return reviewList;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }
        public static ObservableList<Review> getAllRecordsCoach(String nomCoach) throws ClassNotFoundException, SQLException {

        Connection cnnx = ReviewConnection.getInstance().getCnx();
        String requete = "SELECT * FROM review_client WHERE( nom_coach_review = '"+nomCoach+"')";
        try {
            Statement st = cnnx.createStatement();
            ResultSet rs = st.executeQuery(requete);

            ObservableList<Review> reviewList = getReviewObjects(rs);
            return reviewList;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }
                public static ObservableList<Review> getAllRecordsClient(String nomclient) throws ClassNotFoundException, SQLException {

        Connection cnnx = ReviewConnection.getInstance().getCnx();
        String requete = "SELECT * FROM review_client WHERE( nom_client_review = '"+nomclient+"')";
        try {
            Statement st = cnnx.createStatement();
            ResultSet rs = st.executeQuery(requete);

            ObservableList<Review> reviewList = getReviewObjects(rs);
            return reviewList;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    private static ObservableList<Review> getReviewObjects(ResultSet rs) throws ClassNotFoundException, SQLException {

        try {
            ObservableList<Review> reviewList = FXCollections.observableArrayList();
            while (rs.next()) {
                Review r = new Review();
                r.setNom_coach_review(rs.getString("nom_coach_review")); // wlh 1 heure bech fe9t wlhhhhhhhhhhhhhhhhhhhh
                r.setNom_client_review(rs.getString("nom_client_review"));
                r.setDate_review(rs.getString("date_review"));
                r.setDescription_review(rs.getString("description_review"));
                r.setRating(rs.getFloat("rating"));
                reviewList.add(r);
            }
            return reviewList;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void modiferReviewClient2(Review t) {
        try {
            String requete = "UPDATE review_client SET "
                    + "date_review = '" + t.getDate_review() + "', description_review= '" + t.getDescription_review()
                    + "', nom_client_review= '" + t.getNom_client_review() + "', rating= '" + t.getRating()
                    + "' WHERE ( nom_client_review = '" + t.getNom_client_review() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Review Client modifiée !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
