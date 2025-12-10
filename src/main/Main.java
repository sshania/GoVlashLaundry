package main;

import DataBase.Migration;
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainPage;

public class Main extends Application {

    public static void main(String[] args) {
//    	new Migration();
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        PageManager.setStage(primaryStage);

        MainPage home = new MainPage();
        PageManager.setScene(home.getScene());

        primaryStage.setTitle("GoVlash Laundry System");
        primaryStage.show();
    }
}
