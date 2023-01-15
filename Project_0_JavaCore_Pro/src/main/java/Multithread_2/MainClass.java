package Multithread_2;

public class MainClass {

    static class MyRunableClass implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {

        MyRunableClass myRunableClass_1 = new MyRunableClass();
        MyRunableClass myRunableClass_2 = new MyRunableClass();

        new Thread (myRunableClass_1).start();
        new Thread (myRunableClass_1).start();

//        new MyThread().start();
//        new MyThread().start();

    }

}
