package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml")); // загрузка xml, разметка для нашего визуального проекта
        Parent root = loader.load();
        Controller controller = loader.getController(); // наш код с действиями
        //controller.init();
        primaryStage.setTitle("Сортировка"); // имя окна(сцена)
        primaryStage.setScene(new Scene(root, 600, 400)); // размеры окна
        primaryStage.show(); // отобразить окно
    }


    public static void main(String[] args) {
        launch(args);
    }
}