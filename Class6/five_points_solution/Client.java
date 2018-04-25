import java.io.*;
import java.util.*;
import java.net.*;
import java.rmi.registry.*;

public class Client{
    public static void main (String args[]){
      try{
          Socket s = new Socket("localhost", 12345);
          PrintWriter print = new PrintWriter(s.getOutputStream());
          print.println("ready");
          print.flush();

          ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
          ConnectionToRMI c1 = (ConnectionToRMI)ois.readObject();
          ConnectionToRMI c2 = (ConnectionToRMI)ois.readObject();

          System.out.println(c1);
          System.out.println(c2);

          Registry regClient1 = LocateRegistry.getRegistry(c1.getUrl(), c1.getPort());
          Registry regClient2 = LocateRegistry.getRegistry(c2.getUrl(), c2.getPort());

          CountConnectionsInterface count1  =
              (CountConnectionsInterface) regClient1.lookup(c1.getServiceName());

          System.out.println(count1.countMeIn());

          CountConnectionsInterface count2  =
              (CountConnectionsInterface) regClient1.lookup(c2.getServiceName());

          System.out.println(count2.dontCountMe());

        }catch(Exception e){
          System.out.println(e);
        }
    }


}
