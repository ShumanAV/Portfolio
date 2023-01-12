package Lesson_14.Training_Exceptions_Lesson9;

public class App {

    private static final int MAX_LENGTH = 4;
    private static final int MAX_HIGH = 4;

    public static void main(String[] args) {

        String[][] arr = {{"112", "2", "3", "4"},
                          {"5323", "6", "7", "8"},
                          {"92323", "1", "2", "1"},
                          {"734434", "6", "5", "4"}};

        try {
            System.out.println("Сумма массива равна " + arrWork(arr));
        } catch (MyArraySizeException_2 e) {
            e.printStackTrace();
        } catch (MyArrayDataException_2 e) {
            e.printStackTrace();
        } finally {
            System.out.println("Завершение работы программы в finally");
        }

    }

    private static int arrWork(String[][] arr) throws MyArraySizeException_2, MyArrayDataException_2 {

        if (arr.length > MAX_LENGTH || arr[0].length > MAX_HIGH) {
            throw new MyArraySizeException_2();
        }
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                for (int k = 0; k < arr[i][j].length(); k++) {
                    if (arr[i][j].charAt(k) < 48 || arr[i][j].charAt(k) > 57) {
                        throw new MyArrayDataException_2(i, j);
                    }
                }
                sum += Integer.parseInt(arr[i][j]);
            }
        }
        return sum;
    }

}
