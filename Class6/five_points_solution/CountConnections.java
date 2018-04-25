import java.rmi.*;

public class CountConnections
  extends java.rmi.server.UnicastRemoteObject
  implements CountConnectionsInterface{

  private int n;


  public CountConnections(int n) throws RemoteException{
    this.n = n;
  }

  public CountConnections() throws RemoteException{
    this.n = 0;
  }

  public int countMeIn() throws RemoteException{
      n++;
      return n;
  }

  public int dontCountMe() throws RemoteException{
    return n;
  }


}
