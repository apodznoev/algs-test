package de.avpod.problems;

import java.util.HashMap;
import java.util.Map;

public class DecodeWays {

    public static void main(String[] args) {
        DecodeWays dw = new DecodeWays();
        System.out.println(dw.numDecodings(args[0]));
    }

    private static final Map<String, Integer> cache = new HashMap<>();

    public int numDecodings(String s) {
        return Math.max(decodeWays(0, s), 0);
    }

    private int decodeWays(final int start, final String s) {
        String cacheKey = s.substring(start);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        int length = s.length() - start;

        if (length == 0) {
            return 1;
        }

        int decode1 = decodeChar(s, start);

        if (length == 1) {
            return decode1;
        }

        int decode2 = decodeTwoChars(s, start);

        if (decode1 == 0 && decode2 == 0) {
            return 0;
        }

        if (decode1 == 0) {
            return decodeWays(start + 2, s);
        }

        if (decode2 == 0) {
            return decodeWays(start + 1, s);
        }

        int result = decodeWays(start + 1, s) + decodeWays(start + 2, s);
        cache.put(cacheKey, result);
        return result;
    }

    private int decodeChar(String s, int position) {
        if (position >= s.length()) {
            return 0;
        }
        return s.charAt(position) == '0' ? 0 : 1;
    }

    private int decodeTwoChars(String s, int startPosition) {
        if (startPosition >= s.length() - 1) {
            return 0;
        }

        boolean startsZero = s.charAt(startPosition) == '0';
        boolean endsZero = s.charAt(startPosition + 1) == '0';
        if (startsZero) {
            return 0;
        }

        String number = s.substring(startPosition, startPosition + 2);
        if (endsZero) {
            return number.equals("10") || number.equals("20") ? 1 : 0;
        }

        if (Integer.parseInt(number) > 26) {
            return 0;
        }

        return 1;
    }
}
