package DataBase;

import model.Notification;
import java.sql.*;
import java.util.ArrayList;

public class NotificationDAO {

    private Connection conn;

    public NotificationDAO() {
        conn = DBConnection.getInstance().getConnection();
    }

    public void insert(Notification n) {
        String query = "INSERT INTO notifications "
                     + " (id, recipient_id, message, created_at, is_read) "
                     + " VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, n.getNotificationID());
            ps.setString(2, n.getRecipientID());
            ps.setString(3, n.getNotificationMessage());
            ps.setTimestamp(4, Timestamp.valueOf(n.getCreatedAt()));
            ps.setBoolean(5, n.getIsRead());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Notification> getByRecipient(String id) {
        ArrayList<Notification> list = new ArrayList<>();

        String query = "SELECT * FROM notifications "
                     + "WHERE recipient_id = ? "
                     + "ORDER BY created_at DESC";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Notification(
                    rs.getString("id"),             
                    rs.getString("recipient_id"),   
                    rs.getString("message"),        
                    rs.getTimestamp("created_at").toLocalDateTime(), 
                    rs.getBoolean("is_read")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void markAsRead(String notificationID) {
        String query = "UPDATE notifications SET is_read = TRUE WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, notificationID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String notificationID) {
        String query = "DELETE FROM notifications WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, notificationID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}