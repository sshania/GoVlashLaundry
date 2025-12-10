package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import DataBase.NotificationDAO;
import model.Notification;

public class NotificationController {

    private NotificationDAO dao = new NotificationDAO();
    
    private String generateNotificationID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    
    //Untuk mengirimkan notifikasi pesan ke customer side
    public boolean sendNotification(String customerID, String messageContent) {
        try {

            Notification notif = new Notification(
                generateNotificationID(),
                customerID,
                messageContent,
                LocalDateTime.now(),
                false
            );
            dao.insert(notif);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Mengambil semua daftar notifikasi customer tertentu
    public ArrayList<Notification> getNotifications(String customerID) {
        return dao.getByRecipient(customerID);
    }
    
    //Menandakan suatu notifikasi "Read"/"Unread"
    public void markAsRead(Notification notif) {
        dao.markAsRead(notif.getNotificationID());
        notif.setIsRead(true);
    }
    
    //Menghapus notifikasi tertentu
    public void deleteNotification(Notification notif) {
        dao.delete(notif.getNotificationID());
    }
}
