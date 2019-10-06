import Service.*;
import ListManager.*;

public class Client{

    public static void main(String args[]){
        try {
            Corba corba = new Corba(args);
            Manager manager = corba.getManagerObjectReference();

            String teste = manager.add("teste", "teste2");
            System.out.println(teste);

        }catch (Exception e) {
            System.out.println("Outro Erro : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}
