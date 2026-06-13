package tree;

import java.util.*;

public class TreeView {
    public static void main(String[] args) {
        System.out.println(topView(IntBinaryTree.getSampleTree()));
    }

    public static ArrayList<Integer> topView(IntBinaryTree root) {

        Queue<Map.Entry<Integer, IntBinaryTree>> q = new ArrayDeque<>();
        Map<Integer, IntBinaryTree> m = new TreeMap<>();
        q.offer(Map.entry(0, root));
        while (!q.isEmpty()) {
            Map.Entry<Integer, IntBinaryTree> node = q.poll();
            if (node.getValue().left != null) {
                q.offer(Map.entry(node.getKey() - 1, node.getValue().left));
            }
            if (node.getValue().right != null) {
                q.offer(Map.entry(node.getKey() + 1, node.getValue().right));
            }
            m.putIfAbsent(node.getKey(), node.getValue());
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (Map.Entry<Integer, IntBinaryTree> en : m.entrySet()) {
            ans.add(en.getValue().data);
        }
        return ans;
    }

    public static ArrayList<Integer> tobottomView(IntBinaryTree root) {

        Queue<Map.Entry<Integer, IntBinaryTree>> q = new ArrayDeque<>();
        Map<Integer, IntBinaryTree> m = new TreeMap<>();
        q.offer(Map.entry(0, root));
        while (!q.isEmpty()) {
            Map.Entry<Integer, IntBinaryTree> node = q.poll();
            if (node.getValue().left != null) {
                q.offer(Map.entry(node.getKey() - 1, node.getValue().left));
            }
            if (node.getValue().right != null) {
                q.offer(Map.entry(node.getKey() + 1, node.getValue().right));
            }
            m.put(node.getKey(), node.getValue());
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (Map.Entry<Integer, IntBinaryTree> en : m.entrySet()) {
            ans.add(en.getValue().data);
        }
        return ans;
    }

    public List<Integer> rightSideView(IntBinaryTree root) {
        List<Integer> l = new ArrayList<>();
        recursion(root, 0, l);
        return l;
    }

    public void recursion(IntBinaryTree root, int level, List<Integer> l) {
        if (root == null)
            return;
        if (level == l.size()) {
            l.add(root.data);
        }
        recursion(root.right, level + 1, l);
        recursion(root.left, level + 1, l);

    }

}
