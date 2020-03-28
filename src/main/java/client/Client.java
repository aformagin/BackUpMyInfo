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
        PORT = 6969; //Nice.

        //Variable declaration
        Socket socket = null;
        DataOutputStream dataOutputStream;

        Queue<Packet> fileQueue = new LinkedList<>();//TODO change the object type to custom type later

        //Let's get down to work!
//        System.out.println(FilenameUtils.getBaseName(args[0]));
        /*
        We're going to take in the files as arguments for the time being
         */
        File f = new File(args[0]);
        //Listing the initial file and folder list
        System.out.println(Arrays.toString(f.listFiles()));


//        List<File> dirList = new LinkedList<File>(Arrays.asList(f.listFiles()));
        ArrayList<File> dirList = new ArrayList<>(new LinkedList<>(Arrays.asList(f.listFiles())));
        //TODO Convert to an external method
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
            //TODO - Figure out why it isn't listing the files
            else if(temp.isFile() && !temp.equals(null)){
                try {
                    Packet p = new Packet(temp);
                    fileQueue.add(p);
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


//        for (int x = 0; x < size; x++){
//            System.out.println(fileQueue.peek());
//            if(!fileQueue.isEmpty())
//                fileQueue.remove();
//        }

        //END OF TESTING AREA
//        System.exit(1);

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
                    System.out.println(p.getFilePath());
                    dataOutputStream.writeUTF(p.getFilePath());//File path
                    dataOutputStream.writeInt(p.getFileSize()); //Size
                    dataOutputStream.writeUTF(p.getFilename());//Name of file
                    dataOutputStream.write(p.getByteArray());//ByteArray

                    if(fileQueue.isEmpty()){
                        dataOutputStream.close();

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }

    }
}
