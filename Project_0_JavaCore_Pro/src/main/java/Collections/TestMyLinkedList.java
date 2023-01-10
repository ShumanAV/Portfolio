package Collections;

public class TestMyLinkedList {

    public static void main(String[] args) {

        MyLinkedList list = new MyLinkedList();
        list.add(15);
        list.add(5);
        list.add(25);
        list.add(35);
        list.add(55);
        System.out.println(list.getSize());
        System.out.println(list.get(3));
        System.out.println(list);
        list.remove(2);
        list.remove(2);
        list.remove(0);

        System.out.println(list);
        System.out.println(list.getSize());

    }

}
