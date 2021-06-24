# Android FlightGear App
![image](https://user-images.githubusercontent.com/60241230/123306182-a3001880-d529-11eb-99d4-b7f6da4bf85c.png)
![1](https://user-images.githubusercontent.com/60241230/123306299-c925b880-d529-11eb-8d63-fe87072e3ba2.jpg)



***Description :***

It's an Android application designed to fly a FlightGear aircraft using a joystick.


By running the code and the app - you should insert ip number & port number and then press the button connect,
you will be able to direct the airplane with the joystick that we built (in AndroidApp) 

-imageOfJoystick-

*make sure you don't fall and destroy your plane :)*

___________________________________________________________________________________________________________________________________
### Installation 
First please download and install the simulator on your computer- https://www.flightgear.org/download/   
and make sure you change the settings of the FlightGear Simulator Application - 
for the connection:
Add the generic_small.xml file to the /data/Protocol directory where you installed the simulator Config the following settings in the 'Settings' tab in the simulator:

> --telnet=socket,in,10,127.0.0.1,8000,tcp 

> --generic=socket,out,10,127.0.0.1,8000,tcp,generic_small 

so you can start running the app and fly the airplane.

## Running
You should be able to see both of the FlightGear Simulator App and our Android FlightGear App on the screen, when you run the code after opening the simulator app.

-image-

You need to insert the "ip number" (for sure by using the command **ipconfing** in your cmd) , and "port number" which is the same above in settings "8000".

Then press -connect- button to you could start using the joystick and decide the directions of the airplane.

## Directory hierarchy
**Model :** 

which has the server - TcpClient.java

It's basically responsible for connecting to the server and sending data.

**ViewModel :** 

which has the - JoystickActivity.java 

This is responsible to get the data from View and send it to the model (server).

**View :** 

which has : -JoystickView.java, -MainActivity.java

These classes are the view of the app - which contains the buttons,joystick and all what appears.

## Documentation UML and Video

<br>

![image](https://user-images.githubusercontent.com/60241230/123307600-5c132280-d52b-11eb-81ff-8cb0a2f5ca5d.png)

<br>

    # Documentation/UML
Here attached the UML link ,that contains information of the main classes and the connection between them -https://lucid.app/lucidchart/c9b302ef-34ef-4480-8c79-e98f6ff43d68/view?page=0_0# -
For developers there is also documentation of the code in the code files for you.

    # Video
Here attached the link of a video where we demonstrate the use of the server:  -Link-
