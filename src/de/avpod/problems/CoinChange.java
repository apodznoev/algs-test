package de.avpod.problems;

import de.avpod.util.ConsoleUtil;

import java.util.Arrays;

public class CoinChange {

    public static void main(String[] args) {
        CoinChange coinChange = new CoinChange();
        int[] coins = ConsoleUtil.toIntArray(args[0]);
        int amount = Integer.valueOf(args[1]);
        System.out.print(Arrays.toString(coins));
        System.out.print(",");
        System.out.println(amount);
        System.out.println(coinChange.coinChange(coins, amount));
    }

    public int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        Arrays.sort(coins);

        return greedyChange(coins, amount);
    }

    private int greedyChange(int[] coins, int amount) {
        int coinsLength = coins.length;
        int largestCoinValue = coins[coinsLength - 1];
        int largestCoinMaxCount = amount / largestCoinValue;

        if (amount % largestCoinValue == 0) {
            return largestCoinMaxCount;
        }

        if (coins.length == 1) {
            return -1;
        }

        int minCoinsUsed = Integer.MAX_VALUE;

        for (int currentCoinsCount = largestCoinMaxCount; currentCoinsCount >= 0; currentCoinsCount--) {
            int remainingAmount = amount - largestCoinValue * currentCoinsCount;
            int[] remainingCoins = Arrays.copyOf(coins, coinsLength - 1);
            int solvedCoinsCount = greedyChange(remainingCoins, remainingAmount);

            if (solvedCoinsCount != -1) {
                minCoinsUsed = Math.min(currentCoinsCount + solvedCoinsCount, minCoinsUsed);
            }
        }

        return minCoinsUsed != Integer.MAX_VALUE ? minCoinsUsed : -1;
    }
}
