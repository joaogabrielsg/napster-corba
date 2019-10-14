package Adapter;

import PeerToPeer.*;
import PeerToPeer.File;

import java.io.*;

public class FileImpl extends FilePOA {
    String publicPath = "./Public";

    public String getPublicPath(){
        return publicPath;
    }

    public void changePublicPath(String newPathName){
        publicPath = newPathName;
    }

    public byte[] download(String fileName){

        int i;
        String sValue="";


        java.io.File file = new java.io.File(String.format("%s/%s", publicPath, fileName));
        byte buffer[] = new byte[(int) file.length()];

        try
        {
            FileInputStream fn= new FileInputStream(String.format("%s/%s", publicPath, fileName));
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
