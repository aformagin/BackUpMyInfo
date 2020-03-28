package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

//# of files
//File Path
//Size
//File Name
//Byte Array

public class PacketReader implements Runnable {

    private boolean stopFlag = false;
    private boolean pauseFlag = false;
    private int numberOfFiles;
    private int fileSize;
    private String fileName;
    private String filePath;
    private byte[] byteArray;
    private DataInputStream in;

    public PacketReader(Socket clientSocket) throws IOException{
        this.in = new DataInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run(){

    }

    public void getData(){
        try {
            for(int x = 0; x < numberOfFiles; x++){
                filePath = in.readUTF();
                System.out.println("Read FilePath: " + this.filePath);
                fileSize = in.readInt();
                System.out.println("Read FileSize: " + this.fileSize);
                fileName = in.readUTF();
                System.out.println("Read FileName: " + this.fileName);
                byteArray = new byte[fileSize];
                byteArray = in.readNBytes(fileSize);
                System.out.println("Read Byte Array");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
