import java.io.*;
import java.util.*;
import java.net.*;

public class Server{

  public static void main(String args[]){
      Object lock = new Object();
      try{
        ServerSocket ss = new ServerSocket(12345);
        while (true){
            Socket s = ss.accept();
            new Thread(new ServerThread(lock, s)).start();
        }

      }catch(Exception e){
        System.out.println(e);
      }
  }
}

class ServerThread extends Thread{
    Object lock;
    Socket s;

    public ServerThread(Object lock, Socket s){
        this.lock = lock;
        this.s = s;
    }

    public void run(){
      try{
        Scanner scanner = new Scanner(s.getInputStream());
        String text = scanner.nextLine();

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

        if(text.equals("ready")){
          ConnectionToRMI c1 = new ConnectionToRMI("localhost",12345,"something");
          ConnectionToRMI c2 = new ConnectionToRMI("localhost",12345,"something");

          synchronized(lock){
            oos.writeObject(c1);
            oos.writeObject(c2);
          }


        }

      }catch(Exception e){
        System.out.println(e);
      }
    }
}
