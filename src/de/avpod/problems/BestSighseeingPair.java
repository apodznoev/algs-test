package de.avpod.problems;

public class BestSighseeingPair {
    int[] spots;


//    [2,7,7,2,1,7,10,4,3,3]

    public int maxScoreSightseeingPair(int[] values) {
        this.spots = values;
        int score = score(0, 1);
        return findMax(score, 0, 1, 1, 2);
    }

    private int findMax(final int curScore,
                        final int first,
                        final int second,
                        final int prevMaxSpot,
                        final int currentSpot) {
        if (currentSpot == spots.length) {
            return curScore;
        }

        int scoreFirstCurrent = score(first, currentSpot);
        int scoreSecondCurrent = score(second, currentSpot);
        int scorePrevCurrent = score(prevMaxSpot, currentSpot);
        if (curScore > scoreFirstCurrent && curScore > scoreSecondCurrent && curScore > scorePrevCurrent) {
            if (spots[currentSpot] - spots[prevMaxSpot] > Math.abs(currentSpot - prevMaxSpot)) {
                return findMax(curScore, first, second, currentSpot, currentSpot + 1);
            } else {
                return findMax(curScore, first, second, prevMaxSpot, currentSpot + 1);
            }
        }

        if (scoreFirstCurrent > scoreSecondCurrent && scoreFirstCurrent > scorePrevCurrent) {
            return findMax(scoreFirstCurrent, first, currentSpot, currentSpot, currentSpot + 1);
        }

        if (scoreSecondCurrent > scoreFirstCurrent && scoreSecondCurrent > scorePrevCurrent) {
            return findMax(scoreSecondCurrent, second, currentSpot, currentSpot, currentSpot + 1);
        }

        return findMax(scorePrevCurrent, prevMaxSpot, currentSpot, currentSpot, currentSpot + 1);
    }

    private int score(final int i, final int j) {
        return spots[i] + spots[j] - Math.abs(i - j);
    }
}
