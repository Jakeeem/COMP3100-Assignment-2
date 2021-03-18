
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 50000);
            String myString = "";
            boolean end = false;
            handshake(s);
   
//Tells the server that the client is ready for a job
sendMsg(s, "REDY");
            //Get the server state information
         /*   sendMsg(s, "GETS All");
            //Obtains server state info
            myString = readMsg(s);
            System.out.println("Server State Info: " + myString);
            sendMsg(s, "OK");*/
            //While the simulation has not been told to end
            while(end == false){
                //Tells the server that the client is ready for a job
                sendMsg(s, "REDY");
                //Obtains  job from the server
                myString = readMsg(s);
                System.out.println("JOB: " + myString);
                //Checks to see if the received command is "NONE" or "QUIT" which
                //ends the program
                if(myString == "NONE" || myString == "QUIT"){
                    end = true;
                    break;
                }
                //myString is now a job that needs to be scheduled


                








                if(myString.contains("JOBN")){
                    String[] JOBNSplit = myString.split(" ");
                    /*
                    String command = JOBN;
                    int submitTime = 0;
                    int jobID = 0;
                    int estRuntime = 0;
                    int core = 0;
                    int memory = 0;
                    int disk = 0;
                    */

                    
                    //See what servers are available
                    sendMsg(s, "GETS Avail " + JOBNSplit[4] + " " + JOBNSplit[5] + " " + JOBNSplit[6]);
                    myString = readMsg(s);
                    System.out.println("Data: " + myString);
                    //Say OK to the server (ie: OK i got your msg)
                    sendMsg(s, "OK");

                    //Read the available servers data
                    myString = readMsg(s);
                    System.out.println("After Avail: " + myString);


                    String[] AvailSplit = myString.split(" ");
                    sendMsg(s, "OK");
                    if(AvailSplit.length <= 0){
                        break;
                    }
                    
                    myString = readMsg(s);
                    System.out.println("After Avail: " + myString);
                    if(myString == "."){
                        TimeUnit.SECONDS.sleep(1);
                    }
                    //Schedules the job
                    sendMsg(s, "SCHD " + JOBNSplit[2] + AvailSplit[0] + " " + AvailSplit[1]);
                    myString = readMsg(s);
                    System.out.println("SCHD: " + myString);
                }
                if(myString.contains("DATA")){
                    sendMsg(s, "OK");
                }
                else if(myString.contains("NONE")){
                    end = true;
                    break;
                }

                










            }

            //Sends "Quit" to the server to end the session
            sendMsg(s, "QUIT");
            //Closes the socket
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static synchronized String readMsg(Socket s){
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

    public static synchronized void sendMsg(Socket s, String myString){
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
        sendMsg(s, "AUTH " + System.getProperty("user.name"));

        //Check to see if sever has ok'd the client's AUTH
        myString = readMsg(s);
        System.out.println("RCVD: " + myString);
    }
}
