package de.avpod.datastructures;


import static de.avpod.datastructures.RedBlackTree.Color.RED;
import static de.avpod.datastructures.RedBlackTree.Node.isRed;

public class RedBlackTree {
    private final static TreeVisitor traverser = new TreeVisitor() {
        private final StringBuilder path = new StringBuilder();

        @Override
        public void visit(final Node node) {
            System.out.println(path + ">" + node.value + " " + node.color);
        }

        @Override
        public void onNextLevel() {
            path.append("-");
        }

        @Override
        public void onPrevLevel() {
            path.deleteCharAt(path.length() - 1);
        }
    };

    public static void main(String[] args) {


//        printTree(10, 9, 7, 5, 12, 14, 17, 16, 18, 19);
        printTree(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    private static void printTree(int... nodes) {
        RedBlackTree rbt = new RedBlackTree();

        for (int i : nodes) {
            rbt.add(i);
            rbt.traversePreOrder(traverser);
            System.out.println("____________");
        }
    }

    private Node root;

    public void add(int value) {
        root = addLeaf(root, value);
    }

    public boolean contains(int value) {
        return searchValue(root, value);
    }

    public void traversePreOrder(TreeVisitor action) {
        doTraversePreOrder(root, action);
    }

    private void doTraversePreOrder(final Node node, TreeVisitor action) {
        if (node == null) {
            return;
        }
        action.onNextLevel();

        action.visit(node);

        doTraversePreOrder(node.left, action);

        doTraversePreOrder(node.right, action);

        action.onPrevLevel();
    }

    private Node addLeaf(Node parent, int value) {
        if (parent == null) {
            return Node.leafNode(value);
        }

        int parentVal = parent.value;
        int compare = Integer.compare(value, parentVal);
        if (compare < 0) {
            parent.left = addLeaf(parent.left, value);
        } else if (compare > 0) {
            parent.right = addLeaf(parent.right, value);
        } else {
            return parent;
        }

        if (isRed(parent.right)) {
            parent = rotateLeft(parent);
        }
        if (isRed(parent.left) && isRed(parent.left.left)) {
            parent = rotateRight(parent);
        }
        if (isRed(parent.left) && isRed(parent.right)) {
            recolor(parent);
        }

        return parent;


    }


    private Node rotateLeft(final Node root) {
        assert isRed(root.right);

        Node child = root.right;
        root.right = child.left;
        child.left = root;
        child.color = root.color;
        root.color = RED;


        return child;
    }

    private Node rotateRight(final Node root) {
        assert isRed(root.left);

        Node child = root.left;
        root.left = child.right;
        child.right = root;
        child.color = root.color;
        root.color = RED;

        return child;
    }

    private void recolor(final Node root) {
        root.color = root.color.inverse();
        root.left.color = root.left.color.inverse();
        root.right.color = root.right.color.inverse();
    }

    private boolean searchValue(final Node node, final int value) {
        if (node == null) {
            return false;
        }

        int nodeVal = node.value;
        if (nodeVal == value) {
            return true;
        }

        if (value <= nodeVal) {
            return searchValue(node.left, value);
        } else {
            return searchValue(node.right, value);
        }
    }

    public static class Node {
        int value;
        Color color;
        Node left;
        Node right;

        public static boolean isRed(Node node) {
            if (node == null) {
                return false;
            }

            return node.color == RED;
        }

        private Node(final int value, final Node left, final Node right, final Color color) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        public static Node leafNode(int value) {
            return new Node(value, null, null, RED);
        }

    }

    public enum Color {
        RED,
        BLACK;

        public Color inverse() {
            return this == RED ? BLACK : RED;
        }
    }

    private interface TreeVisitor {
        void visit(RedBlackTree.Node node);

        void onNextLevel();

        void onPrevLevel();
    }
}
