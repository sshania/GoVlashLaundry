package view;

import admin.AdminMainPage;
import employee.EmployeeMainPage;
import customer.CustomerMainPage;    
import customer.CustomerRegisterPage;
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

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:#E9E3DF ");

        Label title = new Label("Welcome to GoVlash Laundry");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: black;");
        
        Label subtitle = new Label("Please login to continue");
        subtitle.setFont(Font.font("Didot", 24));
        subtitle.setStyle("-fx-text-fill: black;");
        
        VBox titleBox = new VBox(10, title, subtitle);
        titleBox.setAlignment(Pos.CENTER);
        root.setTop(titleBox);

        //Buttons
        Button loginBtn = new Button("Login");

        String normal = "-fx-font-size: 14px; -fx-padding: 10 25; -fx-background-color: #9BB4C0; -fx-text-fill: black;";
        String hover  = "-fx-font-size: 14px; -fx-padding: 10 25; -fx-background-color: #7EA1AD; -fx-text-fill: black;";

        //Apply style
        loginBtn.setStyle(normal);

        //Hover effect
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(hover));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(normal));

        //Redirect ke loginpage
        loginBtn.setOnAction(e -> PageManager.setScene(new LoginPage().getScene()));
        
        VBox buttonBox = new VBox(15, loginBtn);
        buttonBox.setAlignment(Pos.CENTER);
        root.setCenter(buttonBox);

        scene = new Scene(root, 550, 450);
    }

    public Scene getScene() {
        return scene;
    }
}
