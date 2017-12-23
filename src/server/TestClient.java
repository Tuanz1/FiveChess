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
            output1.writeInt(101);
            output2.writeInt(102);
            output1.writeInt(201);
            output2.writeInt(202);
            output1.writeInt(301);
            output2.writeInt(302);
            output1.writeInt(401);
            output2.writeInt(402);
            output1.writeInt(501);
            output2.writeInt(502);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
