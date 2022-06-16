package de.avpod.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntervalTree {

    private static final TreeVisitor<IntervalNode> traverser = new TreeVisitor<>() {
        private final StringBuilder path = new StringBuilder();

        @Override
        public void visit(final IntervalNode node) {
            System.out.println(path + ">" + "[%d,%d){%d}".formatted(node.low, node.high, node.max) + " " + node.color);
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
        IntervalTree tree = new IntervalTree();
        tree.add(new int[]{10, 30});
        tree.traversePreOrder(traverser);
        System.out.println();

        tree.add(new int[]{20, 50});
        tree.traversePreOrder(traverser);
        System.out.println();

        tree.add(new int[]{70, 100});
        tree.traversePreOrder(traverser);
        System.out.println();

        tree.add(new int[]{-30, -10});
        tree.traversePreOrder(traverser);
        System.out.println();

        tree.add(new int[]{20, 30});
        tree.traversePreOrder(traverser);
        System.out.println();

        tree.add(new int[]{110, 150});
        tree.traversePreOrder(traverser);
        System.out.println();

        tree.add(new int[]{30, 70});
        tree.traversePreOrder(traverser);
        System.out.println();

        tree.add(new int[]{-20, 0});
        tree.traversePreOrder(traverser);
        System.out.println();

        System.out.println(tree.findOverlaps(new int[]{20, 70}));

    }


    IntervalNode root;

    public void add(int[] interval) {
        validateInterval(interval);

        root = add(root, interval);
        root.color = RedBlackTree.Color.BLACK;
    }

    public boolean overlaps(int[] interval) {
        validateInterval(interval);
        return !collectOverlaps(interval).isEmpty();
    }

    public List<List<Integer>> findOverlaps(int[] interval) {
        validateInterval(interval);
        return collectOverlaps(interval)
                .stream()
                .map(node -> List.of(node.low,node.high))
                .toList();
    }

    public void traversePreOrder(TreeVisitor<IntervalNode> action) {
        doTraversePreOrder(root, action);
    }

    public void traverseInOrder(TreeVisitor<IntervalNode> action) {
        doTraverseInOrder(root, action);
    }

    private void validateInterval(final int[] interval) {
        if (interval.length != 2) {
            throw new IllegalArgumentException("Two-elements array exepcted:" + Arrays.toString(interval));
        }

        if (interval[1] <= interval[0]) {
            throw new IllegalArgumentException("High less equal low:" + Arrays.toString(interval));
        }
    }

    private void doTraversePreOrder(final IntervalNode node, TreeVisitor<IntervalNode> action) {
        if (node == null) {
            return;
        }

        action.onNextLevel();

        action.visit(node);

        doTraversePreOrder(node.left, action);

        doTraversePreOrder(node.right, action);

        action.onPrevLevel();
    }

    private void doTraverseInOrder(final IntervalNode node, TreeVisitor<IntervalNode> action) {
        if (node == null) {
            return;
        }

        action.onNextLevel();

        doTraverseInOrder(node.left, action);

        action.visit(node);

        doTraverseInOrder(node.right, action);

        action.onPrevLevel();
    }

    private List<IntervalNode> collectOverlaps(int[] interval) {
        return collectOverlaps(root, interval, new ArrayList<>());
    }

    private List<IntervalNode> collectOverlaps(final IntervalNode parent,
                                               final int[] interval,
                                               List<IntervalNode> overlaps) {
        if(parent == null) {
            return overlaps;
        }

        if (parent.isOverlap(interval)) {
            overlaps.add(parent);
        }

        if (canOverlapLeft(parent.left, interval)) {
            collectOverlaps(parent.left, interval, overlaps);
        }

        if (canOverlapRight(parent.low, parent.right, interval)) {
            collectOverlaps(parent.right, interval, overlaps);
        }

        return overlaps;
    }

    private boolean canOverlapRight(final int minLow, final IntervalNode right, final int[] interval) {
        if (right == null) {
            return false;
        }

        if (interval[1] <= minLow) {
            return false;
        }

        int maxHigh = right.max;

        if (interval[0] >= maxHigh) {
            return false;
        }

        return true;
    }

    private boolean canOverlapLeft(final IntervalNode left, final int[] interval) {
        if (left == null) {
            return false;
        }
        int maxHigh = left.max;

        if (interval[0] >= maxHigh) {
            return false;
        }

        return true;

    }

    private IntervalNode add(IntervalNode parent, final int[] interval) {
        if (parent == null) {
            return IntervalNode.of(interval);
        }

        int compare = compareNodeValue(interval, parent);
        if (compare == 0) {
            parent.duplicates++;
            return parent;
        }

        if (compare <= 0) {
            parent.left = add(parent.left, interval);
            parent.max = Math.max(parent.max, parent.left.max);
        } else {
            parent.right = add(parent.right, interval);
            parent.max = Math.max(parent.max, parent.right.max);
        }

        if (!isRed(parent.left) && isRed(parent.right)) {
            parent = rotateLeft(parent);
        }

        if (isRed(parent.left) && isRed(parent.left.left)) {
            parent = rotateRight(parent);
        }

        if (isRed(parent.left) && isRed(parent.right)) {
            flipColors(parent);
        }

        return parent;
    }

    private int compareNodeValue(final int[] interval, final IntervalNode node) {
        int compare = Integer.compare(interval[0], node.low);
        if(compare != 0) {
            return compare;
        }

        return Integer.compare(interval[1], node.high);
    }

    private void flipColors(final IntervalNode parent) {
        parent.left.color = parent.left.color.inverse();
        parent.right.color = parent.right.color.inverse();
        parent.color = parent.color.inverse();
    }

    private IntervalNode rotateRight(final IntervalNode parent) {
        IntervalNode newParent = parent.left;
        parent.left = newParent.right;
        newParent.right = parent;
        newParent.color = parent.color;
        parent.color = RedBlackTree.Color.RED;

        parent.max = parent.evalMax();
        newParent.max = newParent.evalMax();

        return newParent;
    }

    private IntervalNode rotateLeft(final IntervalNode parent) {
        IntervalNode newParent = parent.right;
        parent.right = newParent.left;
        newParent.left = parent;
        newParent.color = parent.color;
        parent.color = RedBlackTree.Color.RED;

        parent.max = parent.evalMax();
        newParent.max = newParent.evalMax();

        return newParent;
    }

    private boolean isRed(final IntervalNode node) {
        return node != null && node.color == RedBlackTree.Color.RED;
    }

    public static class IntervalNode implements BinaryNode {
        public int low;
        public int high;
        int max;
        public int duplicates;
        RedBlackTree.Color color;
        IntervalNode left;
        IntervalNode right;

        public IntervalNode(final int low, final int high) {
            this.low = low;
            this.high = high;
            this.max = this.high;
            this.color = RedBlackTree.Color.RED;
        }

        public static IntervalNode of(int[] arr) {
            return new IntervalNode(arr[0], arr[1]);
        }

        public int evalMax() {
            this.max = Math.max(
                    left != null ? left.max : this.high,
                    right != null ? right.max : this.high
            );
            return this.max;
        }

        public boolean isOverlap(final int[] interval) {
            return !(interval[1] <= this.low || interval[0] >= this.high);
        }

        public boolean isOverlap(final IntervalNode node) {
            return !(node.high <= this.low || node.low >= this.high);
        }

        @Override
        public BinaryNode left() {
            return left;
        }

        @Override
        public BinaryNode right() {
            return right;
        }
    }


}
