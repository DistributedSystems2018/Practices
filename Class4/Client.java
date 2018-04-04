import java.util.*;
import java.io.*;
import java.net.*;

class Client
{
    public static void main(String[] args)
        throws Exception
    {
        int port = 12345;
        String computer = "localhost";


        Person p = new Person("josh");

        try (
            Socket s = new Socket(computer, port);

            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {

            /*
              ####
              Snippet for ObjectOutputStream
            */

            //ObjectOutputStream oos
            //= new ObjectOutputStream(s.getOutputStream());
            // = new ObjectOutputStream(new FileOutputStream("a.txt"));
            //oos.writeObject(p);
            //oos.flush();
            //oos.close();
            //pw.println(p);
            pw.flush();



            int textLength = sc.nextInt();
            System.out.println(textLength);
        }

    }
}
