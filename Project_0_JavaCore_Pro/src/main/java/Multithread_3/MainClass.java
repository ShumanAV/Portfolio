package Training_Multithreading2_Lesson13;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {

    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        Semaphore smp = new Semaphore(CARS_COUNT / 2);
        Tunnel.setSMP(smp);

        Race race = new Race(new Road(60), new Tunnel(), new Road(40), new Tunnel(), new Road(100));
        Car.setRACE(race);

        CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
        Car.setCDL(cdl);

        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
        Car.setCb(cb);

        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        // main поток ждет выполнения всеми остальными потоками выполнения подготовки к гонке, чтобы сделать объявление о начале гонки
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        // создаем новый cdl объект с новым счетчиком, передаем его в Car, чтобы main  поток дождался прохождения всей трассы всеми потоками и сделал объявление о завершении гонки
        cdl = new CountDownLatch(CARS_COUNT);
        Car.setCDL(cdl);
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

    }

}

