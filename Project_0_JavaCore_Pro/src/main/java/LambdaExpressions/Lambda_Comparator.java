package LambdaExpressions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Lambda_Comparator {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        list.add("Bye bye my friend");
        list.add("Goodbye");
        list.add("Hello");
        list.add("Hey");

        list.sort((o1, o2) -> {
            if (o1.length() > o2.length()) {
                return 1;
            }
            if (o1.length() < o2.length()) {
                return -1;
            }
            return 0;
        });
        System.out.println(list);

        // лямбду можно присваивать переменной
        Comparator<String> comparator = (o1, o2) -> {
            if (o1.length() < o2.length()) {return 1;}
            if (o1.length() > o2.length()) {return -1;}
            return 0;
        };

        list.sort(comparator);
        System.out.println(list);



    }

}
