/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCategorieSport.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author asus
 */
public class DataSource {
    private static GestionOffre.tools.DataSource instance;
    private Connection cnx;
    private final String URL = "jdbc:mysql://localhost:3306/choachi'ni";
    private final String LOGIN = "root";
    private final String PASSWORD = "";

    public DataSource() {
        try {
            cnx = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println(" Connexion établie ! ");
        } catch (SQLException ex) {
            System.out.println(" Erreur de connexion ! ");
            System.err.println(ex.getMessage());
        }
    }

    public static GestionOffre.tools.DataSource getInstance() {
        if (instance == null) {
            instance = new GestionOffre.tools.DataSource();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }    
}
