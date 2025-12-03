package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DataBase.DBConnection;
import model.Service;

public class ServiceController {

    //Validation
    public String validateAddService(String name, String description, double price, int duration) {

        if (name == null || name.trim().isEmpty())
            return "Service name cannot be empty.";

        if (name.length() > 50)
            return "Service name must be ≤ 50 characters.";

        if (description == null || description.trim().isEmpty())
            return "Service description cannot be empty.";

        if (description.length() > 250)
            return "Service description must be ≤ 250 characters.";

        if (price <= 0)
            return "Service price must be greater than 0.";

        if (duration < 1 || duration > 30)
            return "Service duration must be between 1 and 30.";

        return "OK";
    }

    public String validateEditService(String name, String description, double price, int duration) {
        return validateAddService(name, description, price, duration);
    }

    //Add service
    public void addService(String name, String description, double price, int duration) {

        String validate = validateAddService(name, description, price, duration);
        if (!validate.equals("OK")) {
            System.out.println("ERROR: " + validate);
            return;
        }

        String query = "INSERT INTO services (serviceName, serviceDescription, servicePrice, serviceDuration) "
                     + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, duration);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Delete service
    public void deleteService(int serviceID) {

        String query = "DELETE FROM services WHERE serviceID = ?";

        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(query);
            ps.setInt(1, serviceID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Edit service
    public void editService(int serviceID, String name, String description, double price, int duration) {

        String validate = validateEditService(name, description, price, duration);
        if (!validate.equals("OK")) {
            System.out.println("ERROR: " + validate);
            return;
        }

        String query = "UPDATE services SET serviceName=?, serviceDescription=?, servicePrice=?, serviceDuration=? "
                     + "WHERE serviceID=?";

        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, duration);
            ps.setInt(5, serviceID);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all service
    public List<Service> getAllServices() {

        List<Service> list = new ArrayList<>();

        String query = "SELECT * FROM services";

        try {
            ResultSet rs = DBConnection.getInstance().executeQuery(query);

            while (rs.next()) {
                list.add(new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getString("serviceDescription"),
                        rs.getDouble("servicePrice"),
                        rs.getInt("serviceDuration")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
