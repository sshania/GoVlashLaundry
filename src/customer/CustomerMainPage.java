package customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.PageManager;
import view.MainPage;
import view.TransactionHistoryPage;

public class CustomerMainPage {
    public Scene getScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(35));
        root.setStyle("-fx-background-color: #f5e7e4;");
        
        Label title = new Label("Customer Dashboard");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: #6e3938;");

        // Tombol Navigasi
        Button btnTransaction = new Button("Create Transaction");
        Button btnTHistory = new Button("Transaction History");
        Button btnNotif = new Button("notification(blm ada)");
        Button logoutBack = new Button("Logout");
        
        styleBtn(btnTransaction);
        styleBtn(btnTHistory);
        styleBtn(btnNotif);
        styleBtn(logoutBack);

        // buttons redirect
        btnTransaction.setOnAction(e -> PageManager.setScene(new CustomerViewService().getScene()));
        btnTHistory.setOnAction(e -> PageManager.setScene(new TransactionHistoryPage().getScene()));
//        btnNotif.setOnAction(e -> PageManager.setScene(new ().getScene()));
        logoutBack.setOnAction(e -> PageManager.setScene(new MainPage().getScene()));

        root.getChildren().addAll(title, btnTransaction, btnTHistory, btnNotif, logoutBack);
        return new Scene(root, 600, 450);
    }
    
    private void styleBtn(Button btn) {
        btn.setPrefWidth(200);
        btn.setStyle(
                "-fx-background-color: #e3bfa6;" +
                "-fx-font-size: 15;" +
                "-fx-padding: 10 15;" +
                "-fx-background-radius: 8;"
        );
    }
    
}