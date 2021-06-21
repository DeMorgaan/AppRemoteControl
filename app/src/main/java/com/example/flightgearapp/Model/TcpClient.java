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
//    private OutputStream outputStream; //An output streamer to write *to* server.
    private Map<String, String> paths; //A key:value container to direct to the right command
    private PrintWriter printWriter; //Output streamer to write *to* server.
    private String ipAddress; //The ip address
    private int port; //The port number

    //Constructor to assign values.
    public TcpClient(String ip, int port) {
        this.paths = new HashMap<>();
        this.paths.put("AILERON", "/controls/flight/aileron");
        this.paths.put("ELEVATOR", "/controls/flight/elevator");
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
                        System.out.println("Connected Fucker - now it will work!");
                    }

                    else if (!socket.isConnected()) {
                        System.out.println("Fucker did not connected");
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connectThread.start();
    }

    public void sendMessage(float value){

        final String message = "set /controls/flight/aileron "+ value +"\r\n";
        final String message2 = "set /controls/flight/aileron "+ 0.1 +"\r\n";
        final String message3 = "set /controls/engines/current-engine/throttle" + 0.9 + "\r\n";

        final Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Fucker is connected");
                String msg = message;
                printWriter.print(msg);
                String msg2 = message2;
                String msg3 = message3;
                printWriter.print(msg3);


                printWriter.print("set /controls/flight/aileron" + 0.5 + "\r\n");
                printWriter.print("set /controls/engines/current-engine/throttle" + 0.435 + "\r\n");
                printWriter.print("set /controls/flight/rudder" + 0.65 + "\r\n");

                printWriter.flush();

            }
        };thread.start();
    }

//    public void setValue(PrintWriter printWriter, Socket socket) {
//        System.out.println("Inside setValue");
//
//        final Thread msgThread = new Thread() {
//            @Override
//            public void run() {
//
//                System.out.println("Inside setValue-tun()");
//
//                PrintWriter check = null;
//                try {
//                    File file = new File("nowcheck.txt");
//                    check = new PrintWriter(file);
//                    check.print("The text will be passed there");
//
//                    check.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//
//                System.out.println("Check for problems: " + printWriter.checkError());
//
//                System.out.println("Socket is bound/connected: ");
//                System.out.println(socket.isBound() + "/n");
//                System.out.println(socket.isConnected() + "/n");
//                System.out.println(socket.isOutputShutdown());
//
//                    printWriter.print("set /controls/flight/aileron" + 0.5 + "\r\n");
//                    printWriter.print("set /controls/engines/current-engine/throttle" + 0.435 + "\r\n");
//                    printWriter.print("set /controls/flight/elevator" + 0.5 + "\r\n");
//                    printWriter.print("set /controls/engines/current-engine/throttle" + 0.1 + "\r\n");
//                    printWriter.print("set /controls/flight/elevator" + 0.25 + "\r\n");
//                    printWriter.print("set /controls/engines/current-engine/throttle" + 1 + "\r\n");
//
//                    printWriter.flush();
//                    printWriter.close();
//                System.out.println(socket.isOutputShutdown());
//                }
//        }; msgThread.start();
//
//    }
}
