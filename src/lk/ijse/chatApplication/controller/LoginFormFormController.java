package lk.ijse.chatApplication.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lk.ijse.chatApplication.Clients;

import java.io.IOException;
import java.net.URL;

public class LoginFormFormController {
    public Button btnLogInAction;
    public JFXTextField txtUserName;

    public void btnLogInAction(ActionEvent actionEvent) throws IOException {
        ClientFormController.username=txtUserName.getText();
        Stage primaryStage = new Stage();
        URL resource = getClass().getResource("../view/ClientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        primaryStage.setScene(new Scene(load));
        primaryStage.setTitle("Chat Application");
        primaryStage.show();
        txtUserName.clear();
        txtUserName.requestFocus();
    }

    public void txtLoginOnAction(ActionEvent actionEvent) throws IOException {
        btnLogInAction(actionEvent);
    }
}
