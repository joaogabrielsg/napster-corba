import Service.*;
import ListManager.*;
import PeerToPeer.*;

import java.io.*;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Client{
    Manager manager;
    String clientName;
    String publicPath;

    private Client(String args[]){
        Corba corba = new Corba(args);
        manager = corba.getManagerObjectReference();
        publicPath = "./Public";

        clientName = manager.generateNewClientName();

        corba.initPoa();
        corba.saveFileInServerNaming(clientName);

        manager.addClient(clientName);
        getAllFiles();

        manager.printList();
    }

    public void getAllFiles(){
        final File folder = new File(publicPath);
        List<String> result = new ArrayList<>();

        search(folder, result);
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
        System.out.println("b - Baixar um arquivo");
        System.out.println("m - Mudar a pasta compartilhada");
        System.out.println("s - Sair");
    }

    public static void downloadFile(String clientName, String fileName, String args[]){
        Corba corba = new Corba(args);
        PeerToPeer.File fileRef = corba.getFileObjectReference(clientName);


        byte data[] = fileRef.download(fileName);
        FileOutputStream fn = null;
        try {
            fn = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream output = new BufferedOutputStream(fn);
        try {
            output.write(data, 0, data.length);
            output.flush();
            output.close();

        } catch (IOException e) {
            System.out.println("Não foi possível criar o arquivo de output");
        }
    }

    public void menuOptions(String input, String args[]) throws FileNotFoundException {
        switch (input){
            case "p":
                manager.printList();
                break;
            case "r":
                Scanner scanner = new Scanner(System.in);

                System.out.print("Nome do arquivo para baixar: ");
                String fileName = scanner.next();
                String clientName = manager.clientWhoHasTheFile(fileName);

                Client.downloadFile(clientName, fileName, args);
                break;
            case "m":
                break;
        }
    }

    public static void main(String args[]){
        Client client = new Client(args);
        Scanner scanner = new Scanner(System.in);
        Boolean openMenu = true;

        System.out.println(client.clientName);

        ClientServerToSendFile clientServer = new ClientServerToSendFile(client.clientName);
        clientServer.start();

        while(openMenu){
            client.printMenu();
            String input = scanner.next();

            if (input.equals("s")){
                openMenu = false;
            } else {
                try {
                    client.menuOptions(input, args);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
