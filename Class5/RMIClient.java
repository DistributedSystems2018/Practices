import java.rmi.*;
import java.rmi.registry.*;

public class RMIClient
{
    public static void main(String args[])
        throws Exception
    {
        String srvAddr = "localhost";
        int srvPort    = 12345;
        String srvName = args[0];
        String text    = args[1];

        Registry registry = LocateRegistry.getRegistry(srvAddr, srvPort);

        String [] registers = registry.list();
        for(String a : registers)
            System.out.println(a);

        RemoteAppendTxtInterface rmiServer =
            (RemoteAppendTxtInterface)(registry.lookup(srvName));

        String reply = rmiServer.appendTxt(text);
        
        System.out.println(reply);
    }
}
