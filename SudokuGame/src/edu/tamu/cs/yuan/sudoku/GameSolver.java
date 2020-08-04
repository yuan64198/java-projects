package edu.tamu.cs.yuan.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameSolver {
    //There's 9 rows, 9 cols and 9 boxes, each row, col and box can only have each number from 1 to 9 appeared once.
    
    
    
    int num_result = 0;
    int result_limit = 1;
    boolean isSolved = false;
    
    GameSolver() {
    	
    }
    
    public int solveSudoku(SudokuBoard board, int result_limit) {
        return backtrack(0, 0, board, result_limit);
    }
    
    int backtrack(int row, int col, SudokuBoard board, int result_limit) {
        if(board.isEmptyDigit(row, col)) {
        	List<Integer> list = new ArrayList();
        	for(int i = 1; i <= 9; ++i) list.add(i);
        	Collections.shuffle(list);
            for(int num : list) {
                if(!board.canPutNumber(row, col, num)) continue;
                board.writeDigit(row, col, num);
                tryNextSlot(row, col, board, result_limit);
                //if(num_result < result_limit) board.deleteDigit(row, col);
                if(!isSolved) board.deleteDigit(row, col);
            }
        }
        else {
            tryNextSlot(row, col, board, result_limit);
        }
        return num_result;
    }
    
    
    
    void tryNextSlot(int row, int col, SudokuBoard board, int result_limit) {
        if(row == 8 && col == 8) {
        	num_result += 1;
        	isSolved = true;
        	return;
        }
        else {
            if(col == 8) backtrack(row + 1, 0, board, result_limit);
            else backtrack(row, col + 1, board, result_limit);
        }
    }
}
