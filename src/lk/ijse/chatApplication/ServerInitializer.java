package lk.ijse.chatApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.chatApplication.controller.ServerFormController;

import java.io.IOException;
import java.net.URL;

public class ServerInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ServerFormController serverFormController = new ServerFormController();
        serverFormController.startServer();
    }
}
