package com.example.flightgearapp.ViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.flightgearapp.Model.TcpClient;
import com.example.flightgearapp.R;
import com.example.flightgearapp.View.JoystickView;

public class JoystickActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    TcpClient tcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        int port = intent.getIntExtra("port", 0);
        TextView textView = findViewById(R.id.textView);

        String display = "Welcome to FlightGear!" + "\n" + "Connected on: \n" + "PORT: " + port + "\n" + "IP: " + ip;
        textView.setText(display);

//        tcpClient = new TcpClient(ip, port);

        tcpClient = new TcpClient("192.168.1.162", 8000);
    }

    @Override
    public void joystickTouched(float xVal, float yVal) {
        tcpClient.sendMessage(xVal);
        tcpClient.sendMessage(yVal);
        Log.d("Value are: ", xVal + ":" + yVal);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}