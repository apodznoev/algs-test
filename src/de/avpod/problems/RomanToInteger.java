package de.avpod.problems;

import java.util.Map;
import java.util.Set;

public class RomanToInteger {
    Map<Character, Integer> values = Map.of(
            'I', 1,
            'V', 5,
            'X', 10,
            'L', 50,
            'C', 100,
            'D', 500,
            'M', 1000
    );

    public static void main(String[] args) {

    }

    public int romanToInt(String s) {
        if (s.isEmpty()) {
            return 0;
        }

        if (s.length() == 1) {
            return values.get(s.charAt(0));
        }

        Character previousChar = null;
        Character currentChar = null;
        int result = 0;
        boolean previousCharHandled = true;

        for (int i = 0; i < s.length(); i++) {
            previousChar = currentChar;
            currentChar = s.charAt(i);

            if (!previousCharHandled) {
                if (values.get(previousChar) < values.get(currentChar)) {
                    previousCharHandled = true;
                    result += values.get(currentChar) - values.get(previousChar);
                    continue;
                }

                if (values.get(previousChar).equals(values.get(currentChar))) {
                    previousCharHandled = true;
                    result += 2 * values.get(previousChar);
                    continue;
                }

                previousCharHandled = true;
                result += values.get(previousChar);
            }

            if (Set.of('I', 'X', 'C').contains(currentChar)) {
                previousCharHandled = false;
                continue;
            }

            result += values.get(currentChar);
        }

        if (!previousCharHandled) {
            result += values.get(currentChar);
        }

        return result;
    }
}
