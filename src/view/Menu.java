package view;

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

public class Menu {

    private Scene scene;

    public Menu() {
    	BorderPane root = new BorderPane();
    	root.setPadding(new Insets(20));
    	root.setStyle("-fx-background-color: #E9E5DF");

    	Label title = new Label("Service Management Options");
    	title.setFont(Font.font("Georgia", FontWeight.BOLD, 30));
    	title.setStyle("-fx-text-fill: black;");

        Button addBtn = new Button("Add Service");
        Button editBtn = new Button("Edit Service");
        Button deleteBtn = new Button("Delete Service");
        Button viewAllBtn = new Button("View All Services");
        Button backBtn = new Button("Back");

        addBtn.setOnAction(e -> PageManager.setScene(new AddServicePage().getScene()));
        editBtn.setOnAction(e -> PageManager.setScene(new ServiceView().getScene()));
        deleteBtn.setOnAction(e -> PageManager.setScene(new DeleteServicePage().getScene()));
        viewAllBtn.setOnAction(e -> PageManager.setScene(new ViewAllServicesPage().getScene()));
        backBtn.setOnAction(e -> PageManager.setScene(new MainPage().getScene()));

        VBox box = new VBox(20, title, addBtn, editBtn, deleteBtn, viewAllBtn,backBtn);
        box.setAlignment(Pos.CENTER);
        root.setCenter(box);

        scene = new Scene(root, 600, 450);
    }

    public Scene getScene() {
        return scene;
    }
}
