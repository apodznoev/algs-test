package de.avpod.problems;

import java.util.HashMap;
import java.util.Map;

public class StringPermutations {

    public static void main(String[] args) {
        String a = args[0];
        String b = args[1];

        Map<Character, Integer> toMatch = new HashMap<>();
        fillCharacterCounts(toMatch, a);
        Map<Character, Integer> currentWindow = new HashMap<>();
        int trivialPermutations = calculateTrivialPermutations(toMatch);
        int result = 0;
        int curWindowSize = 0;
        for (int i = 0; i < b.length(); i++) {
            char curChar = b.charAt(i);

            if (!toMatch.containsKey(curChar)) {
                i += curWindowSize;
                currentWindow.clear();
                curWindowSize = 0;
                continue;
            }

            incrementCount(currentWindow, curChar);
            curWindowSize++;

            if (charactersMatch(currentWindow, toMatch)) {
                result += trivialPermutations;
            }

            if (curWindowSize == a.length()) {
                decrementCount(currentWindow, b.charAt(i - curWindowSize + 1));
                curWindowSize--;
            }
        }

        System.out.println(result);
    }

    private static boolean charactersMatch(Map<Character, Integer> currentWindow, Map<Character, Integer> toMatch) {
        return currentWindow.equals(toMatch);
    }

    private static void decrementCount(Map<Character, Integer> counts, char character) {
        counts.compute(character, (key, value) -> value == null || value == 1 ? null : value - 1);
    }

    private static void incrementCount(Map<Character, Integer> counts, char character) {
        counts.compute(character, (key, value) -> value == null ? 1 : value + 1);
    }

    private static int calculateTrivialPermutations(Map<Character, Integer> characters) {
        int result = 1;
        for (int charsCount : characters.values()) {
            result *= factorial(charsCount);
        }
        return result;
    }

    private static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private static void fillCharacterCounts(Map<Character, Integer> toMatch, String a) {
        for (char c : a.toCharArray()) {
            toMatch.compute(c, (key, value) -> value == null ? 1 : value + 1);
        }
    }


}
