package employee;

import controller.EmployeeController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import main.PageManager;

public class AddEmployeeFormPage {

    public Scene getScene() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25));
        grid.setHgap(12);
        grid.setVgap(12);

        Label lblName = new Label("Name:");
        TextField tfName = new TextField();

        Label lblEmail = new Label("Email:");
        TextField tfEmail = new TextField();

        Label lblPass = new Label("Password:");
        PasswordField tfPass = new PasswordField();

        Label lblConfirm = new Label("Confirm Password:");
        PasswordField tfConfirm = new PasswordField();

        Label lblGender = new Label("Gender:");
        ComboBox<String> cbGender = new ComboBox<>();
        cbGender.getItems().addAll("Male", "Female");

        Label lblDob = new Label("Date Of Birth:");
        DatePicker dpDob = new DatePicker();

        Label lblRole = new Label("Role:");
        ComboBox<String> cbRole = new ComboBox<>();
        cbRole.getItems().addAll("Admin", "Laundry Staff", "Receptionist");

        Button btnSubmit = new Button("Submit");
        Button btnBack = new Button("Back");
        Button btnView = new Button("View Employees");
        
        Label lblMsg = new Label();
        
        Label lblHeader = new Label("Add Employee Form");
        lblHeader.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // seeting button submit untuk masukin employeenua ke dalam db lewa controller
        btnSubmit.setOnAction(e -> {
	            String validation = EmployeeController.validateEmployee(
	                    tfName.getText(),
	                    tfEmail.getText(),
	                    tfPass.getText(),
	                    tfConfirm.getText(),
	                    cbGender.getValue(),
	                    dpDob.getValue(),
	                    cbRole.getValue()
	            );

            if (!validation.equals("OK")) {
                lblMsg.setText(validation);
                lblMsg.setStyle("-fx-text-fill: red;");
                return;
            }
            
            java.sql.Date dob = null;
            if (dpDob.getValue() != null) {
                dob = java.sql.Date.valueOf(dpDob.getValue()); 
            }

            boolean success = EmployeeController.insert(
                    tfName.getText(),
                    tfEmail.getText(),
                    tfPass.getText(),
                    cbGender.getValue(),
                    dob,
                    cbRole.getValue()
            );

            if (success) {
                lblMsg.setText("Employee added successfully!");
                lblMsg.setStyle("-fx-text-fill: green;");

                // buat clear pas selese
                tfName.clear();
                tfEmail.clear();
                tfPass.clear();
                tfConfirm.clear();
                dpDob.setValue(null);
                cbGender.setValue(null);
                cbRole.setValue(null);

            } else {
                lblMsg.setText("Error while inserting employee!");
                lblMsg.setStyle("-fx-text-fill: red;");
            }
        });
        
        btnBack.setOnAction(e ->
        PageManager.setScene(new EmployeeMainPage().getScene())
		);
		
		btnView.setOnAction(e ->
		        PageManager.setScene(new ViewEmployeePage().getScene())
		);

        // seeting UI nya, yaitu grid
		grid.add(btnBack, 0, 0);
		grid.add(btnView, 1, 0);
		
		grid.add(lblHeader, 0, 1, 2, 1); 

		grid.add(lblName, 0, 2);
		grid.add(tfName, 1, 2);

		grid.add(lblEmail, 0, 3);
		grid.add(tfEmail, 1, 3);

		grid.add(lblPass, 0, 4);
		grid.add(tfPass, 1, 4);

		grid.add(lblConfirm, 0, 5);
		grid.add(tfConfirm, 1, 5);

		grid.add(lblGender, 0, 6);
		grid.add(cbGender, 1, 6);

		grid.add(lblDob, 0, 7);
		grid.add(dpDob, 1, 7);

		grid.add(lblRole, 0, 8);
		grid.add(cbRole, 1, 8);

		grid.add(btnSubmit, 1, 9);
		grid.add(lblMsg, 1, 10);

        return new Scene(grid, 450, 520);
    }
}