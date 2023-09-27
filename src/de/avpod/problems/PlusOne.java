package de.avpod.problems;

import java.util.Arrays;

public class PlusOne {

    public static void main(String[] args) {
        PlusOne plusOne = new PlusOne();
        int[] res = plusOne.plusOne(new int[]{9, 9, 9});
        System.out.println(Arrays.toString(res));
    }

    public int[] plusOne(int[] digits) {
        if (digits[digits.length - 1] != 9) {
            digits[digits.length - 1] = digits[digits.length - 1] + 1;
            return digits;
        }

        return subRoutine(digits, digits.length - 1);
    }

    public int[] subRoutine(int[] digits, int shiftPosition) {
        if (digits[shiftPosition] != 9) {
            throw new IllegalArgumentException("Expected 9 at position " + shiftPosition);
        }

        if (shiftPosition == 0) {
            int[] result = new int[digits.length + 1];
            result[0] = 1;
            result[1] = 0;
            for (int i = 1; i < digits.length; i++) {
                result[i + 1] = digits[i];
            }
            return result;
        } else {
            digits[shiftPosition] = 0;
            if (digits[shiftPosition - 1] == 9) {
                return subRoutine(digits, shiftPosition - 1);
            } else {
                digits[shiftPosition - 1] = digits[shiftPosition - 1] + 1;
                return digits;
            }
        }
    }

}
