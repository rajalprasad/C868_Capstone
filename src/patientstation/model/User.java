/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.model;

/**
 *
 * @author rajalprasad
 */
public class User {
    public static String username;
    public static int userID;
  
    
    public User(int userID, String username) {
        this.username = username;
        this.userID = userID;
    }
    
   
    public static String getUsername() {
        return username;
    }
    
    public static int getUserID() {
        return userID;
    }

}
