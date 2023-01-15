package Multithread_3;

public abstract class Stage {

    protected int length;
    protected String description;
    protected static boolean WIN;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car car);

    protected void win(Stage stage, Car car) {
        if (!WIN) {
            if (Car.getRACE().getStages().indexOf(stage) == Car.getRACE().getStages().size() - 1) {
                WIN = true;
                System.out.println(car.getName() + " WIN");
            }
        }
    }

}
