package edu.tamu.cs.yuan.sudoku;


public class BoardController{
	
	
	int[][] board;
	int BOARD_SIZE = 9;
	int[][] rows = new int[9][10];
    int[][] cols = new int[9][10];
    int[][] boxes = new int[9][10];
    
    boolean isSolved = false;
    
    BoardLayout layout;
    
    public BoardController(int[][] board) {
        setupBoard(board);
        layout = new BoardLayout(this);
    }
    
    private void setupBoard(int[][] board) {
    	this.board = new int[BOARD_SIZE][BOARD_SIZE];
    	for(int row = 0; row < 9; ++row)
    		for(int col = 0; col < 9; ++col)
    			this.board[row][col] = board[row][col];
    	
        for(int row = 0; row < 9; ++row) {
            for(int col = 0; col < 9; ++col) {
                int num = this.board[row][col];
                if(num == 0) continue;
                rows[row][num] += 1;
                cols[col][num] += 1;
                boxes[getBox(row, col)][num] += 1;
            }
        }
        
        //System.out.println(rows[8][4]);
        //System.out.println(cols[8][4]);
        //System.out.println(boxes[8][4]);
    }
    
    void writeDigit(int row, int col, int num) {
    	if(num == 0) return;
        rows[row][num] += 1;
        cols[col][num] += 1;
        boxes[getBox(row, col)][num] += 1;
        board[row][col] = num;
    }
    
    void deleteDigit(int row, int col, int num) {
    	if(num == 0) return;
        rows[row][num] -= 1;
        cols[col][num] -= 1;
        boxes[getBox(row, col)][num] -= 1;
        board[row][col] = 0;
    }
    
    boolean isValidAction(int row, int col, int num) {
        if(rows[row][num] > 0 || cols[col][num] > 0 || boxes[getBox(row, col)][num] > 0) return false;
        else return true;
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
    	
    }
}
