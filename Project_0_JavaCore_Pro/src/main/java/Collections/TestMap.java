package Collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class TestMap {

    public static void main(String[] args) {

        Map<Integer, String> hashMap = new HashMap<>(); // не гарантирует никакого порядка

        Map<Integer, String> linkedHashMap = new LinkedHashMap<>(); // гарантирует порядок добавления/возврата

        Map<Integer, String> treeMap = new TreeMap<>();     // можно проводить сортировку по ключу

        fillMap(hashMap);
        fillMap(linkedHashMap);
        fillMap(treeMap);

    }

    private static void fillMap(Map<Integer, String> map) {
        map.put(1, "Pupok");
        map.put(10, "Puk");
        map.put(25, "Perd");
        map.put(100, "Puka");
        map.put(1500, "Pika");
        map.put(55, "Pipa");

        for (Map.Entry<Integer,String> m: map.entrySet()) {
            System.out.println(m.getKey() + "=" + m.getValue());
        }
    }

}
