package Collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HashCode_Equals {

    public static void main(String[] args) {

        Map<Person, String> map = new HashMap<>();

        Set<Person> set = new HashSet<>();

        Person person1 = new Person(1, "Puka");
        Person person2 = new Person(1, "Pukan");

        map.put(person1, "привет");
        map.put(person2, "привет");

        set.add(person1);
        set.add(person2);

        System.out.println(map);
        System.out.println(set);

    }

}


class Person {

    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Collections.Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // метод сравнения объектов по полям, очень долгий по сравнению с hashCode(), нужен тогда когда объекты разные а hashCode() говорит, что одинаковые
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        return name != null ? name.equals(person.name) : person.name == null;
    }

    // преобразование объектов в целые числа [object] -> [int], для быстрого сравнения объектов
    // бывают случаи когда объекты совершенно разные а их hashCode одинаковый, такие ситуации называются коллизией,
    // это происходит из-за того, что hashCode имеет ограничения из-за расчетов и из-за выходного числа фиксированной длинны
    // в момент возникновения коллизии (когда hashCode объектов равен) вызывается метод equals, который даст точный ответ

    // контракт hashCode() equals()
    // 1. у двух проверяемых объектов первым вызывается метод hashCode(), если hashCode разные, то объекты точно разные, equals в этом случае не вызывается
    // 2. если хэши одинаковые, то это может означать две ситуации:
    //      - когда объекты разные и возникла коллизия
    //      - когда объекты одинаковые
    // чтобы сравнить объекты вызывается метод equals()
    // этот механизм называется контрактом

    // Есть два принципа для самостоятельной генерации функции hashCode():
    //  - она должна быть очень быстрой
    //  - функция должна обеспечивать мин кол-во коллизий
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}