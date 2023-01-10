package Annotation;

@MethodInfo(author = "Tom", dateOfCreation = 2021, purpose = "print hello world")
public class TestMethodInfo {

    @MethodInfo(author = "Tom", dateOfCreation = 2021, purpose = "print hello world")
    public void printHelloWorld() {
        System.out.println("Hello, World!");
    }

}
