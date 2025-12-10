package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import DataBase.NotificationDAO;
import model.Notification;

public class NotificationController {

    private NotificationDAO dao = new NotificationDAO();
    
    //Untuk mengenerate ID unik dengan prefix 'NTF' sebagai penanda ID notifikasi
    private String generateNotificationID() {
        return "NTF" + System.currentTimeMillis();
    }
    
    //Untuk mengirimkan notifikasi pesan ke customer side
    public boolean sendNotification(String customerID) {
        try {
            String message = "Your order is finished and ready for pickup. Thank you for choosing our service!";

            Notification notif = new Notification(
                generateNotificationID(),
                customerID,
                message,
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

    public ArrayList<Notification> getNotifications(String customerID) {
        return dao.getByRecipient(customerID);
    }

    public void markAsRead(Notification notif) {
        dao.markAsRead(notif.getNotificationID());
        notif.setIsRead(true);
    }

    public void deleteNotification(Notification notif) {
        dao.delete(notif.getNotificationID());
    }
}
