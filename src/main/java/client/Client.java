package client;

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
        Queue<File> fileQueue = new LinkedList<>();

        //Let's get down to work!
//        System.out.println(FilenameUtils.getBaseName(args[0]));
        /*
        We're going to take in the files as arguments for the time being
         */
        File f = new File(args[0]);
        //Listing the initial file and folder list
        System.out.println(Arrays.toString(f.listFiles()));


        List<File> dirList = new LinkedList<File>(Arrays.asList(f.listFiles()));
        ArrayList<File> dirList1 = new ArrayList<>(new LinkedList<>(Arrays.asList(f.listFiles())));

        //TODO Convert to an external method
        for(int x = 0; x < dirList.size(); x++){
            File temp = dirList.get(x);
            if(temp.isDirectory()){
                System.out.println(temp.listFiles());
                for(int y = 0; y < temp.listFiles().length - 1; y++){
                    if(!temp.listFiles().equals(null)){
                        System.out.println("Adding folder");
                        dirList.add(temp.listFiles()[y]);
                    }
                }
            }
            //TODO - Figure out why it isn't listing the files
            else if(temp.isFile()){
                //Add to packetQueue
                System.out.println("Adding File");
                fileQueue.add(temp);
                System.out.println(dirList.get(x).getName());
            }
            else{
                System.err.println("Invalid File");
            }
            System.out.println(x);
        }
        System.out.println(dirList.toString());

        //END OF TESTING AREA
        System.exit(1);

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
