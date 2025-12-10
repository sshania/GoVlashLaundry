package view;

import controller.NotificationController;
import controller.TransactionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.PageManager;
import model.Transaction;

import java.util.List;

public class AdminViewTransactionPage {

    private Scene scene;
    private final TransactionController tc = new TransactionController();
    private final NotificationController nc = new NotificationController(); 
    private TableView<Transaction> table;
    private ComboBox<String> cbStatus;

    public AdminViewTransactionPage() {
        initialize();
    }

    private void initialize() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        Label title = new Label("All Transactions");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("All", "Pending", "Finished");
        cbStatus.setValue("All");
        cbStatus.setOnAction(e -> loadData());

        Label lblFilter = new Label("Filter status:");
        HBox filterBox = new HBox(10, lblFilter, cbStatus);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        HBox topBox = new HBox(20, title, filterBox);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(topBox);

        table = new TableView<>();
        
        //Tabel informasi transaksi customer
        TableColumn<Transaction, String> colID = new TableColumn<>("Transaction ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, String> colCustomer = new TableColumn<>("Customer ID");
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        TableColumn<Transaction, String> colService = new TableColumn<>("Service ID");
        colService.setCellValueFactory(new PropertyValueFactory<>("serviceID"));

        TableColumn<Transaction, Double> colWeight = new TableColumn<>("Weight (kg)");
        colWeight.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));

        TableColumn<Transaction, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));

        TableColumn<Transaction, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(cell ->
            new SimpleStringProperty(
                cell.getValue().getTransactionDate() != null
                    ? cell.getValue().getTransactionDate().toString()
                    : ""
            )
        );

        //Kolom notifikasi dengan tombol "Send Notification"
        TableColumn<Transaction, Void> colAction = new TableColumn<>("Action");
        colAction.setPrefWidth(160);
        colAction.setCellFactory(param -> new TableCell<>() {
            
            private final Button btn = new Button("Send Notification");

            {
            	
                btn.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
                
                btn.setOnAction(event -> {

                	Transaction t = getTableView().getItems().get(getIndex());
                    
                    String customerId = t.getCustomerID();
                    String transId = t.getTransactionID();
                    String serviceId = t.getServiceID();
                    
                    String customMsg = "Your order (Service ID: " + serviceId + ") is finished and ready for pickup. Thank you for choosing our service!";
                    
//                    boolean success = nc.sendNotification(customerId);
                    boolean success = nc.sendNotification(customerId, customMsg);

                    
                    if (success) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Notification sent successfully to Customer ID: " + customerId);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Failed to send notification.");
                        alert.showAndWait();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Transaction t = getTableView().getItems().get(getIndex());
                    
                    //Tombol hanya muncul jika status "Finished"
                    if ("Finished".equalsIgnoreCase(t.getTransactionStatus())) {
                        setGraphic(btn);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        table.getColumns().addAll(colID, colCustomer, colService, colWeight, colStatus, colDate, colAction);

        root.setCenter(table);
        
        //Redirect ke main page
        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> PageManager.setScene(new admin.AdminMainPage().getScene()));

        HBox bottomBox = new HBox(btnBack);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        root.setBottom(bottomBox);

        loadData();

        scene = new Scene(root, 750, 450);
    }

    private void loadData() {
        String status = cbStatus.getValue();
        List<Transaction> list;

        if ("Pending".equalsIgnoreCase(status)) {
            list = tc.getPendingTransactions();
        } else if ("Finished".equalsIgnoreCase(status)) {
            list = tc.getFinishedTransactions();
        } else {
            list = tc.getAllTransactions();
        }

        table.getItems().setAll(list);
    }

    public Scene getScene() {
        return scene;
    }
}