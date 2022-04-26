package de.avpod.problems;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

    public static void main(String[] args) {

    }

    private static final Map<Integer, Integer> cache = new HashMap<>();

    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        int fib2 = fib(n - 2);
        cache.put(n - 2, fib2);

        int fib1 = fib(n - 1);
        cache.put(n - 1, fib1);
        return fib1 + fib2;
    }
}
