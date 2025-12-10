package customer;

import controller.NotificationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import main.PageManager;
import model.Notification;

import java.util.ArrayList;

public class CustomerNotification {

    private NotificationController controller = new NotificationController();
    private ObservableList<Notification> notifList;

    public Scene getScene(String customerID) {
    	
    	//Tabel list notifikasi pada customer side
        TableView<Notification> table = new TableView<>();

        TableColumn<Notification, String> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNotificationID())
        );

        TableColumn<Notification, String> colMessage = new TableColumn<>("Message");
        colMessage.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNotificationMessage())
        );

        TableColumn<Notification, String> colDate = new TableColumn<>("Created At");
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCreatedAt().toString())
        );

        TableColumn<Notification, String> colRead = new TableColumn<>("Status");
        colRead.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIsRead() ? "Read" : "Unread")
        );

        table.getColumns().addAll(colID, colMessage, colDate, colRead);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ArrayList<Notification> data = controller.getNotifications(customerID);
        notifList = FXCollections.observableArrayList(data);
        table.setItems(notifList);

        Button btnDetail = new Button("View Detail");
        Button btnDelete = new Button("Delete");
        Button btnBack = new Button("Back");
        
        //Warning muncul apabila notifikasi belum dipilih
        btnDetail.setOnAction(e -> {
            Notification selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Please select a notification first!");
                return;
            }

            Alert detail = new Alert(Alert.AlertType.INFORMATION);
            detail.setTitle("Notification Detail");
            detail.setHeaderText("Message:");
            detail.setContentText(selected.getNotificationMessage());
            detail.showAndWait();

            if (!selected.getIsRead()) {
                controller.markAsRead(selected);
                refresh(customerID);
            }
        });
        
        //Warning muncul jika notifikasi belum dipilih
        btnDelete.setOnAction(e -> {
            Notification selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Please select a notification to delete!");
                return;
            }

            controller.deleteNotification(selected);
            notifList.remove(selected);
        });
        
        //Redirect ke main page
        btnBack.setOnAction(e -> {
            PageManager.setScene(new CustomerMainPage().getScene());
        });
        
        HBox buttonBox = new HBox(10, btnBack, btnDetail, btnDelete);
        buttonBox.setPadding(new Insets(10));
        
        VBox root = new VBox(10, table, buttonBox);
        root.setPadding(new Insets(10));

        return new Scene(root, 700, 400);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void refresh(String customerID) {
        notifList.setAll(controller.getNotifications(customerID));
    }
}