package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataBase.DBConnection;
import model.User;

public class UserController {

    // Login controller panggil function yang di model  
	public static User login(String email, String password) {

		if (email.trim().isEmpty() || password.trim().isEmpty()) 
            return null;


		try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?"
            );

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(""); 
                u.setGender(rs.getString("gender"));
                u.setDob(rs.getDate("dob"));
                u.setRole(rs.getString("role"));
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
	}


    // register customer
    public static String register(String name, String email, String pass, String confirm, String gender, Date dob) {

    	if (name.trim().isEmpty()) return "Name cannot be empty";
    	if (email.trim().isEmpty()) return "Email cannot be empty";
        if (!email.endsWith("@email.com")) return "Email must end with @email.com";
        if (pass.length() < 6) return "Password must be at least 6 chars";
        if (!pass.equals(confirm)) return "Password does not match";
        if (!gender.equals("Male") && !gender.equals("Female")) return "Gender invalid";

     // DOB vaidasi
        try {
            int year = Integer.parseInt(dob.toString().substring(0, 4));
            if (2025 - year < 12) return "Customer must be at least 12";
        } catch (Exception e) {
            return "Invalid DOB format";
        }

        // unique name
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(
                "SELECT * FROM users WHERE username = ?"
            );
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return "Name must be unique";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // validasi unique email 
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(
                "SELECT * FROM users WHERE email = ?"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return "Email must be unique";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // insert mulai jalanin prepare statement untuk masukin ke DB sekalian cek query
        User u = new User();
        boolean success = u.register(name, email, pass, gender, dob);

        return success ? "OK" : "Failed to register";

    }

    // cari user dari id nya
    public static User getById(int id) {
    	return new User().getById(id);
    }


    // update user bisa username, email, gender, dan DOB. Role ga bisa diganti dan password nanti akan dibuat terpisah functionnya
    public static String updateUser(User u) {

    	if (u.getUsername().trim().isEmpty()) return "Name cannot be empty";
        if (!u.getEmail().endsWith("@email.com")) return "Email must end with @email.com";

        // DOB validasi
        try {
            int year = Integer.parseInt(u.getDob().toString().substring(0, 4));
            if (2025 - year < 12) return "Customer must be at least 12";
        } catch (Exception e) {
            return "Invalid DOB format";
        }

        // Uniq skip dirinya 
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(
                "SELECT * FROM users WHERE username = ? AND id != ?"
            );
            ps.setString(1, u.getUsername());
            ps.setInt(2, u.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return "Name must be unique";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // email
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(
                "SELECT * FROM users WHERE email = ? AND id != ?"
            );
            ps.setString(1, u.getEmail());
            ps.setInt(2, u.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return "Email must be unique";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // UPDATE pake model 
        boolean success = new User().updateUser(u);
        return success ? "OK" : "Failed to update";
    }


    // Buat change password
    public static String changePassword(int id, String newPass, String confirm) {

        if (newPass.length() < 6) return "Password must be at least 6 chars";
        if (!newPass.equals(confirm)) return "Password does not match";

        boolean success = new User().changePassword(id, newPass);
        return success ? "OK" : "Failed to change password";
    }

    // delete user
    public static boolean delete(int id) {
        return new User().deleteUser(id);
    }

}