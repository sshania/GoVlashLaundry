package main;

import java.util.Stack;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageManager {

    private static Stage stage;
    private static Stack<Scene> history = new Stack<>();

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void setScene(Scene newScene) {

        Scene current = stage.getScene();

        if (current != null) {
            history.push(current);   // simpan halaman sebelumnya
        }

        stage.setScene(newScene);     // ganti FULL scene
        stage.show();
    }

    public static void returnPage() {
        if (!history.isEmpty()) {
            stage.setScene(history.pop());
            stage.show();
        }
    }
}
