/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import patientstation.model.CustomerDataBase;

/**
 * FXML Controller class
 *
 * @author rajalprasad
 */
public class AddCustomerController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField address2;
    @FXML
    private TextField country;
    @FXML
    private TextField zip;
    @FXML
    private TextField city;
    @FXML
    private TextField phone;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveButton(ActionEvent event) throws IOException {
        
        if (validateEntry() == true) {
            String custName = name.getText();
            String custAddress = address.getText();
            String custAddress2 = address2.getText();
            String custCity = city.getText();
            String custZip = zip.getText();
            String custPhone = phone.getText();
            String custCountry = country.getText();
            try {
            CustomerDataBase.saveCustomer(custName, custAddress, custAddress2, custCity, custZip, custPhone, custCountry);
            } catch (SQLException ex)  {
            System.out.println("SQL Exception: " + ex.getMessage());
            }
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    private boolean validateEntry() {
        
        String vname = name.getText();
        String vaddress = address.getText();
        String vaddress2 = address2.getText();
        String vcountry = country.getText();
        String vzip = zip.getText();
        String vcity = city.getText();
        String vphone = phone.getText();
        
        String errMsg = "";
        
        if (vname == null || vname.length() == 0) {
            errMsg += "Please enter a name\n";
        }
        if (vaddress == null || vaddress.length() == 0) {
            errMsg += "Please enter address\n";
        }
        if (vaddress2 == null || vaddress2.length() == 0) {
            errMsg += "Please enter address 2\n";
        }
        if (vcountry == null || vcountry.length() == 0) {
            errMsg += "Please enter country\n";
        }
        if (vzip == null || vzip.length() == 0) {
            errMsg += "Please enter zip\n";
        }
        if (vcity == null || vcity.length() == 0) {
            errMsg += "Please enter city\n";
        }
        if (vphone == null || vphone.length() == 0) {
            errMsg += "Please enter phone\n";
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
    }

    @FXML
    private void cancelButton(ActionEvent event) throws IOException {
        
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

