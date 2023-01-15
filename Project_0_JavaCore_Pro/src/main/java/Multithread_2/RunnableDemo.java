package Multithread_2;

public class RunnableDemo {

    public static void main(String[] args) {
        RunnableClass rc = new RunnableClass();
        new Thread(rc).start();
        try {
            System.out.printf("Поток уснул на %dмс: %s %n", 800, Thread.currentThread().getName());
            Thread.sleep(800);
            rc.mySuspend();
            System.out.printf("Поток уснул на %dмс: %s %n", 3000, Thread.currentThread().getName());
            Thread.sleep(3000);
            rc.myResume();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class RunnableClass implements Runnable {

        boolean suspended = false;

        public void run() {
            System.out.println("Запуск потока: " + Thread.currentThread().getName() + Thread.currentThread().getState());
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.printf("Цикл %d,поток уснул на %d: %s %n", i, 300, Thread.currentThread().getName() + Thread.currentThread().getState());
                    Thread.sleep(300);
                    synchronized (this) {
                        while (suspended) {
                            System.out.println("Поток остановился: " + Thread.currentThread().getName() + Thread.currentThread().getState());
                            wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Поток прерван: " + Thread.currentThread().getName() + Thread.currentThread().getState());
            }
            System.out.println("Завершение потока: " + Thread.currentThread().getName() + Thread.currentThread().getState());
        }

        public void mySuspend() {
            suspended = true;
        }

        public synchronized void myResume() {
            suspended = false;
            notify();
        }

    }

}
