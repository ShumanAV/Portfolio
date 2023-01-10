package Collections;

import java.util.*;

public class SortNaturalOrder {

    public static void main(String[] args) {

        List<Person> personList = new ArrayList<>();
        List<Person> linkedList = new LinkedList<>();
        Set<Person> personSet = new TreeSet<>();
        Set<Person> linkedHashSet = new LinkedHashSet<>();

        addElements(personList);
        addElements(linkedList);
        addElements(personSet);
        addElements(linkedHashSet);

        // при реализованном интерфейсе Comparable методе compareTo не нужно добавлять в метод sort новый объект с сортировкой,
        // как в случае с Comparator, поэтому она и называется естественный порядок
        Collections.sort(personList);
        Collections.sort(linkedList);

        System.out.println(personList);
        System.out.println(linkedList);
        System.out.println(personSet);
        System.out.println(linkedHashSet);

    }

    static void addElements(Collection collection) {
        collection.add(new Person(10, "Pukan"));
        collection.add(new Person(1, "Kakana"));
        collection.add(new Person(3, "Konusok"));
        collection.add(new Person(2, "Perdolet"));
    }

    // Comparable - с англ "годящийся для сравнения", сравнивает один объект с каким-то другим
    // делает сортировку по умолчанию - Естественный порядок, необходима при добавлении собственных объектов в TreeSet,
    // т.к. они сразу должны сортироваться
    private static class Person implements Comparable<Person>{

        private int id;
        private String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            if (id != person.id) return false;
            return name != null ? name.equals(person.name) : person.name == null;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Collections.Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        // сортировка по id
//        @Override
//        public int compareTo(Collections.Person o) {
//            if (this.id > o.id) {
//                return 1;
//            }
//            if (this.id < o.id) {
//                return -1;
//            }
//            return 0;
//        }

        // сортировка по длине имени
        @Override
        public int compareTo(Person o) {
            if (this.id > o.id) {
                return 1;
            }
            if (this.id < o.id) {
                return -1;
            }
            return 0;
//            if (this.name.length() > o.name.length()) {
//                return 1;
//            }
//            if (this.name.length() < o.name.length()) {
//                return -1;
//            }
//            return 0;
        }

    }

}
