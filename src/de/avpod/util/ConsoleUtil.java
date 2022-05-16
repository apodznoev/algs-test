package de.avpod.util;

import java.util.Arrays;

public class ConsoleUtil {
    public static int[] firstArgToIntArrayWithBrackets(String[] s) {
        if (s.length == 0) {
            return new int[]{};
        }

        return Arrays.stream(s[0]
                        .replace("]", "")
                        .replace("[", "")
                        .split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static int[] toIntArray(String s) {
        return Arrays.stream(s.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static int[] toIntArray(String[] args) {
        int[] result = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            result[i] = Integer.parseInt(args[i]);
        }
        return result;
    }
}
