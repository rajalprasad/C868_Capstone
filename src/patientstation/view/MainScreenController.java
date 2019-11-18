/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import patientstation.model.AppointmentDataBase;

/**
 * FXML Controller class
 *
 * @author rajalprasad
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button custmanbutton;
    @FXML
    private Button apptmanbutton;
    @FXML
    private Button calendarbutton;
    @FXML
    private Button reportsbutton;
    
    @FXML
    public void handleCustManButton(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void handleApptManButton(ActionEvent event) throws IOException {
        
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Appointment.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    @FXML
    public void handleReportsButton(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void handleLogFileButton(ActionEvent event) {
        File file = new File("logs.txt");
        if (file.exists()) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    System.out.println("Error opening log: " + ex.getMessage());
                }
            }
        }
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (AppointmentDataBase.apptsIn15() == true) {
            Alert apptIn15Alert = new Alert(Alert.AlertType.INFORMATION);
            apptIn15Alert.setTitle("Appointment Alert");
            apptIn15Alert.setHeaderText("Appointment in 15 minutes");
            apptIn15Alert.setContentText("There is an upcoming appointment in 15 minutes, please check the calendar.");
            apptIn15Alert.showAndWait();
        }
    }    
}
