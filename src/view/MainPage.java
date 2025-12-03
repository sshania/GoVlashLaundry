package view;

import admin.AdminMainPage;
import employee.EmployeeMainPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.PageManager;

public class MainPage {

    private Scene scene;

    public MainPage() {
        initialize();
    }
    
    private void initialize() {

        //Main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:#E9E3DF ");

        //Title
        Label title = new Label("Welcome to GoVlash Laundry");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: black;");
        
        //Label
        Label subtitle = new Label("Choose your role to continue");
        subtitle.setFont(Font.font("Didot", 25));
        subtitle.setStyle("-fx-text-fill: black;");
        
        VBox titleBox = new VBox(10, title, subtitle);
        titleBox.setAlignment(Pos.CENTER);
        root.setTop(titleBox);

        //Buttons
        Button adminBtn = new Button("Admin");
        Button employeeBtn = new Button("Employee");

        String normal = "-fx-font-size: 14px; -fx-padding: 10 25; -fx-background-color: #9BB4C0; -fx-text-fill: black;";
        String hover  = "-fx-font-size: 14px; -fx-padding: 10 25; -fx-background-color: #7EA1AD; -fx-text-fill: black;";

        //Apply style
        adminBtn.setStyle(normal);
        employeeBtn.setStyle(normal);

        //Hover effect
        adminBtn.setOnMouseEntered(e -> adminBtn.setStyle(hover));
        adminBtn.setOnMouseExited(e -> adminBtn.setStyle(normal));

        employeeBtn.setOnMouseEntered(e -> employeeBtn.setStyle(hover));
        employeeBtn.setOnMouseExited(e -> employeeBtn.setStyle(normal));

        //Redirect on click
        adminBtn.setOnAction(e -> PageManager.setScene(new AdminMainPage().getScene()));
        employeeBtn.setOnAction(e -> PageManager.setScene(new EmployeeMainPage().getScene()));

        VBox buttonBox = new VBox(15, adminBtn, employeeBtn);
        buttonBox.setAlignment(Pos.CENTER);
        root.setCenter(buttonBox);

        scene = new Scene(root, 600, 450);
    }

    public Scene getScene() {
        return scene;
    }
}
