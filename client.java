
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 50000);
            //Set up variables to be used
            String[] biggestServer = {""};
            boolean biggestFound = false;
            String currentMsg = "";
            Handshake(s);

            // While there are more jobs to be done
            while (!currentMsg.contains("NONE")) {
                // Tells the server that the client is ready for a command and reads it
                SendMsg(s, "REDY\n");
                currentMsg = ReadMsg(s);
                
                // Checks to see if the received command is a new job
                if (currentMsg.contains("JOBN")) {
                    String[] JOBNSplit = currentMsg.split(" ");
                    //Ask what servers are available to run a job with the given data
                    SendMsg(s, "GETS Avail " + JOBNSplit[4] + " " + JOBNSplit[5] + " " + JOBNSplit[6] + "\n");
                    //Reads the msg saying what data is about to be sent and responds with "OK"
                    currentMsg = ReadMsg(s);
                    SendMsg(s, "OK\n");

                    // Reads the available servers data and responds with "OK"
                    currentMsg = ReadMsg(s);
                    SendMsg(s, "OK\n");

                    //Checks to see if the biggest Server has been found
                    //Used as a flag to see that it was found on the first round
                    if(biggestFound == false){
                        biggestServer = findBiggestServer(currentMsg);
                        biggestFound = true;
                    }

                    //Reads "." from the server
                    currentMsg = ReadMsg(s);

                    //Schedule the current job to the biggest server (SCHD JobNumber ServerName ServerNumber)
                    SendMsg(s, "SCHD " + JOBNSplit[2] + " " + biggestServer[0] + " " + biggestServer[1] + "\n");

                    //Read the next JOB
                    currentMsg = ReadMsg(s);
                    System.out.println("SCHD: " + currentMsg);
                }
                else if (currentMsg.contains("DATA")) {
                    SendMsg(s, "OK\n");
                }
            }
            // Sends "Quit" to the server to end the session and then closes the socket
            SendMsg(s, "QUIT\n");
            s.close();
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }

    // Function used to read a msg from the server
    public static synchronized String ReadMsg(Socket s) {
        String currentMsg = "FAIL";
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            byte[] byteArray = new byte[dis.available()];
            // Reset byteArray to have 0 elements so it is ready to recieve
            // a msg and wait until a msg is recieved
            byteArray = new byte[0];
            while (byteArray.length == 0) {
                // Read the bytestream from the server
                byteArray = new byte[dis.available()];
                dis.read(byteArray);
                // Make a string using the recieved bytes and print it
                currentMsg = new String(byteArray, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return the msg just recieved from the server
        return currentMsg;
    }

    // Function used to send a msg to the server
    public static synchronized void SendMsg(Socket s, String currentMsg) {
        try {
            //Converts the String msg to an array of bytes and sends them to the server
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            byte[] byteArray = currentMsg.getBytes();
            dout.write(byteArray);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Handshake(Socket s) {
        String currentMsg = "";

        // Initiate Handshake with server
        SendMsg(s, "HELO\n");

        // Check for response from sever for "HELO"
        currentMsg = ReadMsg(s);
        System.out.println("RCVD: " + currentMsg);

        // Authenticate with a username (ubuntu)
        SendMsg(s, "AUTH " + System.getProperty("user.name") + "\n");

        // Check to see if sever has ok'd the client's AUTH
        currentMsg = ReadMsg(s);
        System.out.println("RCVD: " + currentMsg);
    }

    public static String[] FindBestFit(){
        return "";
    }
}
