/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import patientstation.services.DBConnection;

/**
 *
 * @author rajalprasad
 */
public class CustomerDataBase {
    private static ObservableList<CustomerGetSet> allCustomers = 
            FXCollections.observableArrayList();
    
    public static ObservableList<CustomerGetSet> getAllCustomers() {
        allCustomers.clear();
        try {
            Statement stmt = DBConnection.conn.createStatement();
            String query = 
                "SELECT customer.customerId, customer.customerName, "
                + "address.address, address.address2, address.phone, "
                + "address.postalCode, city.city, country.country"
                + " FROM customer INNER JOIN address "
                + "ON customer.addressId = address.addressId "
                + "INNER JOIN city ON address.cityId = city.cityId "
                +"INNER JOIN country ON city.cityId = country.countryId";
            ResultSet results = stmt.executeQuery(query);
            while (results.next()) {
                CustomerGetSet customer = new CustomerGetSet(
                results.getInt("customerId"),
                results.getString("customerName"),
                results.getString("address"),
                results.getString("address2"),
                results.getString("city"),
                results.getString("postalCode"),
                results.getString("country"),
                results.getString("phone"));
                
                allCustomers.add(customer);
            }
            stmt.close();
            return allCustomers;
                
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }
    
    public static void saveCustomer(String name, String address, 
            String address2, String city, String zip, String phone, String country) 
            throws SQLException {
                        
                PreparedStatement ps = DBConnection.conn.prepareStatement(
                "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?,now(),?,now(),?)", 
                Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, country);
                ps.setString(2, User.getUsername());         
                ps.setString(3, User.getUsername());
                ps.execute();
                ResultSet countryIdRS = ps.getGeneratedKeys();
                int countryPK = -1;
                if (countryIdRS.next()) {
                    countryPK = countryIdRS.getInt(1);
                }
                //System.out.println("Country PK: " + countryPK);
            
            
            PreparedStatement ps2 = DBConnection.conn.prepareStatement(
                "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, now(), ?, now(), ?)",
                Statement.RETURN_GENERATED_KEYS);
                ps2.setString(1, city);
                ps2.setInt(2, countryPK);
                ps2.setString(3, User.getUsername());
                ps2.setString(4, User.getUsername());
                ps2.execute();
                ResultSet cityIdRS = ps2.getGeneratedKeys();
                int cityPK = -1;
                if (cityIdRS.next()) {
                    cityPK = cityIdRS.getInt(1);
                }
                //System.out.println("City PK: " + cityPK);
           
            PreparedStatement ps3 = DBConnection.conn.prepareStatement(
                "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?,?,?,?,?, now(),?, now(), ?)",
                Statement.RETURN_GENERATED_KEYS);
                ps3.setString(1, address);
                ps3.setString(2, address2);
                ps3.setInt(3, cityPK);
                ps3.setString(4, zip);
                ps3.setString(5, phone);
                ps3.setString(6, User.getUsername());
                ps3.setString(7, User.getUsername());
                ps3.execute();
                ResultSet addressIdRS = ps3.getGeneratedKeys();
                int addressPK = -1;
                if (addressIdRS.next()) {
                    addressPK = addressIdRS.getInt(1);
                }
                //System.out.println("Address PK: " + addressPK);

            PreparedStatement ps4 = DBConnection.conn.prepareStatement(
                "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?,?,1,now(),?,now(),?)",
                Statement.RETURN_GENERATED_KEYS);
                ps4.setString(1, name);
                ps4.setInt(2, addressPK);
                ps4.setString(3, User.getUsername());
                ps4.setString(4, User.getUsername());
                ps4.execute();
            
    }
    
    public static void updateCustomer(int id, String name, String address, 
            String address2, String city, String zip, String country, String phone) {
        
            try {
            PreparedStatement psupdate = DBConnection.conn.prepareStatement(
                "UPDATE customer, address, city, country "
                + "SET customerName = ?, address = ?, address2 = ?, city = ?, postalCode = ?, country = ?, phone = ?"
                + " WHERE customer.customerId = ? AND customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId"
            );
            psupdate.setString(1, name);
            psupdate.setString(2, address);
            psupdate.setString(3, address2);
            psupdate.setString(4, city);
            psupdate.setString(5, zip);
            psupdate.setString(6, country);
            psupdate.setString(7, phone);
            psupdate.setInt(8, id);
            psupdate.execute();
            }
            catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
            }
        
    }
    
    public static void deleteCustomer(int custID) {
        try{
            PreparedStatement psdelete1 = DBConnection.conn.prepareStatement(
                    "DELETE FROM appointment where appointment.customerId = ?");
            psdelete1.setInt(1, custID);
            psdelete1.execute();
            
            PreparedStatement psdelete2 = DBConnection.conn.prepareStatement(
            "DELETE customer.*, address.* FROM customer, address WHERE customer.customerId = ? AND customer.addressId = address.addressId");
            psdelete2.setInt(1, custID);
            psdelete2.execute();
            
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
            }
    }
}
