package Multithread;

import java.util.Random;
import java.util.concurrent.*;

public class MyCallable {

    public static void main(String[] args) {

        // Callable и Future используется для возврата из потоков значений, Callable параметризированный тем типом, который возвращаем,
        // получаем объект из Callable при помощи объекта Future, далее метод .get()
        // также Callable и Future позволяют выбрасывать исключения из потоков и ловить их в потоке main
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Starting");
                Thread.sleep(1000);
                System.out.println("Finished");

                Random random = new Random();
                int number = random.nextInt(10);

                if (number < 5) {
                    throw new Exception("произошла ошибка");
                }
                return number;
            }
        });

        try {
            System.out.println(future.get());   // метод get() останавливает поток main, пока будет выполнен поток-0 и вернется объект Future
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {    // в данном блоке ExecutionException (выполнение исключения) - из объекта Future ловим исключение из потока-0
            Throwable ex = e.getCause();    //здесь получаем причину нашего исключения
            System.out.println(ex.getMessage());    // получаем сообщение из исключения
        }

        service.shutdown();

    }


}
