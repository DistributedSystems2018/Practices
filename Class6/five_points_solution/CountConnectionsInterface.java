import java.rmi.*;

public interface CountConnectionsInterface extends Remote{
  public int countMeIn() throws RemoteException;
  public int dontCountMe() throws RemoteException;
}
