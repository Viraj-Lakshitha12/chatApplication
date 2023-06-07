package lk.ijse.chatApplication;

import lk.ijse.chatApplication.controller.ClientFormController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Clients implements Runnable {
    private Socket socket;
    private Socket socket2;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String clientUserName;

    public static ArrayList<Clients> clients = new ArrayList<>();
    public Clients(Socket socket,Socket socket2) {
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());


            this.socket2 = socket2;
            this.dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());
            this.dataInputStream2 = new DataInputStream(socket2.getInputStream());

            clientUserName = dataInputStream.readUTF();
            sendMessageClientEnter(this," has entered the chat ! ");
            clients.add(this);

        } catch (IOException e) {
            closeEverything(socket,dataInputStream,dataOutputStream,dataInputStream2,dataOutputStream2);
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
                closeEverything(socket,dataInputStream,dataOutputStream,dataInputStream2,dataOutputStream2);
                break;
            }
        }
    }
    public void removeClientHandler(){
        clients.remove(this);
        sendMessageClientEnter(this, " \nhas left the chat ! ");

    }
    private void sendMessageClientEnter(Clients client, String messageTo) {
        for (Clients clients1 : clients) {
            try {
                if (clients1 != client) {
                    clients1.dataOutputStream.writeUTF(clientUserName + " : " + messageTo);
                    clients1.dataOutputStream.flush();
                }
            } catch (IOException e) {
            }
        }
    }

    public void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream,DataInputStream dataInputStream2,DataOutputStream dataOutputStream2){
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
}
