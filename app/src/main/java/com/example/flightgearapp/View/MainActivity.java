package com.example.flightgearapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flightgearapp.R;
import com.example.flightgearapp.ViewModel.JoystickActivity;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText ip = findViewById(R.id.ip);
        EditText port = findViewById(R.id.port);
        Button connect = findViewById(R.id.connect);
        TextView output = findViewById(R.id.output);

        JoystickView joystickView = new JoystickView(this);


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setContentView(R.layout.activity_joystick);
                String ipAddress = ip.getText().toString();
                int portNumber = Integer.parseInt(port.getText().toString());


//                startActivity(new Intent(this, JoystickActivity.class));

                Intent intent = new Intent(MainActivity.this, JoystickActivity.class);
                intent.putExtra("ip", ipAddress);
                intent.putExtra("port", portNumber);
                startActivity(intent);

//                output.setText(ipAddress + "\r\n" + portNumber);

            }
        });
    }

    @Override
    public void joystickTouched(float xVal, float yVal) {
        Log.d("Locatin is: " , xVal + ": " + yVal);
    }
}