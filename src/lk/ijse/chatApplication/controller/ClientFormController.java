package lk.ijse.chatApplication.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientFormController {

    public TextField txtSendMessage;
    public TextArea textArea;
    public TextArea meSendMessageTextArea;
    public AnchorPane pane;
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
    byte b[];
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


        new Thread(() -> {
            try {
                while (true){
                    // message= dataInputStream.readUTF();
                    int readInt = dataInputStream2.readInt();
                    byte[] bytes = new byte[readInt];
                    dataInputStream2.readFully(bytes,0,readInt);
                    FileOutputStream fileOutputStream = new FileOutputStream("F:\\Group Chat Java Socket Application Caurce Work\\Group Chat Java Socket Application Caurce Work\\src\\lk\\ijse\\chatApplication\\assets\\images\\sendImage.png");
                    fileOutputStream.write(bytes);

                    ImageView imageView = new ImageView("file:F:\\Group Chat Java Socket Application Caurce Work\\Group Chat Java Socket Application Caurce Work\\src\\lk\\ijse\\chatApplication\\assets\\images\\sendImage.png");
                    imageView.preserveRatioProperty().set(true);
                    imageView.setFitHeight(250);
                    imageView.setFitWidth(250);
//                    textArea.appendText(imageView);

                }
            } catch (IOException exception) {
                exception.printStackTrace();
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

    public void btnSendImageOnAction(ActionEvent actionEvent) {
        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(pane.getScene().getWindow());
        System.out.println(file.getPath());

        ImageView imageView = new ImageView("file:" + file.getPath());
        imageView.preserveRatioProperty().set(true);
        imageView.setFitHeight(250);
        imageView.setFitWidth(250);
        Text text = new Text("Me : \n");
        TextFlow textFlow = new TextFlow(text,imageView);
        textArea.appendText(textFlow.toString());

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            dataOutputStream2.writeInt((int) file.length());
            dataOutputStream2.write(bytes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
