package de.avpod.algorithms;

import de.avpod.datastructures.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class TreeTraversal {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        traverse(root, result);
        return result;
    }

    private void traverse(final TreeNode root, final List<Integer> result) {
        if(root == null) {
            return;
        }

        traverse(root.left, result);
        result.add(root.val);
        traverse(root.right, result);
    }
}
