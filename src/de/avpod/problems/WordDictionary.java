package de.avpod.problems;

public class WordDictionary {
    Trie trie;

    public WordDictionary() {
        trie = new Trie();
    }

    public void addWord(String word) {
        trie.insert(word);
    }

    public boolean search(String word) {
        return trie.search(word);
    }

    private static class Trie {
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
            return hasWord(root, word, 0);
        }


        boolean hasWord(TrieNode node, String word, int begin) {
            if(node == null) {
                return false;
            }

            if (word.length() == begin) {
                return node.isWord;
            }

            char curChar = word.charAt(begin);

            if(curChar == '.') {
                for(TrieNode child : node.children) {
                    boolean wildcardMatch = hasWord(child, word, begin + 1);
                    if(wildcardMatch) {
                        return true;
                    }
                }
                return false;
            }

            TrieNode nextNode = node.children[curChar % 26];

            return hasWord(nextNode, word, ++begin);
        }

        private static class TrieNode {
            TrieNode[] children = new TrieNode[26];
            boolean isWord;
        }

    }
}
