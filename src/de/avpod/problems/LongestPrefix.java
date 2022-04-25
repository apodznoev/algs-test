package de.avpod.problems;

public class LongestPrefix {

    public static void main(String[] args) {
        LongestPrefix lp = new LongestPrefix();
        System.out.println(lp.longestCommonPrefix(new String[]{"dog","racecar","car"}));
    }

    private static final char SPECIAL_CHARACTER = '-';

    public String longestCommonPrefix(String[] strs) {
        char[] currentMatch = strs[0].toCharArray();

        for(String str : strs) {
            if(str.length() == 0) {
                return "";
            }

            for(int i = 0; i < currentMatch.length; i++) {
                if(currentMatch[i] == SPECIAL_CHARACTER) {
                    break;
                }

                if(str.length() < i + 1) {
                    currentMatch[i] = SPECIAL_CHARACTER;
                    break;
                }

                if(currentMatch[i] != str.charAt(i)) {
                    currentMatch[i] = SPECIAL_CHARACTER;
                    break;
                }
            }
        }
        StringBuilder result = new StringBuilder();

        for (final char matchedCharacter : currentMatch) {
            if (matchedCharacter == SPECIAL_CHARACTER) {
                return result.toString();
            }

            result.append(matchedCharacter);
        }

        return result.toString();
    }
}
