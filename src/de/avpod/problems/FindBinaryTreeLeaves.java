package de.avpod.problems;

import java.util.ArrayList;
import java.util.List;

public class FindBinaryTreeLeaves {

    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> leaves = new ArrayList<>();
        boolean finished;
        do {
            List<Integer> nextLevelLeaves = new ArrayList<>();
            finished = collectLeaves(root, nextLevelLeaves);
            leaves.add(nextLevelLeaves);
        } while (!finished);

        return leaves;
    }

    private boolean collectLeaves(final TreeNode node, final List<Integer> leaves) {
        if (node == null) {
            return true;
        }

        if (node.left == null && node.right == null) {
            leaves.add(node.val);
            return true;
        }

        final boolean leftWasLeaf = collectLeaves(node.left, leaves);
        final boolean rightWasLeaf = collectLeaves(node.right, leaves);

        if (leftWasLeaf) {
            node.left = null;
        }

        if (rightWasLeaf) {
            node.right = null;
        }

        return false;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
