package Adapter;

import ListManager.*;

public class ManagerImpl extends ManagerPOA{
    public String add(String client, String fileName){
        System.out.println(client);
        System.out.println(fileName);
        return "Arquivo adicionado";
    }
    public String remove(String client){
        return "Cliente removido";
    }
}