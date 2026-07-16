import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }

    public static TreeNode buildTree(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;

        while (!queue.isEmpty() && i < arr.length) {
            TreeNode current = queue.poll();
            if (i < arr.length && arr[i] != null) {
                current.left = new TreeNode(arr[i]);
                queue.offer(current.left);
            }
            i++;
            if (i < arr.length && arr[i] != null) {
                current.right = new TreeNode(arr[i]);
                queue.offer(current.right);
            }
            i++;
        }

        return root;
    }
}

public class Dev {
    public static void main(String[] args) {
        Solution o = new Solution();
        TreeNode root = TreeNode.buildTree(new Integer[] { 5, 3, 6, 2, 4, null, 7 });
        System.out.println(o.deleteNode(root, 3));
    }

}

class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode keyNode = new TreeNode(key);
        TreeNode keyNodeParent = new TreeNode(-1);
        TreeNode temp = root;
        while (root != null && root.val != keyNode.val) {
            keyNodeParent = root;
            root = root.val < keyNode.val ? root.right : root.left;
        }
        keyNode = root;
        root = temp;
        if (root == null)
            return null;
        TreeNode keyNode1 = keyNode.right;
        TreeNode keyNodeParent1 = keyNode;
        root = keyNode1;
        while (root.left != null) {
            keyNodeParent1 = root;
            keyNode1 = root.left;
            root = root.left;
        }
        if (keyNodeParent != null & keyNode != null) {
            System.out.println(keyNode.val);
            System.out.println(keyNodeParent.val);
            System.out.println(keyNode1.val);
            System.out.println(keyNodeParent1.val);
        }
        // if (find != null) {
        // if (find.right != null) {
        // TreeNode tempRoot = find.right;

        // }
        // if (find.left != null) {

        // }
        // }
        return root;
    }

    public void findInorderLeafnode(TreeNode root, TreeNode keyNode, TreeNode keyNodeParent) {
        if (root == null)
            return;
        while (root.left != null) {
            keyNodeParent = root;
            keyNode = root.left;
            root = root.left;
        }
    }
}