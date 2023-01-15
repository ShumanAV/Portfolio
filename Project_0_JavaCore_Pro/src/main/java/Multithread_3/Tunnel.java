package Multithread_3;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    private static Semaphore SMP;

    public static void setSMP(Semaphore SMP) {
        Tunnel.SMP = SMP;
    }

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car car) {
        try {
            try {
                System.out.println(car.getName() + " готовится к этапу(ждет): " + description);
                SMP.acquire();
                System.out.println(car.getName() + " начал этап: " + description);
                Thread.sleep(length / car.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(car.getName() + " закончил этап: " + description);
                win(this, car);
                SMP.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

