package LambdaExpressions;

import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;

import java.util.*;
import java.util.stream.Collectors;

public class Lambda_Stream {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        int[] arr = new int[10];

        fillList(list);
        fillArray(arr);

        // сделаем тоже самое только с помощью лямбды, для этого нам потребуется привратить массив и лист в другую сущность - в поток,
        // для этого используется статический метод stream()

        // map() - метод берет каждый элемент потока и сопостовляет ему новый элемент, вот это сопоставление и нужно описать в методе map(),
        // map - переводится как отображение, пришло из теории множеств, мы берем оригинальное множество и по какому-то правилу сопоставляем другому множеству
        // [1,2,3] -> [2,4,6]
        // map() возвращает поток, поэтому чтобы его преобразовать обратно в массив нужно вызвать метод toArray(),
        // для листа метод collect(Collectors.toList())

        arr = Arrays.stream(arr).map(a -> a * 2).toArray();
        list = list.stream().map(a -> a * 2).collect(Collectors.toList());

        System.out.println(list);
        System.out.println(Arrays.toString(arr));

        // filter - в теле ЛВ указывается условие, которое возвращает true или false, когда true это число остается в результате работы фильтра, false нет
        List<Integer> list2 = new ArrayList<>();
        int[] arr2 = new int[10];

        fillList(list2);
        fillArray(arr2);

        arr2 = Arrays.stream(arr2).filter(a -> a % 2 == 0).toArray();
        list2 = list2.stream().filter(a -> a % 2 == 0).collect(Collectors.toList());

        System.out.println(list);
        System.out.println(Arrays.toString(arr2));

        //foreach - встроенный цикл, проходит по элементам массива/листа
        // когда внутри цикла foreach одна только команда, можно еще сократить код
        Arrays.stream(arr2).forEach(System.out::println);
        list2.stream().forEach(System.out::println);

        // reduce (acc, b) - уменьшение, превращение потока в какое-то число,
        // где acc - аккумулятор - накапливаемое итоговое значение, b - текущий элемент на каждой итерации
        List<Integer> list3 = new ArrayList<>();
        int[] arr3 = new int[10];

        fillList(list3);
        fillArray(arr3);

        //посчитаем сумму массива, методы getAsInt() и get() возвращают int
        // без указания начального значения acc, он приравнивается первому значению, а нумерация итерации начинается со второго значения,
        // можно установить начальное значение acc - .reduce(0, (acc, b) -> acc + b), в этом случае итерация начнется с первого значения
        int sum = Arrays.stream(arr3).reduce((acc, b) -> acc + b).getAsInt();
        int mul = list3.stream().reduce((acc, b) -> acc * b).get();

        System.out.println(sum);
        System.out.println(mul);

        // лямбда с set
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set = set.stream().map(a -> a * 3).collect(Collectors.toSet());
        System.out.println(set);

        // лямбда с map не показывал
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        String str = map.entrySet().stream().findAny().toString();
        System.out.println(str);

    }

    private static void fillArray(int[] arr) {
        for (int i = 0; i < 10; i++) {
            arr[i] = i + 1;
        }
    }

    private static void fillList(List<Integer> list) {
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
    }

}
