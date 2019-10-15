package ListManager;


/**
* ListManager/ManagerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Adapter/ListManagerIDL.idl
* Segunda-feira, 14 de Outubro de 2019 23h08min44s BRT
*/

public abstract class ManagerPOA extends org.omg.PortableServer.Servant
 implements ListManager.ManagerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("addClient", new java.lang.Integer (0));
    _methods.put ("removeClient", new java.lang.Integer (1));
    _methods.put ("addFileToClient", new java.lang.Integer (2));
    _methods.put ("removeFileFromClient", new java.lang.Integer (3));
    _methods.put ("clientWhoHasTheFile", new java.lang.Integer (4));
    _methods.put ("clientsWhoHasTheFile", new java.lang.Integer (5));
    _methods.put ("generateNewClientName", new java.lang.Integer (6));
    _methods.put ("printList", new java.lang.Integer (7));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // ListManager/Manager/addClient
       {
         String client = in.read_string ();
         this.addClient (client);
         out = $rh.createReply();
         break;
       }

       case 1:  // ListManager/Manager/removeClient
       {
         String client = in.read_string ();
         this.removeClient (client);
         out = $rh.createReply();
         break;
       }

       case 2:  // ListManager/Manager/addFileToClient
       {
         String client = in.read_string ();
         String fileName = in.read_string ();
         this.addFileToClient (client, fileName);
         out = $rh.createReply();
         break;
       }

       case 3:  // ListManager/Manager/removeFileFromClient
       {
         String client = in.read_string ();
         String fileName = in.read_string ();
         this.removeFileFromClient (client, fileName);
         out = $rh.createReply();
         break;
       }

       case 4:  // ListManager/Manager/clientWhoHasTheFile
       {
         String fileName = in.read_string ();
         String $result = null;
         $result = this.clientWhoHasTheFile (fileName);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // ListManager/Manager/clientsWhoHasTheFile
       {
         String fileName = in.read_string ();
         String $result[] = null;
         $result = this.clientsWhoHasTheFile (fileName);
         out = $rh.createReply();
         ListManager.ManagerPackage.stringListHelper.write (out, $result);
         break;
       }

       case 6:  // ListManager/Manager/generateNewClientName
       {
         String $result = null;
         $result = this.generateNewClientName ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 7:  // ListManager/Manager/printList
       {
         String $result = null;
         $result = this.printList ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ListManager/Manager:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Manager _this() 
  {
    return ManagerHelper.narrow(
    super._this_object());
  }

  public Manager _this(org.omg.CORBA.ORB orb) 
  {
    return ManagerHelper.narrow(
    super._this_object(orb));
  }


} // class ManagerPOA
