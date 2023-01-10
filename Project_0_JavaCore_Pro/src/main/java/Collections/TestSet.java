package Collections;

import java.util.*;

public class TestSet {

    public static void main(String[] args) {

        Set<String> hashSet = new HashSet<>();  // нет порядка

        Set<String> linkedHashSet = new LinkedHashSet<>();  // сохраняется порядок добавления

        Set<String> treeSet = new TreeSet<>();  // сортировка объектов

        fillSet(hashSet);
        fillSet(linkedHashSet);
        fillSet(treeSet);

    }

    private static void fillSet(Set<String> set) {
        set.add("Pupok");
        set.add("Puk");
        set.add("Perd");
        set.add("Puka");
        set.add("Pika");
        set.add("Pipa");
        set.add("Pipa");
        set.add("Pipa");

        System.out.println(set.toString());

        for (String s : set) {
            System.out.println("Лист: " + set.getClass().getSimpleName() + " " + s);
        }

        // метод нахождения очень быстрый в отличии от коллекций типа List, там нужно перебирать все элементы
        System.out.println(set.contains("Perd"));
        System.out.println(set.contains("Prod"));
        System.out.println(set.contains("Pipa"));
    }


}
