package tree;

public class IntBinaryTree {
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

    public static IntBinaryTree getSampleTree() {
        IntBinaryTree root = new IntBinaryTree(1);
        IntBinaryTree n2 = new IntBinaryTree(2);
        IntBinaryTree n3 = new IntBinaryTree(3);
        root.left = n2;
        root.right = n3;
        n2.left = new IntBinaryTree(4);
        n2.right = new IntBinaryTree(5);
        n3.left = new IntBinaryTree(6);
        n3.right = new IntBinaryTree(7);
        return root;
    }

    public static IntBinaryTree getSampleTree2() {
        IntBinaryTree root = new IntBinaryTree(3);
        IntBinaryTree n2 = new IntBinaryTree(1);
        IntBinaryTree n3 = new IntBinaryTree(4);
        root.left = n2;
        root.right = n3;
        n2.left = new IntBinaryTree(0);
        n2.right = new IntBinaryTree(2);
        n3.left = new IntBinaryTree(2);
        // n3.right = new IntBinaryTree(7);
        return root;
    }

    public static IntBinaryTree getSampleTree3() {
        IntBinaryTree root = new IntBinaryTree(0);

        root.left = new IntBinaryTree(10);
        root.right = new IntBinaryTree(1);

        root.right.left = new IntBinaryTree(2);
        root.right.right = new IntBinaryTree(4);

        root.right.left.left = new IntBinaryTree(3);
        root.right.left.right = new IntBinaryTree(5);

        root.right.left.left.left = new IntBinaryTree(6);

        root.right.left.left.left.left = new IntBinaryTree(8);

        root.right.left.right.left = new IntBinaryTree(7);
        root.right.left.right.right = new IntBinaryTree(9);

        root.right.left.right.right.right = new IntBinaryTree(11);

        root.right.left.right.right.right.left = new IntBinaryTree(12);

        return root;
    }

    public static IntBinaryTree getSampleTree4() {
    //     1
    //    / \
    //   2   3
    //  / \
    // 4   5
    //    / \
    //   6   7       
        IntBinaryTree root = new IntBinaryTree(1);

        root.left = new IntBinaryTree(2);
        root.right = new IntBinaryTree(3);

        root.left.left = new IntBinaryTree(4);
        root.left.right = new IntBinaryTree(5);

        root.left.right.left = new IntBinaryTree(6);
        root.left.right.right = new IntBinaryTree(7);
        return root;
    }

}
