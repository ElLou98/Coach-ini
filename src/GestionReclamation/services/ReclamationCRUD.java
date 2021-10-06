package GestionReclamation.services;

import GestionReclamation.entite.Reclamation;
import tools.MyConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adem
 */
public class ReclamationCRUD implements IserviceReclamation<Reclamation> {

    Connection cnx = MyConnection.getInstance().getCnx();

    @Override
    public void ajouterReclamation(Reclamation t) {
        try {
            String requete = "INSERT INTO reclamation (login,descriptionReclamation,typeReclamation,dateReclamation,enCours,traite) "
                    + "VALUES ('" + t.getLogin() + "','" + t.getDescriptionReclamation() + "','" 
                    + t.getTypeReclamation() +"','"+t.getDateReclamation()+"','"
                    +t.getEnCours()+"','"+t.getTraite()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Reclamation Client Ajouteé !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerReclamation(Reclamation t) {
        try {
            String requete = "DELETE FROM reclamation WHERE dateReclamation like '" + t.getDateReclamation()+ "' AND login like '"+t.getLogin()+"';";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Reclamation Client Supprimée !! ");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @Override
    public void modiferReclamation(Reclamation t) {
        try {
            int enCours;
            int traite;
            if (t.getEnCoursString().equals("Oui"))
                    {
                        enCours=1;
                    }
            else
            {
                enCours=0;
            }
            if (t.getTraiteString().equals("Oui"))
                    {
                        traite=1;
                    }
            else
            {
                traite=0;
            }
            
            String requete = "UPDATE reclamation SET "
                    + "enCours = '" + enCours +"', traite = '"+ traite
                    +"' WHERE  login like '"+ t.getLogin()
                    +"' AND dateReclamation like '"+t.getDateReclamation()+"';";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Reclamation Client modifiée !! ");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Reclamation> afficherReclamation() {
        List<Reclamation> List = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reclamation ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()) {
            List.add(new Reclamation(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)
            ,rs.getInt(6),rs.getInt(7)));
            }
            
            System.out.println("Reclamation Client affichée !! ");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return List;
    }


   

}
