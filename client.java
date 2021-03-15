
import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 50000);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("HELO\n");
            dout.flush();
            String str = (String) dis.readUTF();
            System.out.println("message= " + str);
            dout.writeUTF("AUTH");
            dout.flush();
            str = (String) dis.readUTF();
            System.out.println("message= " + str);
            dout.writeUTF("AUTH");
            dout.flush();
            //str = (String) dis.readUTF();
           // System.out.println("message= " + str);
            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
