package recursion.string;

import java.util.*;

public class subsetOfList {

    public static void main(String[] args) {
        Integer[] nums = { 1, 2, 3 };
        List<Integer> s = new ArrayList<>(Arrays.asList(nums));
        System.out.println(fun(s));
    }

    public static List<List<Integer>> fun(List<Integer> s) {
        List<List<Integer>> ans = new ArrayList<>();
        return fun2(new ArrayList<>(), s, ans);
    }

    public static List<List<Integer>> fun2(List<Integer> s1, List<Integer> s2, List<List<Integer>> l) {
        if (s2.isEmpty()) {
            l.add(s1);
            return l;
        }
        List<Integer> notAdded = new ArrayList<>(s1);
        s1.addAll(s2.subList(0, 1));
        fun2(s1, s2.subList(1, s2.size()), l);
        fun2(notAdded, s2.subList(1, s2.size()), l);
        return l;
    }
}
