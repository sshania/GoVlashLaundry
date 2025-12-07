package customer;

import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.PageManager;
import main.Session;
import model.Service;
import view.SuccessPage;
import customer.CustomerViewService; 

public class BuyServicePage {
    private Service selectedService;

    // Constructor accepting the selected service
    public BuyServicePage(Service service) {
        this.selectedService = service;
    }

    public Scene getScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Order Form");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10); 
        grid.setVgap(10); 
        grid.setAlignment(Pos.CENTER);

        // read only
        TextField tfService = new TextField(selectedService.getServiceName());
        tfService.setEditable(false); 
        tfService.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: gray;");

        TextField tfWeight = new TextField(); 
        tfWeight.setPromptText("Min 2kg");
        
        TextArea taNotes = new TextArea(); 
        taNotes.setPrefHeight(60);
        
        Label lblMsg = new Label();

        grid.add(new Label("Service:"), 0, 0); grid.add(tfService, 1, 0);
        grid.add(new Label("Weight (kg):"), 0, 1); grid.add(tfWeight, 1, 1);
        grid.add(new Label("Notes:"), 0, 2); grid.add(taNotes, 1, 2);

        Button btnOrder = new Button("Confirm Order");
        btnOrder.setOnAction(e -> {
            try {
                double weight = Double.parseDouble(tfWeight.getText());
                String notes = taNotes.getText();
                
                TransactionController tc = new TransactionController();
                String res = tc.validateOrder(String.valueOf(weight), notes); 
                
                if (!res.equals("OK")) {
                    lblMsg.setText(res);
                    lblMsg.setStyle("-fx-text-fill: red;");
                } else {
                    // Check Login Session
                    if (Session.getUser() == null) {
                        lblMsg.setText("Error: Not logged in!");
                        return;
                    }
                    
                    int customerId = Session.getUser().getId();
                    
                    tc.createTransaction(selectedService.getServiceID(), weight, notes);
                    
                    PageManager.setScene(new SuccessPage("Order Success!", new CustomerMainPage().getScene()).getScene());
                }
            } catch (NumberFormatException ex) {
                lblMsg.setText("Invalid weight! Please enter a number.");
                lblMsg.setStyle("-fx-text-fill: red;");
            } catch (Exception ex) {
                ex.printStackTrace();
                lblMsg.setText("An error occurred.");
            }
        });

        Button btnBack = new Button("Cancel");

        btnBack.setOnAction(e -> PageManager.setScene(new CustomerViewService().getScene()));

        root.getChildren().addAll(title, grid, btnOrder, btnBack, lblMsg);
        return new Scene(root);
    }
}