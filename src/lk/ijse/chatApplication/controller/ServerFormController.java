package lk.ijse.chatApplication.controller;


import lk.ijse.chatApplication.Clients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    private ServerSocket serverSocket;
    private ServerSocket serverSocket2;

    public ServerFormController(ServerSocket serverSocket,ServerSocket serverSocket2){
        this.serverSocket =serverSocket;
        this.serverSocket2 =serverSocket2;
    }
    public void startServer(){
                while (true){
                    try {
                        Clients clients = new Clients(serverSocket.accept(),serverSocket2.accept());
                        Thread thread = new Thread(clients);
                        thread.start();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
//                dataInputStream = new DataInputStream(socket.getInputStream());
//                dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }
}
