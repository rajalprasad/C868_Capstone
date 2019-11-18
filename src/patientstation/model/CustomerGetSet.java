/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author rajalprasad
 */
public class CustomerGetSet {
    private final SimpleIntegerProperty custID = new SimpleIntegerProperty();
    private final SimpleStringProperty custName = new SimpleStringProperty();
    private final SimpleStringProperty custAddress = new SimpleStringProperty();
    private final SimpleStringProperty custAddress2 = new SimpleStringProperty();
    private final SimpleStringProperty custCity = new SimpleStringProperty();
    private final SimpleStringProperty custZip = new SimpleStringProperty();
    private final SimpleStringProperty custCountry = new SimpleStringProperty();
    private final SimpleStringProperty custPhone = new SimpleStringProperty();
    
    public CustomerGetSet (int ID, String name,String address,String address2,
            String city, String zip, String country, String phone) {
        
        setCustID(ID);
        setCustName(name);
        setCustAddress(address);
        setCustAddress2(address2);
        setCustCity(city);
        setCustZip(zip);
        setCustCountry(country);
        setCustPhone(phone);
        
    }
    
    public int getCustID() {
        return custID.get();
    }
    
    public String getCustName() {
        return custName.get();
    }
    
    public String getCustAddress() {
        return custAddress.get();
    }
    public String getCustAddress2() {
        return custAddress2.get();
    }    
    public String getCustCity() {
        return custCity.get();
    }
    public String getCustZip() {
        return custZip.get();
    }
    public String getCustCountry() {
        return custCountry.get();
    }
    public String getCustPhone() {
        return custPhone.get();
    }
    
    //setters
    public void setCustID(int custID) {
        this.custID.set(custID);
    }
    
    public void setCustName(String custName) {
        this.custName.set(custName);
    }
    
    public void setCustAddress(String custAddress) {
        this.custAddress.set(custAddress);
    }
    public void setCustAddress2(String custAddress2) {
        this.custAddress2.set(custAddress2);
    }
    public void setCustCity(String custCity) {
        this.custCity.set(custCity);
    }
    public void setCustZip(String custZip) {
        this.custZip.set(custZip);
    }
    public void setCustCountry(String custCountry) {
        this.custCountry.set(custCountry);
    }
    public void setCustPhone(String custPhone) {
        this.custPhone.set(custPhone);
    }
    
    
}
