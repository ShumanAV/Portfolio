package Training_Jenerics_Lesson11;

public class App {

    public static void main(String[] args) {

        Generics<Integer> igen = new Generics<>(155, 2, 2, 2, 3, 4);
        Generics<Float> fgen = new Generics<>(155.01f, 2.04f, 2.3f, 2.3f, 3.2f, 4.1f);

        System.out.println("igen.avg(): " + igen.avg());
        System.out.println("fgen.avg(): " + fgen.avg());
        System.out.println("igen == fgen: " + igen.sameAvg(fgen));

    }

}
