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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import patientstation.model.Appointment;
import patientstation.services.DBConnection;

/**
 * FXML Controller class
 *
 * @author rajalprasad
 */
public class SearchController implements Initializable {

    @FXML
    private TextField keywordTextField;
    @FXML
    private TableView<Appointment> searchResultsTable;
    @FXML
    private TableColumn<Appointment, String> patientNameCol;
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    @FXML
    private TableColumn<Appointment, String> apptTimeCol;
    @FXML
    private TableColumn<Appointment, String> physicianCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSearchButton(ActionEvent event) {
        searchResultsTable.getItems().clear();
        String key = keywordTextField.getText();
        keywordTextField.setText("");
        
        ObservableList<Appointment> searchResult = FXCollections.observableArrayList();
        Appointment search;
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String q = "SELECT appointment.contact, appointment.type, "
                    + "customer.customerName, appointment.start, appointment.end "
                    +"FROM appointment, customer "
                    +"WHERE appointment.customerId = customer.customerId "
                    +"AND customer.customerName LIKE '%" +key +"%'";
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()) {
                search = new Appointment(
                    rs.getString("appointment.contact"),
                    rs.getString("appointment.type"),
                    rs.getString("customer.customerName"),
                    rs.getString("appointment.start"),
                    rs.getString("appointment.end"));
                searchResult.add(search);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        
        patientNameCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptCustProp(); 
        });
        apptTypeCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptTypeProp(); 
        });
        apptTimeCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptStartProp(); 
        });
        physicianCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptContactProp(); 
        });
        searchResultsTable.setItems(searchResult);
    }

    @FXML
    private void handleExitButton(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
}
