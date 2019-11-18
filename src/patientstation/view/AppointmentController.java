/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.view;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import patientstation.model.Appointment;
import patientstation.model.AppointmentDataBase;
import patientstation.model.CustomerDataBase;
import patientstation.model.CustomerGetSet;

/**
 * FXML Controller class
 *
 * @author rajalprasad
 */
public class AppointmentController implements Initializable {

    @FXML
    private TableView<CustomerGetSet> custTable;
    @FXML
    private TableColumn<CustomerGetSet, Integer> custID;
    @FXML
    private TableColumn<CustomerGetSet, String> custName;
    @FXML
    private TableView<Appointment> monthlycal;
//    @FXML
//    private TableColumn<Appointment, String> mDescription;
    @FXML
    private TableColumn<Appointment, String> mContact;
    @FXML
    private TableColumn<Appointment, String> mLocation;
    @FXML
    private TableColumn<Appointment, String> mStart;
    @FXML
    private TableColumn<Appointment, String> mEnd;
    @FXML
    private TableView<Appointment> weeklycal;
//    @FXML
//    private TableColumn<Appointment, String> wDescription;
    @FXML
    private TableColumn<Appointment, String> wContact;
    @FXML
    private TableColumn<Appointment, String> wLocation;
    @FXML
    private TableColumn<Appointment, String> wStart;
    @FXML
    private TableColumn<Appointment, String> wEnd;
    @FXML
    private Tab monthly;
    //
    private boolean calItemSelected;
    public static Appointment selectedAppt;
    public static CustomerGetSet selectCust;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        custName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        custTable.setItems(CustomerDataBase.getAllCustomers());
        //Monthly Calendar
        //Using Lambda to bind a value to a table column
//        mDescription.setCellValueFactory(cellData -> {
//            return cellData.getValue().getApptDescriptionProp(); 
//        });
        mContact.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptContactProp(); 
        });
        mLocation.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptLocationProp(); 
        });
        mStart.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptStartProp(); 
        });
        mEnd.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptEndProp(); 
        });
        monthlycal.setItems(AppointmentDataBase.getMonthlyAppts());
        //Weekly Calendar
//        wDescription.setCellValueFactory(cellData -> {
//            return cellData.getValue().getApptDescriptionProp(); 
//        });
        wContact.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptContactProp(); 
        });
        wLocation.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptLocationProp(); 
        });
        wStart.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptStartProp(); 
        });
        wEnd.setCellValueFactory(cellData -> {
            return cellData.getValue().getApptEndProp(); 
        });
        weeklycal.setItems(AppointmentDataBase.getWeeklyAppts());
    }    

    @FXML
    private void addApptButton(ActionEvent event) throws IOException {
        selectCust = custTable.getSelectionModel().getSelectedItem();
        if (selectCust == null) {
            Alert CustSelectAlert = new Alert(Alert.AlertType.ERROR);
            CustSelectAlert.setTitle("Error");
            CustSelectAlert.setHeaderText("Customer selection error");
            CustSelectAlert.setContentText("Please select a customer before adding appointment");
            CustSelectAlert.showAndWait();
        } else {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void updateApptButton(ActionEvent event) {
        if (monthly.isSelected()) {
            if (monthlycal.getSelectionModel().getSelectedItem() != null) {
                calItemSelected = true;
                selectedAppt = monthlycal.getSelectionModel().getSelectedItem();
            } 
        } else {
            if (weeklycal.getSelectionModel().getSelectedItem() != null) {
                calItemSelected = true;
                selectedAppt = weeklycal.getSelectionModel().getSelectedItem();
            } else if (weeklycal.getSelectionModel().getSelectedItem() == null && monthlycal.getSelectionModel().getSelectedItem() == null) {
                calItemSelected = false;
            }
        }
        if (calItemSelected == true) {
            try {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("UpdateAppointment.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("IOException: " + ex.getMessage());
            }
        } else {
            Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
            deleteAlert.setTitle("Update Error");
            deleteAlert.setContentText("Please make a selection before update");
            deleteAlert.setHeaderText("Selection Error");
            deleteAlert.showAndWait();
        }
    }

    @FXML
    private void deleteApptButton(ActionEvent event) {
        
        if (monthly.isSelected()) {
            if (monthlycal.getSelectionModel().getSelectedItem() != null) {
                calItemSelected = true;
                selectedAppt = monthlycal.getSelectionModel().getSelectedItem();
            } 
        } else {
            if (weeklycal.getSelectionModel().getSelectedItem() != null) {
                calItemSelected = true;
                selectedAppt = weeklycal.getSelectionModel().getSelectedItem();
            } else if (weeklycal.getSelectionModel().getSelectedItem() == null && monthlycal.getSelectionModel().getSelectedItem() == null) {
                calItemSelected = false;
            }
        }
        if (calItemSelected == true) {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("Delete Confirmation");
            deleteAlert.setContentText("Delete item");
            deleteAlert.setHeaderText("Delete selected item?");
            //Using Lambda to get user response and initiating delete function
            deleteAlert.showAndWait().ifPresent((response -> {
            if (response == ButtonType.OK) {
                AppointmentDataBase.deleteAppt(selectedAppt.getApptID());
                monthlycal.setItems(AppointmentDataBase.getMonthlyAppts());
                weeklycal.setItems(AppointmentDataBase.getWeeklyAppts());
                }
            })); 
        } else {
            Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
            deleteAlert.setTitle("Delete Error");
            deleteAlert.setContentText("Please make a selection before deleting");
            deleteAlert.setHeaderText("Selection Error");
            deleteAlert.showAndWait();
        }
    }

    @FXML
    private void exitButton(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
}
