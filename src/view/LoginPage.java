package view;

import admin.AdminMainPage;
import controller.UserController;
import customer.CustomerMainPage;
import customer.CustomerRegisterPage;
import employee.EmployeeMainPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.PageManager;
import main.Session;
import model.User;

public class LoginPage {
	
	/* NOTES UNTUK LOGIN PERTAMA KALI:
	 * 	Admin default sudah tersedia di database:
	 *  email: admin@govlash.com
	 *  password: admin123
							*/
	
    private Scene scene;

    public LoginPage() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label lblEmail = new Label("Email:");
        TextField tfEmail = new TextField();
        
        Label lblPass = new Label("Password:");
        PasswordField pfPass = new PasswordField();

        grid.add(lblEmail, 0, 0);
        grid.add(tfEmail, 1, 0);
        
        grid.add(lblPass, 0, 1);
        grid.add(pfPass, 1, 1);

        Button btnLogin = new Button("Login");
        Button btnBack = new Button("Back");
        
        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: red;");

        Hyperlink linkRegister = new Hyperlink("Don't have an account? Register here");
        linkRegister.setStyle("-fx-border-color: transparent; -fx-padding: 0;");
        linkRegister.setOnAction(e -> PageManager.setScene(new CustomerRegisterPage().getScene()));

        VBox loginGroup = new VBox(10, btnLogin, linkRegister);
        loginGroup.setAlignment(Pos.CENTER);

        //validasi login untuk role
        btnLogin.setOnAction(e -> {
            String email = tfEmail.getText();
            String pass = pfPass.getText();

            User u = UserController.login(email, pass);

            if (u != null) {
                Session.setUser(u);
                
                // Cek role dan redirect ke halaman masing-masing
                String role = u.getRole();
                
                if (role.equalsIgnoreCase("Admin")) {
                    PageManager.setScene(new AdminMainPage().getScene());
                    
                } else if (role.equalsIgnoreCase("Customer")) {
                    PageManager.setScene(new CustomerMainPage().getScene());
                    
                } else if (role.equalsIgnoreCase("Laundry Staff")) {
                    PageManager.setScene(new LaundryStaffMainPage().getScene());
                    
                } else if (role.equalsIgnoreCase("Receptionist")) {
                    PageManager.setScene(new ReceptionistMainPage().getScene());
                    
                } else {
                    lblError.setText("Error: Unknown role (" + role + ")");
                }
                
            } else {
                lblError.setText("Invalid email or password!");
            }
        });

        btnBack.setOnAction(e -> PageManager.setScene(new MainPage().getScene()));

        root.getChildren().addAll(title, grid, loginGroup, btnBack, lblError);
        
        scene = new Scene(root, 550, 450);
    }

    public Scene getScene() {
        return scene;
    }
}