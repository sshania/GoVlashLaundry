package view;

import controller.EmployeeController;
import controller.TransactionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.PageManager;
import main.Session;
import model.Transaction;
import model.User;

import java.util.List;

public class TransactionHistoryPage {

    private Scene scene;
    private final TransactionController tc = new TransactionController();
    private final EmployeeController ec = new EmployeeController();
    private TableView<Transaction> table;

    public TransactionHistoryPage() {
        initialize();
    }

    private void initialize() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        Label title = new Label("Transaction Order");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox topBox = new HBox(20, title);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(topBox);

        table = new TableView<>();

        TableColumn<Transaction, String> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, String> colService = new TableColumn<>("Service ID");
        colService.setCellValueFactory(new PropertyValueFactory<>("serviceID"));

        TableColumn<Transaction, Double> colWeight = new TableColumn<>("Weight");
        colWeight.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));

        TableColumn<Transaction, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));

        TableColumn<Transaction, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(cell ->
            new SimpleStringProperty(cell.getValue().getTransactionDate().toString())
        );

        table.getColumns().addAll(colID, colService, colWeight, colStatus, colDate);

        String role = Session.getUser().getRole();

        if (role.equalsIgnoreCase("Receptionist")) {
            addAssignColumn();
        } else if (role.equalsIgnoreCase("Laundry Staff")) {
            addFinishColumn();
        }

        root.setCenter(table);

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> goBackToDashboard());

        HBox bottomBox = new HBox(btnBack);
        bottomBox.setPadding(new Insets(10));
        bottomBox.setAlignment(Pos.CENTER);
        root.setBottom(bottomBox);

        loadData();
        scene = new Scene(root, 550, 450);
    }

    // return data based on role
    private void loadData() {
        String role = Session.getUser().getRole();

        if (role.equalsIgnoreCase("Admin")) {
            table.getItems().setAll(tc.getAllTransactions());
        }
        else if (role.equalsIgnoreCase("Receptionist")) {
            table.getItems().setAll(tc.getPendingTransactions());
        }
        else if (role.equalsIgnoreCase("Laundry Staff")) {
            table.getItems().setAll(tc.getStaffAssignedTransactions(Session.getUser().getId()));
        }
        else if (role.equalsIgnoreCase("Customer")) {
            table.getItems().setAll(tc.getUserTransactions());
        }
    }

    //receptionist: assign order
    private void addAssignColumn() {
        TableColumn<Transaction, Void> colAction = new TableColumn<>("Action");

        colAction.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Assign Staff");

            {
                btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Transaction t = getTableView().getItems().get(getIndex());

                    if (!t.getTransactionStatus().equalsIgnoreCase("Pending")) {
                        new Alert(Alert.AlertType.WARNING, "Order is already assigned.").show();
                        return;
                    }

                    showAssignDialog(t);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        table.getColumns().add(colAction);
    }

    private void showAssignDialog(Transaction t) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Assign Laundry Staff");
        dialog.setHeaderText("Order #" + t.getTransactionID());

        ButtonType assignBtn = new ButtonType("Assign", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(assignBtn, ButtonType.CANCEL);

        ComboBox<String> cbStaff = new ComboBox<>();
        List<User> staffList = ec.getAllLaundryStaff();

        for (User u : staffList) {
            cbStaff.getItems().add(u.getId() + " - " + u.getUsername());
        }

        VBox box = new VBox(10, new Label("Choose Staff:"), cbStaff);
        box.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(box);

        dialog.setResultConverter(btn -> {
            if (btn == assignBtn && cbStaff.getValue() != null) {

                int staffId = Integer.parseInt(cbStaff.getValue().split(" - ")[0]);
                int recepId = Session.getUser().getId();
                int transId = Integer.parseInt(t.getTransactionID());

                tc.assignJob(transId, staffId, recepId);
                loadData();
            }
            return null;
        });

        dialog.showAndWait();
    }

    // laundry staff: finish order
    private void addFinishColumn() {
        TableColumn<Transaction, Void> colAction = new TableColumn<>("Action");

        colAction.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Finish Order");

            {
                btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Transaction t = getTableView().getItems().get(getIndex());

                    tc.finishJob(Integer.parseInt(t.getTransactionID()));

                    loadData(); // refresh
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Transaction t = getTableView().getItems().get(getIndex());

                // button visible jika belum finish
                if (t.getTransactionStatus().equalsIgnoreCase("Finished")) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        }); 

        table.getColumns().add(colAction);
    } 


    
    private void goBackToDashboard() {
        String role = Session.getUser().getRole();

        switch (role.toLowerCase()) {
            case "admin":
                PageManager.setScene(new admin.AdminMainPage().getScene());
                break;
            case "customer":
                PageManager.setScene(new customer.CustomerMainPage().getScene());
                break;
            case "receptionist":
                PageManager.setScene(new view.ReceptionistMainPage().getScene());
                break;
            case "laundry staff":
                PageManager.setScene(new view.LaundryStaffMainPage().getScene());
                break;
            default:
                PageManager.setScene(new view.MainPage().getScene());
        }
    }

    public Scene getScene() {
        return scene;
    }
}
