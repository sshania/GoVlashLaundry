package employee;

import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.ResultSet;

import DataBase.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.PageManager;

public class ViewEmployeePage {
	
    public Scene getScene() {

        TableView<ObservableList<String>> table = new TableView<>();
        Button btnBack = new Button("Back to Menu");

        btnBack.setOnAction(e -> {
            EmployeeMainPage menu = new EmployeeMainPage();
            PageManager.setScene(menu.getScene());
        });

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        try {
            Connection con = DBConnection.getInstance().getConnection();
            ResultSet rs = con.createStatement().executeQuery(
                "SELECT * FROM users WHERE role IN ('Admin', 'Laundry Staff', 'Receptionist')"
            );

            // Jika tidak ada data
            if (!rs.isBeforeFirst()) {
                BorderPane emptyPane = new BorderPane();
                emptyPane.setCenter(new Label("No employees found!"));

                HBox bottom = new HBox(btnBack);
                bottom.setAlignment(Pos.CENTER);
                bottom.setStyle("-fx-padding: 10;");

                emptyPane.setBottom(bottom);
                return new Scene(emptyPane, 650, 400);
            }

            int columnCount = rs.getMetaData().getColumnCount();

            // Buat kolom dinamis
            for (int i = 0; i < columnCount; i++) {
                final int colIndex = i;

                TableColumn<ObservableList<String>, String> col =
                        new TableColumn<>(rs.getMetaData().getColumnName(i + 1));

                col.setCellValueFactory(param ->
                        new javafx.beans.property.SimpleStringProperty(
                                param.getValue().get(colIndex)));

                table.getColumns().add(col);
            }

            // Isi data
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            table.setItems(data);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // Layout
        BorderPane pane = new BorderPane();
        pane.setCenter(table);

        HBox bottom = new HBox(btnBack);
        bottom.setAlignment(Pos.CENTER);
        bottom.setStyle("-fx-padding: 10;");

        pane.setBottom(bottom);

        return new Scene(pane, 650, 400);
    }

}