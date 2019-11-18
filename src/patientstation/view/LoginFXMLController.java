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
import java.util.Locale;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import patientstation.model.User;
import patientstation.services.DBConnection;
import patientstation.services.LogService;

/**
 *
 * @author rajalprasad
 */
public class LoginFXMLController implements Initializable {
    
    @FXML
    private TextField usernametxt;
    @FXML
    private PasswordField passwordtxt;
    @FXML
    private Label UserNameLabel;
    
    @FXML
    private Label PasswordLabel;
    
    @FXML
    private Button LoginButton;
    
    private String ErrTitle;
    private String ErrHeader;
    private String ErrMsg;
    
    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernametxt.getText();
        String password = passwordtxt.getText();
        //User user = new User(username);
        usernametxt.clear();
        passwordtxt.clear();
     
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String sqlStatement = "SELECT * FROM user WHERE userName = '" + 
                    username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(sqlStatement);
            if(rs.next()) {  
                int uID = rs.getInt("userId");
                String uName = rs.getString("userName");
                User user = new User(uID, uName);
                LogService.Logger(username, true);
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                LogService.Logger(username, false);
                Alert LoginAlert = new Alert(Alert.AlertType.ERROR);
                LoginAlert.setTitle(ErrTitle);
                LoginAlert.setHeaderText(ErrHeader);
                LoginAlert.setContentText(ErrMsg);
                LoginAlert.showAndWait();
            }
            stmt.close();
        } catch (SQLException e) {
               System.out.println("SQL Exception: " + e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("patientstation/view/Languages/Lang", locale);
        UserNameLabel.setText(rb.getString("username"));
        PasswordLabel.setText(rb.getString("password"));
        LoginButton.setText(rb.getString("login"));
        ErrTitle = rb.getString("errortitle");
        ErrHeader = rb.getString("errorheader");
        ErrMsg = rb.getString("errormsg");
        
        
    }    
    
}
