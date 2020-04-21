package lab8;

import java.net.*;
import java.io.*;

public class ClientReadThread extends Thread {
    private Socket socket;
    static private BufferedReader ReadFromServer = null;

    ClientReadThread(Socket socket) {
        this.socket = socket;
        try {
            ReadFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Couldn't open buffer - connection failed");
        }
    }

    @Override
    public void run() {
        do {
            try {
                String recievedData = ReadFromServer.readLine();
                if (recievedData == null) {
                    throw new Exception("Server is not responding");
                }
                System.out.println("[Notification:]\t" + recievedData);
            } catch (Exception e) {
                System.out.println("Connection with server interrupted!");
                System.exit(1);
            }
        } while (true);
    }

}