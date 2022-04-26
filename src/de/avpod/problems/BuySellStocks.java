package de.avpod.problems;

public class BuySellStocks {
    public int maxProfit(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        int[] bestBuySell = subroutine(prices, prices.length);

        return Math.max(0, bestBuySell[1] - bestBuySell[0]);
    }

    int[] subroutine(int[] prices, int n) {
        if (n == 2) {
            return new int[]{ prices[0], prices[1], Math.min(prices[0], prices[1])};
        }
        int[] bestBuySellMin = subroutine(prices, n - 1);
        int currentProfit = bestBuySellMin[1] - bestBuySellMin[0];
        int buyPrice = bestBuySellMin[0];
        int sellPrice = bestBuySellMin[1];
        int minObservedPrice = bestBuySellMin[2];

        int newSellPrice = prices[n - 1];
        int assessProfit1 = newSellPrice - buyPrice;
        int assessProfit2 = newSellPrice - sellPrice;
        int assessProfit3 = newSellPrice - minObservedPrice;

        if (assessProfit1 > currentProfit && assessProfit1 >= assessProfit2 && assessProfit1 >= assessProfit3) {
            return new int[]{buyPrice, newSellPrice, Math.min(newSellPrice, minObservedPrice)};
        }

        if (assessProfit2 > currentProfit && assessProfit2 >= assessProfit1 && assessProfit2 >= assessProfit3) {
            return new int[]{sellPrice, newSellPrice, Math.min(newSellPrice, minObservedPrice)};
        }

        if (assessProfit3 > currentProfit) {
            return new int[]{minObservedPrice, newSellPrice, Math.min(newSellPrice, minObservedPrice)};
        }

        return new int[]{ buyPrice, sellPrice, Math.min(newSellPrice, minObservedPrice)};
    }
}
