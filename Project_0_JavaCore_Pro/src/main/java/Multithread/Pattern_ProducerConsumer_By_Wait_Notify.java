package Multithread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Pattern_ProducerConsumer_By_Wait_Notify {

    public static void main(String[] args) throws InterruptedException {

        ProducerConsumer pc = new ProducerConsumer();

        Thread thread1 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });                                             // это поток производитель
        Thread thread2 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });                                              // это поток потребитель

        thread1.start();
        thread2.start();

        thread1.join();     // поток Main ждет выполнения потока 1 и 2
        thread2.join();

    }

}

class ProducerConsumer {

    private Queue<Integer> queue = new LinkedList<>();
    private final int LIMIT = 10;  // ограничение кол-во целых чисел в очереди
    private final Object lock = new Object();

    public void produce() throws InterruptedException{

        Random random = new Random();

        while (true) {
            synchronized (lock) {
                while (queue.size() == LIMIT) {
                    lock.wait();
                }
                queue.add(random.nextInt(100));
                lock.notify();
            }
            Thread.sleep(500);
        }

    }

    public synchronized void consume() throws InterruptedException{

        while (true) {
            synchronized (lock) {
                while (queue.size() == 0) {
                    lock.wait();
                }
                System.out.println("забрал " + queue.poll() + " из " + queue.size());
                lock.notify();
            }
            Thread.sleep(1000);
        }

    }
}
