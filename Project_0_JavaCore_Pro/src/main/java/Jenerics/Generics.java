package Training_Jenerics_Lesson11;

public class Generics <T extends Number> {

    private T[] obj;

    public Generics(T... obj) {
        this.obj = obj;
    }

    public double avg() {
        double sum = 0;
        for (int i = 0; i < obj.length; i++) {
            sum += obj[i].doubleValue();
        }
        return sum / obj.length;
    }

    public boolean sameAvg(Generics<?> obj) {
        return Math.abs(this.avg() - obj.avg()) < 0.0001;
    }

}
