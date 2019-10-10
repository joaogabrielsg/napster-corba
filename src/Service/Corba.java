package Service;
import Adapter.*;
import ListManager.*;
import PeerToPeer.File;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;


public class Corba{
    private ORB orb;
    private NamingContext naming;
    private POA rootPOA;
    private Manager manager;
    private File file;

    public Corba(String orbArgs[]){
        try{
            orb = ORB.init(orbArgs, null);

            org.omg.CORBA.Object obj = this.orb.resolve_initial_references("NameService");
            naming = NamingContextHelper.narrow(obj);

        } catch (Exception ex){
            System.out.println("Erro ao iniciar o ORB");
            ex.printStackTrace();
        }
    }

    public void initPoa(){
        try{
            org.omg.CORBA.Object objPoa = orb.resolve_initial_references("RootPOA");
            this.rootPOA = POAHelper.narrow(objPoa);
        } catch (Exception ex){
            System.out.println("Erro ao iniciar o POA");
            ex.printStackTrace();
        }
    }

    public void activePoa(){
        try{
            this.rootPOA.the_POAManager().activate();
        } catch (Exception ex){
            System.out.println("Erro ao ativar o POA");
            ex.printStackTrace();
        }
    }

    public void saveManagerInServerNaming(){
        try {
            ManagerImpl managerImpl = new ManagerImpl();
            org.omg.CORBA.Object   objRef =	 this.rootPOA.servant_to_reference(managerImpl);
            NameComponent[] name = {new NameComponent("Manager","Exemplo")};
            this.naming.rebind(name,objRef);
        } catch (Exception ex){
            System.out.println("Erro ao salvar o manager no servidor de nomes");
            ex.printStackTrace();
        }
    }

    private void createManagerObjectReference(){
        try {
            NameComponent[] name = {new NameComponent("Manager","Exemplo")};
            org.omg.CORBA.Object objRef =  this.naming.resolve(name);
            this.manager = ManagerHelper.narrow(objRef);
        } catch (Exception ex) {
            System.out.println("Erro ao criar a referencia para o Manager");
            ex.printStackTrace();
        }
    }

    public Manager getManagerObjectReference(){
        this.createManagerObjectReference();
        return this.manager;
    }

    public void saveFileInServerNaming(String clientName){
        try {
            FileImpl fileImpl = new FileImpl();
            org.omg.CORBA.Object   objRef =	 this.rootPOA.servant_to_reference(fileImpl);
            NameComponent[] name = {new NameComponent(clientName,"Exemplo")};
            this.naming.rebind(name,objRef);
        } catch (Exception ex){
            System.out.println("Erro ao salvar o File no servidor de nomes");
            ex.printStackTrace();
        }
    }

    private void createFileObjectReference(String clientName){
        try {
            NameComponent[] name = {new NameComponent(clientName,"Exemplo")};
            org.omg.CORBA.Object objRef =  this.naming.resolve(name);
            this.manager = ManagerHelper.narrow(objRef);
        } catch (Exception ex) {
            System.out.println("Erro ao criar a referencia para o File");
            ex.printStackTrace();
        }
    }

    public File getFileObjectReference(String clientName){
        this.createFileObjectReference(clientName);
        return this.file;
    }


    public void run(){
        try {
            this.orb.run();
        } catch (Exception ex){
            System.out.println("Erro ao rodar o ORB");
            ex.printStackTrace();
        }
    }
}
