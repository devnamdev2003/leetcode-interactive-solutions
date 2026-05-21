package recursion.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class ReverseStack {
    public static void main(String[] args) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (Integer i : Arrays.asList(1, 2, 3)) {
            stack.push(i);
        }
        reverseStatck(stack);
        System.out.println(stack);
    }

    public static void reverseStatck(Deque<Integer> s) {
        if (s.size() == 1) {
            return;
        }
        int poped = s.pop();
        reverseStatck(s);
        putOnBottom(s, poped);
    }

    public static void putOnBottom(Deque<Integer> s, int val) {
        if (s.size() == 0) {
            s.push(val);
            return;
        }
        int poped = s.pop();
        putOnBottom(s, val);
        s.push(poped);
    }
}
