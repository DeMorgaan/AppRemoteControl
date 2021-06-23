package com.example.flightgearapp.Model;

//This class is the client side of the TCP-socket communication,
//The server-side is the FlightGear simulator itself.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


//Initializing members
public class TcpClient {
    private Socket socket; //A socket to communicate with the server.
    private PrintWriter printWriter; //Output streamer to write *to* server.
    private String ipAddress; //The ip address
    private int port; //The port number
    private boolean ipIsValid;

    //Constructor to assign values.
    public TcpClient(String ip, int port) {
        this.ipAddress = ip;
        this.port = port;
            connect();
    }

    public void connect() {
        final Thread connectThread = new Thread() {
            @Override
            public void run() {
                try {
                    InetAddress inetAddress = InetAddress.getByName(ipAddress);



                    socket = new Socket(inetAddress, port);

//                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter = new PrintWriter(socket.getOutputStream(), true);

//                    sendMessage();
//                    setValue(printWriter, socket);

                    if(socket.isConnected()){
                        System.out.println("Connected");
                    }
                    else {
                        System.out.println("Not Connected");
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connectThread.start();
    }

    //Sending the message to the server
    public void sendMessage(String path, float value){
        final Thread thread = new Thread() {
            @Override
            public void run() {

                final String message = path + value + "\r\n";
                printWriter.print(message);

                printWriter.flush();

            }
        };thread.start();
    }

    public void disconnect() {
        printWriter.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
