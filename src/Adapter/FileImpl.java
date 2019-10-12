package Adapter;

import PeerToPeer.*;
import PeerToPeer.File;

import java.io.*;

public class FileImpl extends FilePOA {

    public byte[] download(String fileName){

        int i;
        String sValue="";


        java.io.File file = new java.io.File("./Public/nomes.txt");
        byte buffer[] = new byte[(int) file.length()];

        try
        {
            FileInputStream fn= new FileInputStream("./Public/nomes.txt");
            BufferedInputStream input = new BufferedInputStream(fn);

            input.read(buffer, 0, buffer.length);
            input.close();

            //transformar em um array de bytes e não em uma string, e retornar esse array de bytes.
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
