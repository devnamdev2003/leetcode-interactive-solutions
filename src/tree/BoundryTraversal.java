package tree;

import java.util.*;

public class BoundryTraversal {
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
        System.out.println(boundryTraversal(root));

    }

    public static List<Integer> boundryTraversal(IntBinaryTree root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;
        ans.add(root.data);
        ans.addAll(leftTraversal(root.left));
        ans.addAll(leafTraversal(root));
        ans.addAll(rightTraversal(root.right));
        return ans;
    }

    public static List<Integer> leftTraversal(IntBinaryTree root) {
        List<Integer> l = new ArrayList<>();
        if (root == null || isLeafNode(root))
            return l;
        l.add(root.data);
        if (root.left == null) {
            l.addAll(leftTraversal(root.right));
        } else {
            l.addAll(leftTraversal(root.left));
        }
        return l;

    }

    public static List<Integer> rightTraversal(IntBinaryTree root) {
        List<Integer> l = new ArrayList<>();
        if (root == null || isLeafNode(root))
            return l;
        if (root.right == null) {
            l.addAll(rightTraversal(root.left));
        } else {
            l.addAll(rightTraversal(root.right));
        }
        l.add(root.data);
        return l;

    }

    public static List<Integer> leafTraversal(IntBinaryTree root) {
        List<Integer> l = new ArrayList<>();
        if (root == null)
            return l;
        l.addAll(leafTraversal(root.left));
        l.addAll(leafTraversal(root.right));
        if (isLeafNode(root))
            l.add(root.data);
        return l;

    }

    public static boolean isLeafNode(IntBinaryTree root) {
        if (root.left == null && root.right == null)
            return true;
        return false;
    }
}
