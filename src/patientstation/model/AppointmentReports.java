/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author rajalprasad
 */
public class AppointmentReports {
    
    private final SimpleStringProperty month = new SimpleStringProperty();
    private final SimpleStringProperty type = new SimpleStringProperty();
    private final SimpleStringProperty amount = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    
    public AppointmentReports(String month, String type, String amount) {
        setMonth(month);
        setType(type);
        setAmount(amount);
    }
    
    public AppointmentReports(String location, String amount) {
        setLocation(location);
        setAmount(amount);
    }
    
    public String getMonth() {
        return month.get();
    }
    public String getType() {
        return type.get();
    }
    public String getAmount() {
        return amount.get();
    }
    public String getLocation() {
        return location.get();
    }
    
    public void setMonth(String month) {
        this.month.set(month);
    }
    public void setType(String type) {
        this.type.set(type);
    }
    public void setAmount(String amount) {
        this.amount.set(amount);
    }
    public void setLocation(String location) {
        this.location.set(location);
    }
}
