/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.view;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import patientstation.model.Appointment;
import patientstation.model.AppointmentReports;
import patientstation.services.DBConnection;

/**
 * FXML Controller class
 *
 * @author rajalprasad
 */
public class ReportsController implements Initializable {

    //Appointment Types By Month
    @FXML
    private TableView<AppointmentReports> apptTypesByMonthTV;
    @FXML
    private TableColumn<AppointmentReports, String> monthColm;
    @FXML
    private TableColumn<AppointmentReports, String> typeColm;
    @FXML
    private TableColumn<AppointmentReports, String> amountColm;
    
    //Consultant Schedule
    @FXML
    private TableView<Appointment> consultScheduleTV;
    @FXML
    private TableColumn<Appointment, String> patientColm;
    @FXML
    private TableColumn<Appointment, String> consultTypeColm;
    @FXML
    private TableColumn<Appointment, String> physicianColm;
//    @FXML
//    private TableColumn<Appointment, String> custColm;
//    @FXML
//    private TableColumn<Appointment, String> titleColm;
    @FXML
    private TableColumn<Appointment, String> startColm;
    @FXML
    private TableColumn<Appointment, String> endColm;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Appointment Types By Month
        monthColm.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColm.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColm.setCellValueFactory(new PropertyValueFactory<>("amount"));
        apptTypesByMonthTV.setItems(getApptTypeReport());
        
        // All Upcoming Appointments
        patientColm.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptCustProp(); 
        });
        consultTypeColm.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptTypeProp(); 
        });
        physicianColm.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptContactProp(); 
        });
        startColm.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptStartProp(); 
        });
        endColm.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptEndProp(); 
        });
        consultScheduleTV.setItems(getConsultScheduleReport());
        
    }
    
    public static ObservableList<AppointmentReports> getApptTypeReport() {
        ObservableList<AppointmentReports> appttype = FXCollections.observableArrayList();
        AppointmentReports appttypereport;
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "SELECT MONTHNAME(`start`) AS Month, description AS Type, "
                    + "COUNT(*) AS Amount\n" +
                    "FROM appointment \n" +
                    "GROUP BY MONTHNAME(`start`), description;";
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()) {
                appttypereport = new AppointmentReports(rs.getString("Month"), 
                    rs.getString("Type"),rs.getString("Amount"));
                appttype.add(appttypereport);
            }
            stmt.close();
            return appttype;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }
    
    public static ObservableList<Appointment> getConsultScheduleReport() {
        ObservableList<Appointment> consultschedule = FXCollections.observableArrayList();
        Appointment consult;
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "SELECT  appointment.contact, appointment.type, "
                + "customer.customerName, appointment.start, "
                + "appointment.end\n" +
                "FROM appointment, customer \n" +
                "WHERE appointment.customerId = customer.customerId AND appointment.start >= CURRENT_DATE \n" +
                "ORDER by start AND contact;";
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()) {
                consult = new Appointment(
                    rs.getString("appointment.contact"),
                    rs.getString("appointment.type"),
                    rs.getString("customer.customerName"),
//                    rs.getString("appointment.title"),
                    rs.getString("appointment.start"),
                    rs.getString("appointment.end"));
                consultschedule.add(consult);
            }
            stmt.close();
            return consultschedule;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }
    
    public static ObservableList<AppointmentReports> getOfficeTotalAppt() {
        ObservableList<AppointmentReports> officeApptTotal = FXCollections.observableArrayList();
        AppointmentReports officeTotal;
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "SELECT location , COUNT(*) AS Amount\n" +
                "FROM appointment\n" +
                "GROUP BY location;";
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()) {
                officeTotal = new AppointmentReports(rs.getString("location"),
                rs.getString("Amount"));
                officeApptTotal.add(officeTotal);
            }
            stmt.close();
            return officeApptTotal;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }
    
    @FXML
    private void handleExitButton1(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
    @FXML
    private void handleExitButton2(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
    @FXML
    private void handleExitButton3(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
}
