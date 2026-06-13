package tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class DFSTraversal {

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

        IntBinaryTree root2 = new IntBinaryTree(1);
        IntBinaryTree n22 = new IntBinaryTree(2);
        IntBinaryTree n32 = new IntBinaryTree(3);
        root2.left = n22;
        root2.right = n32;
        n22.left = new IntBinaryTree(4);
        n22.right = new IntBinaryTree(5);
        // n32.left = new IntBinaryTree(6);
        n32.right = new IntBinaryTree(7);

        // inorderTraversal(root);
        // System.out.println();
        // preorderTraversal(root);
        // System.out.println();
        // postorderTraversal(root);
        System.out.println();
        rootToNode(root, n3.left);

    }

    public static void inorderTraversal(IntBinaryTree root) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left);
        System.out.print(root.data);
        inorderTraversal(root.right);
    }

    public static void preorderTraversal(IntBinaryTree root) {
        if (root == null) {
            return;
        }
        System.out.print(root.data);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    public static void postorderTraversal(IntBinaryTree root) {
        if (root == null) {
            return;
        }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.data);
    }

    public static List<Integer> inorderTraversalForLoop(IntBinaryTree root) {
        Deque<IntBinaryTree> s = new ArrayDeque<>();
        List<Integer> l = new ArrayList<>();
        while (true) {
            if (root != null) {
                s.push(root);
                root = root.left;
            } else {
                if (s.isEmpty()) {
                    break;
                } else {
                    root = s.pop();
                    l.add(root.data);
                    root = root.right;
                }

            }
        }
        return l;
    }

    public static void preorderTraversalForLoop(IntBinaryTree root) {
        Deque<IntBinaryTree> s = new ArrayDeque<>();
        List<Integer> l = new ArrayList<>();
        s.push(root);
        while (s.isEmpty() == false) {
            IntBinaryTree node = s.pop();
            if (node.right != null)
                s.push(node.right);
            if (node.left != null)
                s.push(node.left);
            l.add(node.data);
        }
        System.out.println(l);
    }

    public static void postorderTraversalForLoop(IntBinaryTree root) {
        Deque<IntBinaryTree> s = new ArrayDeque<>();
        List<Integer> ans = new ArrayList<>();
        s.push(root);
        while (true) {
            if (s.isEmpty()) {
                break;
            }
            IntBinaryTree node = s.pop();
            if (node.left != null)
                s.push(node.left);
            if (node.right != null)
                s.push(node.right);
            ans.add(node.data);
        }
        Collections.reverse(ans);
        System.out.println(ans);

    }

    public static List<Integer> inorderTraversalList(IntBinaryTree root) {
        List<Integer> l = new ArrayList<>();
        if (root == null) {
            return l;
        }
        l.addAll(inorderTraversalList(root.left));
        l.add(root.data);
        l.addAll(inorderTraversalList(root.right));
        return l;
    }

    public static void allTraversal(IntBinaryTree root) {
        Deque<Map.Entry<IntBinaryTree, Integer>> s = new ArrayDeque<>();
        List<Integer> in = new ArrayList<>();
        List<Integer> pre = new ArrayList<>();
        List<Integer> pos = new ArrayList<>();
        s.push(Map.entry(root, 1));
        while (true) {
            if (s.isEmpty()) {
                break;
            }
            Map.Entry<IntBinaryTree, Integer> node = s.peek();
            if (node.getValue() == 1) {
                pre.add(node.getKey().data);
                s.pop();
                s.push(Map.entry(node.getKey(), node.getValue() + 1));
                if (node.getKey().left != null)
                    s.push(Map.entry(node.getKey().left, 1));
            } else if (node.getValue() == 2) {
                in.add(node.getKey().data);
                s.pop();
                s.push(Map.entry(node.getKey(), node.getValue() + 1));
                if (node.getKey().right != null)
                    s.push(Map.entry(node.getKey().right, 1));

            } else {
                pos.add(node.getKey().data);
                s.pop();
            }
        }
        System.out.println(pre);
        System.out.println(in);
        System.out.println(pos);
    }

    public static int checkBalancedTree(IntBinaryTree root) {
        if (root == null) {
            return 0;
        }
        int i = Math.max(checkBalancedTree(root.left), checkBalancedTree(root.right)) + 1;
        return i;

    }

    public static int checkHeight(IntBinaryTree root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(checkHeight(root.left), checkHeight(root.right));
    }

    public static int checkDiameter(IntBinaryTree root) {
        int[] h = { 0 };
        checkDiameterHelper(root, h);
        return h[0];
    }

    public static int checkDiameterHelper(IntBinaryTree root, int[] h) {

        if (root == null) {
            return 0;
        }
        int l = checkDiameterHelper(root.left, h);
        int r = checkDiameterHelper(root.right, h);

        h[0] = Math.max(h[0], l + r);

        return 1 + Math.max(l, r);

    }

    public static int checkMaximumPath(IntBinaryTree root) {
        int[] h = { 0 };
        checkMaximumPathHelper(root, h);
        return h[0];
    }

    public static int checkMaximumPathHelper(IntBinaryTree root, int[] h) {
        if (root == null) {
            return 0;
        }
        int l = checkMaximumPathHelper(root.left, h);
        int r = checkMaximumPathHelper(root.right, h);
        h[0] = Math.max(h[0], l + r + root.data);
        return Math.max(root.data + l, root.data + r);
    }

    public static boolean checkTwoTreeIndentical(IntBinaryTree root1, IntBinaryTree root2) {
        if (root1 == null || root2 == null) {
            return root1 == root2;
        }
        return root1.data == root2.data && checkTwoTreeIndentical(root1.left, root2.left)
                && checkTwoTreeIndentical(root1.right, root2.right);
    }

    public static boolean flag = false;

    public static void rootToNode(IntBinaryTree root, IntBinaryTree Node) {
        if (root == null) {
            return;
        }
        if (root == Node) {
            flag = true;
            System.out.println(Node.data);
            return;
        }
        if (flag == true) {
            System.out.println(root.data);
            return;
        }
        rootToNode(root.left, Node);
        if (flag == true) {
            System.out.println(root.data);
            return;
        }
        rootToNode(root.right, Node);
        if (flag == true) {
            System.out.println(root.data);
            return;
        }

    }
}
