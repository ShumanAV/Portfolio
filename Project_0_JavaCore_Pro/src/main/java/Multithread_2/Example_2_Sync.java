package Training_Multithreading1_Lesson12;

public class Example_2_Sync {

    private Object lock1 = new Object();    //в качестве монитора выступает объект типа Object

    public static void main(String[] args) {

        Example_2_Sync e2 = new Example_2_Sync();
        System.out.println("Старт main потока");
        new Thread(() -> e2.method1()).start();
        new Thread(() -> e2.method1()).start();

    }

    public void method1() {

        System.out.println("Метод запущен: " + Thread.currentThread().getName());

        System.out.println("Блок 1 начало: " + Thread.currentThread().getName());
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Блок 1 конец: " + Thread.currentThread().getName());

        synchronized (lock1) {      //создаем блок синхронизации
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

        System.out.println("Метод завершил свою работу: " + Thread.currentThread().getName());
    }
}

