package view;

import controller.ServiceController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.PageManager;
import model.Service;

public class EditServicePage {

    private Scene scene;
    private ServiceController sc = new ServiceController();
    private Service service;

    private TextField nameField;
    private TextField descField;
    private TextField priceField;
    private TextField durationField;

    public EditServicePage(Service service) {
        this.service = service;
        initialize();
    }

    private void initialize() {
        Label title = new Label("Edit Service");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        nameField = new TextField(service.getServiceName());
        descField = new TextField(service.getServiceDescription());
        priceField = new TextField(String.valueOf(service.getServicePrice()));
        durationField = new TextField(String.valueOf(service.getServiceDuration()));

        Button submitBtn = new Button("Update Service");
        Button backBtn = new Button("Back");

        submitBtn.setOnAction(e -> handleEditService());
        backBtn.setOnAction(e -> PageManager.setScene(new Menu().getScene()));

        VBox layout = new VBox(10, title, nameField, descField, priceField, durationField, submitBtn, backBtn);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 600, 450);
    }

    private void handleEditService() {

        String name = nameField.getText();
        String desc = descField.getText();
        String priceStr = priceField.getText();
        String durStr = durationField.getText();

        //Validataion
        if (name.isEmpty() || desc.isEmpty() || priceStr.isEmpty() || durStr.isEmpty()) {
            showError("All fields must be filled!");
            return;
        }

        if (name.length() > 50) {
            showError("Name must be ≤ 50 characters.");
            return;
        }

        if (desc.length() > 250) {
            showError("Description must be ≤ 250 characters.");
            return;
        }

        double price;
        int duration;

        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                showError("Price must be greater than 0.");
                return;
            }
        } catch (Exception e) {
            showError("Price must be a valid number.");
            return;
        }

        try {
            duration = Integer.parseInt(durStr);
            if (duration < 1 || duration > 30) {
                showError("Duration must be between 1 and 30.");
                return;
            }
        } catch (Exception e) {
            showError("Duration must be a valid number.");
            return;
        }

        sc.editService(service.getServiceID(), name, desc, price, duration); // update into db

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setHeaderText(null);
        success.setContentText("Service updated successfully!");
        success.showAndWait();

        PageManager.setScene(new ServiceView().getScene());
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }
}
