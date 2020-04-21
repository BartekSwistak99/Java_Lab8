package lab8;

import java.net.Socket;
import java.util.Scanner;

import jdk.dynalink.beans.StaticClass;

import java.io.*;

public class Client {
    static public final int PORT_NUMBER = 8888;
    static private Scanner in = new Scanner(System.in);
    static private PrintWriter WriteToServer = null;

    public static void main(String[] args) {
        Socket socket = connectToServer(PORT_NUMBER);
        try {
            WriteToServer = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Couldn't open buffer - connection failed");
        }
        //thread reading server response
        ClientReadThread thread=new ClientReadThread(socket);
        thread.start();
        // reading data
        do {
            System.out.println("\tPress 'a' to add notification, or 'q' to quit");
            do {
                try {
                    String entry = in.nextLine();
                    if (entry.charAt(0) == 'q' || entry.charAt(0) == 'Q') {
                        socket.close();
                        System.exit(0);
                    } else if (entry.charAt(0) == 'A' || entry.charAt(0) == 'a')
                        break;
                    else 
                        throw new IncorrectEntryException();
                } catch (Exception e) {
                    System.out.println("Incorrect input! try again");
                }
            } while (true);

            String NotificationContent = enterNotification();
            int sec = enterTime();

            // sending data to server
            try {
                WriteToServer.println(NotificationContent);
                WriteToServer.println(sec);
                WriteToServer.flush();
                System.out.println("Request sent");
            } catch (Exception e) {
                System.out.println("Couldn't send data");
                e.printStackTrace();
            }
        } while (true);

    }

    private static Socket connectToServer(int port) {
        Socket socket = null;
        do {
            try {
                socket = new Socket("localhost", port);
                break;
            } catch (Exception e) {
                System.out.println("Couldn't connect to server! Press enter to try again or 'q' to quit");
                String key = in.nextLine();
                if (key.length() > 0 && (key.charAt(0) == 'q' || key.charAt(0) == 'Q'))
                    System.exit(0);
            }
        } while (true);
        System.out.println("Connected to server!");
        return socket;
    }

    public static String enterNotification() {
        String NotificationContent;
        while (true) {
            System.out.println("Enter notification content:");
            try {
                NotificationContent = in.nextLine();
                if (NotificationContent.length() == 0)
                    throw new IncorrectEntryException();
                break;
            } catch (Exception e) {
                System.out.println("Enter notification! try again");
            }
        }
        return NotificationContent;
    }

    public static int enterTime() {
        int sec = -1;
        while (true) {
            try {
                System.out.println("Enter time (in sec): ");
                String secString = in.nextLine();// clearing buffer
                sec = Integer.parseInt(secString);
                if (sec <= 0)
                    throw new IncorrectEntryException();
                break;
            } catch (Exception e) {
                System.out.println("Incorrect time! try again");
            }
        }
        return sec;
    }
}
