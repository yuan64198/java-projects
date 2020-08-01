package edu.tamu.cs.yuan.sudoku;

public class GameSolver {
    //There's 9 rows, 9 cols and 9 boxes, each row, col and box can only have each number from 1 to 9 appeared once.
    boolean[][] rows = new boolean[9][10];
    boolean[][] cols = new boolean[9][10];
    boolean[][] boxes = new boolean[9][10];
    
    boolean isSolved = false;
    
    public void solveSudoku(int[][] board) {
        setupBoard(board);
        backtrack(board, 0, 0);
    }
    
    private void backtrack(int[][] board, int row, int col) {
        if(isEmptySlot(board, row, col)) {
            for(int num = 1; num <= 9; ++num) {
                if(!canPlaceNumber(row, col, num)) continue;
                placeNumber(board, row, col, num);
                tryNextSlot(board, row, col);
                if(!isSolved) deleteNumber(board, row, col, num);
            }
        }
        else {
            tryNextSlot(board, row, col);
        }
    }
    
    private void placeNumber(int[][] board, int row, int col, int num) {
        rows[row][num] = true;
        cols[col][num] = true;
        boxes[getBox(row, col)][num] = true;
        board[row][col] = num;
    }
    
    private void deleteNumber(int[][] board, int row, int col, int num) {
        rows[row][num] = false;
        cols[col][num] = false;
        boxes[getBox(row, col)][num] = false;
        board[row][col] = 0;
    }
    
    private boolean canPlaceNumber(int row, int col, int num) {
        return !(rows[row][num] || cols[col][num] || boxes[getBox(row, col)][num]);
    }
    
    private void tryNextSlot(int[][] board, int row, int col) {
        if(row == 8 && col == 8) isSolved = true;
        else {
            if(col == 8) backtrack(board, row + 1, 0);
            else backtrack(board, row, col + 1);
        }
    }
    
    private boolean isEmptySlot(int[][] board, int row, int col) {
        return board[row][col] == 0;
    }
    
    private void setupBoard(int[][] board) {
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                if(board[i][j] == '.') continue;
                int num = board[i][j];
                rows[i][num] = true;
                cols[j][num] = true;
                boxes[getBox(i, j)][num] = true;
            }
        }
    }
    
    private int getBox(int row, int col) {
        int blockX = row / 3;
        int blockY = col / 3;
        return blockX * 3 + blockY;
    }
}
