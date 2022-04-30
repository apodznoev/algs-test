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
            bestBuySellMin[0] = buyPrice;
            bestBuySellMin[1] = newSellPrice;
        }

        if (assessProfit2 > currentProfit && assessProfit2 >= assessProfit1 && assessProfit2 >= assessProfit3) {
            bestBuySellMin[0] = sellPrice;
            bestBuySellMin[1] = newSellPrice;
        }

        if (assessProfit3 > currentProfit) {
            bestBuySellMin[0] = minObservedPrice;
            bestBuySellMin[1] = newSellPrice;
        }

        bestBuySellMin[2] = Math.min(newSellPrice, minObservedPrice);

        return bestBuySellMin;
    }
}
