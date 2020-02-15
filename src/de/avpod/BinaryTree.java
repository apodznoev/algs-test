package de.avpod;

import java.util.function.Consumer;

public class BinaryTree<T> {


    public static void main(String[] args) {
        TreeNode<String> root = new TreeNode<>("A");
        TreeNode<String> b = new TreeNode<>("B");
        root.left = b;
        TreeNode<String> c = new TreeNode<>("C");
        root.right = c;
        TreeNode<String> d = new TreeNode<>("D");
        b.left = d;
        b.right = new TreeNode<>("E");
        d.left = new TreeNode<>("H");
        d.right = new TreeNode<>("I");
        TreeNode<String> f = new TreeNode<>("F");
        c.left = f;
        f.left = new TreeNode<>("1");
        f.right = new TreeNode<>("2");
        TreeNode<String> g = new TreeNode<>("G");
        c.right = g;
        g.left = new TreeNode<>("Z");
        TreeNode<String> j = new TreeNode<>("J");
        g.right = j;
        j.left = new TreeNode<>("K");

        System.out.print("In order: ");
        root.traverseInOrder(s -> System.out.print(s + " "));
        System.out.println();

        System.out.print("Pre order: ");
        root.traversePreOrder(s -> System.out.print(s + " "));
        System.out.println();

        System.out.print("Post order: ");
        root.traversePostOrder(s -> System.out.print(s + " "));
        System.out.println();
        System.out.println();


        TreeNode<String> root2 = new TreeNode<>("G");
        root2.left = new TreeNode<>("Z");
        TreeNode<String> j1 = new TreeNode<>("J");
        root2.right = j1;
        j1.right = new TreeNode<>("K");

        System.out.print("In order: ");
        root2.traverseInOrder(s -> System.out.print(s + " "));
        System.out.println();

        System.out.print("Pre order: ");
        root2.traversePreOrder(s -> System.out.print(s + " "));
        System.out.println();

        System.out.print("Post order: ");
        root2.traversePostOrder(s -> System.out.print(s + " "));

        TreeNode<Integer> root3 = new TreeNode<>(100);
        root3.left = new TreeNode<>(50);
        root3.right = new TreeNode<>(120);

        root3.left.left = new TreeNode<>(10);
        root3.left.right = new TreeNode<>(70);

        root3.right = new TreeNode<>(120);
        System.out.println(isBst(root3));
    }

    public static boolean isBst(TreeNode<Integer> root) {
        return checkIsBst(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean checkIsBst(TreeNode<Integer> node, int minValue, int maxValue) {
        if (node == null)
            return true;

        if (minValue <= node.value && node.value < maxValue) {
            return checkIsBst(node.left, minValue, node.value)
                    && checkIsBst(node.right, node.value, maxValue);
        }

        return false;
    }


    public static class TreeNode<T> {
        private T value;
        private TreeNode<T> left;
        private TreeNode<T> right;

        public TreeNode(T value) {
            this.value = value;
        }


        public void visit(Consumer<T> visitor) {
            visitor.accept(value);
        }

        public void traverseInOrder(Consumer<T> visitor) {
            if (left != null) {
                left.traverseInOrder(visitor);
            }
            visitor.accept(value);
            if (right != null) {
                right.traverseInOrder(visitor);
            }
        }

        public void traversePreOrder(Consumer<T> visitor) {
            visitor.accept(value);
            if (left != null) {
                left.traversePreOrder(visitor);
            }
            if (right != null) {
                right.traversePreOrder(visitor);
            }
        }

        public void traversePostOrder(Consumer<T> visitor) {
            if (left != null) {
                left.traversePostOrder(visitor);
            }
            if (right != null) {
                right.traversePostOrder(visitor);
            }
            visitor.accept(value);
        }
    }
}
