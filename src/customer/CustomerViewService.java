package customer;

import controller.ServiceController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.PageManager;
import model.Service;

public class CustomerViewService {
    public Scene getScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label title = new Label("Select a Service");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TableView<Service> table = new TableView<>();

        // Kolom name, description, price, duration
        TableColumn<Service, String> colID = new TableColumn<>("Service ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        
        TableColumn<Service, String> colName = new TableColumn<>("Service Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        
        TableColumn<Service, Double> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

        TableColumn<Service, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        
        TableColumn<Service, Double> colDuration = new TableColumn<>("Duration (days)");
        colDuration.setCellValueFactory(new PropertyValueFactory<>("serviceDuration"));

        // Kolom tombol order
        TableColumn<Service, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Order");
            {
                btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Service data = getTableView().getItems().get(getIndex());
                    // Pindah ke Form Order bawa data service
                    PageManager.setScene(new BuyServicePage(data).getScene());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        table.getColumns().addAll(colID, colName, colDesc, colPrice, colDuration, colAction );
        
        // Load Data dari Database
        table.setItems(FXCollections.observableArrayList(new ServiceController().getAllServices()));

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> PageManager.setScene(new CustomerMainPage().getScene()));

        layout.getChildren().addAll(title, table, btnBack);
        return new Scene(layout, 600, 500);
    }
}