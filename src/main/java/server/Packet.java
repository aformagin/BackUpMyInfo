package server;


import org.apache.commons.io.FilenameUtils;

import java.io.*;


public class Packet {

    private int chunkSize;
    private File file;
    private String filePath;    //Includes / at end
    private long fileSize;
    private String filename;
    private byte[] byteArray;
    private BufferedInputStream in;

    public Packet(String filename, String filePath) throws IOException {
        this.filename = filename;
        File file = new File(this.filename);
        this.filePath = filePath;
        if(!file.exists()) throw new IOException();
        this.fileSize = file.length();
        in = new BufferedInputStream(new FileInputStream(file));
    }

    public Packet(File file) throws IOException {
        if(!file.exists()) throw new IOException();
        in = new BufferedInputStream(new FileInputStream(file));
        this.filePath = FilenameUtils.getPath(file.getPath());
        this.filename = file.getName();

        //Can be up to 9.22e18
        this.fileSize = file.length();
    }

    public void send(DataOutputStream out) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(this.file));

        out.writeUTF(this.filePath);//File path
        out.writeLong(this.fileSize); //Size
        out.writeUTF(this.filename);//Name of file

        //Chunk Scaling:
        // If file is large, use larger chunk size to reduce # of ops

        //If file is less than 5MB
        if(fileSize<5000000){
            chunkSize = 5000000;
        } else if(5000000 < fileSize && fileSize < 50000000){   //If file is between 5-50MB
            chunkSize = 50000000;
        } else{         //If file is between 50-500MB
            chunkSize = 500000000;
        }

        byteArray = new byte[chunkSize];
        while(in.available() > 0){
            //TODO: Read and write bytes from file to stream
            in.read(byteArray);
            out.write(byteArray);
        }
    }

    public static void createFile(String filename, String filePath, byte[] byteArray) throws IOException {
        File dir = new File(filePath);
        if(dir.mkdirs()){
            System.out.println("Created Directories");
        }
        File file = new File(filePath + filename);
        if(file.createNewFile()) {
            System.out.println("Created File");
        }
        OutputStream fw = new FileOutputStream(file);
        fw.write(byteArray);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
