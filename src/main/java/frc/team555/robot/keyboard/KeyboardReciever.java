package frc.team555.robot.keyboard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class KeyboardReciever {

    private Socket socketReceive;
    private InputStream inputStream;
    private DataInputStream dataInputStream;

    public static void main(String[] args) throws IOException {
        KeyboardReciever keyboardReciever = new KeyboardReciever();
        keyboardReciever.startSocket("127.0.0.1",5810);
        while (true){
            System.out.println("Receiving Data");
            System.out.println(keyboardReciever.receiveData());
        }
    }

    public void startSocket(String ip, int port) throws IOException {
        socketReceive = new Socket(ip, port);
    }

    public String receiveData() throws IOException {
        inputStream = socketReceive.getInputStream();
        dataInputStream = new DataInputStream(inputStream);
        return dataInputStream.readUTF();
    }

    public void stopSocket() throws IOException {
        dataInputStream.close();
        inputStream.close();
        socketReceive.close();
    }

}
