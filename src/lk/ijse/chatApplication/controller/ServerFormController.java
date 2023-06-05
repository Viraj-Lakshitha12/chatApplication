package lk.ijse.chatApplication.controller;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    ServerSocket serverSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket socket;
    String s = "";

    public void startServer(){
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(3125);
                System.out.println("Server Start");
                socket = serverSocket.accept();
                System.out.println("\nClient Accept");
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    while (true) {
                        s = dataInputStream.readUTF();
                        System.out.println(s);
                    }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


    public void closeServer(){
        if (serverSocket == null){
            try {
                dataOutputStream.close();
                dataInputStream.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
