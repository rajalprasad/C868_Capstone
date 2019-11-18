/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import patientstation.services.DBConnection;

/**
 *
 * @author rajalprasad
 */
public class AppointmentDataBase {
    
    public static ObservableList<Appointment> getMonthlyAppts() {
        ObservableList<Appointment> appts = FXCollections.observableArrayList();
        Appointment appt;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "SELECT * FROM appointment WHERE start >= '" + start + "' AND start <= '" + end + "'"; 
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()) {
                appt = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getString("start"),
                    rs.getString("end"), rs.getString("location"), rs.getString("contact"));
                appts.add(appt);
            }
            stmt.close();
            return appts;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }
    
    public static ObservableList<Appointment> getWeeklyAppts() {
        ObservableList<Appointment> appts = FXCollections.observableArrayList();
        Appointment appt;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "SELECT * FROM appointment WHERE start >= '" + start + "' AND start <= '" + end + "'";; 
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()) {
                appt = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getString("start"),
                    rs.getString("end"), rs.getString("location"), rs.getString("contact"));
                appts.add(appt);
            }
            stmt.close();
            return appts;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }
    
    public static void deleteAppt(int apptID) {
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "DELETE FROM appointment where appointmentId = " + apptID;
            stmt.execute(q);
        } catch (SQLException ex) {
            System.out.println("SQLException: " +ex.getMessage());
        }
    }
    
    public static boolean apptsIn15() {
   
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime locZDT = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utcZDT = locZDT.withZoneSameInstant(ZoneOffset.UTC);
      
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "SELECT * FROM appointment WHERE start BETWEEN '" +utcZDT+ "' AND '" +utcZDT.plusMinutes(15)+"'";
            ResultSet rs = stmt.executeQuery(q);
            if(rs.next()) {
                return true;
            } 
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return false;
    }
}

