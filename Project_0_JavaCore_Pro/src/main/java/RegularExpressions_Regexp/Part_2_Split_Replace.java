package RegularExpressions_Regexp;

import java.util.Arrays;

public class Part_2_Split_Replace {

    public static void main(String[] args) {

        /*
        регулярные выражения часть 2
        split() - разделяет строку при помощи какого либо разделительного символа, на выходе массив строк   .split(" ")
                                                                                                        или .split("\\.")
                                                                                                        или .split("\\d+")
        replace() - замена символа или символов на другой/другие, принимает на вход строку                  .replace(", ", ".")
        replaceall() - замена символа или символов на другой/другие, принимает на вход регулярное выражение,
                        когда нельзя описать символами что нужно заменить, только паттерном                 .replaceAll("\\d+", ".");
        replacefirst() - аналог replaceall(), заменяет только первое выражение
         */

        String str1 = "hello there hey";
        String[] arrWords1 = str1.split(" ");
        System.out.println(Arrays.toString(arrWords1));

        String str2 = "hello.there.hey";
        String[] arrWords2 = str2.split("\\.");
        System.out.println(Arrays.toString(arrWords2));

        String str3 = "hello43234there2345654hey";
        String[] arrWords3 = str3.split("\\d+");
        System.out.println(Arrays.toString(arrWords3));

        String str4 = "hello, there, hey";
        String modifiedStr = str4.replace(", ", ".");
        System.out.println(modifiedStr);

        String str5 = "hello11212there34534534hey";
        String modifiedStr2 = str5.replaceAll("\\d+", ".");
        System.out.println(modifiedStr2);



    }

}
