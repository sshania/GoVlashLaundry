package view;

import controller.ServiceController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.PageManager;
import model.Service;

import java.util.List;

public class ViewAllServicesPage {

    private Scene scene;
    private ServiceController sc = new ServiceController();

    public ViewAllServicesPage() {

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label title = new Label("All Services");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TableView<Service> table = new TableView<>();

        //Kolom-kolom tabel
        TableColumn<Service, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        colID.setPrefWidth(60);

        TableColumn<Service, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colName.setPrefWidth(120);

        TableColumn<Service, String> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        colDesc.setPrefWidth(200);

        TableColumn<Service, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        colPrice.setPrefWidth(80);

        TableColumn<Service, Integer> colDuration = new TableColumn<>("Duration");
        colDuration.setCellValueFactory(new PropertyValueFactory<>("serviceDuration"));
        colDuration.setPrefWidth(80);

        table.getColumns().addAll(colID, colName, colDesc, colPrice, colDuration);

        //Load data
        List<Service> list = sc.getAllServices();

        if (list.isEmpty()) {
            Label empty = new Label("No services available.");
            empty.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
            layout.getChildren().addAll(title, empty, createBackButton());
            scene = new Scene(layout, 500, 500);
            return;
        }

        ObservableList<Service> data = FXCollections.observableArrayList(list);
        table.setItems(data);

        layout.getChildren().addAll(title, table, createBackButton());

        scene = new Scene(layout, 500, 500);
    }

    private Button createBackButton() {
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20;");
        backBtn.setOnAction(e -> PageManager.returnPage()); // kembali ke menu admin services
        return backBtn;
    }

    public Scene getScene() {
        return scene;
    }
}
