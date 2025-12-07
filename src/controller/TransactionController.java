package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DataBase.DBConnection;
import main.Session;
import model.Transaction;

public class TransactionController {

    public String validateOrder(String weightStr, String notes) {
        if (weightStr.isEmpty()) return "Weight cannot be empty.";
        
        double weight;
        try {
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            return "Weight must be a number.";
        }

        if (weight < 2 || weight > 50) return "Weight must be between 2 and 50 kg.";
        if (notes.length() > 250) return "Notes must be <= 250 characters.";

        return "OK";
    }

    public boolean createTransaction(int serviceId, double weight, String notes) {
        String query = "INSERT INTO transactions (service_id, customer_id, transaction_date, transaction_status, total_weight, notes) VALUES (?, ?, NOW(), 'Pending', ?, ?)";
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(query);
            ps.setInt(1, serviceId);
            ps.setInt(2, Session.getUser().getId());
            ps.setDouble(3, weight);
            ps.setString(4, notes);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getUserTransactions() {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE customer_id = ? ORDER BY transaction_date DESC";
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(query);
            ps.setInt(1, Session.getUser().getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //  admin transaction: view all
    public List<Transaction> getAllTransactions() {
        return runQuery("SELECT * FROM transactions ORDER BY transaction_date DESC");
    }

    //  receptionist transaction: view pending only
    public List<Transaction> getPendingTransactions() {
        return runQuery("SELECT * FROM transactions WHERE transaction_status = 'Pending' ORDER BY transaction_date DESC");
    }

    //  filter only finish
    public List<Transaction> getFinishedTransactions() {
        return runQuery("SELECT * FROM transactions WHERE transaction_status = 'Finished' ORDER BY transaction_date DESC");
    }

    //  laundry staff: assigned jobs
    public List<Transaction> getStaffAssignedTransactions(int staffId) {
        String query = "SELECT * FROM transactions WHERE laundry_staff_id = " + staffId + " ORDER BY transaction_date DESC";
        return runQuery(query);
    }

    //  receptionist: assign jobs
    public boolean assignJob(int transactionId, int staffId, int recepId) {
        String query = "UPDATE transactions SET laundry_staff_id = ?, receptionist_id = ?, transaction_status = 'In Progress' WHERE id = ?";
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(query);
            ps.setInt(1, staffId);
            ps.setInt(2, recepId);
            ps.setInt(3, transactionId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // staff mark as finish 
    public boolean finishJob(int transactionId) {
        String query = "UPDATE transactions SET transaction_status = 'Finished' WHERE id = ?";
        try {
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(query);
            ps.setInt(1, transactionId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 
    private List<Transaction> runQuery(String query) {
        List<Transaction> list = new ArrayList<>();
        try {
            ResultSet rs = DBConnection.getInstance().executeQuery(query);
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Transaction mapRow(ResultSet rs) throws Exception {
        return new Transaction(
            String.valueOf(rs.getInt("id")),
            String.valueOf(rs.getInt("service_id")),
            String.valueOf(rs.getInt("customer_id")),
            String.valueOf(rs.getInt("receptionist_id")),
            String.valueOf(rs.getInt("laundry_staff_id")),
            rs.getDate("transaction_date"),
            rs.getString("transaction_status"),
            rs.getDouble("total_weight"),
            rs.getString("notes")
        );
    }
}