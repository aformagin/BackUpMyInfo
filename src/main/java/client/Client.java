package client;

import server.Packet;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Client {
    private static int PORT;
    private static String IP_ADDRESS;
    private static ClientWindow window;

    public Client(String IP_ADDRESS, int PORT) {

        //Variable declaration

        window = new ClientWindow();
        window.getUploadBtn().addActionListener(ex->{
            Socket socket = null;
            DataOutputStream dataOutputStream;
            Queue<Packet> fileQueue = new LinkedList<>();
            fileQueue = window.getQ();
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
                        p.send(dataOutputStream);
                    }
                    if(fileQueue.isEmpty()){
                        dataOutputStream.close();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                  
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    public static void main(String[] args){

        //Let's get down to work!
        //Temp IP and PORT numbers
        IP_ADDRESS = "192.168.0.44"; //Temporary
        PORT = 25565; //Nice.

        Client c = new Client(IP_ADDRESS, PORT);


    }
}
