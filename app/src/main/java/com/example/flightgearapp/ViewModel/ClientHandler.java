package com.example.flightgearapp.ViewModel;


import android.widget.TextView;

import com.example.flightgearapp.Model.TcpClient;

/*
Helper class, is responsible for the entire logic of JoystickActivity,
it decouples its relation to the program -
and makes the Joustick component totally independent.
 */
public class ClientHandler {

    //Members
    private TcpClient tcpClient;
    private final int port;
    private final String ip;
    //Setup the commands to FG
    private final String aileron = "set /controls/flight/aileron ";
    private final String elevator = "set /controls/flight/elevator ";
    private final String rudder = "set /controls/flight/rudder ";
    private final String throttle = "set /controls/engines/current-engine/throttle ";


    public ClientHandler(int port, String ip) {
        this.port = port;
        this.ip = ip;
        tcpClient = new TcpClient(ip, port);
//        tcpClient = new TcpClient("192.168.1.162", 8000); //My default input
    }

    /**
     * Setter methods - they send the data to TCPClient which than pass it
     * to FG Server.
     * @param val
     */
    public void setAileron(float val) {
        tcpClient.sendMessage(aileron, val);
    }

    public void setElevator(float val) {
        tcpClient.sendMessage(elevator, val);
    }

    public void setThrottle(int val) {
        float normalizeValue = normalThrottleValue(val);
        tcpClient.sendMessage(throttle, normalizeValue);
    }

    public void setRudder(int val) {
        float normalizedValue = normalRudderValue(val);
        tcpClient.sendMessage(rudder, normalizedValue);
    }

    /*
    Static methods to normalize the input values from the seek-bars,
    as the default input values are integers.
     */
    public static float normalThrottleValue (int value) {
        float throttleValue = (float)value;
        throttleValue /= 10; //Decide num of decimal digits
        throttleValue /= 100; //Adjust the value to the FG API (0-1 values)
        System.out.println("Throttle: " + throttleValue);
        return throttleValue;
    }

    public static float normalRudderValue (int value) {
        float rudderValue = (float)value;
        rudderValue /= 10; //Decide num of decimal numbers
        rudderValue /= 100; //Adjust the value to the FG API (values from -1 to 1)
        System.out.println("Rudder: " + rudderValue);
        return rudderValue;
    }

    public void closeConnection() {
        tcpClient.disconnect();
    }
}

