package Collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyIterable {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // то, что дает понять java что это коллекция, что по этому объекту ArrayList можно пройтись циклом foreach - это и называется интерфейсом Iterable
        // Iterable в переводе с англ - то по чему можно итерироваться или проходиться
        // все классы коллекций реализуют интерфейс Iterable
        // для того чтобы проходиться по элементам собственного класса, нужно реализовать интерфейс Iterable
        // цикл foreach обеспечивает абстракцию над Iterator'ом, здесь мы не пишем Iterator, но внутри используется Iterator
        // эта запись идентична циклу foreach, только в итераторе мы можем удалять объекты прямо внутри Итератора, в цикле нельзя этого делать
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            iterator.remove();
        }

        for (Integer i: list) {
            System.out.println(i);
        }


    }

}
