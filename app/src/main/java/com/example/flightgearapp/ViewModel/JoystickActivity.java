package com.example.flightgearapp.ViewModel;

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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.flightgearapp.R;
import com.example.flightgearapp.View.JoystickView;

public class JoystickActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    ClientHandler clientHandler; //In order to decouple JoystickActivity logic
    Button disconnectBtn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        //Design the top of the screen
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int topColor = Color.parseColor("#114064");
        window.setStatusBarColor(topColor);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        //Extract XML Elements
        TextView greetingLine = findViewById(R.id.textView);
        TextView connectDetails = findViewById(R.id.details);
        SeekBar throttle = findViewById(R.id.throttle);
        SeekBar rudder = findViewById(R.id.ruddder);
        disconnectBtn = findViewById(R.id.disco);

        //Extract connection detailes
        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        int port = intent.getIntExtra("port", 0);

        //*Responsible for the entire logic: connection, data-send etc*
        clientHandler = new ClientHandler(port, ip); //conncecting to FG server automatically

        //Textview contect
        String display = "CONNECTION ESTABLISHED \n";
        String details = "PORT: " + port + "\n" + "IP: " + ip;
        greetingLine.setText(display);
        connectDetails.setText(details);

        disconnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greetingLine.setText("DISCONNECTED");
                connectDetails.setText("I hope your driving skills are better...");
                clientHandler.closeConnection();
            }
        });


        //Inject functionality into the elements, while joystick is being touched
        //Throttle seek-bar
        throttle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                throttle.setMax(1000);
//                float throttleValue = ((float)progress / 10) / 100;
                clientHandler.setThrottle(progress);
//                System.out.println("Throttle: " + clientHandler.normalThrottleValue(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Rudder seek-bar
        rudder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rudder.setMax(1000);
                rudder.setMin(-1000);
                clientHandler.setRudder(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //Joystick functionality
    //The method implemented from JoystickListener interface (which resides in JoystickView)
    @Override
    public void joystickTouched(float xVal, float yVal) {

//        tcpClient.sendMessage(xVal);
//        tcpClient.sendMessage(yVal);
        Log.d("Value are: ", xVal + ":" + yVal);
        clientHandler.setAileron(xVal);
        clientHandler.setElevator(-1 * yVal); //We multiply by -1 to adjust the joystick
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}