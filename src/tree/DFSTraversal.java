package tree;

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

public class DFSTraversal {

    public static void main(String[] args) {
        IntBinaryTree bt = new IntBinaryTree(1);
        IntBinaryTree n2 = new IntBinaryTree(2);
        IntBinaryTree n3 = new IntBinaryTree(3);
        bt.left = n2;
        bt.right = n3;
        n2.left = new IntBinaryTree(4);
        n2.right = new IntBinaryTree(5);
        n3.left = new IntBinaryTree(6);
        n3.right = new IntBinaryTree(7);
        inorderTraversal(bt);
        System.out.println();
        preorderTraversal(bt);
        System.out.println();
        postorderTraversal(bt);
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
}
