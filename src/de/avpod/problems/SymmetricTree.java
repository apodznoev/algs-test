package de.avpod.problems;

import de.avpod.datastructures.TreeNode;

public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        return checkSubtrees(root.left, root.right);
    }

    private boolean checkSubtrees(final TreeNode left, final TreeNode right) {
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null || left.val != right.val) {
            return false;
        }
        boolean leftSymmetric = checkSubtrees(left.left, right.right);
        if (!leftSymmetric) {
            return false;
        }

        return checkSubtrees(left.right, right.left);
    }
}
