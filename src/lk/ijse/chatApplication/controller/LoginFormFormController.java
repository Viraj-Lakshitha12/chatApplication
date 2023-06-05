package lk.ijse.chatApplication.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginFormFormController {
    public Button btnLogInAction;
    public JFXTextField txtUserName;

    String name="";
    public void  initialize(){
        name= txtUserName.getText();
    }
    public String getUserName(){
        return name;
    }

    public void btnLogInAction(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        URL resource = getClass().getResource("../view/ClientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        primaryStage.setScene(new Scene(load));
        primaryStage.show();
    }
}
