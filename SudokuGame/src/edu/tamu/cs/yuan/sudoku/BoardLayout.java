package edu.tamu.cs.yuan.sudoku;

import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
import javax.swing.*;     // Uses Swing's Container/Components
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
 

public class BoardLayout extends JFrame{
	public static final int GRID_SIZE = 3;    // Size of the board
	public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
	 
	// Name-constants for UI control (sizes, colors and fonts)
	public static final int CELL_SIZE = 60;   // Cell width/height in pixels
	public static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
	                                             // Board width/height in pixels
	public static final Color OPEN_CELL_BGCOLOR = Color.WHITE;
	public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB
	public static final Color OPEN_CELL_TEXT_NO = Color.RED;
	public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
	public static final Color CLOSED_CELL_TEXT = Color.BLACK;
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
	
	private SubGrid[] subGrids;
	InputListener listener = new InputListener();
	BoardController controller;
	
	public BoardLayout (BoardController controller) {
		
		this.controller = controller;
		
        subGrids = new SubGrid[SUBGRID_SIZE * SUBGRID_SIZE];
        
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, 2, 2));
        
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int index = (row * GRID_SIZE) + col;
                SubGrid board = new SubGrid(index);
                //board.setBorder(new CompoundBorder(new LineBorder(Color.GRAY, 3), new EmptyBorder(4, 4, 4, 4)));
                subGrids[index] = board;
                add(board);
            }
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setTitle("Sudoku");
        setPreferredSize(new Dimension(CANVAS_WIDTH * 3, CANVAS_HEIGHT * 3));
        pack();
        setVisible(true);
	}
	
	
	//4:      0100
	//7:      0111
	//4 ^ 7 = 0100 = 4
	/*
	class Grid extends JPanel{
        

        private SubGrid[] subGrids;

        public Grid() {
            
        }
	}*/
	
	class JTextFieldLimit extends PlainDocument {
		private int limit = 1;
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
		         return;
			if ((getLength() + str.length()) <= limit && Character.isDigit(str.charAt(0))) {
		         super.insertString(offset, str, attr);
			}
		}
	}
	
	
	class SubGrid extends JPanel {
		JTextField[][] tfCells = new JTextField[SUBGRID_SIZE][SUBGRID_SIZE];
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		public SubGrid(int index) {
			setBorder(new EmptyBorder(4, 4, 4, 4));
			setLayout(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));
			
			int X = index / 3, Y = index % 3;
			
			for(int row = 0; row < SUBGRID_SIZE; ++row) {
				for(int col = 0; col < SUBGRID_SIZE; ++col) {
					tfCells[row][col] = new JTextField(); // Allocate element of array
					tfCells[row][col].setDocument(new JTextFieldLimit());
		            add(tfCells[row][col]);            // ContentPane adds JTextField
		            
		            if(controller.board[X * 3 + row][Y * 3 + col] == 0) {
		            	tfCells[row][col].setText("");
		            	tfCells[row][col].setEditable(true);
		            	tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
		            	tfCells[row][col].addActionListener(listener);
		            }
		            else {
		            	tfCells[row][col].setText(controller.board[X * 3 + row][Y * 3 + col] + "");
		            	tfCells[row][col].setEditable(false);
		                tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
		                tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
		            }
		            tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
		            tfCells[row][col].setFont(FONT_NUMBERS);
				}
			}
			this.setBorder(blackline);
			setVisible(true);
		}
	}
	
	   // [TODO 2]
	   // Inner class to be used as ActionEvent listener for ALL JTextFields
	private class InputListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// All the 9*9 JTextFileds invoke this handler. We need to determine
	        // which JTextField (which row and column) is the source for this invocation.
	        int gridSelected = -1;
			int rowSelected = -1;
	        int colSelected = -1;
	        // Get the source object that fired the event
	        JTextField source = (JTextField)e.getSource();
	        // Scan JTextFileds for all rows and columns, and match with the source object
	        boolean found = false;
	        for(int index = 0; index < 9; ++index) {
		        for (int row = 0; row < SUBGRID_SIZE && !found; ++row) {
		        	for (int col = 0; col < SUBGRID_SIZE && !found; ++col) {
		        		if (subGrids[index].tfCells[row][col] == source) {
		        			gridSelected = index;
		        			rowSelected = row;
		        			colSelected = col;
		        			found = true;  // break the inner/outer loops
		        		}
		            }
		        }
	        }
	        //System.out.println(gridSelected);
	        //System.out.println(rowSelected);
	        //System.out.println(colSelected);
	         /*
	          * [TODO 5]
	          * 1. Get the input String via tfCells[rowSelected][colSelected].getText()
	          * 2. Convert the String to int via Integer.parseInt().
	          * 3. Assume that the solution is unique. Compare the input number with
	          *    the number in the puzzle[rowSelected][colSelected].  If they are the same,
	          *    set the background to green (Color.GREEN); otherwise, set to red (Color.RED).
	          */
	        
	        
	        writeDigit(gridSelected, rowSelected, colSelected, -1);
	         /* 
	          * [TODO 6] Check if the player has solved the puzzle after this move.
	          * You could update the masks[][] on correct guess, and check the masks[][] if
	          * any input cell pending.
	          */
		}
		
	}
	
	private int getRow(int gridSelected, int rowSelected, int colSelected) {
		int result = -1;
		return gridSelected / 3 * 3 + rowSelected;
	}
	
	private int getCol(int gridSelected, int rowSelected, int colSelected) {
		return gridSelected % 3 * 3 + colSelected;
	}
	
	void writeDigit(int gridSelected, int rowSelected, int colSelected, int solverAns) {
		String input = solverAns == -1 ? subGrids[gridSelected].tfCells[rowSelected][colSelected].getText() : Integer.toString(solverAns);
		int num = input.equals("") ? 0 : Integer.parseInt(input);
        int row = getRow(gridSelected, rowSelected, colSelected);
        int col = getCol(gridSelected, rowSelected, colSelected);
        
        int original_num = controller.board[row][col];
    	controller.deleteDigit(row, col, original_num);
        
    	if(num == 0) {
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_BGCOLOR);
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setText("");
    	}
    	else if(controller.isValidAction(row, col, num)) {
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setBackground(Color.GREEN);
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setText(num + "");
    	}
    	else {
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setBackground(Color.RED);
    	}
    	controller.writeDigit(row, col, num);
        
	}
	
}
