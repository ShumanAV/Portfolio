package Collections;

import java.util.Arrays;

// реализация односвязного листа LinkedList
public class MyLinkedList {

    private Node head;
    private int size;

    // добавляем новый элемент в конец, в первый раз приравниваем head Новому элементу, в последующие разы новый элемент записываем в next последнего элемента
    public void add(int value) {
        if (head == null) {
            head = new Node(value);
        } else {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(new Node(value));
//          [1] -> [2] -> [3]
        }
        size++;
    }

    // метод toString(), превращаем лист в массив, и возвращаем массив в виде строки
    @Override
    public String toString() {
        int[] arr = new int[size];
        int i = 0;
        Node temp = head;
        while (temp != null) {
            arr[i++] = temp.getValue();
            temp = temp.getNext();
        }
        return Arrays.toString(arr);
    }

    public int get(int index) {
        if (!validateIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        Node temp = head;
        while (temp != null) {
            if (i++ == index) {
                break;
            }
            temp = temp.getNext();
        }
        return temp.getValue();
    }

    public void remove(int index) {
        if (!validateIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
        int currentIndex = 0;
        Node temp = head;
        while (temp != null) {
            if (index == 0) {
                head = temp.getNext();
                size--;
                return;
            } else if (currentIndex == index - 1) {
                temp.setNext(temp.getNext().getNext());
                size--;
                return;
            }
            temp = temp.getNext();
            currentIndex++;
        }
    }

    private boolean validateIndex(int index) {
        if (index < size && index >= 0) {
            return true;
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    // класс узлов для листа
    private static class Node {

        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
