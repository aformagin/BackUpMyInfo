package server;

import java.io.*;
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
        try {
            for(int x = 0; x < numberOfFiles; x++){
                filePath = in.readUTF();
                fileSize = in.readInt();
                fileName = in.readUTF();
                byteArray = new byte[fileSize];
                byteArray = in.readNBytes(fileSize);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Packet createPacket() throws IOException {
        File file = new File(filePath + fileName);
        /*if(file.mkdirs()){
            System.out.println("Success");
        } else{
            System.out.println("Dir Already Exists");
            throw new IOException();
        }*/
        if(file.createNewFile()){
            System.out.println("Success");
        } else{
            System.out.println("File Already Exists");
        }
        OutputStream fw = new FileOutputStream(file);
        fw.write(byteArray);
        return new Packet(file);
    }



}
