/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.view;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import patientstation.model.User;
import patientstation.services.DBConnection;

/**
 * FXML Controller class
 *
 * @author rajalprasad
 */
public class UpdateAppointmentController implements Initializable {

    @FXML
    private TextField custTxtField;
//    @FXML
//    private TextField titleTxtField;
//    @FXML
//    private TextField descriptionTxtField;
//    @FXML
//    private TextField urlTxtField;
    @FXML
    private ComboBox<String> startHourCombo;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ComboBox<String> startMinuteCombo;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> endHourCombo;
    @FXML
    private ComboBox<String> endMinuteCombo;
    @FXML
    private ComboBox<String> locationCombo;
    @FXML
    private ComboBox<String> contactCombo;
    @FXML
    private ComboBox<String> typeCombo;
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    ObservableList<String> contacts = FXCollections.observableArrayList();
    ObservableList<String> locations = FXCollections.observableArrayList();
    ObservableList<String> type = FXCollections.observableArrayList();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        custTxtField.setText(getCustName(AppointmentController.selectedAppt.getApptID()));
//        titleTxtField.setText(AppointmentController.selectedAppt.getApptTitle());
//        descriptionTxtField.setText(AppointmentController.selectedAppt.getApptDescription());
//        urlTxtField.setText(AppointmentController.selectedAppt.getApptURL());
        
        hours.addAll("08", "09", "10", "11","12", "13", "14", "15", "16");
        minutes.addAll("00", "15", "30", "45");
        contacts.addAll("Ben Jerry, MD", "Bob Alder, DO", "Stacy Green, MD", "Tyler Brown, MD");
        locations.addAll("San Jose");
        type.addAll("Physical", "Vaccination", "Referrals", "Other");
        startHourCombo.setItems(hours);
        startMinuteCombo.setItems(minutes);
        endHourCombo.setItems(hours);
        endMinuteCombo.setItems(minutes);  
        locationCombo.setItems(locations);
        contactCombo.setItems(contacts);
        typeCombo.setItems(type);
        
    }    

    @FXML
    private void saveButton(ActionEvent event) throws SQLException {
        
         if (validateEntry() == true) {
            
            //Start time
            LocalDate startdate = startDatePicker.getValue();
            String starthour = startHourCombo.getValue();
            String startminute = startMinuteCombo.getValue();
            LocalDateTime startldt = LocalDateTime.of(startdate.getYear(), 
                    startdate.getMonthValue(), startdate.getDayOfMonth(), 
                    Integer.parseInt(starthour), Integer.parseInt(startminute));
            ZonedDateTime localStartZDT = ZonedDateTime.of(startldt, ZoneId.systemDefault());
            ZonedDateTime utcStartZDT = localStartZDT.withZoneSameInstant(ZoneOffset.UTC);
            //End time
            LocalDate enddate = endDatePicker.getValue();
            String endhour = endHourCombo.getValue();
            String endminute = endMinuteCombo.getValue();
            LocalDateTime endldt = LocalDateTime.of(enddate.getYear(), 
                    enddate.getMonthValue(), enddate.getDayOfMonth(), 
                    Integer.parseInt(endhour), Integer.parseInt(endminute));
            ZonedDateTime localEndZDT = ZonedDateTime.of(endldt, ZoneId.systemDefault());
            ZonedDateTime utcEndZDT = localEndZDT.withZoneSameInstant(ZoneOffset.UTC);

            PreparedStatement apptUpdate = DBConnection.conn.prepareStatement(
                "UPDATE appointment " +
                "SET location = ?,contact = ?,type = ?"
                +",start = ?,end = ?,lastUpdate = now(),lastUpdateBy = ? " 
                +"WHERE appointmentId = ?;");
//            apptUpdate.setString(1, titleTxtField.getText());
//            apptUpdate.setString(2, descriptionTxtField.getText());
            apptUpdate.setString(1, locationCombo.getValue());
            apptUpdate.setString(2, contactCombo.getValue());
            apptUpdate.setString(3, typeCombo.getValue());
//            apptUpdate.setString(6, urlTxtField.getText());
            apptUpdate.setString(4, dtf.format(utcStartZDT));
            apptUpdate.setString(5, dtf.format(utcEndZDT));
            apptUpdate.setString(6, User.getUsername());
            apptUpdate.setInt(7, AppointmentController.selectedAppt.getApptID());
            apptUpdate.execute();
            try {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("Appointment.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("IOException: " + ex.getMessage());
            }
        }
    }
    
    private boolean validateEntry() {
        try {
           
            //Start time
            LocalDate startdate = startDatePicker.getValue();
            String starthour = startHourCombo.getValue();
            String startminute = startMinuteCombo.getValue();
            LocalDateTime startldt = LocalDateTime.of(startdate.getYear(), 
                    startdate.getMonthValue(), startdate.getDayOfMonth(), 
                    Integer.parseInt(starthour), Integer.parseInt(startminute));
            ZonedDateTime localStartZDT = ZonedDateTime.of(startldt, ZoneId.systemDefault());
            ZonedDateTime utcStartZDT = localStartZDT.withZoneSameInstant(ZoneOffset.UTC);
            //End time
            LocalDate enddate = endDatePicker.getValue();
            String endhour = endHourCombo.getValue();
            String endminute = endMinuteCombo.getValue();
            LocalDateTime endldt = LocalDateTime.of(enddate.getYear(), 
                    enddate.getMonthValue(), enddate.getDayOfMonth(), 
                    Integer.parseInt(endhour), Integer.parseInt(endminute));
            ZonedDateTime localEndZDT = ZonedDateTime.of(endldt, ZoneId.systemDefault());
            ZonedDateTime utcEndZDT = localEndZDT.withZoneSameInstant(ZoneOffset.UTC);

            String errMsg = "";
            
//            String title = titleTxtField.getText();
//            String description = descriptionTxtField.getText();
//            String url = urlTxtField.getText();
            
//            if (title == null || title.length() == 0) {
//                errMsg += "Please enter a title\n";
//            }
//            if (description == null || description.length() == 0) {
//                errMsg += "Please enter a description\n";
//            }
            if (locationCombo.getValue() == null) {
                errMsg += "Please select a location\n";
            }
            if (contactCombo.getValue() == null) {
                errMsg += "Please select a contact\n";
            }
            if (typeCombo.getValue() == null) {
                errMsg += "Please select a type\n";
            }
//            if (url == null || url.length() == 0) {
//                errMsg += "Please enter a url\n";
//            }
            if (startDatePicker.getValue() == null) {
                errMsg += "Please select a date\n";
            }
            if (startHourCombo.getValue() == null) {
                errMsg += "Please select a hour\n";
            }  
            if (startMinuteCombo.getValue() == null) {
                errMsg += "Please select a minute\n";
            } 
            if (endDatePicker.getValue() == null) {
                errMsg += "Please select a date\n";
            }
            if (endHourCombo.getValue() == null) {
                errMsg += "Please select a hour\n";
            }  
            if (endMinuteCombo.getValue() == null) {
                errMsg += "Please select a minute\n";
            } 
            if (endldt.equals(startldt) || endldt.isBefore(startldt)) {
                errMsg += "Appointment end time must be after the start time\n";
            }
            if (overLappingAppt(dtf.format(utcStartZDT),dtf.format(utcEndZDT),
            contactCombo.getValue(),locationCombo.getValue()) == true) {
                errMsg += "Appointment already exists in selected time";
            }
            if (errMsg.length() == 0) {
                return true; 
            } else {
                Alert valalert = new Alert(Alert.AlertType.ERROR);
                valalert.setTitle("Appointment Validation Alert");
                valalert.setHeaderText("Error");
                valalert.setContentText(errMsg);
                valalert.showAndWait();
                return false;
            }
        } catch (NullPointerException ex) {
            Alert dateAlert = new Alert(Alert.AlertType.ERROR);
            dateAlert.setTitle("Date Error");
            dateAlert.setHeaderText("Error in Date Entry");
            dateAlert.setContentText("Please correct your date selections");
            dateAlert.showAndWait();
        }
        return false;
    }
    
    public static boolean overLappingAppt(String start, String end, String contact, String location) {
        
        try {
            PreparedStatement ps = DBConnection.conn.prepareStatement(
                    "SELECT * FROM appointment WHERE ? BETWEEN start AND "
                            + "end OR ? BETWEEN start AND end "
                            + "AND contact = ? AND location = ?");
            ps.setString(1, start);
            ps.setString(2, end);
            ps.setString(3, contact);
            ps.setString(4, location);
            ps.execute();
            ResultSet rs =  ps.getResultSet();
            while(rs.next()) {
                return true;
            }
            
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex);
        }
        return false;
    }

    @FXML
    private void cancelButton(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Appointment.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private static String getCustName(int apptID) {
        String custName = "";
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "SELECT customer.customerName FROM appointment INNER JOIN "
                    + "customer ON customer.customerId=appointment.customerId AND "
                    + "appointment.appointmentId = " 
                    + apptID; 
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()) {
                custName = rs.getString("customerName");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            
        }
        return custName;
    }
    
}
