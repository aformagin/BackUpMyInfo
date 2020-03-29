package server;

import java.io.*;
import java.net.Socket;

public class PacketReader extends Thread{

    private int numberOfFiles;
    private long fileSize;
    private String fileName;
    private String filePath;
    private DataInputStream in;

    public PacketReader(Socket clientSocket) throws IOException {
        this.in = new DataInputStream(clientSocket.getInputStream());
    }

    //run() method for Thread
    public void run(){
        try {
            //Read number of files from input stream
            numberOfFiles = in.readInt();
            System.out.println("Number of Files: " + numberOfFiles);

            //For all files in the stream
            for (int x = 0; x < numberOfFiles; x++) {
                System.out.println("File #" + (x+1) + ":");
                filePath = "backup/" + in.readUTF();            //Read file path, relative folder named backup/
                System.out.println("File Path: " + filePath);
                fileSize = in.readLong();                       //Read file size
                System.out.println("File Size: " + fileSize);
                fileName = in.readUTF();                        //Read file name
                System.out.println("File Name: " + fileName);

                //Create File object to check if the parent directories of file exist or not
                File dir = new File(filePath);
                if(dir.mkdirs()){
                    System.out.println("Created Directories");
                }

                //Create File object to check if the file exists or not
                File file = new File(filePath + fileName);
                if(file.createNewFile()) {
                    System.out.println("Created File");
                }

                //Output stream to the file
                OutputStream fw = new FileOutputStream(file);

                //Write bytes to the file
                for(long i = 0; i < fileSize; i++){
                    fw.write(in.readByte());
                }
                fw.close();
            }
            //Close input stream
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
