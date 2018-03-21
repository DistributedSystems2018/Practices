
class Multithreaded
{
    public static void main(String[] args) throws Exception {
        Object lock = new Object();
        SynchronizedCounter s = new SynchronizedCounter();

        Thread t1 = new TextPrintThread("aaaaaaaa", lock, s);
        Thread t2 = new TextPrintThread("bbbbbbbb", lock, s);
        //new TextPrintThread("cccccccc", lock).start();
        //new TextPrintThread("xxxxxxxx", lock).start();
        t1.start();
        //t1.join();
        t2.start();
        //t2.join();
    }
}


// The class Thread already implements Runnable
// class TextPrintThread extends X implements Runnable
class TextPrintThread extends Thread {
    String text;
    Object lock;
    SynchronizedCounter s;
    public TextPrintThread(String text, Object lock, SynchronizedCounter s) {
        this.text = text;
        this.lock = lock;
        this.s = s;
    }

    public void run() {
        for (int i = 0; i < 10; ++i)
        {

            try{
              sleep(1);
            } catch (Exception e){}
            s.increment();
            //System.out.println(text);
            myPrintln(text);
        }
        System.out.println(s.value());
    }

//     void myPrintln(String text)
     synchronized void myPrintln(String text){
       //synchronized (this) {
          //synchronized (lock) {
             for (char c : text.toCharArray())
             {
                 System.out.print(c);
             }
             System.out.println();
         //}
    }
}
