package Collections;

import java.util.HashMap;
import java.util.Map;

public class TestHashMap {

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "один");
        map.put(2, "два");
        map.put(3, "три");

        // Entry - в англ это объект ключ\значение, entrySet() - получаем все множество объектов Entry из мар
        for (Map.Entry<Integer, String> entry: map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

    }


}
