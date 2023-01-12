package Training_Jenerics_Lesson11;

public class AppFruit {

    public static void main(String[] args) {

        Apple apple_1 = new Apple(5);
        Apple apple_2 = new Apple(2);
        Apple apple_3 = new Apple(3);
        Apple apple_4 = new Apple(6);
        Apple apple_5 = new Apple(11);

        Orange orange_1 = new Orange(2);
        Orange orange_2 = new Orange(4);
        Orange orange_3 = new Orange(5);
        Orange orange_4 = new Orange(6);
        Orange orange_5 = new Orange(1);

        Box<Apple> appleBox_1 = new Box<>();
        appleBox_1.add(apple_1);
        appleBox_1.add(apple_2);
        appleBox_1.add(apple_3);
        appleBox_1.add(apple_4);
        appleBox_1.add(apple_5);

        Box<Orange> orangeBox_1 = new Box<>();
        orangeBox_1.add(orange_1);
        orangeBox_1.add(orange_2);
        orangeBox_1.add(orange_3);
        orangeBox_1.add(orange_4);
        orangeBox_1.add(orange_5);

        System.out.println("Коробка яблок, вес: " + appleBox_1.getWeight());
        System.out.println("Коробка апельсин, вес: " + orangeBox_1.getWeight());
        System.out.println("Коробки равны по весу: " + appleBox_1.compare(orangeBox_1));

        Box<Apple> appleBox_2 = new Box<>();
        appleBox_2.add(apple_2);
        appleBox_2.add(apple_3);
        appleBox_2.add(apple_4);

        Box<Orange> orangeBox_2 = new Box<>();
        orangeBox_2.add(orange_1);
        orangeBox_2.add(orange_2);
        orangeBox_2.add(orange_3);

        System.out.println("Коробка яблок №2, вес: " + appleBox_2.getWeight());
        System.out.println("Коробка апельсин №2, вес: " + orangeBox_2.getWeight());
        System.out.println("Коробки равны по весу: " + appleBox_2.compare(orangeBox_2));

        appleBox_1.twoBoxInOne(appleBox_2);
        System.out.println("appleBox_1: " + appleBox_1.getWeight());
        System.out.println("appleBox_2: " + appleBox_2.getWeight());

        orangeBox_1.twoBoxInOne(orangeBox_2);
        System.out.println("orangeBox_1: " + orangeBox_1.getWeight());
        System.out.println("orangeBox_2: " + orangeBox_2.getWeight());



    }

}
