package edu.tamu.cs.yuan.sudoku;


public class BoardController{
    
    private BoardLayout layout;
    
    
    protected SudokuBoard board;
    
    private int[][] original_board;
    
    public BoardController(int[][] board) {
    	
        this.board = new SudokuBoard(board);
        this.original_board = board;
        layout = new BoardLayout(this);
    }
    
    protected void resetBoard() {
    	this.board = new SudokuBoard(this.original_board);
    }
    
    protected void loadNewBoard(int level) {
    	if(level == 0) {
    		this.original_board = new BoardGenerator().generateBoard(40);
    		this.board = new SudokuBoard(this.original_board);
    		
    	}
    	else if(level == 1) {
    		this.original_board = new BoardGenerator().generateBoard(35);
    		this.board = new SudokuBoard(this.original_board);
    	}
    	else {
    		this.original_board = new BoardGenerator().generateBoard(30);
    		this.board = new SudokuBoard(this.original_board);
    	}
    }
    

    
    void solveSudoku() {
    	resetBoard();
    	GameSolver solver = new GameSolver();
    	solver.solveSudoku(this.board, 1);
    }

}
