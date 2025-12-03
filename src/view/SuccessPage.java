package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.PageManager;

public class SuccessPage {

    private Scene scene;

    // Tambahkan parameter returnScene agar bisa balik ke halaman mana saja
    public SuccessPage(String message, Scene returnScene) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);

        Label msgLabel = new Label(message);
        msgLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-padding: 10 20; -fx-background-color: #E9E3DF; -fx-font-size: 14px;");

        backBtn.setOnAction(e -> PageManager.setScene(returnScene));

        layout.getChildren().addAll(msgLabel, backBtn);
        scene = new Scene(layout, 450, 450);
    }

    public Scene getScene() {
        return scene;
    }
}
