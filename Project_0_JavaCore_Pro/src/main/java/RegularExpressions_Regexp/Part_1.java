package RegularExpressions_Regexp;

public class Part_1 {

    public static void main(String[] args) {

        /*
            Регулярные выражения часть 1
            полезный сайт там много выражений regexlib.com/cheatsheet.aspx
            \\ обычный символ делают специальным и наоборот
            \\d - одна цифра            "1" и "\\d" - .matches = true
            \\w - одна английская буква "a" и "\\w" - .matches = true
            + - одна или более          "12345" и "\\d+" - .matches = true
            * - 0 и более               "12345" и "\\d*", "" и "\\d*" - .matches = true
            ? - 0 или 1 символов до     "12345" или "-12345" и "-?\\d*" - .matches = true
            ( | ) - логическое или      "12345" или "+12345" или "-12345" и "(-|\\+)?\\d*" - .matches = true
            (a|b|c|d...) - логическое или, одно из нескольких строк
            [abc] = (a|b|c) - аналогичные выражения
            [a-zA-Z] - один символ из диапазона всех букв английского алфавита  "a2234234" или "B2234234" и "[a-zA-Z]\\d+" - .matches = true
                                                                            или "fdfsda2234234" и "[a-zA-Z]+\\d+" - .matches = true
                                                                            или "3333311111fd111f33sda2234234" и "[a-zA-Z13]+\\d+" - .matches = true
            [0-9] = \\d - аналогично, одна любая цифра из диапазона
            [a-zA-Z] = \\w - аналогично, одна любая английская буква
            [^abc] - отрицание, все символы кроме a, b, c       "hello" и "[^abc]*" - .matches = true
            . - любой символ                        "https://www.google.com"  или "http://www.mail.ru" и "https?\://www\..+\.(com|ru)" - .matches = true
            .+ - любой набор символов
            {2} - два символа до                    "54" и "\\d{2}" - .matches = true
            {2,} - от 2 символов до
            {2, 4} - от 2 до 4 символов до          "5423" и "\\d{2,4}" - .matches = true
         */

        String str1 = "-2234234";
        String str2 = "2234234";
        String str3 = "+2234234";
        System.out.println(str1.matches("(-|\\+)?\\d*"));    // сравнивает строки
        System.out.println(str2.matches("(-|\\+)?\\d*"));    // сравнивает строки
        System.out.println(str3.matches("(-|\\+)?\\d*"));    // сравнивает строки

        String str4 = "hello";
        System.out.println(str4.matches("[^abc]*"));    // сравнивает строки

        String url = "https://www.google.com";
        String url2 = "http://www.mail.ru";
        System.out.println(url.matches("https?\\://www\\..+\\.(com|ru)"));    // сравнивает строки
        System.out.println(url2.matches("https?\\://www\\..+\\.(com|ru)"));    // сравнивает строки

        String str5 = "http5434";
        System.out.println(str5.matches("http\\d{2,4}"));    // сравнивает строки


    }

}
