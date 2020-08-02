package edu.tamu.cs.yuan.sudoku;


public class BoardController{
	
	
	int[][] board;
	int BOARD_SIZE = 9;
	boolean[][] rows = new boolean[9][10];
    boolean[][] cols = new boolean[9][10];
    boolean[][] boxes = new boolean[9][10];
    
    boolean isSolved = false;
    
    public BoardController(int[][] board) {
        setupBoard(board);
    }
    
    private void setupBoard(int[][] board) {
    	this.board = new int[BOARD_SIZE][BOARD_SIZE];
    	for(int i = 0; i < 9; ++i)
    		for(int j = 0; j < 9; ++j)
    			this.board[i][j] = board[i][j];
    	
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                int num = this.board[i][j];
                rows[i][num] = true;
                cols[j][num] = true;
                boxes[getBox(i, j)][num] = true;
            }
        }
    }
    
    void writeDigit(int row, int col, int num) {
        rows[row][num] = true;
        cols[col][num] = true;
        boxes[getBox(row, col)][num] = true;
        board[row][col] = num;
    }
    
    void deleteDigit(int row, int col, int num) {
        rows[row][num] = false;
        cols[col][num] = false;
        boxes[getBox(row, col)][num] = false;
        board[row][col] = 0;
    }
    
    boolean isValidAction(int row, int col, int num) {
        return !(rows[row][num] || cols[col][num] || boxes[getBox(row, col)][num]);
    }
    

    
    boolean isEmptySlot(int row, int col) {
        return board[row][col] == 0;
    }
    
    int getBox(int row, int col) {
        int blockX = row / 3;
        int blockY = col / 3;
        return blockX * 3 + blockY;
    }
    
    public void display() {
    	BoardLayout layout = new BoardLayout(this);
    }
}
