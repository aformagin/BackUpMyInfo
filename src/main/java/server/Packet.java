package server;

import java.io.*;

public class Packet {

    private File file;
    private String filePath;    //Includes / at end
    private int fileSize;
    private String filename;
    private byte[] byteArray;
    private BufferedInputStream in;

    public Packet(String filename, String filePath) throws IOException {
        this.filename = filename;
        this.filePath = filePath;
        File file = new File(this.filename);
        if(!file.exists()) throw new IOException();
        in = new BufferedInputStream(new FileInputStream(file));
        this.fileSize = (int) file.length();
        byteArray = new byte[this.fileSize];
        byteArray = in.readNBytes(fileSize);

    }

    public Packet(File file) throws IOException {
        if(!file.exists()) throw new IOException();
        in = new BufferedInputStream(new FileInputStream(file));
        this.filePath = file.getPath();
        this.filename = file.getName();
        this.fileSize = (int) file.length();
        byteArray = new byte[this.fileSize];
        byteArray = in.readNBytes(fileSize);
    }

    public static Packet createPacket(String filename, String filePath, byte[] byteArray) throws IOException {
        File file = new File(filename);
        if(file.createNewFile()){
            System.out.println("Success");
        } else{
            System.out.println("File Already Exists");
        }
        OutputStream fw = new FileOutputStream(file);
        fw.write(byteArray);
        return new Packet(file);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileSize() {
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
