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

    private Client(){
        Corba corba = new Corba(new String[0]);
        manager = corba.getManagerObjectReference();

        clientName = manager.generateNewClientName();

        corba.initPoa();
        corba.saveFileInServerNaming(clientName);

        ClientServerToSendFile clientServer = new ClientServerToSendFile(clientName);
        clientServer.start();

        manager.addClient(clientName);
        getAllFiles();
    }

    public String getPublicPath() {
        Corba corba = new Corba(new String[0]);
        PeerToPeer.File clientRef = corba.getFileObjectReference(clientName);

        return clientRef.getPublicPath();
    }

    public void changePublicPath(String newPathName){
        Corba corba = new Corba(new String[0]);
        PeerToPeer.File clientRef = corba.getFileObjectReference(clientName);

        removeAllFiles();

        clientRef.changePublicPath(newPathName);

        getAllFiles();
    }

    public void getAllFiles(){
        final File folder = new File(getPublicPath());
        List<String> result = new ArrayList<>();

        search(folder, result);
        for (String s : result) {
            manager.addFileToClient(clientName, s);
        }
    }

    public void removeAllFiles(){
        final File folder = new File(getPublicPath());
        List<String> result = new ArrayList<>();

        search(folder, result);
        for (String s : result) {
            manager.removeFileFromClient(clientName, s);
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

    public static void downloadFile(String clientName, String fileName){
        Corba corba = new Corba(new String[0]);
        PeerToPeer.File fileRef = corba.getFileObjectReference(clientName);


        byte[] data = fileRef.download(fileName);
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

            System.out.println("Arquivo baixado com sucesso");

        } catch (IOException e) {
            System.out.println("Não foi possível criar o arquivo de output");
        }
    }

    public void menuOptions(String input) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        switch (input){
            case "p":
                System.out.println(manager.printList());
                break;
            case "b":
                System.out.print("Nome do arquivo para baixar: ");
                String fileName = scanner.next();
                String clientName = manager.clientWhoHasTheFile(fileName);

                Client.downloadFile(clientName, fileName);
                break;
            case "m":
                System.out.print("Caminho da pasta compartilhada: ");
                String newPublicPathName = scanner.next();

                changePublicPath(newPublicPathName);
                break;
        }
    }

    public static void main(String[] args){
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        Boolean openMenu = true;

        System.out.println(client.clientName);

        while(openMenu){
            client.printMenu();
            String input = scanner.next();

            if (input.equals("s")){
                openMenu = false;
            } else {
                try {
                    client.menuOptions(input);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
