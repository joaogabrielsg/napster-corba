import Service.*;
import ListManager.*;
import PeerToPeer.*;

import java.io.*;
import java.util.*;
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
        System.out.println("e - Pesquisar pelas substrings dos nomes dos arquivos ou pelas extensões");
        System.out.println("s - Sair");
    }

    public void downloadFile(String clientName, String fileName){
        System.out.println("Arquivo " + fileName + " esta sendo baixado");
        Corba corba = new Corba(new String[0]);
        PeerToPeer.File fileRef = corba.getFileObjectReference(clientName);

        byte[] data = fileRef.download(fileName);
        FileOutputStream fn = null;
        try {
            fn = new FileOutputStream( String.format("%s/%s", getPublicPath(), fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream output = new BufferedOutputStream(fn);
        try {
            output.write(data, 0, data.length);
            output.flush();
            output.close();
            removeAllFiles();
            getAllFiles();

            System.out.println("Arquivo baixado com sucesso");

        } catch (IOException e) {
            System.out.println("Não foi possível criar o arquivo de output");
        }
    }

    public void menuOptions(String input) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        switch (input){
            case "p":
                System.out.println("");
                System.out.println(manager.printList());
                break;
            case "b":
                System.out.println("");
                System.out.print("Nome do arquivo para baixar: ");
                String fileName = scanner.next();
                String[] clientsName = manager.clientsWhoHasTheFile(fileName);

                if(clientsName.length == 0){
                    System.out.println("");
                    System.out.println("Nenhum arquivo encontrado.");
                } else {
                    System.out.println("");
                    System.out.println("Clientes que possuem o arquivo:");

                    for (int i = 0; i < clientsName.length; i++){
                        System.out.println(clientsName[i]);
                    }

                    System.out.println("");
                    System.out.print("Escolha o cliente para baixar o arquivo:");
                    String clientName = scanner.next();

                    downloadFile(clientName, fileName);
                }
                break;
            case "m":
                System.out.println("");
                System.out.print("Caminho da pasta compartilhada: ");
                String newPublicPathName = scanner.next();

                changePublicPath(newPublicPathName);
                break;
            case "e":
                System.out.println("");
                System.out.print("Buscar por:");
                String subString = scanner.next();
                System.out.println("");
                System.out.println("Arquivos encontrados:");
                System.out.println(manager.printFilesHasSubString(subString));
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
