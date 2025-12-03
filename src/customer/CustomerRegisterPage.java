package customer;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.PageManager;

import java.sql.Date;

public class CustomerRegisterPage {

    private Scene scene;
    private BorderPane mainLayout;
    private GridPane gridLayout;

    private Label title;
    private Label lblUsername, lblEmail, lblPassword, lblConfirm, lblGender, lblDob, lblMsg;
    private TextField tfUsername, tfEmail, tfDob;
    private PasswordField pfPassword, pfConfirm;
    private ComboBox<String> cbGender;
    private Button btnRegister, btnBack;

    private UserController uc = new UserController();

    public CustomerRegisterPage() {
        initComponents();

        mainLayout.setTop(title);
        mainLayout.setCenter(gridLayout);

        // add components to grid
        gridLayout.add(lblUsername, 0, 0);
        gridLayout.add(tfUsername, 1, 0);

        gridLayout.add(lblEmail, 0, 1);
        gridLayout.add(tfEmail, 1, 1);

        gridLayout.add(lblPassword, 0, 2);
        gridLayout.add(pfPassword, 1, 2);

        gridLayout.add(lblConfirm, 0, 3);
        gridLayout.add(pfConfirm, 1, 3);

        gridLayout.add(lblGender, 0, 4);
        gridLayout.add(cbGender, 1, 4);

        gridLayout.add(lblDob, 0, 5);
        gridLayout.add(tfDob, 1, 5);

        gridLayout.add(btnRegister, 1, 6);
        gridLayout.add(lblMsg, 1, 7);
//        gridLayout.add(btnBack, 0, 6);

        scene = new Scene(mainLayout, 500, 400);

        // tombol register
        btnRegister.setOnAction(e -> {
            String username = tfUsername.getText();
            String email = tfEmail.getText();
            String password = pfPassword.getText();
            String confirm = pfConfirm.getText();
            String gender = cbGender.getValue();
            String dobStr = tfDob.getText();

            try {
                Date dob = Date.valueOf(dobStr); // format YYYY-MM-DD
                String result = uc.register(username, email, password, confirm, gender, dob);
                if (result.equals(" ")) {
                    lblMsg.setText("Registration successful!");
                    lblMsg.setStyle("-fx-text-fill: green;");
                } else {
                    lblMsg.setText(result);
                    lblMsg.setStyle("-fx-text-fill: red;");
                }
            } catch (IllegalArgumentException ex) {
                lblMsg.setText("DOB must be in YYYY-MM-DD format");
                lblMsg.setStyle("-fx-text-fill: red;");
            }
        });

    }

    public Scene getScene() {
        return scene;
    }

    private void initComponents() {
        mainLayout = new BorderPane();
        gridLayout = new GridPane();
        gridLayout.setPadding(new Insets(20));
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);
        gridLayout.setAlignment(Pos.CENTER);

        title = new Label("Customer Registration");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(20,0,20,0));

        lblUsername = new Label("Username");
        lblEmail = new Label("Email");
        lblPassword = new Label("Password");
        lblConfirm = new Label("Confirm Password");
        lblGender = new Label("Gender");
        lblDob = new Label("Date of Birth (YYYY-MM-DD)");
        lblMsg = new Label();

        tfUsername = new TextField();
        tfEmail = new TextField();
        tfDob = new TextField();

        pfPassword = new PasswordField();
        pfConfirm = new PasswordField();

        ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
        cbGender = new ComboBox<>(genders);

        btnRegister = new Button("Register");
        btnBack = new Button("Back");
    }
}
