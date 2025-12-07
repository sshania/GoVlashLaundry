package view;

import controller.ServiceController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.PageManager;

public class AddServicePage {

    private Scene scene;
    private ServiceController sc = new ServiceController();

    private TextField nameField;
    private TextField descField;
    private TextField priceField;
    private TextField durationField;

    public AddServicePage() {
        initialize();
    }

    private void initialize() {    	
        Label title = new Label("Add New Service");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setVgap(30);
        form.setHgap(10);
        form.setPadding(new Insets(20));

        Label nameLbl = new Label("Service Name:");
        nameField = new TextField();
        nameField.setPromptText("Service Name");

        Label descLbl = new Label("Description:");
        descField = new TextField();
        descField.setPromptText("Service Description");

        Label priceLbl = new Label("Price:");
        priceField = new TextField();
        priceField.setPromptText("Service Price");

        Label durationLbl = new Label("Duration (1–30):");
        durationField = new TextField();
        durationField.setPromptText("Duration");

        form.add(nameLbl, 0, 0);
        form.add(nameField, 1, 0);

        form.add(descLbl, 0, 1);
        form.add(descField, 1, 1);

        form.add(priceLbl, 0, 2);
        form.add(priceField, 1, 2);

        form.add(durationLbl, 0, 3);
        form.add(durationField, 1, 3);

        //Tombol
        Button submitBtn = new Button("Add Service");
        Button backBtn = new Button("Back");
        submitBtn.setOnAction(e -> handleAddService());
        backBtn.setOnAction(e -> PageManager.setScene(new Menu().getScene()));

        HBox buttonBox = new HBox(15, submitBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, title, form, buttonBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));

        scene = new Scene(layout, 600, 450);
    }

    private void handleAddService() {

        String name = nameField.getText();
        String desc = descField.getText();
        String priceStr = priceField.getText();
        String durStr = durationField.getText();

        //Validasi field kosong
        if (name.isEmpty() || desc.isEmpty() || priceStr.isEmpty() || durStr.isEmpty()) {
            showError("All fields must be filled!");
            return;
        }
        
        //Name harus <= 50 chars
        if (name.length() > 50) {
            showError("Name must be less than or equal to 50 characters.");
            return;
        }
        
        //Desc max 250
        if (desc.length() > 250) {
            showError("Description must be less than or equal to 250 characters.");
            return;
        }   

        double price;
        int duration;

        //Price harus angka
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

        //Duration harus angka
        try {
            duration = Integer.parseInt(durStr);
        } catch (Exception e) {
            showError("Duration must be between 1 and 30 days .");
            return;
        }

        //Validasi dari controller (nama ≤50, desc ≤250, duration 1–30)
        String validation = sc.validateAddService(name, desc, price, duration);

        if (!validation.equals("OK")) {
            showError(validation);
            return;
        }

        //Insert ke db
        sc.addService(name, desc, price, duration);

        showSuccess("Service added successfully!");

        //Balik ke table
        PageManager.setScene(new Menu().getScene());
    }

    //PopUp
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showSuccess(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }
}
