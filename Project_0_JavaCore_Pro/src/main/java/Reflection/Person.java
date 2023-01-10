package Reflection;

import Annotation.MethodInfo;

@MethodInfo(purpose = "Собрать информацию о классе")
public class Person {

    private int id;
    private String name;

    public void sayHello() {
        System.out.printf("Person with id: %d and name: %s says hello!", id, name);
    }

    public Person() {
        this.id = -1;
        this.name = "no name";
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
