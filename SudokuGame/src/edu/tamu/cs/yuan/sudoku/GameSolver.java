package edu.tamu.cs.yuan.sudoku;

import java.util.concurrent.TimeUnit;

public class GameSolver {
    //There's 9 rows, 9 cols and 9 boxes, each row, col and box can only have each number from 1 to 9 appeared once.
    boolean[][] rows = new boolean[9][10];
    boolean[][] cols = new boolean[9][10];
    boolean[][] boxes = new boolean[9][10];
    
    boolean isSolved = false;
    
    BoardController controller;
    
    GameSolver(BoardController controller) {
    	this.controller = controller;
    }
    
    public void solveSudoku() {
        backtrack(0, 0);
    }
    
    private void backtrack(int row, int col) {
        if(controller.isEmptySlot(row, col)) {
            for(int num = 1; num <= 9; ++num) {
                if(!controller.isValidAction(row, col, num)) continue;
                int gridSelected = row / 3 * 3 + col / 3;
                int rowSelected = row % 3;
                int colSelected = col % 3;
                controller.layout.writeDigit(gridSelected, rowSelected, colSelected, num);
                tryNextSlot(row, col);
                if(!isSolved) controller.layout.writeDigit(gridSelected, rowSelected, colSelected, 0);
                
                try {
                    // thread to sleep for 1000 milliseconds
                    Thread.sleep(10);
                 } catch (Exception e) {
                    System.out.println(e);
                 }
                 
            }
        }
        else {
            tryNextSlot(row, col);
        }
    }
    
    
    private void tryNextSlot(int row, int col) {
        if(row == 8 && col == 8) isSolved = true;
        else {
            if(col == 8) backtrack(row + 1, 0);
            else backtrack(row, col + 1);
        }
    }
}
