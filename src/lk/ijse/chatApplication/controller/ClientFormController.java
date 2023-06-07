package lk.ijse.chatApplication.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController {

    public TextField txtSendMessage;
    public TextArea textArea;
    public TextArea meSendMessageTextArea;
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
        txtSendMessage.requestFocus();
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
                closeEverything(socket,dataInputStream,dataOutputStream,dataInputStream2,dataOutputStream2);
                e.printStackTrace();
            }

        }).start();

    }
    public void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream,DataInputStream dataInputStream2, DataOutputStream dataOutputStream2){
        try {
            if (dataInputStream !=null){
                dataInputStream.close();
            }
            if (dataOutputStream!=null){
                dataOutputStream.close();
            }
            if (dataInputStream2 !=null){
                dataInputStream2.close();
            }
            if (dataOutputStream2!=null){
                dataOutputStream2.close();
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
            textArea.appendText("\n                                                                                                                  " +
            "                                      Me : "+messageToSend);
            dataOutputStream.flush();
            txtSendMessage.clear();
            txtSendMessage.requestFocus();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSendMessageOnAction(ActionEvent actionEvent) {
        btnSendMessageOnAction(actionEvent);
    }
}
