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
    Socket socket2;
    final int PORT = 1234;
    final int PORT2 = 1235;
    static String username;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;
    String  message ="";
    public void initialize() throws IOException {
        socket = new Socket("localhost",PORT);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(username);

        socket2 = new Socket("localhost",PORT2);
        dataInputStream2 = new DataInputStream(socket2.getInputStream());
        dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());

        new Thread(() ->{
            try {
                while (true){
                    message = dataInputStream.readUTF();
                    textArea.appendText("\n"+message);
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
