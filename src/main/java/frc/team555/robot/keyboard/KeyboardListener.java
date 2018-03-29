package frc.team555.robot.keyboard;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class KeyboardListener extends Thread implements NativeKeyListener {

    ServerSocket serverSocket;
    private Socket recieveSocket;
    private OutputStream outputStream;
    public DataOutputStream dataOutputStream;

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
        try {
            dataOutputStream.writeUTF(NativeKeyEvent.getKeyText(e.getKeyCode()));
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("NOT SENT");
        }
    }

    public void startListener(){
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new KeyboardListener());
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(5810);
            System.out.println("Server started on Port 5810");
            while (true) {
                recieveSocket = serverSocket.accept();
                outputStream = recieveSocket.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                startListener();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KeyboardListener keyboardListener = new KeyboardListener();
        keyboardListener.start();
    }
}
