package Annotation;

public class TestThread {

    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();

        System.out.println("Привет из потока Main");

    }

}

class MyThread extends Thread{

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Привет из моего потока");
    }
}
