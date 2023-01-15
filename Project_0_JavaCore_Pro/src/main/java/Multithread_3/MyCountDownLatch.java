package Multithread_3;

import java.util.concurrent.CountDownLatch;

public class MyCountDownLatch {

    public static void main(String[] args) {

        final int THREADS_COUNT = 6;                                    // задаем количество потоков
        final CountDownLatch cdl = new CountDownLatch(THREADS_COUNT);   // задаем значение счетчика

        System.out.println("Начинаем");
        for (int i = 0; i < THREADS_COUNT; i++) {
            final int w = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " приступил к задаче");
                    Thread.sleep(500 + (int)(500 * Math.random()));       // считаем, что выполнение задачи занимает ~1 сек
                    System.out.println(Thread.currentThread().getName() + " выполнил задачу");
                    cdl.countDown();                                           // как только задача выполнена, уменьшаем счетчик
                    System.out.println(Thread.currentThread().getName() + " - готов");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        try {
            cdl.await();                           // пока счетчик не приравняется нулю, будем стоять на этой строке
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " Работа завершена");     // как только все потоки выполнили свои задачи - пишем сообщение

    }
}

