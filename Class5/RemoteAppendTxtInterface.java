
// Remote Method Invocation

import java.rmi.*;

// The Interface should extend java.rmi.Remote
public interface RemoteAppendTxtInterface extends Remote
{
    // Just the method signature
    String appendTxt(String str) throws RemoteException;
}
