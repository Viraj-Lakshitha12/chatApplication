package lk.ijse.chatApplication;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Socket socket2;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String clientUserName;

    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    public ClientHandler(Socket socket, Socket socket2) {
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
        IncomingImages();
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
    private void IncomingImages() {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {

                    int readInt = dataInputStream2.readInt();
                    byte[] bytes = new byte[readInt];
                    dataInputStream2.readFully(bytes,0,readInt);
                    sendMessageClientImage(this,readInt,bytes);

                } catch (IOException e) {

                }
            }
        }).start();
    }

    private void sendMessageClientImage(ClientHandler client, int i, byte[] bytes) {
        for (ClientHandler clientHandler1 : clients) {
            try {
                if (clientHandler1 != client) {
                    // clientHandler.dataOutputStream.writeUTF(clientUserName);
                    //   clientHandler.dataOutputStream.flush();
                    clientHandler1.dataOutputStream2.writeInt(i);
                    clientHandler1.dataOutputStream2.write(bytes);
                }

            } catch (IOException e) {

            }
        }
    }
    public void removeClient(){
        clients.remove(this);
        sendMessageClientEnter(this, " \nhas left the chat ! ");

    }
    private void sendMessageClientEnter(ClientHandler client, String messageTo) {
        for (ClientHandler clientHandler1 : clients) {
            try {
                if (clientHandler1 != client) {
                    clientHandler1.dataOutputStream.writeUTF(clientUserName + " : " + messageTo);
                    clientHandler1.dataOutputStream.flush();
                }
            } catch (IOException e) {
            }
        }
    }

    public void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream,DataInputStream dataInputStream2,DataOutputStream dataOutputStream2){
        removeClient();
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
