package lab8;

import java.net.*;
import java.util.ArrayList;
public class Server {
    static final int PORT_NUMBER = 8888;
    static ServerSocket serverSocket = null;
    private static ArrayList<Thread> ClientThreadsArray=new ArrayList<Thread> ();
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
        } catch (Exception e) {
            System.out.println("Couldn't listen on port: " + PORT_NUMBER);
            System.exit(1);
        }
        System.out.println("Server created!");
         clients_add();
        //Thread thread1=new Thread(new ClientThread(),"Thread1");
        //Thread thread2=new Thread(new ClientThread(),"Thread2");
    }

    public static void clients_add() {
        while (true) {
            try {
                Socket client_socket = serverSocket.accept();
                System.out.println("#\tNew client connected on port: " + client_socket.getPort());
                ClientThread client=new ClientThread(client_socket);
                Thread client_thread=new Thread(client);
                client_thread.start();
                ClientThreadsArray.add(client_thread);
            } catch (Exception e) {
                System.out.println("#\tAccept failed on port:" + PORT_NUMBER);
            }
        }

    }
}