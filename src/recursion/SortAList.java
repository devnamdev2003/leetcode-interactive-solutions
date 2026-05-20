package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortAList {
    public static void main(String[] args) {
        Integer[] arr = { 13, 2, 5 };
        ArrayList<Integer> l = new ArrayList<>(Arrays.asList(arr));
        System.out.println(fun(l));
    }

    public static List<Integer> fun(List<Integer> l) {
        if (l.size() == 1) {
            return l;
        }
        List<Integer> subL = new ArrayList<>(l.subList(0, l.size() - 1));
        subL = fun(subL);
        fun2(subL, l.get(l.size() - 1));
        return subL;
    }

    public static void fun2(List<Integer> subArr, int n) {
        for (int i = 0; i < subArr.size(); i++) {
            if (subArr.get(i) >= n) {
                subArr.add(i, n);
                return;
            }
        }
        subArr.add(n);
        return;
    }
}
