package Multithread_2;

public class Example_3_Sync {        //в качестве монитора выступает сам класс, т.к. метод synchronized статический

    public static void main(String[] args) {

        System.out.println("Start");
        new Thread(() -> method()).start();
        new Thread(() -> method()).start();
        System.out.println("End");

    }

    public synchronized static void method() {

        System.out.println("Начало синхронизированного блока: " + Thread.currentThread().getName());
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Конец синхронизированного блока: " + Thread.currentThread().getName());

    }

}
