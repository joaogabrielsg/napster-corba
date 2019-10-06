import Service.*;

public class Server {
    public static void main(String args[]) {
        Corba corba = new Corba(args);
        corba.initPoa();
        corba.saveManagerInServerNaming();
        corba.activePoa();
        corba.run();
    }
}
