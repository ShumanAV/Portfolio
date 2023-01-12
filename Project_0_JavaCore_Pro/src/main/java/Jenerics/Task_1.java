package Training_Jenerics_Lesson11;

import java.util.ArrayList;
import java.util.Collections;

public class Task_1 <T extends Object> {

    private T[] arr;

    public T[] changeArr(int i, int j, T... arr) {
        T buffer = arr[i];
        arr[i] = arr[j];
        arr[j] = buffer;
        return arr;
    }

    public ArrayList<T> arrInList(T... arr) {
        ArrayList<T> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, arr);
        return arrayList;
    }

}
