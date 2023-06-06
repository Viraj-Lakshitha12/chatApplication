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
import lk.ijse.chatApplication.Clients;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    final int PORT = 1234;
    static String username;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    private final LoginFormFormController loginFormFormController = new LoginFormFormController();

    String  message ="";
    public void initialize(){
        try {
            socket = new Socket("localhost",PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(() ->{
            try {
                while (true){
                    message = dataInputStream.readUTF();
                    Text text = new Text(message);
                    TextFlow textFlow = new TextFlow(text);
                    textArea.appendText("\n"+textFlow);
                }
            } catch (IOException e) {
                closeEverything(socket,dataInputStream,dataOutputStream);
                e.printStackTrace();
            }

        }).start();

    }
    public void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream){
        try {
            if (dataInputStream !=null){
                dataInputStream.close();
            }
            if (dataOutputStream!=null){
                dataOutputStream.close();
            }

            if (socket !=null){
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void btnSendMessageOnAction(ActionEvent actionEvent) {
        String messageToSend = txtSendMessage.getText().trim();
        try {
            dataOutputStream.writeUTF(messageToSend);
            textArea.appendText("\nMe :"+messageToSend);
            dataOutputStream.flush();
            txtSendMessage.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
