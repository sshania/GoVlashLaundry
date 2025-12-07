package view;

import java.util.List;

import controller.ServiceController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.PageManager;
import model.Service;

public class DeleteServicePage {

    private Scene scene;
    private BorderPane root;

    private TableView<Service> table;

    private ServiceController sc = new ServiceController();

    public DeleteServicePage() {
        initialize();
        loadData();
    }

    private void initialize() {
        root = new BorderPane();
        scene = new Scene(root, 550, 450);

        table = new TableView<>();

        // TABLE COLUMNS
        TableColumn<Service, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));

        TableColumn<Service, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

        TableColumn<Service, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

        TableColumn<Service, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

        TableColumn<Service, Integer> durationCol = new TableColumn<>("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("serviceDuration"));

        table.getColumns().addAll(idCol, nameCol, descCol, priceCol, durationCol);

        Button deleteBtn = new Button("Delete");
        Button backBtn = new Button("Back");

        deleteBtn.setOnAction(e -> handleDelete());
        backBtn.setOnAction(e -> PageManager.setScene(new Menu().getScene()));

        HBox buttonBox = new HBox(10, deleteBtn, backBtn);
        buttonBox.setPadding(new Insets(10));

        root.setCenter(table);
        root.setBottom(buttonBox);
    }

    private void loadData() {
        List<Service> list = sc.getAllServices();
        table.getItems().setAll(list); 
    }

    private void handleDelete() {
        Service selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showInfo("Please select a service to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Confirmation");
        confirm.setHeaderText("Delete Service");
        confirm.setContentText(
                "Are you sure you want to delete:\n\n" +
                "Service ID: " + selected.getServiceID() + "\n" +
                "Service Name: " + selected.getServiceName() + "\n" +
                "Service Description: " + selected.getServiceDescription() + "\n" +
                "Service Price: " + selected.getServicePrice() + "\n" +
                "Service Duration: " + selected.getServiceDuration() + "\n"
        );

        confirm.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                sc.deleteService(selected.getServiceID());
                loadData();  
                showInfo("Service deleted successfully!");
            }
        });
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    public Scene getScene() {
        return scene;
    }
}
