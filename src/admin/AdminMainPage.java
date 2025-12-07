package admin;

import employee.EmployeeMainPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.PageManager;
import view.AdminViewTransactionPage;
import view.LoginPage;
import view.Menu;

public class AdminMainPage {

    private Scene scene;

    public AdminMainPage() {

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #FCF6D9;");

        Label title = new Label("Admin Dashboard");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: #5b4636;");

        Button manageServiceBtn = new Button("Manage Services");
        Button manageEmployeeBtn = new Button("Manage Employee");
        Button viewTransactionBtn = new Button("View Transactions");
        Button logoutBtn = new Button("Logout");
        
        manageServiceBtn.setStyle("-fx-padding: 10 25; -fx-background-color: #A8BBA3;");
        manageEmployeeBtn.setStyle("-fx-padding: 10 25; -fx-background-color: #A8BBA3;");
        viewTransactionBtn.setStyle("-fx-padding: 10 25; -fx-background-color: #A8BBA3;");
        logoutBtn.setStyle("-fx-padding: 5 20; -fx-background-color: #A8BBA3;");
        
        
        manageServiceBtn.setOnAction(e -> {
            PageManager.setScene(new Menu().getScene());
        });
        manageEmployeeBtn.setOnAction(e -> {
        	PageManager.setScene(new EmployeeMainPage().getScene());
        });
        viewTransactionBtn.setOnAction(e -> {
        	PageManager.setScene(new AdminViewTransactionPage().getScene());
        });
        logoutBtn.setOnAction(e -> PageManager.setScene(new LoginPage().getScene()));
        

        root.getChildren().addAll(title, manageEmployeeBtn, manageServiceBtn, viewTransactionBtn, logoutBtn);

        scene = new Scene(root, 550, 450);
    }

    public Scene getScene() {
        return scene;
    }
}
