import Service.Corba;

public class ClientServerToSendFile extends Thread {
    String args[];
    String clientName;

    public ClientServerToSendFile(String clientName){
        this.clientName = clientName;
    }

    public void run()
    {
        try
        {
            Corba corba = new Corba(args);
            corba.initPoa();
            corba.saveFileInServerNaming(clientName);
            corba.activePoa();
            corba.run();
        }
        catch (Exception e)
        {
            System.out.println ("Não foi possível iniciar o servidor para compartilhar os arquivos.");
        }
    }
}
