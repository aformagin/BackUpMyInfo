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
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for Connection");
            clientSocket = serverSocket.accept();
            System.out.println("Connected!");

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            numberOfFiles = in.readInt();
            System.out.println("Number of Files: " + numberOfFiles);

            Packet[] packets = new Packet[numberOfFiles];

            String filename;
            String filePath;
            int fileSize;
            byte[] byteArray;

            for (int x = 0; x < numberOfFiles; x++) {
                System.out.println("File #" + (x+1) + ":");
                filePath = in.readUTF();
                System.out.println("File Path: " + filePath);
                fileSize = in.readInt();
                System.out.println("File Size: " + fileSize);
                filename = in.readUTF();
                System.out.println("File Name: " + filename);
                byteArray = new byte[fileSize];
                in.read(byteArray);

                packets[x] = Packet.createPacket(filename, filePath, byteArray);
            }

            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static void main(String[] args){
        Server s = new Server();

        s.start(25565, args);
    }
}
