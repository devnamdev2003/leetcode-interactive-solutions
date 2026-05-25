package tree;

import java.util.*;

class IntBinaryTree {
    int data;
    IntBinaryTree left;
    IntBinaryTree right;

    public IntBinaryTree() {
        this.data = 0;
        this.left = null;
        this.right = null;
    }

    public IntBinaryTree(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

}

public class BFSTraversal {

    public static void main(String[] args) {
        IntBinaryTree root = new IntBinaryTree(1);
        IntBinaryTree n2 = new IntBinaryTree(2);
        IntBinaryTree n3 = new IntBinaryTree(3);
        root.left = n2;
        root.right = n3;
        n2.left = new IntBinaryTree(4);
        n2.right = new IntBinaryTree(5);
        n3.left = new IntBinaryTree(6);
        n3.right = new IntBinaryTree(7);

        System.out.println(zigzagTraversal(root));
        System.out.println(fun2(root));
    }

    public static List<List<Integer>> fun(IntBinaryTree root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<IntBinaryTree> q = new ArrayDeque<>();
        q.offer(root);
        while (q.isEmpty() == false) {
            int levelCount = q.size();
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < levelCount; i++) {
                if (q.peek().left != null)
                    q.offer(q.peek().left);
                if (q.peek().right != null)
                    q.offer(q.peek().right);
                l.add(q.poll().data);
            }
            ans.add(l);
        }

        return ans;
    }

    public static List<Integer> fun2(IntBinaryTree root) {
        Queue<IntBinaryTree> q = new ArrayDeque<>();
        List<Integer> l = new ArrayList<>();
        q.add(root);
        while (q.isEmpty() == false) {
            if (q.peek().left != null)
                q.offer(q.peek().left);
            if (q.peek().right != null)
                q.offer(q.peek().right);
            l.add(q.poll().data);
        }
        return l;
    }

    public static List<List<Integer>> zigzagTraversal(IntBinaryTree root) {
        Queue<IntBinaryTree> q = new ArrayDeque<>();
        List<List<Integer>> l = new ArrayList<>();
        q.offer(root);
        boolean flag = false;
        while (q.size() != 0) {
            List<Integer> temp = new ArrayList<>();
            int size = q.size();

            for (int i = 0; i < size; i++) {
                IntBinaryTree node = q.poll();
                if (node.left != null)
                    q.offer(node.left);
                if (node.right != null)
                    q.offer(node.right);
                temp.add(node.data);
            }
            if (flag) {
                Collections.reverse(temp);
            }
            l.add(temp);

            flag = !flag;
        }
        return l;
    }

}
