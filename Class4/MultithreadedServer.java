
import java.util.*;
import java.io.*;
import java.net.*;

class MultithreadedServer
{
    public static void main(String[] args) throws Exception {
        int port = 12345;
        ServerSocket ss = new ServerSocket(port);

        while(true){
          // No try with resources here guys
          try{
            Socket s  = ss.accept();
            System.out.println(s);

            new Thread(new Server(s)).start();
          }catch(Exception e){}
        }
    }
}


class Server extends Thread{
    Socket s;
    Scanner sc ;
    PrintWriter pw;
    ObjectInputStream ois;


    public Server(Socket s) throws Exception{
      sc = new Scanner(s.getInputStream());
      pw = new PrintWriter(s.getOutputStream());
      ois = new ObjectInputStream(s.getInputStream());
    }

    public void run(){
        try{
          Person p;
          p =  (Person) ois.readObject();
          System.out.println("cool");
        }catch(Exception e){
          System.out.println("not cool");
        }




        for (int i = 0; i < 10; ++i)
        {
          pw.println("hi");
          pw.flush();
        }

        try{
          s.close();
          System.out.println("Finished Thread");
        }catch(IOException e){
          System.out.println("Thread Finished, socket couldn't be closed");
        }

    }
}
