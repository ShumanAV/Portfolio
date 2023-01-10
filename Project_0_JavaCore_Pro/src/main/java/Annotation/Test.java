package Annotation;

@MyAnnotation (name = "Fufu", dateOfBirth = 2010)
public class Test {

    @MyAnnotation (name = "Fufu", dateOfBirth = 2010)
    private int id;

    @MyAnnotation (name = "Fufu", dateOfBirth = 2010)
    public Test(int id) {
        this.id = id;
    }

    public static void main(String[] args) {

    }

    @MyAnnotation
    private void testMethod(@MyAnnotation int x) {
        @MyAnnotation
        String str = "";
    }

}
