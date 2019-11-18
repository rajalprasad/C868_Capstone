/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import patientstation.model.CustomerDataBase;
import patientstation.model.CustomerGetSet;

/**
 * FXML Controller class
 *
 * @author rajalprasad
 */
public class CustomerController implements Initializable {

    @FXML
    private TableView<CustomerGetSet> custTable;
    @FXML
    private TableColumn<CustomerGetSet, Integer> custID;
    @FXML
    private TableColumn<CustomerGetSet, String> custName;
    
    public static CustomerGetSet updateCust;
    
    @FXML
    private void handleAddButton(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleUpdateButton(ActionEvent event) throws IOException {
        if (custTable.getSelectionModel().getSelectedItem() != null) {
            updateCust = custTable.getSelectionModel().getSelectedItem();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("UpdateCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert deleteAlert = new Alert(Alert.AlertType.ERROR);
            deleteAlert.setTitle("Update Error");
            deleteAlert.setContentText("Please make a selection before update");
            deleteAlert.setHeaderText("Selection Error");
            deleteAlert.showAndWait();
        }
    }
    
    @FXML
    private void handleDeleteButton(ActionEvent event) {
        if (custTable.getSelectionModel().getSelectedItem() != null) {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("Delete Confirmation");
            deleteAlert.setContentText("Delete item");
            deleteAlert.setHeaderText("Delete selected item?");
            Optional<ButtonType> result = deleteAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                CustomerGetSet deleteCust = custTable.getSelectionModel().getSelectedItem();
                CustomerDataBase.deleteCustomer(deleteCust.getCustID());
                custTable.setItems(CustomerDataBase.getAllCustomers());
            }
        } else {
            Alert deleteAlert = new Alert(Alert.AlertType.ERROR);
            deleteAlert.setTitle("Delete Error");
            deleteAlert.setContentText("Please make a selection before deleting");
            deleteAlert.setHeaderText("Selection Error");
            deleteAlert.showAndWait();
        }        
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        custName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        custTable.setItems(CustomerDataBase.getAllCustomers());
        
    }    
    
}
