package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread t;
    private PacketReader pr;
    private int numberOfFiles;
    private boolean stopFlag = false;
    private ArrayList<Packet>packetList;

    public Server(){
        packetList = new ArrayList<>();
    }

    public void start(int port, String[] args) {
        while (!stopFlag) {
            try {
                serverSocket = new ServerSocket(port);
                clientSocket = serverSocket.accept();

                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                numberOfFiles = in.readInt();

                pr = new PacketReader(clientSocket);
                t = new Thread(pr);

                for(int x = 0; x < numberOfFiles; x++){
                    t.start();
                    t.join();
                    packetList.add(pr.createPacket());
                }

                out.close();
                in.close();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Server s = new Server();

        s.start(55588, args);
    }
}
