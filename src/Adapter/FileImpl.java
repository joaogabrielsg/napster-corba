package Adapter;

import PeerToPeer.*;
import PeerToPeer.File;

import java.io.*;

public class FileImpl extends FilePOA {

    public byte[] download(String fileName){

        int i;
        String sValue="";


        java.io.File file = new java.io.File(String.format("./Public/%s", fileName));
        byte buffer[] = new byte[(int) file.length()];

        try
        {
            FileInputStream fn= new FileInputStream(String.format("./Public/%s", fileName));
            BufferedInputStream input = new BufferedInputStream(fn);

            input.read(buffer, 0, buffer.length);
            input.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Error");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
