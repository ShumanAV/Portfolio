package Multithread;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Pattern_ProducerConsumer {

    /*
        Многопоточность
        в пакете java.util.concurrent находятся много потокобезопасных классов
        Паттерн производитель - потребитель (producer - consumer), одни потоки производят, другие потребляют это
    */

    // ArrayBlockingQueue работает по принципу FIFO, у нее есть определенная емкость

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });                                             // это поток производитель
        Thread thread2 = new Thread(() -> {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });                                              // это поток потребитель

        thread1.start();
        thread2.start();

        thread1.join();     // поток Main ждет выполнения потока 1 и 2
        thread2.join();

    }

    public static void produce() throws InterruptedException {      // метод для производителя
        Random random = new Random();

        while (true) {
            queue.put(random.nextInt(100));    // метод put() предназначен для работы с множеством потоков
        }
    }

    // consumer берет элемент из очереди (как-будто сервер отрабатывает запрос от пользователя при ограничениях в 10 запросов),
    // метод put() будет ждать до тех пор, пока метод consumer не достанет элемент из очереди,
    // в нашем случае consumer это ПК, который считает арифметическую операцию, дает ответ пользователю и убирает из нашей очереди это запрос,
    // после этого put() мгновенно добавляет еще один элемент в очередь и снова ждет
    public static void consumer() throws InterruptedException {     // метод для потребителя
        Random random = new Random();

        while (true) {
            Thread.sleep(100);

            if (random.nextInt(10) == 5) {
                System.out.println(queue.take());
                System.out.println("Queue size is " + queue.size());
            }
        }

    }

}
