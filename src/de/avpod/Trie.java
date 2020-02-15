package de.avpod;

import java.util.Arrays;
import java.util.List;

public class Trie {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.addAll(args);

        List<String> toTest = Arrays.asList(
                "test", "tes", "check", "checker", "t", "word"
        );

        for (String word : toTest) {
            System.out.println(word + " has key: " + trie.hasKey(word) + " has prefix: " + trie.hasPrefix(word));
        }
    }

    private static final int ALPHABET_SIZE = 26;

    private final TrieNode root = new TrieNode();

    public void addAll(String[] keys) {
        Arrays.stream(keys).peek(this::add).count();
    }

    public void add(String key) {
        TrieNode curNode = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            int childIndex = getIndex(c);
            TrieNode nextNode = curNode.getChild(childIndex);

            if (nextNode == null) {
                nextNode = new TrieNode();
                curNode.setChild(childIndex, nextNode);
            }
            curNode = nextNode;
        }
        curNode.terminates = true;
    }

    public boolean hasKey(String key) {
        TrieNode lastNode = getNodeForKey(key);
        return lastNode != null && lastNode.terminates;
    }

    public boolean hasPrefix(String key) {
        TrieNode lastNode = getNodeForKey(key);
        return lastNode != null;
    }

    public TrieNode getNodeForKey(String key) {
        TrieNode curNode = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            int childIndex = getIndex(c);
            TrieNode nextNode = curNode.getChild(childIndex);

            if (nextNode == null) {
                return null;
            }
            curNode = nextNode;
        }
        return curNode;
    }

    private static int getIndex(char c) {
        return c - 'a';
    }

    public static class TrieNode {
        private TrieNode[] children;
        boolean terminates;


        public boolean hasChildren() {
            return children != null;
        }

        public TrieNode getChild(int childIndex) {
            return children == null ? null : children[childIndex];
        }

        public void setChild(int childIndex, TrieNode nextNode) {
            if (children == null) {
                children = new TrieNode[ALPHABET_SIZE];
            }
            children[childIndex] = nextNode;
        }
    }
}
