package recursion.tree;

import tree.BinaryTree;

public class CalculateBinaryTreeHeight {
    public static void main(String[] args) {
        BinaryTree<Integer> ob = new BinaryTree<>(1);

        BinaryTree<Integer> ob1 = new BinaryTree<>(2);
        ob1.setLeft(new BinaryTree<Integer>(4));
        ob1.setRight(new BinaryTree<Integer>(5));

        BinaryTree<Integer> ob2 = new BinaryTree<>(3);
        ob2.setLeft(new BinaryTree<Integer>(6));
        ob2.setRight(new BinaryTree<Integer>(7));

        ob2.getLeft().setLeft(new BinaryTree<Integer>(12));

        ob.setLeft(ob1);
        ob.setRight(ob2);

        System.err.println(BinaryTree.calHieght(ob));
    }

    public static <E> int calHieght(BinaryTree<E> tree) {
        if (tree == null) {
            return 0;
        }
        int l = calHieght(tree.getLeft());
        int r = calHieght(tree.getRight());
        return 1 + Math.max(l, r);
    }
}
