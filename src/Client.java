import Service.*;
import ListManager.*;
import java.util.Scanner;

public class Client{
    Manager manager;

    private Client(String args[]){
        Corba corba = new Corba(args);
        manager = corba.getManagerObjectReference();

        String clientName = manager.generateNewClientName();
        manager.addClient(clientName);
        manager.addFileToClient(clientName, "file1.txt");
    }

    public void printMenu(){
        System.out.println("-----------Napster------------");
        System.out.println("p - Printar lista de arquivos");
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
