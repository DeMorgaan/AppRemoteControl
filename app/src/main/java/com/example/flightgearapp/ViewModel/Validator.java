package com.example.flightgearapp.ViewModel;


/*
This class is a help class, responsible for valdating the input IP Address and Port number.
There are several valdations, such as: input != empty && port is in range etc.
 */
public class Validator {
    //Validate the input IP Address

    //Members:
    int port;
    String ip;

    /*
    There are 2 C'tors, empty one incase of using the inner methods,
    And the other one is in case of full valdiating checking.
     */
    public Validator(){}

    public Validator(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    /**
     * Validate if the given port number is in range.
     * @param port
     * @return
     */
    public boolean portValidator(int port) {
        if(port > 1024 && port < 65535)
            return true;
        else
            return false;
    }

    /**
     * Validate if the IP Address is valid.
     * @param ip
     * @return
     */
    public boolean ipValidator(String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Validate that both IP and PORT input is not empty.
     * @param ip
     * @param prePort
     * @return
     */
    public boolean emptyValidation(String ip, String prePort) {
        if(prePort.matches("") || ip.matches(""))
            return false;
        else
                return true;
    }
}
