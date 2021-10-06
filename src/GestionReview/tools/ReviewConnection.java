/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReview.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Adem
 */
public class ReviewConnection {
    public String url = "jdbc:mysql://localhost:3306/choachi'ni";
    public String login = "root";
    public String pwd = "";
    
    public Connection cn;
    public static ReviewConnection instance; //ReviewConnection
    
     public ReviewConnection() { //ReviewConnection
        try {
            cn = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connection etablit");
        } catch (SQLException ex) {
            System.out.println("Erreur de connection");
            System.out.println(ex.getMessage());
        }

    }
     public static ReviewConnection getInstance() { //ReviewConnection
        if (instance == null) {
            instance = new ReviewConnection(); //ReviewConnection
        }
        return instance;
    }

    public Connection getCnx() {
        return cn;
    }
    
}
