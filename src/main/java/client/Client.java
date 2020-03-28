package client;

import org.apache.commons.io.FilenameUtils;
import server.Packet;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    private static int PORT;
    private static String IP_ADDRESS;
    public static void main(String[] args) throws InterruptedException {
        //Temp IP and PORT numbers
        IP_ADDRESS = "192.168.0.44"; //Temporary
        PORT = 25565; //Nice.

        //Variable declaration
        Socket socket = null;
        DataOutputStream dataOutputStream;

        Queue<Packet> fileQueue = new LinkedList<>();

        //Let's get down to work!
        /*
        We're going to take in the files as arguments for the time being
         */
//        File f = new File(args[0]);
//        //Listing the initial file and folder list
//        System.out.println(Arrays.toString(f.listFiles()));
//
//
//        ArrayList<File> dirList = new ArrayList<>(new LinkedList<>(Arrays.asList(f.listFiles())));
//        //TODO Convert to an external method
//        for(int x = 0; x < dirList.size(); x++){
//            File temp = dirList.get(x);
//            if(temp.isDirectory()){
//                System.out.println(temp.listFiles());
//                for(int y = 0; y < temp.listFiles().length; y++){
//                    if(!temp.listFiles().equals(null)){
//                        System.out.println("Adding folder");
//                        dirList.add(temp.listFiles()[y]);
//                    }
//                }
//            }
//
//            else if(temp.isFile() && !temp.equals(null)){
//                try {
//                    Packet p = new Packet(temp);
//                    fileQueue.add(p);
//                    System.out.println("Adding File");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            else{
//                System.err.println("Invalid File");
//            }
//            System.out.println(x);
//        }

        fileQueue = populateQueue(args);

        for(int attempts = 0; attempts < 5; attempts++){
            try {
                System.out.println("Awaiting connection...");
                socket = new Socket(IP_ADDRESS, PORT);
                System.out.println("CONNECTED TO SERVER: " + IP_ADDRESS + ":" + PORT);
                int size = fileQueue.size();

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeInt(size);//Sending number of files in queue


                for(int x = 0; x < size; x++){
                    Packet p = fileQueue.remove();
                    System.out.println("PATH: " + p.getFilePath() +
                            "\nSIZE: " + p.getFileSize() +
                            "\nNAME: " + p.getFilename());
                    dataOutputStream.writeUTF(p.getFilePath());//File path
                    dataOutputStream.writeInt(p.getFileSize()); //Size
                    dataOutputStream.writeUTF(p.getFilename());//Name of file
                    dataOutputStream.write(p.getByteArray());//ByteArray
                }
                if(fileQueue.isEmpty()){
                    dataOutputStream.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }

    }

    public static Queue<Packet> populateQueue(String args[]){
        File f = new File(args[0]);
        Queue<Packet> q = new LinkedList<>();

        //Listing the initial file and folder list
        System.out.println(Arrays.toString(f.listFiles()));


        ArrayList<File> dirList = new ArrayList<>(new LinkedList<>(Arrays.asList(f.listFiles())));
        for(int x = 0; x < dirList.size(); x++){
            File temp = dirList.get(x);
            if(temp.isDirectory()){
                System.out.println(temp.listFiles());
                for(int y = 0; y < temp.listFiles().length; y++){
                    if(!temp.listFiles().equals(null)){
                        System.out.println("Adding folder");
                        dirList.add(temp.listFiles()[y]);
                    }
                }
            }

            else if(temp.isFile() && !temp.equals(null)){
                try {
                    Packet p = new Packet(temp);
                    q.add(p);
                    System.out.println("Adding File");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.err.println("Invalid File");
            }
            System.out.println(x);
        }

        return q;
    }
}
