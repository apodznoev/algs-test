package de.avpod.problems;

import java.util.Arrays;
import java.util.List;

public class SingleNumber {

    public static void main(String[] args) {
        System.out.println(singleNumber(Arrays.asList(
                1, 2, 3, 4, 4, 5, 3, 2, 1
        )));
    }

    public static int singleNumber(final List<Integer> A) {
        int result = 0;
        for(int i : A) {
            result = result ^ i;
        }
        return result;
    }
}
