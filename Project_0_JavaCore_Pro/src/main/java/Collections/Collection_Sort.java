package Collections;

import java.util.*;

public class Collection_Sort {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("zebra");
        list.add("cat");
        list.add("goose");
        list.add("dog");
        list.add("horse");
        list.add("chimpanzee");
        list.add("rabbit");
        list.add("z");

        // метод sort сортирует по уже реализованному методу compare()
        Collections.sort(list);
        System.out.println(list);

        // метод sort сортирует по нашей логике по реализованному нами методу compare(), для этого передаем в него объект с реализацией Comparator
        Collections.sort(list, new StringLengthComparator());
        System.out.println(list);

        // в случае если мы не планируем много раз использовать реализованный Comparator, можно сделать
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() > o2.length()) {
                    return -1;
                }
                if (o1.length() < o2.length()) {
                    return 1;
                }
                return 0;
            }
        });
        System.out.println(list);

        List<MyPerson> listPerson = new ArrayList<>();
        listPerson.add(new MyPerson(23, "Pike"));
        listPerson.add(new MyPerson(10, "Puk"));
        listPerson.add(new MyPerson(35, "Jopa"));
        listPerson.add(new MyPerson(140, "Kakan"));
        listPerson.add(new MyPerson(3, "Cotaperd"));

        Collections.sort(listPerson, new MyPerson());
        System.out.println(listPerson);

    }

}

// Comparator - с англ "тот кто сравнивает" два объекта
class StringLengthComparator implements Comparator<String> {

    // конвенция для метода Comparator'a
    // O1 > O2 возращается 1
    // O1 < O2 возращается -1
    // O1 = O2 возращается 0
    @Override
    public int compare(String o1, String o2) {
        if (o1.length() > o2.length()) {
            return 1;
        }
        if (o1.length() < o2.length()) {
            return -1;
        }
        return 0;
    }

}

class MyPerson implements Comparator <MyPerson>{

    private int id;
    private String name;

    public MyPerson(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public MyPerson() {

    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compare(MyPerson o1, MyPerson o2) {
        if (o1.id > o2.id) {
            return 1;
        }
        if (o1.id < o2.id) {
            return -1;
        }
        return 0;
    }
}