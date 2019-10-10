package Adapter;

import ListManager.*;
import Objects.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ManagerImpl extends ManagerPOA{
    public List<ClientsFiles> files = new ArrayList<ClientsFiles>();

    public void addClient(String client){
        ClientsFiles clientsFiles = new ClientsFiles(client);
        files.add(clientsFiles);
    }

    public void removeClient(String client){
        for (int i = 0; i < files.size(); i++){
            if (files.get(i).name.equals(client)){
                files.remove(i);
            }
        }
    }

    public void addFileToClient(String client, String fileName){
        files.forEach(file -> {
            if (file.name.equals(client)){
                file.addFile(fileName);
            }
        });
    }

    public void removeFileFromClient(String client, String fileName){
        files.forEach(file -> {
            if (file.name.equals(client)){
                file.removeFile(fileName);
            }
        });
    }

    public String clientWhoHasTheFile(String fileName){
        AtomicReference<String> clientNameWhoHasTheFile = new AtomicReference<>("");
        files.forEach(file -> {
            if (file.hasFile(fileName)){
                clientNameWhoHasTheFile.set(file.name);
            }
        });
        return clientNameWhoHasTheFile.get();
    }

    public String generateNewClientName(){
        return "Client" + String.format("%d", files.size() + 1);
    }

    public void printList() {
        files.forEach(file -> {
            System.out.println(file.toString());
        });
    }
}