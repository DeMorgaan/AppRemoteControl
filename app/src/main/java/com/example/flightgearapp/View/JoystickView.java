package com.example.flightgearapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.example.flightgearapp.Model.TcpClient;
import com.example.flightgearapp.R;


//The Joystick View - Setup & Drawing
public class JoystickView extends SurfaceView implements View.OnTouchListener, SurfaceHolder.Callback {

    //Class embers
    private float midX; //Joystick's location point
    private float midY;
    private float radius;
    private float hatRadius; //The radius of the top-part of the joystick
    private final int shade = 5; //The shade of the joystick 'stalk'
    private JoystickListener joystickListener; //responsible for the clallback method

    /*
    A method to set up the dimensions of the Joystick view, so it can fit to the Surface View.
    I divided the values - just as an effort to locate the joystick in the center as much as possible.
    Method is called from the constructor.
     */
    public void setSizes() {
        this.midX = getWidth() / 2;
        this.midY = getHeight() / 2;
        radius = Math.min(getWidth(), getHeight()) / 3;
        hatRadius = Math.min(getWidth(), getHeight()) / 7;
    }

    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this); //This method notify the
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joystickListener = (JoystickListener) context;
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joystickListener = (JoystickListener) context;
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joystickListener = (JoystickListener) context;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);
//
//        TextView textView = findViewById(R.id.tv);
//        String ip =getIntent().getStringExtra("ip");
//
//        TcpClient tcpClient = new TcpClient("192.168.1.162", 8000);
//        System.out.println("Arrived!");
//
////        tcpClient.setValue();
//
////        tcpClient.sendMessage();
//
////        tcpClient.connect("192.168.1.162", 8080);
//
//    }

    /*
        A method derived from the OnTouchListener interface, used for user-input interaction,
        Method's been called everything the user touches the screen.
         */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float distance; //Used to constrain the joystick

        if(v.equals(this))
        { //A validation to make sure the incoming touch input comes from this specific surface.
            if(event.getAction() != event.ACTION_UP)
            { //We want the joystick to be in motion only when the user *touches* the screen, ow - it resets
                distance = (float)Math.sqrt(Math.pow(event.getX() - midX, 2) + Math.pow(event.getY() - midY, 2));

                if (distance < radius)
                { //Check if the touch in the joys is out of the boundaries
                    joystickDrawer(event.getX(), event.getY()); //The event x/y values represents the user touch and joystick's hat is moving accordingly
                    joystickListener.joystickTouched((event.getX() - midX)/radius, (event.getY() - midY)/radius);
                }
                else
                {
                    //Constraining the Joystick
                    float ratio = radius / distance;
                    float consX = midX + (event.getX() - midX)*ratio;
                    float consY = midY + (event.getY() - midY)*ratio;
                    joystickDrawer(consX, consY);
                    joystickListener.joystickTouched((consX - midX)/radius, (consY - midY)/radius);

                }
            }
            else
            {
                joystickDrawer(midX, midY);
                joystickListener.joystickTouched(0,0);
            } //Reset the joystick if no touch has occured
        }
        return true; //Returnig false might prevent onTouch receive future touches
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        setSizes();
        joystickDrawer(midX, midY);

        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }



    /*
    A method ot draw the Joystick on the screen, using Canvas Object.
     */
    public void joystickDrawer(float xValue, float yValue) {

        if(getHolder().getSurface().isValid())
        {
            Canvas canvasDrawer = this.getHolder().lockCanvas(); //The canvas, on which it will be drawn
            Paint painter = new Paint(); //A painter, which will set colors and make the drawing

            canvasDrawer.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //A method to clean the Canvas.

            float angleCalc; //Helps caculate the shading rate of the joystick's stalk

            angleCalc = (float)Math.sqrt(Math.pow(xValue - midX, 2) + Math.pow(yValue - midY, 2));
            float sin = (yValue - midY) / angleCalc;
            float cos = (xValue - midX) / angleCalc;

            painter.setARGB(255, 219,189,31);
            canvasDrawer.drawCircle(this.midX, this.midY, this.radius, painter); //Drawing the Joystick

            for (int i = 1; i <= (int)(radius/shade); i++)
            {
//            painter.setARGB(30, 120, 12, 15); //Set a color to the painter
//                painter.setARGB(150/i, 120, 218, 135);
                painter.setARGB(150/i, 94, 72, 1);
//            canvasDrawer.drawCircle(this.midX, this.midY, this.radius, painter); //Drawing the Joystick
                canvasDrawer.drawCircle(xValue - cos * angleCalc * (shade / radius) * i,
                        yValue - sin * angleCalc * (shade / radius) * i, i * (hatRadius * shade / radius), painter);
            }

            //To draw the hat
            for(int i = 0; i <= (int)(hatRadius / shade); i++)
            {
                painter.setARGB(130, (int)(i*(120*shade/hatRadius)), (int)(i*(255*shade/hatRadius)), 135);
                canvasDrawer.drawCircle(xValue, yValue, hatRadius - (float)i*(shade)/3, painter); //Drawing the hat of the joystick
            }
//        painter.setARGB(255, 0, 0, 255);

            getHolder().unlockCanvasAndPost(canvasDrawer); //Prints the canvas to the Surface view - here's the actual drawing
        }
    }
    public interface JoystickListener
    {
        void joystickTouched(float xVal, float yVal);
    }
}