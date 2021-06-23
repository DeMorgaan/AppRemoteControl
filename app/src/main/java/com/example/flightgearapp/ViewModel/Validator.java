package com.example.flightgearapp.ViewModel;

public class Validator {
    //Validate the input IP Address

    //Members:
    int port;
    String ip;

    public Validator(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean portValidator(int port) {
        if(port > 1024 && port < 65535)
            return true;
        else
            return false;
    }

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
}
