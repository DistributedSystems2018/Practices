import java.io.*;
import java.util.*;
import java.net.*;

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

    
        }catch(Exception e){
          System.out.println(e);
        }
    }


}
