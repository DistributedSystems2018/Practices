
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
    ObjectOutputStream outObject;

    public Server(Socket s) throws Exception{
      sc = new Scanner(s.getInputStream());
      pw = new PrintWriter(s.getOutputStream());
      this.s = s;
      ois = new ObjectInputStream(s.getInputStream());
      outObject = new ObjectOutputStream(s.getOutputStream());
    }

    public void run(){
        try{
          Person p;
          Person p2;
          p =  (Person) ois.readObject();
          p2 = (Person) ois.readObject();
          System.out.println(p2.getName());
          p2.setFamilyName(p2.getFamilyName()+" "+p.getFamilyName());
          p2.setMessage("happy wedding");

          outObject.writeObject(p2);
          s.close();
        }catch(Exception e){
          System.out.println(e);
        }




        // for (int i = 0; i < 10; ++i)
        // {
        //   pw.println("hi");
        //   pw.flush();
        // }

        // try{
        //   s.close();
        //   System.out.println("Finished Thread");
        // }catch(IOException e){
        //   System.out.println("Thread Finished, socket couldn't be closed");
        // }

    }
}
