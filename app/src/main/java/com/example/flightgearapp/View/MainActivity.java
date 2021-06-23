package com.example.flightgearapp.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flightgearapp.R;
import com.example.flightgearapp.ViewModel.JoystickActivity;
import com.example.flightgearapp.ViewModel.Validator;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    Validator validator;
    int counter = 0;
    int portNumber;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            EditText ip = findViewById(R.id.ip);
            EditText port = findViewById(R.id.port);
            Button connect = findViewById(R.id.connect);
            TextView info = findViewById(R.id.info);
            Button about = findViewById(R.id.about);

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int colorCodeDark = Color.parseColor("#114064");
            window.setStatusBarColor(colorCodeDark);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);

            connect.setOnClickListener(v -> {

                String ipAddress = ip.getText().toString();
                String prePort = port.getText().toString();
                if(prePort.matches("") || ipAddress.matches("")) {
                    Toast.makeText(this, "Please insert input", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    portNumber = Integer.parseInt(prePort);
                }
                validator = new Validator(ipAddress, portNumber);

                    if(validator.ipValidator(ipAddress) && validator.portValidator(portNumber)) {
//                        setContentView(R.layout.activity_joystick);

                        Intent intent = new Intent(MainActivity.this, JoystickActivity.class);
                        intent.putExtra("ip", ipAddress);
                        intent.putExtra("port", portNumber);
                        startActivity(intent);
                    } else {
                        TextView clientMsg = findViewById(R.id.msg);
                        clientMsg.setText("WRONG CONNECTION DETAILS");
                    }
            });

            about.setOnClickListener(v -> {
                counter++;
                if(counter % 2 != 0) {
                    info.setText(R.string.info);
                    about.setText("HIDE");
                } else if (counter % 2 == 0) {
                    info.setText("");
                    about.setText("ABOUT");
                }

            });
    }



    @Override
    public void joystickTouched(float xVal, float yVal) {
        Log.d("Locatin is: " , xVal + ": " + yVal);
    }
}