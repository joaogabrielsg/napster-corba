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

    public void menuOptions(String input, String args[]) throws FileNotFoundException {
        switch (input){
            case "p":
//                manager.printList();
                System.out.println(manager.clientWhoHasTheFile("nomes.txt"));
                break;
            case "r":
                Corba corba = new Corba(args);
                String clientName = manager.clientWhoHasTheFile("nomes.txt");
                PeerToPeer.File fileRef = corba.getFileObjectReference(clientName);


                byte data[] = fileRef.download("nomes.txt");
                FileOutputStream fn = new FileOutputStream("nomes.txt");
                BufferedOutputStream output = new BufferedOutputStream(fn);
                System.out.println(data[0]);
                try {
                    output.write(data, 0, data.length);
                    output.flush();
                    output.close();

                } catch (IOException e) {
                    System.out.println("Não foi possível criar o arquivo de output");
                }

//                java.io.File file = new java.io.File("./Public/nomes.txt");


//                System.out.println(fileRef.download("nomes.txt"));

//                FileOutputStream fout= null;
//                try {
//                    fout = new FileOutputStream("./Download/file.txt");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                //Write the file contents to the new file created
//                new PrintStream(fout).println (fileRef.download("nomes.txt"));
//                try {
//                    fout.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
////                System.out.println(file);
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
