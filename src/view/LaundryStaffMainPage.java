package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.PageManager;

public class LaundryStaffMainPage {

    private Scene scene;

    public LaundryStaffMainPage() {

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #FCF6D9;");

        Label title = new Label("Laundry Staff Dashboard");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: #5b4636;");

        Button viewTransactionBtn = new Button("View Assigned Orders");
        Button logoutBtn = new Button("Logout");
        
        viewTransactionBtn.setStyle("-fx-padding: 10 25; -fx-background-color: #A8BBA3;");
        logoutBtn.setStyle("-fx-padding: 5 20; -fx-background-color: #A8BBA3;");
        
        //buttons action
        viewTransactionBtn.setOnAction(e -> {
        	PageManager.setScene(new TransactionHistoryPage().getScene());
        });
        logoutBtn.setOnAction(e -> PageManager.setScene(new LoginPage().getScene()));
        

        root.getChildren().addAll(title, viewTransactionBtn, logoutBtn);

        scene = new Scene(root, 550, 450);
    }

    public Scene getScene() {
        return scene;
    }
}
