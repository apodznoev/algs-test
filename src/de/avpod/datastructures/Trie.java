package de.avpod.datastructures;

public class Trie {
    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode curNode = root;

        for (char c : word.toCharArray()) {
            if (curNode.children[c % 26] == null) {
                curNode.children[c % 26] = new TrieNode();
            }
            curNode = curNode.children[c % 26];
        }

        curNode.isWord = true;
    }

    public boolean search(String word) {
        TrieNode nodeWithPrefix = getStartsWith(word);
        return nodeWithPrefix != null && nodeWithPrefix.isWord;
    }

    public boolean startsWith(String prefix) {
        return getStartsWith(prefix) != null;
    }

    private TrieNode getStartsWith(String prefix) {
        TrieNode curNode = root;

        for (char c : prefix.toCharArray()) {
            TrieNode nextNode = curNode.children[c % 26];
            if (nextNode == null) {
                return null;
            }
            curNode = nextNode;
        }

        return curNode;
    }

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }
}
