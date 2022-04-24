package de.avpod.problems;

import de.avpod.datastructures.Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Respace {

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("No arguments provided");
        }

        String text = args[0];
        String[] dictionary = Arrays.copyOfRange(args, 1, args.length);
        Trie trie = new Trie();
        trie.addAll(dictionary);

        int unrecognizedCount = 0;
        List<Integer> whitespacesPositions = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        StringBuilder currentUnrecognized = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char nextChar = text.charAt(i);
            currentWord.append(nextChar);
            Trie.TrieNode node = trie.getNodeForKey(currentWord.toString());

            if (node == null) {
                currentUnrecognized.append(currentWord);
                unrecognizedCount += currentWord.length();
                currentWord = new StringBuilder();
                continue;
            }

            if (!node.terminates) {
                continue;
            }

            if (node.terminates
//                    && !node.hasChildren()
            ) {
                if (currentUnrecognized.length() > 0) {
                    whitespacesPositions.add(i - currentWord.length() - currentUnrecognized.length());
                }
                whitespacesPositions.add(i - currentWord.length());
                currentWord = new StringBuilder();
                currentUnrecognized = new StringBuilder();
            }
        }

        text = setWhitespaces(text, whitespacesPositions);
        System.out.println(text + "      unrecognized:" + unrecognizedCount);
    }

    private static String setWhitespaces(String text, List<Integer> whitespacesPositions) {
        int previousPosition = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < whitespacesPositions.size(); i++) {
            int position = whitespacesPositions.get(i) + 1;
            result.append(text, previousPosition, position ).append(" ");
            previousPosition = position;
        }
        return result.toString();
    }
}
