import java.rmi.registry.*;

public class DeployCountConnections{

  public static void main (String [] args ) throws Exception{
      Registry r = LocateRegistry.createRegistry(12344);
      r.rebind("StartFromFive", new CountConnections(5));
      r.rebind("StartFromZero", new CountConnections(0));

  }

}
