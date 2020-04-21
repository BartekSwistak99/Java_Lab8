package lab8;

import java.net.*;
import java.io.*;

public class ClientThread extends Thread {
  static int num = 0;
  int id;
  protected Socket socket = null;
  protected BufferedReader ReadFromClient;

  ClientThread() {
    num++;
    id = num;
  }

  ClientThread(Socket socket) {
    num++;
    id = num;
    this.socket = socket;
    try {
      ReadFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (Exception e) {
      System.out.println("Couldn't open buffers");
    }
  }

  @Override
  public void run() {
    int sec = 0;
    String notification = null;
    // reading data from client
    while (socket.isClosed() == false) {
      try {
        notification = ReadFromClient.readLine();
        String secString = ReadFromClient.readLine();
        sec = Integer.parseInt(secString);
        if (sec <= 0)
          throw new NumberFormatException();
      } catch (NumberFormatException exc) {
        if (notification == null) {
          System.out.println("#\tClient no. " + id + "  terminated");
          num--;
          return;
        }
        System.out.println("Recived data from client " + id + " is incorrect");
      } catch (Exception e) {
        System.out.println("Couldn't recieve data from  client no. " + id);
      }
      System.out.println("Recived data from client no. " + id+"\t[" + notification + " | " + sec + "s]");
      // creating thread which send notification
      ServerSendNotificationThread thread = new ServerSendNotificationThread(socket, notification, sec, id);
      thread.start();
    }
  }
}