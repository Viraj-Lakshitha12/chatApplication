package lk.ijse.chatApplication.controller;


import lk.ijse.chatApplication.Clients;

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
                serverSocket = new ServerSocket(1234);
                System.out.println("Server Start");

                while (true){
                    socket = serverSocket.accept();
                    System.out.println("\nClient Accept");

                    Clients clients = new Clients(serverSocket.accept());
                    Thread clientThread = new Thread(clients);
                    clientThread.start();
                }
//                dataInputStream = new DataInputStream(socket.getInputStream());
//                dataOutputStream = new DataOutputStream(socket.getOutputStream());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


}
