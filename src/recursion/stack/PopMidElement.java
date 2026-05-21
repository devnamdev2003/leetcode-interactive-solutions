package recursion.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

public class PopMidElement {
    public static void main(String[] args) {
        Deque<Integer> stack = new ArrayDeque<>(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6)));

        int mid = stack.size() / 2 + 1;
        System.out.println(popMidElement(stack, mid));

    }

    public static Deque<Integer> popMidElement(Deque<Integer> s, int mid) {
        if (mid == 1) {
            s.pop();
            return s;
        }
        int pop = s.pop();
        Deque<Integer> newS = popMidElement(s, mid - 1);
        newS.push(pop);
        return newS;
    }
}
