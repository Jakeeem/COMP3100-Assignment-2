
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 50000);
            String myString = "";
            boolean end = false;
            handshake(s);
   
            //While the simulation has not been told to end
            while(end == false){
                //Obtains  job from the server
                myString = readMsg(s);
                System.out.println("RCVD: " + myString);
                //Checks to see if the received command is "NONE" or "QUIT" which
                //ends the program
                if(myString == "NONE" || myString == "QUIT"){
                    end = true;
                    break;
                }
                //myString is now a job that needs to be scheduled
                //changing end to true here for debugging purposes
                end = true;
            }

            //Sends "Quit" to the server to end the session
            sendMsg(s, "QUIT");
            //Closes the socket
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String readMsg(Socket s){
        String myString = "FAIL";
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            byte[] byteArray = new byte[dis.available()];
            //Reset byteArray to have 0 elements so it is ready to recieve
            //a msg and wait until a msg is recieved
            byteArray = new byte[0];
            while(byteArray.length == 0){
                //Read the bytestream from the server
                byteArray = new byte[dis.available()];
                dis.read(byteArray);
                //Make a string using the recieved bytes and print it
                myString = new String(byteArray, StandardCharsets.UTF_8);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myString;
        
    }

    public static void sendMsg(Socket s, String myString){
        try {
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            byte[] byteArray = myString.getBytes();
            dout.write(byteArray);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void handshake(Socket s){
        String myString = "";

        //Initiate handshake with server
        sendMsg(s, "HELO");
        
        //Check for response from sever for "HELO"
        myString = readMsg(s);
        System.out.println("RCVD: " + myString);

        //Authenticate with a username (ubuntu)
        sendMsg(s, "AUTH ubuntu");

        //Check to see if sever has ok'd the client's AUTH
        myString = readMsg(s);
        System.out.println("RCVD: " + myString);

        //Tells the server that the client is ready to start taking jobs
        sendMsg(s, "REDY");
    }
}
