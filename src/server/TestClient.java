package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args){
        try {
            Socket client1 = new Socket("localhost", 8000);
            Socket client2 = new Socket("localhost", 8000);
            DataInputStream input1  = new DataInputStream(client1.getInputStream());
            System.out.println(input1.readInt());
            DataInputStream input2  = new DataInputStream(client2.getInputStream());
            System.out.println(input2.readInt());

            DataOutputStream output1 = new DataOutputStream(client1.getOutputStream());
            DataOutputStream output2 = new DataOutputStream(client2.getOutputStream());
            while(true) {
                output1.writeInt(101);
                output2.writeInt(201);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
