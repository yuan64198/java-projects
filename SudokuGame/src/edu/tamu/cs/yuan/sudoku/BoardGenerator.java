package edu.tamu.cs.yuan.sudoku;

import java.util.*;

public class BoardGenerator{
	GameSolver solver;
	public BoardGenerator() {
		this.solver = new GameSolver();
	}
	
	private SudokuBoard generateCompleteBoard() {
		SudokuBoard board = new SudokuBoard();
		solver.solveSudoku(board, 1);
		return board;
	}
	
	public int[][] generateBoard(int k) {
		SudokuBoard board = generateCompleteBoard();
		
		
		
		int num = board.BOARD_SIZE * board.BOARD_SIZE - k;
		
		
		for(int i = 0; i < num; ++i) {
			int row;
			int col;
			do {
				int rand = (int)(Math.random() * board.BOARD_SIZE * board.BOARD_SIZE);
				row = rand / board.BOARD_SIZE;
				col = rand % board.BOARD_SIZE;
			} while(board.isEmptyDigit(row, col));
			
			int digit = board.getNum(row, col);
			board.deleteDigit(row, col);
			
			int num_result = solver.solveSudoku(new SudokuBoard(board), 1);
			if(num_result == 2) {
				board.writeDigit(row, col, digit);
			}
		}
		
		
		int[][] result = new int[board.BOARD_SIZE][board.BOARD_SIZE];
		for(int row = 0; row < board.BOARD_SIZE; ++row) {
			for(int col = 0; col < board.BOARD_SIZE; ++col) {
				result[row][col] = board.getNum(row, col);
			}
		}
		
		return result;
	}
	
}
