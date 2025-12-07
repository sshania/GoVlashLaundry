package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DataBase.DBConnection;
import model.User;

public class EmployeeController {
	
    public static String validateEmployee(
            String name, String email, String pass, String confirm,
            String gender, LocalDate dob, String role) {

        if (name.isEmpty()) return "Name cannot be empty";
        if (email.isEmpty() || !email.endsWith("@govlash.com")) 
            return "Email must end with @govlash.com";
        if (pass.length() < 6) 
            return "Password must be at least 6 characters";
        if (!pass.equals(confirm)) 
            return "Password does not match";
        if (!gender.equals("Male") && !gender.equals("Female")) 
            return "Gender invalid";
        if (!role.equals("Admin") && !role.equals("Laundry Staff") && !role.equals("Receptionist")) 
            return "Role invalid";

        // SIMPLE AGE CHECK
        if (dob == null) {
            return "DOB cannot be empty";
        }
        
        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 17) {
            return "Employee must be at least 17 years old";
        }

        // CHECK UNIQUE NAME
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
                return "Name must be unique";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // CHECK UNIQUE EMAIL
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(
                    "SELECT * FROM users WHERE email = ?"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
                return "Email must be unique";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "OK";
    }

    public List<User> getAllLaundryStaff() {
        List<User> list = new ArrayList<>();

        String query = "SELECT * FROM users WHERE role = 'Laundry Staff'";

        try {
            ResultSet rs = DBConnection.getInstance().executeQuery(query);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setGender(rs.getString("gender"));
                u.setDob(rs.getDate("dob"));
                u.setRole(rs.getString("role"));
                // password left empty on purpose, like in mapToUser
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static boolean insert(String name, String email, String pass, String gender, Date dob, String role) {
    	return new User().insertEmployee(name, email, pass, gender, dob, role);
    }
}