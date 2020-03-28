package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
        File file = new File(this.filePath + this.filename);
        if(!file.exists()) throw new IOException();
        in = new BufferedInputStream(new FileInputStream(file));
        this.fileSize = (int) file.length();
        byteArray = new byte[this.fileSize];
        byteArray = in.readNBytes(fileSize);

    }

    public Packet(File file) throws IOException {
        if(!file.exists()) throw new IOException();
        in = new BufferedInputStream(new FileInputStream(file));
        this.fileSize = (int) file.length();
        byteArray = new byte[this.fileSize];
        byteArray = in.readNBytes(fileSize);
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
