package tree;

public class BinaryTree<T> {
    private T data;
    private BinaryTree<T> left;
    private BinaryTree<T> right;

    public BinaryTree() {
        this.data = null;
        this.left = null;
        this.right = null;
    }

    public BinaryTree(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        String s = this.data + "";
        return s;
    }

    public void setLeft(BinaryTree<T> node) {
        if (isNodeNull(node)) {
            throw new NullPointerException("Node is null!");
        }
        this.left = node;
    }

    public void setRight(BinaryTree<T> node) {
        if (isNodeNull(node)) {
            throw new NullPointerException("Node is null!");
        }
        this.right = node;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean isNodeNull(BinaryTree<T> node) {
        return node == null ? true : false;
    }

    public BinaryTree<T> getLeft() {
        return this.left;
    }

    public BinaryTree<T> getRight() {
        return this.right;
    }

    public T getData() {
        return this.data;
    }

    public static <E> void printTree(BinaryTree<E> tree) {
        if (tree == null) {
            return;
        }
        System.out.println(tree.data);
        printTree(tree.left);
        printTree(tree.right);
    }

    public static <E> int calHieght(BinaryTree<E> tree) {

        if (tree == null) {
            return 0;
        }
        int l = calHieght(tree.left);
        int r = calHieght(tree.right);
        return 1 + Math.max(l, r);
    }
}