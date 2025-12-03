package employee;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.PageManager;
import view.ServiceView;

public class EmployeeMainPage {

    private Scene scene;

    public EmployeeMainPage() {

    	VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(35));
        root.setStyle("-fx-background-color: #f2e9d8;");

        Label title = new Label("Employee Dashboard");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: #5b4636;");

        Button btnAddEmployee = new Button("Add Employee");
        Button btnViewEmployee = new Button("View Employees");
        Button btnViewServices = new Button("View Services");
        Button btnExit = new Button("Exit");

        styleBtn(btnAddEmployee);
        styleBtn(btnViewEmployee);
        styleBtn(btnViewServices);
        styleBtn(btnExit);

        btnAddEmployee.setOnAction(e -> 
            PageManager.setScene(new AddEmployeeFormPage().getScene())
        );

        btnViewEmployee.setOnAction(e -> 
            PageManager.setScene(new ViewEmployeePage().getScene())
        );

        btnViewServices.setOnAction(e -> 
            PageManager.setScene(new ServiceView().getScene())
        );

        btnExit.setOnAction(e -> System.exit(0));


        VBox menu = new VBox(15, btnAddEmployee, btnViewEmployee, btnViewServices, btnExit);
        menu.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, menu);

        scene = new Scene(root, 600, 450);
    }

    private void styleBtn(Button btn) {
        btn.setPrefWidth(200);
        btn.setStyle(
                "-fx-background-color: #C4A484;" +
                "-fx-font-size: 15;" +
                "-fx-padding: 10 15;" +
                "-fx-background-radius: 8;"
        );
    }

    public Scene getScene() {
        return scene;
    }
}
