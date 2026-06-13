package tree;

import java.util.*;

public class VerticalTraversing {

    static class Pair {
        IntBinaryTree node;
        int x;
        int y;

        Pair(IntBinaryTree node, int x, int y) {
            this.node = node;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        System.out.println(verticalTraversal(IntBinaryTree.getSampleTree3()));
    }

    public static List<List<Integer>> verticalTraversal(IntBinaryTree root) {

        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();

        Queue<Pair> q = new LinkedList<>();

        q.offer(new Pair(root, 0, 0));

        while (!q.isEmpty()) {

            Pair p = q.poll();

            map.putIfAbsent(p.x, new TreeMap<>());

            map.get(p.x)
                    .putIfAbsent(p.y, new PriorityQueue<>());

            map.get(p.x)
                    .get(p.y)
                    .offer(p.node.data);

            if (p.node.left != null) {
                q.offer(new Pair(
                        p.node.left,
                        p.x - 1,
                        p.y + 1));
            }

            if (p.node.right != null) {
                q.offer(new Pair(
                        p.node.right,
                        p.x + 1,
                        p.y + 1));
            }
        }

        List<List<Integer>> ans = new ArrayList<>();

        for (TreeMap<Integer, PriorityQueue<Integer>> ys : map.values()) {

            List<Integer> col = new ArrayList<>();

            for (PriorityQueue<Integer> pq : ys.values()) {

                while (!pq.isEmpty()) {
                    col.add(pq.poll());
                }
            }

            ans.add(col);
        }

        return ans;
    }
}