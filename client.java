
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 50000);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            
            //Make a string, convert it to an array of bytes then
            //send a bytestream to the server
            String myString = "HELO";
            byte[] byteArray = myString.getBytes();
            dout.write(byteArray);
            dout.flush();

            //Read the bytestream from the server
            byteArray = new byte[dis.available()];
            dis.read(byteArray);
            //Make a string using the recieved bytes and print it
            myString = new String(byteArray, StandardCharsets.UTF_8);
            System.out.println(myString);
            
            //Authenticate with a username (ubuntu)
            myString = "AUTH ubuntu";
            byteArray = myString.getBytes();
            dout.write(byteArray);
            dout.flush();
            //Tells the server that the client wants to quit
            myString = "QUIT";
            byteArray = myString.getBytes();
            dout.write(byteArray);
            dout.flush();

            //Closes Session
            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
