import java.io.*;
import java.util.*;
import java.net.*;

public class Server{

  public static void main(String args[]){
      try{
        ServerSocket ss = new ServerSocket(12345);
        Socket s = ss.accept();

        Scanner scanner = new Scanner(s.getInputStream());
        String text = scanner.nextLine();

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

        if(text.equals("ready")){
          ConnectionToRMI c1 = new ConnectionToRMI("localhost",12345,"something");
          ConnectionToRMI c2 = new ConnectionToRMI("localhost",12345,"something");

          oos.writeObject(c1);
          oos.writeObject(c2);

        }

      }catch(Exception e){
        System.out.println(e);
      }
  }
}
