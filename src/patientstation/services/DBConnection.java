/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rajalprasad
 */
public class DBConnection {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1/PatientStation";
    private static final String USER = "root";
    private static final String PASS = "aaSAaDEW*9823";
    public static Connection conn;
    
  
     public static void makeConnection() throws ClassNotFoundException, SQLException {
         Class.forName(DRIVER);
         conn = DriverManager.getConnection(URL, USER, PASS);
         System.out.println("Connection Successful");
     }
     
     public static void closeConnection() throws SQLException {
         conn.close();
         System.out.println("Connection Closed");
     }
     
     
}
