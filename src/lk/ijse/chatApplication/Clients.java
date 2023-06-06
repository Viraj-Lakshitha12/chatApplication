package lk.ijse.chatApplication;

import lk.ijse.chatApplication.controller.ClientFormController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Clients implements Runnable {
    private Socket socket;

    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String clientUserName;

    public static ArrayList<Clients> clients = new ArrayList<>();
    public Clients(Socket socket) {
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());


            clientUserName = dataInputStream.readUTF();
            sendMessageClientEnter(this," has entered the chat ! ");
            clients.add(this);

        } catch (IOException e) {
            closeEverything(socket,dataInputStream,dataOutputStream);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        IncomingMessage();
    }

    private void IncomingMessage() {
        while (socket.isConnected()) {
            try {
                sendMessageClientEnter(this, dataInputStream.readUTF());
            } catch (IOException e) {
                closeEverything(socket,dataInputStream,dataOutputStream);
                break;
            }
        }
    }
    public void removeClientHandler(){
        clients.remove(this);
        sendMessageClientEnter(this, " has left the chat ! ");

    }
    private void sendMessageClientEnter(Clients client, String messageTo) {
        for (Clients clientHandler : clients) {
            try {
                if (clientHandler != client) {
                    clientHandler.dataOutputStream.writeUTF(clientUserName + " : " + messageTo);
                    clientHandler.dataOutputStream.flush();

                }
            } catch (IOException e) {
                closeEverything(socket,dataInputStream,dataOutputStream);
            }
        }
    }

    public void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream){
        removeClientHandler();
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
}
