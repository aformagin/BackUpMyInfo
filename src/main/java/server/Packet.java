package server;

import java.io.*;

public class Packet {

    private File file;
    private String filePath;    //Needs / at end
    private long fileSize;
    private String filename;
    private byte[] byteArray;
    private DataInputStream in;

    public Packet(File file, String filePath) throws IOException {
        if(!file.exists()) throw new IOException();             //Checks if file exists, if not, throw IOException
        in = new DataInputStream(new FileInputStream(file));    //Stream Reader
        this.filename = file.getName();                         //Sets file name
        this.filePath = filePath;                               //Sets file path
        this.fileSize = file.length();                          //Sets file size
    }

    //Sends file path, size, name, and data into an output stream.
    public void send(DataOutputStream out) throws IOException {
        out.writeUTF(this.filePath);            //Send file path
        out.writeLong(this.fileSize);           //Send file size
        out.writeUTF(this.filename);            //Send file name

        //Send bytes from file to stream
        for(long i = 0; i < fileSize; i++){
            out.writeByte(in.readByte());
        }
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
