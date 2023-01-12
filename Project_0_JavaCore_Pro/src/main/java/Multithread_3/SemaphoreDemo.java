package Training_Multithreading2_Lesson13;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore smp = new Semaphore(2);
        for (int i = 0; i < 5; i++) {
            final int w = i;
            System.out.println(Thread.currentThread().getName() + " цикл " + i);
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " перед семафором");
                    System.out.println(Thread.currentThread().getName() + ". Доступно потоков перед acquire() в smp " + smp.availablePermits());
                    smp.acquire();
                    System.out.println(Thread.currentThread().getName() + " получил доступ к ресурсу");
                    System.out.println(Thread.currentThread().getName() + ". Доступно потоков после acquire() в smp " + smp.availablePermits());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " освободил ресурс");
                    smp.release();
                    System.out.println(Thread.currentThread().getName() + ". Доступно потоков после release() в smp " + smp.availablePermits());
                }
            }).start();
        }
        System.out.println(Thread.currentThread().getName() + " завершил работу");

    }

}
