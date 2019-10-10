import Service.*;
import ListManager.*;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Client{
    Manager manager;
    String clientName;

    private Client(String args[]){
        Corba corba = new Corba(args);
        manager = corba.getManagerObjectReference();

        clientName = manager.generateNewClientName();

        corba.initPoa();
        corba.saveFileInServerNaming(clientName);

        manager.addClient(clientName);
        getAllFiles();

        manager.printList();
    }

    public void getAllFiles(){
        final File folder = new File("./Public");
        List<String> result = new ArrayList<>();

        search(folder, result);
        System.out.println(result.size());
        for (String s : result) {
            manager.addFileToClient(clientName, s);
        }
    }

    public static void search(final File folder, List<String> result) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                search(f, result);
            }

            if (f.isFile()) {
                result.add(f.getName());
            }
        }
    }

    public void printMenu(){
        System.out.println("-----------Napster------------");
        System.out.println("p - Printar lista de arquivos");
        System.out.println("r - Procurar por um arquivo");
        System.out.println("s - Sair");
    }

    public void menuOptions(String input){
        switch (input){
            case "p":
                manager.printList();
                break;
        }
    }

    public static void main(String args[]){
        Client client = new Client(args);
        Scanner scanner = new Scanner(System.in);
        Boolean openMenu = true;

        ClientServerToSendFile clientServer = new ClientServerToSendFile(client.clientName);
        clientServer.start();

        while(openMenu){
            client.printMenu();
            String input = scanner.next();

            if (input.equals("s")){
                openMenu = false;
            } else {
                client.menuOptions(input);
            }
        }

    }
}
