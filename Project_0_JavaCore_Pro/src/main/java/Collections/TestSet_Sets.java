package Collections;

import java.util.HashSet;
import java.util.Set;

public class TestSet_Sets {

    public static void main(String[] args) {

        Set<Integer> hashSet_1 = new HashSet<>();
        hashSet_1.add(0);
        hashSet_1.add(1);
        hashSet_1.add(2);
        hashSet_1.add(3);
        hashSet_1.add(4);
        hashSet_1.add(5);

        Set<Integer> hashSet_2 = new HashSet<>();
        hashSet_2.add(3);
        hashSet_2.add(4);
        hashSet_2.add(5);
        hashSet_2.add(6);
        hashSet_2.add(7);

        Set<Integer> union = new HashSet<>(hashSet_1);
        union.addAll(hashSet_2);

        //union - объединение множеств
        System.out.println(union);

        //intersection - пересечение множеств, оставляет в intersection все элементы, которые есть в hashSet_2
        Set<Integer> intersection = new HashSet<>(hashSet_1);
        intersection.retainAll(hashSet_2);
        System.out.println(intersection);

        //difference - разность множеств из difference вычитаем hashSet_2
        Set<Integer> difference = new HashSet<>(hashSet_1);
        difference.removeAll(hashSet_2);
        System.out.println(difference);






    }


}
