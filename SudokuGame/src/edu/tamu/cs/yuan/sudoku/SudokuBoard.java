package edu.tamu.cs.yuan.sudoku;

public class SudokuBoard {
	
	
	int BOARD_SIZE = 9;
	
	private int[][] board;
	private int[][] rows;
	private int[][] cols;
	private int[][] boxes;
	
	public SudokuBoard() {
		this.board = new int[BOARD_SIZE][BOARD_SIZE];
		rows = new int[BOARD_SIZE][BOARD_SIZE + 1];
		cols = new int[BOARD_SIZE][BOARD_SIZE + 1];
		boxes = new int[BOARD_SIZE][BOARD_SIZE + 1];
	}
	
	public SudokuBoard(int[][] board) {
		this.board = new int[BOARD_SIZE][BOARD_SIZE];
		rows = new int[BOARD_SIZE][BOARD_SIZE + 1];
		cols = new int[BOARD_SIZE][BOARD_SIZE + 1];
		boxes = new int[BOARD_SIZE][BOARD_SIZE + 1];
		setupBoard(board);
	}
	
	public SudokuBoard(SudokuBoard sdkb) {
		this.board = new int[BOARD_SIZE][BOARD_SIZE];
		rows = new int[BOARD_SIZE][BOARD_SIZE + 1];
		cols = new int[BOARD_SIZE][BOARD_SIZE + 1];
		boxes = new int[BOARD_SIZE][BOARD_SIZE + 1];
		int[][] clone = new int[BOARD_SIZE][BOARD_SIZE];
		for(int i = 0 ; i < BOARD_SIZE; ++i) {
			for(int j = 0; j < BOARD_SIZE; ++j) {
				clone[i][j] = sdkb.getNum(i, j);
			}
		}
		setupBoard(clone);
	}
	
	public void setupBoard(int[][] board) {
		for(int row = 0; row < BOARD_SIZE; ++row) {
			for(int col = 0; col < BOARD_SIZE; ++col) {
				int num = board[row][col];
				writeDigit(row, col, num);
			}
		}
	}
	
	public boolean isEmptyDigit(int row, int col) {
		return this.board[row][col] == 0;
	}
	
	public int getNum(int row, int col) {
		return this.board[row][col];
	}
	
	public void writeDigit(int row, int col, int num) {
		if(num == 0) return;
        rows[row][num] += 1;
        cols[col][num] += 1;
        boxes[getBoxIndex(row, col)][num] += 1;
        board[row][col] = num;
	}
	
	public void deleteDigit(int row, int col) {
		int num = board[row][col];
    	if(num == 0) return;
        rows[row][num] -= 1;
        cols[col][num] -= 1;
        boxes[getBoxIndex(row, col)][num] -= 1;
        board[row][col] = 0;
    }
	
	public boolean isSudokuSolved() {
		for(int i = 1; i < BOARD_SIZE; ++i) {
			for(int j = 1; j < BOARD_SIZE + 1; ++j) {
				if(rows[i][j] != 1) return false;
				if(cols[i][j] != 1) return false;
				if(boxes[i][j] != 1) return false;
			}
		}
		return true;
	}
	
	public boolean canPutNumber(int row, int col, int num) {
        if(rows[row][num] > 0 || cols[col][num] > 0 || boxes[getBoxIndex(row, col)][num] > 0) return false;
        else return true;
    }
	
	private int getBoxIndex(int row, int col) {
        return row / 3 * 3 + col / 3;
    }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int row = 0; row < this.BOARD_SIZE; ++row) {
			for(int col = 0; col < this.BOARD_SIZE; ++ col) {
				sb.append(this.board[row][col]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
