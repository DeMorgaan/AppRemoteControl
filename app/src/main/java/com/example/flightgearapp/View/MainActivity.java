package com.example.flightgearapp.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flightgearapp.R;
import com.example.flightgearapp.ViewModel.JoystickActivity;
import com.example.flightgearapp.ViewModel.Validator;

public class MainActivity extends AppCompatActivity {

    //Members:
    Validator validator;
    int counter = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Extract XML elements
        EditText ip = findViewById(R.id.ip);
        EditText port = findViewById(R.id.port);
        Button connect = findViewById(R.id.connect);
        TextView info = findViewById(R.id.info);
        Button about = findViewById(R.id.about);

        //Design: Set the color of the App Theme.
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#114064");
        window.setStatusBarColor(colorCodeDark);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        //When "Connect" button is clicked
        connect.setOnClickListener(v -> {

            validator = new Validator();

            String ipAddress = ip.getText().toString();
            String prePort = port.getText().toString();
            int portNumber;

            //Validations, such as: given String is not empty, port num is within possible ranges etc.
            if(!validator.emptyValidation(ipAddress, prePort))
                Toast.makeText(this, "PLEASE INSERT DATA INPUT!\n", Toast.LENGTH_LONG).show(); //No input inserted
            else
                {
                    portNumber = Integer.parseInt(prePort); //String are not empty, wrap it
                    validator = new Validator(ipAddress, portNumber); //Values aren't empty so they are passed to validation

                    if(validator.ipValidator(ipAddress) && validator.portValidator(portNumber))
                    {
                        Intent intent = new Intent(MainActivity.this, JoystickActivity.class);
                        intent.putExtra("ip", ipAddress);
                        intent.putExtra("port", portNumber);
                        startActivity(intent);
                    }
                    else
                    {
                        TextView clientMsg = findViewById(R.id.msg);
                        clientMsg.setText("WRONG CONNECTION DETAILS");
                    }
                }
        });


        //When "ABOUT" button is being clicked.
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
}