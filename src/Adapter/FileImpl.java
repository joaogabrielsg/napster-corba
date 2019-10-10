package Adapter;

import PeerToPeer.*;
import java.io.*;

public class FileImpl extends FilePOA {

    public String download(String fileName){

        int i;
        String sValue="";
        try
        {
            FileInputStream fn= new FileInputStream(fileName);

            do
            {
                i = fn.read();
                if(i!= -1)
                    sValue=sValue+((char)i);
            } while(i != -1);
            fn.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Error");
        }
        catch(Exception e){System.exit(0);}
        return sValue;
    }
}
