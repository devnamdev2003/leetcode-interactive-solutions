package array;

import java.util.*;

import util.*;

public class Array2d {
    public static void main(String[] args) {
        int[] arr = { 1, 2, 3 };

        List<Integer> list = Arrays.stream(arr)
                .boxed()
                .toList();
        System.out.println(list);
    }
}
