package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public void start(int port, String[] args) {
        while(true){
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Waiting for Connection");
                clientSocket = serverSocket.accept();
                System.out.println("Connected!");

                PacketReader pr = new PacketReader(clientSocket);
                pr.start();

            } catch (IOException e) {
                e.printStackTrace();
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
        Server s = new Server();

        s.start(25565, args);
    }
}