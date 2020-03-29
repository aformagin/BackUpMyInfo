package client;

import org.apache.commons.io.FilenameUtils;
import server.Packet;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ClientWindow extends JFrame {
    private JButton uploadBtn, openFilesBtn, scheduleBtn;
    private JList<?> list;
    private JFileChooser fileChooser;
    private JTextField username;
    private JPasswordField password;
    private Queue<Packet> q;

    private JPanel entryFields, fileDisplay, buttonArea;

    public ClientWindow() {
        entryFields = new JPanel();
        fileDisplay = new JPanel();
        buttonArea = new JPanel();

        q = new LinkedList<>();


        uploadBtn = new JButton("Upload");
        openFilesBtn = new JButton("Open/Select Folder");

        username = new JTextField("username");
        password = new JPasswordField("Password");

        list = new JList<String>();

        //Fields
        entryFields.add(username);
        entryFields.add(password);
        //File List Display
        fileDisplay.add(list);
        //Buttons
        buttonArea.add(uploadBtn);
        buttonArea.add(openFilesBtn);
        setSize(new Dimension(800, 800));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel p = new JPanel();//Temporary JPanel to test adding the other panels
        p.add(entryFields);
        p.add(fileDisplay);
        p.add(buttonArea);
        add(p);

        setVisible(true);


        //Action Listeners

        openFilesBtn.addActionListener(e ->{
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                //TODO Set these to a variable to work with
                System.out.println("getCurrentDirectory(): "
                        + fileChooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : "
                        + FilenameUtils.getName(String.valueOf(fileChooser.getSelectedFile())));

                populateQueue(fileChooser.getSelectedFile());
            }
            else {
                System.out.println("No Selection ");
            }
        });
    }

    public static void main(String[] args) {
    }

    public Queue<Packet> populateQueue(File args){
        File selectedDir = args; //This is the initial directory
        //System.getproperty(os.name)
        //Listing the initial file and folder list
        System.out.println(Arrays.toString(selectedDir.listFiles()));


        ArrayList<File> dirList = new ArrayList<>(new LinkedList<>(Arrays.asList(selectedDir.listFiles())));

        //Sorting through directory lists, and adding them to dirList
        for(int x = 0; x < dirList.size(); x++){
            File tempFile = dirList.get(x);
            if(tempFile.isDirectory()){
                System.out.println(tempFile.listFiles());
                for(int y = 0; y < tempFile.listFiles().length; y++){
                    if(!tempFile.listFiles().equals(null)){
                        System.out.println("Adding folder");
                        dirList.add(tempFile.listFiles()[y]);
                    }
                }
            }


            else if(tempFile.isFile() && !tempFile.equals(null)){
                try {

                    String lookFor = selectedDir.getName();
                    String givenPath = "backup/"; //Default path

                    //Truncating the file path for relative uses
                    if(tempFile.getPath().contains(lookFor)){
                        int n = tempFile.getPath().indexOf(lookFor);
                        givenPath = tempFile.getPath().substring(n);
                    }
                    givenPath = FilenameUtils.getPath(givenPath);
                    System.out.println(givenPath);
                    //Creating the packet of data to be sent
                    System.out.println(tempFile.getName());
                    Packet p = new Packet(tempFile, givenPath);
                    String testString = p.getFilePath(); //DBS


                    q.add(p);
                    System.out.println("Adding File"); //DBS
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

    public JButton getUploadBtn(){
        return this.uploadBtn;
    }

    public Queue<Packet> getQ(){
        return q;
    }
}
