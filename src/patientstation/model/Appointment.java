/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author rajalprasad
 */
public class Appointment {
    private final SimpleIntegerProperty apptID = new SimpleIntegerProperty();
    private final SimpleIntegerProperty apptCustID = new SimpleIntegerProperty();
    private final SimpleStringProperty apptStart = new SimpleStringProperty();
    private final SimpleStringProperty apptEnd = new SimpleStringProperty();
//    private final SimpleStringProperty apptTitle = new SimpleStringProperty();
//    private final SimpleStringProperty apptDescription = new SimpleStringProperty();
    private final SimpleStringProperty apptLocation = new SimpleStringProperty();
    private final SimpleStringProperty apptContact = new SimpleStringProperty();
//    private final SimpleStringProperty apptURL = new SimpleStringProperty();
    private final SimpleStringProperty apptType = new SimpleStringProperty();
    private final SimpleStringProperty apptCustName = new SimpleStringProperty();
    
    public Appointment(int ID, int custID, String start, String end, String location, String contact) {
        setApptID(ID);
        setApptCustID(custID);
        setApptStart(start);
        setApptEnd(end);
//        setApptTitle(title);
//        setApptDescription(description);
        setApptLocation(location);
        setApptContact(contact); 
//        setApptURL(url);
    }
    
    public Appointment(String contact, String type, String custName, String start, String end) {
        setApptContact(contact);
        setApptType(type);
        setApptCustName(custName);
//        setApptTitle(title);
        setApptStart(start);
        setApptEnd(end);
    }
    
    public int getApptID() {
        return apptID.get();
    }
    public int getApptCustID() {
        return apptCustID.get();
    }
    public String getApptStart() {
        return apptStart.get();
    }
    public String getApptEnd() {
        return apptEnd.get();
    }
//    public String getApptTitle() {
//        return apptTitle.get();
//    }
//    public String getApptDescription() {
//        return apptDescription.get();
//    }
    public String getApptLocation() {
        return apptLocation.get();
    }
    public String getApptContact() {
        return apptContact.get();
    }
//    public String getApptURL() {
//        return apptURL.get();
//    }
    public String getApptType() {
        return apptType.get();
    }
    public String getCustName() {
        return apptCustName.get();
    }
    
    public void setApptID(int apptID) {
        this.apptID.set(apptID);
    }
    public void setApptCustID(int apptCustID) {
        this.apptCustID.set(apptCustID);
    }
    public void setApptStart(String apptStart) {
        this.apptStart.set(apptStart);
    }
    public void setApptEnd(String apptEnd) {
        this.apptEnd.set(apptEnd);
    }
//    public void setApptTitle(String apptTitle) {
//        this.apptTitle.set(apptTitle);
//    }
//    public void setApptDescription(String apptDescription) {
//        this.apptDescription.set(apptDescription);
//    }
    public void setApptLocation(String apptLocation) {
        this.apptLocation.set(apptLocation);
    }
    public void setApptContact(String apptContact) {
        this.apptContact.set(apptContact);
    }
//    public void setApptURL(String apptURL) {
//        this.apptURL.set(apptURL);
//    }
    public void setApptType(String apptType) {
        this.apptType.set(apptType);
    }
    public void setApptCustName(String apptCustName) {
        this.apptCustName.set(apptCustName);
    }
    
    public StringProperty getApptStartProp() {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(getApptStart(), dateformatter);
        ZonedDateTime utcZDT = ZonedDateTime.of(ldt, ZoneId.of("UTC"));
        ZonedDateTime localZDT = utcZDT.withZoneSameInstant(ZoneOffset.systemDefault());
        StringProperty date = new SimpleStringProperty(dateformatter.format(localZDT));
        return date;        
    }
    
    public StringProperty getApptEndProp() {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(getApptEnd(), dateformatter);
        ZonedDateTime utcZDT = ZonedDateTime.of(ldt, ZoneId.of("UTC"));
        ZonedDateTime localZDT = utcZDT.withZoneSameInstant(ZoneOffset.systemDefault());
        StringProperty date = new SimpleStringProperty(dateformatter.format(localZDT));
        return date;        
    }
    
//    public StringProperty getApptTitleProp() {
//        return this.apptTitle;
//    }
//    
//    public StringProperty getApptDescriptionProp() {
//        return this.apptDescription;
//    }
    
    public StringProperty getApptLocationProp() {
        return this.apptLocation;
    }
    
    public StringProperty getApptContactProp() {
        return this.apptContact;
    }
    public StringProperty getApptTypeProp() {
        return this.apptType;
    }
    public StringProperty getApptCustProp() {
        return this.apptCustName;
    }
}
