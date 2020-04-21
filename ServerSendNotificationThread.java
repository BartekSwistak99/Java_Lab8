package lab8;

import java.net.*;
import java.io.*;

public class ServerSendNotificationThread extends Thread {
    private int waitTime;
    private String notification;
    private int id;
    protected Socket socket;
    protected PrintWriter WriteToClient = null;

    ServerSendNotificationThread(Socket socket, String notification, int waitTime, int id) {
        this.socket = socket;
        this.id = id;
        this.waitTime = waitTime;
        this.notification = notification;
        try {
            WriteToClient = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Couldn't open buffers");
        }
    }
    @Override
    public void run() {
        try {
            sleep(waitTime * 1000);
            WriteToClient.println(notification);
            WriteToClient.flush();
            System.out.println("notification sent to client no. " + id);
            return;
        } catch (Exception e) {
            System.out.println("Couldn't send data to  client no. " + id);
        }
    }
}