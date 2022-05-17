package de.avpod.problems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpiralMatrix {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int colStart = 0;
        int colEnd = matrix[0].length - 1;
        int rowStart = 0;
        int rowEnd = matrix.length - 1;

        while (colStart <= colEnd && rowStart <= rowEnd) {
            result.addAll(traverseSpiral(colStart, colEnd, rowStart, rowEnd, matrix));
            colStart++;
            colEnd--;
            rowStart++;
            rowEnd--;
        }

        return result;
    }

    private List<Integer> traverseSpiral(final int colStart, final int colEnd,
                                         final int rowStart, final int rowEnd,
                                         final int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        for (int i = colStart; i <= colEnd; i++) {
            result.add(matrix[rowStart][i]);
        }

        for (int j = rowStart + 1; j <= rowEnd; j++) {
            result.add(matrix[j][colEnd]);
        }

        if(colStart == colEnd || rowStart == rowEnd){
            return result;
        }

        for (int i = colEnd - 1; i >= colStart; i--) {
            result.add(matrix[rowEnd][i]);
        }

        for (int j = rowEnd - 1; j > rowStart; j--) {
            result.add(matrix[j][colStart]);
        }

        return result;
    }
}
