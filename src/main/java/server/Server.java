package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;


    public void start(int port) {
        while (true) {
            try {

                //Creates server socket, waits for client to connect
                serverSocket = new ServerSocket(port);
                System.out.println("Waiting for Connection");
                clientSocket = serverSocket.accept();
                System.out.println("Connected!");

                //Initializes PacketReader with newly connected clientSocket, and starts the thread
                PacketReader pr = new PacketReader(clientSocket);
                pr.start();

            } catch (IOException e) {
                e.printStackTrace();

                //If IOException is thrown, sleep for 1s and restart server
                try {
                    System.out.println("Connection not Found!");
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        //Initialize server and start it

        Server s = new Server();
        s.start(25565);
    }
}