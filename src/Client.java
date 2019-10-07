import Service.*;
import ListManager.*;

public class Client{

    public static void main(String args[]){
        Corba corba = new Corba(args);
        Manager manager = corba.getManagerObjectReference();

        manager.addClient("Cliente1");
        manager.addFileToClient("Cliente1", "file1.txt");
        manager.addFileToClient("Cliente1", "file2.txt");
        manager.addClient("Cliente2");
        manager.addFileToClient("Cliente2", "file1.txt");

        manager.printList();

    }
}
