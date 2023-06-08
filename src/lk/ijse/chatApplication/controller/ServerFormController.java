package lk.ijse.chatApplication.controller;


import lk.ijse.chatApplication.Clients;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    private ServerSocket serverSocket;
    private ServerSocket serverSocket2;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public ServerFormController(ServerSocket serverSocket,ServerSocket serverSocket2){
        this.serverSocket =serverSocket;
        this.serverSocket2 =serverSocket2;

//
//        try {
//            socket=serverSocket.accept();
//            dataInputStream = new DataInputStream(socket.getInputStream());
//            dataOutputStream = new DataOutputStream(socket.getOutputStream());
//            String req = dataInputStream.readUTF();
//            File f=new File(req);
//            FileInputStream fin=new FileInputStream(f);
//
//            int c;
//            int sz=(int)f.length();
//            byte b[]=new byte [sz];
//            dataOutputStream.writeUTF(String.valueOf(new Integer(sz)));
//            dataOutputStream.flush();
//            int j=0;
//            while ((c = fin.read()) != -1) {
//
//                b[j]=(byte)c;
//                j++;
//            }
//
//            dataOutputStream.write(b,0,b.length);
//            dataOutputStream.flush();
////            System.out.println ("Size "+sz);
////            System.out.println ("buf size"+ss.getReceiveBufferSize());
//            dataOutputStream.writeUTF(new String("Ok"));
//            dataOutputStream.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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

    }
}
