
import java.rmi.registry.*;

public class RMIDeploy
{
    public static void main(String args[])
        throws Exception
    {
        final int PORT = 12345;

        Registry registry = LocateRegistry.createRegistry(PORT);
        registry.rebind("rmiAppendBla", new RemoteAppendTxtServer("Bla"));
        registry.rebind("rmiAppendXYZ", new RemoteAppendTxtServer("XYZ"));
     }
}
