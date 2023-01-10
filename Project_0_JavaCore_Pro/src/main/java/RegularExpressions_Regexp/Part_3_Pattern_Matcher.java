package RegularExpressions_Regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part_3_Pattern_Matcher {

    public static void main(String[] args) {

        /*
        регулярные выражения часть 3
        Pattern и Matcher - создание паттернов и поиск их в тексте, например, поиск имэйлов и т.д.
         */

        String str = "hello hey guys i go to home my e-mail address puka@mail.ru hello " +
                "i am fine and you my address joe@google.com hello" +
                "i go to airport take my address tim@yandex.ru";

        // нумерация групп идет слева направо начиная с 1, если есть вложенности ( () () ), большая 1, слева мелкая 2, справа мелкая 3,
        // в данном случае ( () ) (), первая слева большая 1, первая слева мелкая 2, вторая слева мелкая 3
        Pattern email = Pattern.compile("(\\w+)@(mail|google|yandex)\\.(ru|com)");
        Matcher matcher = email.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());    // group() - вывести все группы в паттерне в круглых скобках
            System.out.println(matcher.group(1));    // group(1) - вывести группы 1, т.е. имена
            System.out.println(matcher.group(2));    // group(2) - вывести группы 2, имена сайтов
            System.out.println(matcher.group(3));    // group(3) - вывести группы 3, т.е. домены
        }

    }

}
