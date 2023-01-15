package Multithread_3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {

    private static CyclicBarrier CB;
    private static CountDownLatch CDL;
    private static int CARS_COUNT;
    private static Race RACE;
    private int speed;
    private String name;

    public static void setCb(CyclicBarrier cb) {
        Car.CB = cb;
    }

    public static void setCDL(CountDownLatch CDL) {
        Car.CDL = CDL;
    }

    public static void setRACE(Race RACE) {
        Car.RACE = RACE;
    }

    public static Race getRACE() {
        return RACE;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(int speed) {
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            CDL.countDown();
            CB.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < RACE.getStages().size(); i++) {
            RACE.getStages().get(i).go(this);
        }
        CDL.countDown();
    }

}
