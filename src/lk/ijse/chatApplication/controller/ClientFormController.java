package lk.ijse.chatApplication.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.awt.ComponentOrientation.*;

public class ClientFormController {

    public TextField txtSendMessage;
    public TextArea textArea;
    @FXML
    private Pane topPane;

    @FXML
    private Label lblNameText;

    @FXML
    private Pane bottomPane;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    private final LoginFormFormController loginFormFormController = new LoginFormFormController();

    String message;
    public void initialize(){
        new Thread(() ->{
            try {
                socket = new Socket("localhost",3125);
                System.out.println("Client Start");
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                message ="";
                 String userName = loginFormFormController.getUserName();
                System.out.println(userName);
                while (!message.equals("end")){
                    textArea.appendText("\n"+userName);
                    message = dataInputStream.readUTF();
                    textArea.appendText("\n"+message);
                }
                socket.close();
                dataOutputStream.close();
                dataInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void btnSendMessageOnAction(ActionEvent actionEvent) {
        String messageToSend = txtSendMessage.getText().trim();
        try {
            dataOutputStream.writeUTF(messageToSend);
//            textArea.setPadding(new Insets(5,10,5,10));
            textArea.appendText("\nMe :"+messageToSend);
            dataOutputStream.flush();
            txtSendMessage.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
