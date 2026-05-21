package recursion.string;

import java.util.*;

public class subsetOfString {

    public static void main(String[] args) {
        String s = "abc";
        System.out.println(fun(s));
    }

    public static List<String> fun(String s) {
        List<String> ans = new ArrayList<>();
        return fun2("", s, ans);
    }

    public static List<String> fun2(String s1, String s2, List<String> l) {
        if (s2.isEmpty()) {
            l.add(s1);
            return l;
        }
        fun2(s1 + s2.substring(0, 1), s2.substring(1), l);
        fun2(s1, s2.substring(1), l);
        return l;
    }
}
