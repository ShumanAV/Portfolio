package Training_Jenerics_Lesson11;

import java.util.ArrayList;
import java.util.Iterator;

public class Box <T extends Fruit> {

    private ArrayList<T> box = new ArrayList<>();

    public void add(T obj) {
        box.add(obj);
    }

    public float getWeight() {
        float weightBox = 0;
        for (T obj : box) {
            weightBox += obj.getNumber() * obj.getWeightOneFruit();
        }
        return weightBox;
    }

    public boolean compare(Box <?> box) {
        if (box == null) {
            return false;
        }
        return Math.abs(this.getWeight() - box.getWeight()) < 0.001;
    }

    public void twoBoxInOne(Box <T> box) {
        Iterator<T> iterator = box.getBox().iterator();
        while (iterator.hasNext()) {
            this.box.add(iterator.next());
            iterator.remove();
        }
    }

    public ArrayList<T> getBox() {
        return box;
    }

}
