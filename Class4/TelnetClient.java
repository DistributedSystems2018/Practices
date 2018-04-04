// Code from Professor Kitley Robert


import java.util.*;
import java.io.*;
import java.net.*;

class TelnetClient
{
    public static void main(String[] args)
        throws Exception
    {
        String machine = args[0];
        int    port    = Integer.parseInt(args[1]);

        try (
            Socket s = new Socket(machine, port);
            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            Thread t1 = new ClientToSrvThread(pw);
            Thread t2 = new SrvToClientThread(sc);

            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }
    }
}

class ClientToSrvThread extends Thread
{
    PrintWriter pw;

    ClientToSrvThread(PrintWriter pw)
    {
        this.pw = pw;
    }

    public void run()
    {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            pw.println(line);
            pw.flush();
        }
        // pw.flush();
    }
}

class SrvToClientThread extends Thread
{
    Scanner sc;

    SrvToClientThread(Scanner sc)
    {
        this.sc = sc;
    }

    public void run()
    {
        while (true)
        while (sc.hasNextLine()) {

            String sor = sc.nextLine();
            System.out.println(sor);
            System.out.flush();
        }
        //System.out.println("dead!");
    }
}
