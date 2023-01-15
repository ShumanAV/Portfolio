package Multithread_2;

public class WaitNotifyClass {

    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {

        WaitNotifyClass w = new WaitNotifyClass();
        Thread t1 = new Thread(() -> w.printA());
        Thread t2 = new Thread(() -> w.printB());
        t1.start();
        t2.start();

    }

    public void printA() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 3; i++) {
                    System.out.printf("PrintA, начало цикла %d: %s %n", i, Thread.currentThread().getName());
                    Thread.sleep(500);
                    while (currentLetter != 'A') {
                        System.out.println("PrintA, поток остановлен: " + Thread.currentThread().getName());
                        mon.wait();
                    }
                    currentLetter = 'B';
                    System.out.println("PrintA, currentLetter = В: " + Thread.currentThread().getName());
                    mon.notify();
                    System.out.println("PrintA, запуск notify(): " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("PrintA, завершение синх блока: " + Thread.currentThread().getName());
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 3; i++) {
                    System.out.printf("PrintB, начало цикла %d: %s %n", i, Thread.currentThread().getName());
                    Thread.sleep(500);
                    while (currentLetter != 'B') {
                        System.out.println("PrintВ, поток остановлен: " + Thread.currentThread().getName());
                        mon.wait();
                    }
                    currentLetter = 'A';
                    System.out.println("PrintВ, currentLetter = А: " + Thread.currentThread().getName());
                    mon.notify();
                    System.out.println("PrintB, запуск notify(): " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("PrintB, завершение синх блока: " + Thread.currentThread().getName());
        }
    }

}