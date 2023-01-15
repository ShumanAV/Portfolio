package Multithread_2;

public class Example_1_Sync {

    public static void main(String[] args) {
        Example_1_Sync e1 = new Example_1_Sync();         //в качестве монитора выступает данный объект
        new Thread(() -> e1.method1()).start();
        new Thread(() -> e1.method2()).start();
    }

    public synchronized void method1() {
        System.out.println("M1");
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("M2");
    }

    public synchronized void method2() {
        System.out.println("M1");
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("M2");
    }

}
