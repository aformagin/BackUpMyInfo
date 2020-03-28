package main.java.client;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    private static int PORT;
    private static String IP_ADDRESS;
    public static void main(String[] args) {
        //Temp IP and PORT numbers
        IP_ADDRESS = "thirdspare.com"; //Temporary
        PORT = 6969; //Nice.

        //Variable declaration
        Socket socket = null;
        FileInputStream fileInputStream;
        OutputStream outputStream;
        DataOutputStream dataOutputStream;
        Queue<File> fileQueue = null;//TODO change the object type to custom type later

        //Let's get down to work!
//        System.out.println(FilenameUtils.getBaseName(args[0]));
        /*
        We're going to take in the files as arguments for the time being
         */
        File f = new File(args[0]);
        System.out.println(FilenameUtils.getBaseName(args[0]));
        System.out.println(FilenameUtils.getFullPath(args[0]));
        System.out.println(Arrays.toString(f.listFiles()));


        List<File> dirList = new LinkedList<File>(Arrays.asList(f.listFiles()));

        //TODO remove so many gets, add local variable
        for(int x = 0; x < dirList.size() - 1; x++){
            File temp = dirList.get(x);
            if(temp.isDirectory()){
                System.out.println(temp.listFiles());
                for(int y = 0; y < temp.listFiles().length - 1; y++){
                    System.out.println("Adding");
                    if(!temp.listFiles().equals(null))
                        dirList.add(temp.listFiles()[y]);
                    System.out.println("Added");
                }
            }
            else{
                System.out.println(dirList.get(x).getName());
            }
        }
        System.out.println(dirList.toString());
        System.exit(1);

        /*
        OBJECT NAME: Packet(File)/(Filename, filepath)
        File
        File Path - String
        Size
        Name
        ByteArray
        */

        //Adding files to the queue
        for(int x = 0; x < args.length; x++){
            File tempFile = new File(args[x]);
            fileQueue.add(tempFile);
        }
        try {
            System.out.println("Awaiting connection...");
            socket = new Socket(IP_ADDRESS, PORT);
            System.out.println("CONNECTED TO SERVER: " + IP_ADDRESS + ":" + PORT);


            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(fileQueue.size());//Sending number of files in queue
            for(int x = 0; x < fileQueue.size(); x++){
                dataOutputStream.writeUTF("filepath");//File path
                dataOutputStream.writeInt(1); //Size
                dataOutputStream.writeUTF("Name");//Name of file
//                dataOutputStream.write();//ByteArray

            }
        } catch (IOException e) {
            e.printStackTrace();
        }





    }
}
