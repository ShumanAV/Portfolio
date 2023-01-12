package Training_Jenerics_Lesson11;

import java.util.Arrays;

public class Task_1_App <T> {

    public static void main(String[] args) {

        Integer[] iarr = {1,2,3,4,5,6,7};
        String[] sarr = {"1","2","3","4","5","6","7"};
        Float[] farr = {1f,2f,3f,4f,5f,6f,7f};
        Character[] carr = {'1','2','3','4','5','6','7'};

        Task_1 task_1 = new Task_1();
        System.out.println(Arrays.toString(task_1.changeArr(1,2, iarr)));
        System.out.println(Arrays.toString(task_1.changeArr(1,2, sarr)));
        System.out.println(Arrays.toString(task_1.changeArr(1,2, farr)));
        System.out.println(Arrays.toString(task_1.changeArr(1,2, carr)));

        System.out.println(task_1.arrInList(iarr));
        System.out.println(task_1.arrInList(sarr));
        System.out.println(task_1.arrInList(farr));
        System.out.println(task_1.arrInList(carr));

    }

}
