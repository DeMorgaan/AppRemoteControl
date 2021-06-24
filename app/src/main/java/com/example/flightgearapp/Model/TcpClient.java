package com.example.flightgearapp.Model;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * This class is the client side of the TCP-socket communication,
 * The server-side is the FlightGear simulator itself.
 */
public class TcpClient {

    //Members:
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

    /**
     * A method to set the connection to the FG Server.
     * Running on a different thread.
     */
    public void connect() {
        final Thread connectThread = new Thread() {
            @Override
            public void run() {
                try
                {
                    //Setting the connection required elements
                    InetAddress inetAddress = InetAddress.getByName(ipAddress);
                    socket = new Socket(inetAddress, port);
                    printWriter = new PrintWriter(socket.getOutputStream(), true); //Simply wrap the socket's output stream.
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
        connectThread.start();
    }

    /**
     * Sending the commands to the server.
     * Running on a different thread.
     * @param path
     * @param value
     */
    public void sendMessage(String path, float value){
        final Thread thread = new Thread() {
            @Override
            public void run()
            {
                final String message = path + value + "\r\n";
                printWriter.print(message);
                printWriter.flush();
            }
        };thread.start();
    }

    /**
     * Disconnect from the server,
     * simply closing the PrintWriter and the Socket.
     */
    public void disconnect() {
        printWriter.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
