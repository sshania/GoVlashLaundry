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
        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register Customer");
        
        Button adminBtn = new Button("Admin");
        Button employeeBtn = new Button("Employee");
        
        Button customerBtn = new Button("Customer Dashboard"); 

        String normal = "-fx-font-size: 14px; -fx-padding: 10 25; -fx-background-color: #9BB4C0; -fx-text-fill: black;";
        String hover  = "-fx-font-size: 14px; -fx-padding: 10 25; -fx-background-color: #7EA1AD; -fx-text-fill: black;";

        //Apply style
        loginBtn.setStyle(normal);
        registerBtn.setStyle(normal);
        adminBtn.setStyle(normal);
        employeeBtn.setStyle(normal);
        customerBtn.setStyle(normal);

        //Hover effect
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(hover));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(normal));
        
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle(hover));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle(normal));        

        adminBtn.setOnMouseEntered(e -> adminBtn.setStyle(hover));
        adminBtn.setOnMouseExited(e -> adminBtn.setStyle(normal));
        
        employeeBtn.setOnMouseEntered(e -> employeeBtn.setStyle(hover));
        employeeBtn.setOnMouseExited(e -> employeeBtn.setStyle(normal));
        
        customerBtn.setOnMouseEntered(e -> customerBtn.setStyle(hover));
        customerBtn.setOnMouseExited(e -> customerBtn.setStyle(normal));
        

        //Redirect on click
        loginBtn.setOnAction(e -> PageManager.setScene(new LoginPage().getScene()));

        adminBtn.setOnAction(e -> PageManager.setScene(new AdminMainPage().getScene()));
        employeeBtn.setOnAction(e -> PageManager.setScene(new EmployeeMainPage().getScene()));
        customerBtn.setOnAction(e -> PageManager.setScene(new CustomerMainPage().getScene()));
        registerBtn.setOnAction(e -> PageManager.setScene(new CustomerRegisterPage().getScene()));
        
        
        VBox buttonBox = new VBox(15, loginBtn, adminBtn, employeeBtn, customerBtn, registerBtn);
        buttonBox.setAlignment(Pos.CENTER);
        root.setCenter(buttonBox);

        scene = new Scene(root, 600, 450);
    }

    public Scene getScene() {
        return scene;
    }
}
