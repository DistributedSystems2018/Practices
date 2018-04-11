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


        Person p = new Person("josh", "smith");
        Person p2 = new Person("amanda", "whatever");

        try {
            Socket s = new Socket(computer, port);


            ObjectOutputStream oos
              = new ObjectOutputStream(s.getOutputStream());

            ObjectInputStream inputObject
              = new ObjectInputStream(s.getInputStream());
            // = new ObjectOutputStream(new FileOutputStream("a.txt"));
            oos.writeObject(p);
            oos.writeObject(p2);
            oos.flush();
            
            p2 = (Person) inputObject.readObject();
            System.out.println(p2.getFamilyName());
            //pw.println(p);
            //pw.flush();



            //int textLength = sc.nextInt();
            //  System.out.println(textLength);
        }catch(Exception e){

        }

    }
}
