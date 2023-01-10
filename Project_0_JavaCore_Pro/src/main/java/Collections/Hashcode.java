package Collections;

public class Hashcode {

    public static void main(String[] args) {

        String str1 = "KING";
        String str2 = "CLARK";
        String str3 = "BLAKE";
        String str4 = "FORD";
        String str5 = "SMITH";
        String str6 = "WARD";
        String str7 = "JONES";

        int capacity = 16 - 1;

        System.out.println(str1.hashCode());

        System.out.println("Str = " + str1 + " & " + (capacity&str1.hashCode()) + " " + Integer.toBinaryString(capacity&str1.hashCode()));
        System.out.println("Str = " + str2 + " & " + (capacity&str2.hashCode()) + " " + Integer.toBinaryString(capacity&str2.hashCode()));
        System.out.println("Str = " + str3 + " & " + (capacity&str3.hashCode()) + " " + Integer.toBinaryString(capacity&str3.hashCode()));
        System.out.println("Str = " + str4 + " & " + (capacity&str4.hashCode()) + " " + Integer.toBinaryString(capacity&str4.hashCode()));
        System.out.println("Str = " + str5 + " & " + (capacity&str5.hashCode()) + " " + Integer.toBinaryString(capacity&str5.hashCode()));
        System.out.println("Str = " + str6 + " & " + (capacity&str6.hashCode()) + " " + Integer.toBinaryString(capacity&str6.hashCode()));
        System.out.println("Str = " + str7 + " & " + (capacity&str7.hashCode()) + " " + Integer.toBinaryString(capacity&str7.hashCode()));

        System.out.println("Str = " + str1 + " & " + (str1.hashCode() % capacity));

    }

}
