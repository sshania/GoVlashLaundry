package view;

import java.util.List;

import controller.ServiceController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.PageManager;
import model.Service;

public class ServiceView {

    private Scene scene;
    private BorderPane mainPane;

    private TableView<Service> table;
    private ObservableList<Service> serviceList;

    private Button addBtn, editBtn, deleteBtn, viewAllBtn, backBtn;

    private ServiceController sc;

    public ServiceView() {
        sc = new ServiceController();
        initialize();
        loadServiceData();
    }

    private void initialize() {

        mainPane = new BorderPane();
        scene = new Scene(mainPane, 600, 450);

        //Table
        table = new TableView<>();

        TableColumn<Service, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));

        TableColumn<Service, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

        TableColumn<Service, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

        TableColumn<Service, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

        TableColumn<Service, Integer> durationCol = new TableColumn<>("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("serviceDuration"));

        table.getColumns().addAll(idCol, nameCol, descCol, priceCol, durationCol);


        //Buttons
        addBtn = new Button("Add Service");
        editBtn = new Button("Edit Service");
        deleteBtn = new Button("Delete Service");
        viewAllBtn = new Button("View All Service");
        backBtn = new Button("Back");

        HBox buttonBox = new HBox(10, addBtn, editBtn, deleteBtn, viewAllBtn, backBtn);
        buttonBox.setPadding(new Insets(10));

        mainPane.setCenter(table);
        mainPane.setBottom(buttonBox);

        //Buttons action
        addBtn.setOnAction(e -> PageManager.setScene(new AddServicePage().getScene()));

        editBtn.setOnAction(e -> {
            Service selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                PageManager.setScene(new EditServicePage(selected).getScene());
            }
        });

        deleteBtn.setOnAction(e -> PageManager.setScene(new DeleteServicePage().getScene()));
        viewAllBtn.setOnAction(e -> PageManager.setScene(new ViewAllServicesPage().getScene()));
        backBtn.setOnAction(e -> PageManager.setScene(new Menu().getScene()));
    }

    private void loadServiceData() {
        List<Service> list = sc.getAllServices();
        serviceList = FXCollections.observableArrayList(list);
        table.setItems(serviceList);
    }

    public Scene getScene() {
        return scene;
    }
}
