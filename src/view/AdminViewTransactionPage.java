package view;

import controller.TransactionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.PageManager;
import model.Transaction;

import java.util.List;

public class AdminViewTransactionPage {

    private Scene scene;
    private final TransactionController tc = new TransactionController();
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

        table.getColumns().addAll(colID, colCustomer, colService, colWeight, colStatus, colDate);

        root.setCenter(table);

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> PageManager.setScene(new admin.AdminMainPage().getScene()));

        HBox bottomBox = new HBox(btnBack);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        root.setBottom(bottomBox);

        loadData();

        scene = new Scene(root, 550, 450);
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
