package util;

public class ArrayUtil {

    public static void main(String[] args) {

    }

    public static <T> void print2dArray(T[][] arr) {

        for (T[] i : arr) {
            for (T j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
}
