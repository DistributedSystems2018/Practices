
import java.rmi.*;

// Our Server

public class RemoteAppendTxtServer
    extends java.rmi.server.UnicastRemoteObject
    // from the documentation
      //Used for exporting a remote object with JRMP and
      // obtaining a stub that communicates to the remote object.

    implements RemoteAppendTxtInterface
{
    String appendTxt;

    public RemoteAppendTxtServer(String appendTxt) throws RemoteException
    {
        this.appendTxt = appendTxt;
    }

    public String appendTxt(String str) throws RemoteException
    {
        return str + appendTxt;        
    }
}
