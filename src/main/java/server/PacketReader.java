package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class PacketReader extends Thread{

    private int numberOfFiles;
    private int fileSize;
    private String fileName;
    private String filePath;
    private byte[] byteArray;
    private DataInputStream in;

    public PacketReader(Socket clientSocket) throws IOException {
        this.in = new DataInputStream(clientSocket.getInputStream());
    }

    public void run(){
        try {
            numberOfFiles = in.readInt();
            System.out.println("Number of Files: " + numberOfFiles);

            for (int x = 0; x < numberOfFiles; x++) {
                System.out.println("File #" + (x+1) + ":");
                filePath = in.readUTF();
                System.out.println("File Path: " + filePath);
                fileSize = in.readInt();
                System.out.println("File Size: " + fileSize);
                fileName = in.readUTF();
                System.out.println("File Name: " + fileName);
                byteArray = in.readNBytes(fileSize);

                Packet.createFile(fileName, filePath, byteArray);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
