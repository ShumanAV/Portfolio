package Training_Multithreading1_Lesson12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task_1 {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {

        Task_1 task_1 = new Task_1();
        task_1.arr_1();
        task_1.arr_2();

    }

    public void arr_1() {

        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.printf("arr_1(): поток: %s, время расчета: %d %n", Thread.currentThread().getName(), System.currentTimeMillis() - a);

    }

    public void arr_2() {

        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }

        float[] a1 = new float[SIZE];
        float[] a2 = new float[SIZE];

        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        System.out.printf("arr_2(): поток: %s, время разделения массивов: %d %n", Thread.currentThread().getName(), System.currentTimeMillis() - a);

        Task_1 task_1 = new Task_1();

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(new Runnable() {
            @Override
            public void run() {
                task_1.arr_2_1(a1);
            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                task_1.arr_2_1(a2);
            }
        });
        service.shutdown();

//        Thread t1 = new Thread(() -> task_1.arr_2_1(a1));
//        Thread t2 = new Thread(() -> task_1.arr_2_1(a2));
//        t1.start();
//        t2.start();

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(arr[5]);
        System.out.println(arr[100]);
        System.out.println(arr[1000]);

        a = System.currentTimeMillis();
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.printf("arr_2(): поток: %s, время склеивания массивов: %d %n", Thread.currentThread().getName(), System.currentTimeMillis() - a);

        System.out.println(arr[5]);
        System.out.println(arr[100]);
        System.out.println(arr[1000]);

    }

    public void arr_2_1(float[] arr) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.printf("arr_2_1(): поток: %s, время расчета: %d %n", Thread.currentThread().getName(), System.currentTimeMillis() - a);
    }

}
