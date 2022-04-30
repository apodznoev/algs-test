package de.avpod.problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SurroundedRegions {
    public static void main(String[] args) {
        SurroundedRegions surroundedRegions = new SurroundedRegions();
        String tmp = args[0];
        tmp = tmp.replace("[[", "");
        tmp = tmp.replace("]]", "");
        String[] rows = Arrays.stream(tmp.split("\\],\\["))
                .map(s -> s
                        .replace(",", "")
                        .replace(" ", ""))
                .collect(Collectors.toList())
                .toArray(new String[]{});
        System.out.println(rows.length);
        System.out.println(rows[0].length());

        char[][] board = new char[rows.length][rows[0].length()];
        for (int j = 0; j < rows.length; j++) {
            for (int i = 0; i < rows[j].length(); i++) {
                board[j][i] = rows[j].charAt(i);
            }
        }
        printBoard(0, 0, board);


        surroundedRegions.solve(board);
        System.out.println("___\\  /_________");
        System.out.println("____\\/_________");
        printBoard(0, 0, board);
    }

    private static void printBoard(final int curi, final int curj, final char[][] board) {
        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board[0].length; i++) {
                System.out.print(i == 0 ? "[" : "");
                System.out.print(i == curi && j == curj ? Character.toLowerCase(board[j][i]) : board[j][i]);
                System.out.print(i != board[0].length - 1 ? "," : "]");
            }
            System.out.println();
        }
        System.out.println("________________");

    }

    private static final char X = 'X';
    private static final char O = 'O';

    private static final class Coordinate {
        public final int column;//i column
        public final int row;// j row

        public Coordinate(final int column, final int row) {
            this.column = column;
            this.row = row;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Coordinate that = (Coordinate) o;
            return column == that.column && row == that.row;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + row;
            result = 31 * result + column;
            return result;
        }
    }

    public void solve(char[][] board) {
        int m = board.length;//rowsnum
        int n = board[0].length;//colssnum
        Set<Coordinate> cellsToInvert = new HashSet<>();
        Set<Coordinate> visitedCells = new HashSet<>();

        for (int j = 1; j < m; j++) {
            for (int i = 1; i < n; i++) {
                char curChar = board[j][i];

                if (curChar != O) {
                    continue;
                }
                Coordinate coordinate = new Coordinate(i, j);
                if (visitedCells.contains(coordinate)) {
                    continue;
                }

                var checkedCells = new HashSet<Coordinate>();
                boolean isolated = isIsolated(i, j, board, checkedCells);
                if (isolated) {
                    cellsToInvert.addAll(checkedCells);
                }

                visitedCells.addAll(checkedCells);
            }
        }

        for (Coordinate coordinate : cellsToInvert) {
            board[coordinate.row][coordinate.column] = X;
        }
    }

    /**
     * returns true if none of the 'O's connected to board[i][j] touches the border
     */
    private boolean isIsolated(final int i,
                               final int j,
                               char[][] board,
                               final HashSet<Coordinate> checkedCells) {
        if (checkedCells.contains(new Coordinate(i, j))) {
            return true;
        }

        char curChar = board[j][i];
        if (curChar != O) {
            checkedCells.add(new Coordinate(i, j));
            return true;
        }

        //current char is 'O' and it is on the border
        if (i == 0 || j == 0 || j == board.length - 1 || i == board[0].length - 1) {
            return false;
        }

        checkedCells.add(new Coordinate(i, j));

        boolean topTouchesBorder = !isIsolated(i, j - 1, board, checkedCells);
        boolean buttomTouchesBorder = !isIsolated(i, j + 1, board, checkedCells);
        boolean leftTouchesBorder = !isIsolated(i - 1, j, board, checkedCells);
        boolean rightTouchesBorder = !isIsolated(i + 1, j, board, checkedCells);


        return !topTouchesBorder && !buttomTouchesBorder && !leftTouchesBorder && !rightTouchesBorder;
    }
}
