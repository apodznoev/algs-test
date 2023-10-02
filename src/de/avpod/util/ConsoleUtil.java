package de.avpod.util;

import java.util.Arrays;
import java.util.List;

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

    public static int[][] firstArgToIntMatrixWithBrackets(final String[] args) {
        if (args.length != 1) {
            return new int[][]{{}};
        }


        List<String> rows = Arrays.stream(args[0]
                        .replace("]]", "")
                        .replace("[[", "")
                        .split("],\\["))
                .toList();
        int[][] result = new int[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            String s = rows.get(i);
            int[] row = firstArgToIntArrayWithBrackets(new String[]{s});
            result[i] = row;
        }

        return result;
    }
}
