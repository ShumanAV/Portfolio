package Collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayList_VS_LinkedList {

//    head -> [1] -> [2] -> [3] -> [4] - реализация linkedList (LL), элементы в [i] - называются узлами, head - головной узел,
//    он содержит ссылку на первый и последний элементы, поэтому для вставки в начало элемента, в LL просто в head меняется
//    ссылка на первый элемент, а в первом элементе, который стал вторым меняется ссылка на предыдущий элемент,
//    поэтому это гораздо быстрее чем в AL, т.к. в AL неоьбходимо было переносить все элементы вправо на один элемент,
//    метод get(i) гораздо быстрее в AL, чем в LL, т.к. в AL мы получаем элемент по номеру индекса,
//    а в LL приходиться добираться до этого элемента каждый раз проходя с начала

    private static int NUMBER_ELEMENTS = 100000;

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        fillList(arrayList);
        fillList(linkedList);

        editList(arrayList);
        editList(linkedList);

    }

    private static void fillList(List<Integer> list) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_ELEMENTS; i++) {
            list.add(0, i);
        }
        System.out.println("Лист: " + list.getClass().getSimpleName() + ", Время заполнения листа: " + (System.currentTimeMillis() - a));
    }

    private static void editList(List<Integer> list) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_ELEMENTS; i++) {
            list.get(i);
        }

        System.out.println("Лист: " + list.getClass().getSimpleName() + ", Время редактирования листа: " + (System.currentTimeMillis() - a) + ", размер листа: " + list.size());
    }


}
