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
	private BoardController controller;
	
	static JMenuBar mb;
	
	// JMenu 
    static JMenu mainMenu, startNewGame; 
  
    // Menu items 
    static JMenuItem showAnswer, easy, medium, hard, reset;
	
	public BoardLayout (BoardController controller) {
		
		this.controller = controller;
		
        subGrids = new SubGrid[SUBGRID_SIZE * SUBGRID_SIZE];
        
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, 2, 2));
        
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int index = (row * GRID_SIZE) + col;
                SubGrid subGrid = new SubGrid(index);
                //board.setBorder(new CompoundBorder(new LineBorder(Color.GRAY, 3), new EmptyBorder(4, 4, 4, 4)));
                subGrids[index] = subGrid;
                add(subGrid);
            }
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setTitle("Sudoku");
        setPreferredSize(new Dimension(CANVAS_WIDTH * 3, CANVAS_HEIGHT * 3));
        pack();
        
        setMenuBar();
        setVisible(true);
	}
	
	
	
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
		            
		            if(controller.board.isEmptyDigit(X * 3 + row, Y * 3 + col)) {
		            	//System.out.println(X * 3 + row);
		            	tfCells[row][col].setText("");
		            	tfCells[row][col].setEditable(true);
		            	tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
		            	tfCells[row][col].addActionListener(listener);
		            }
		            else {
		            	tfCells[row][col].setText(controller.board.getNum(X * 3 + row, Y * 3 + col) + "");
		            	tfCells[row][col].setEditable(false);
		                tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
		                tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
		            }
		            tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
		            tfCells[row][col].setFont(FONT_NUMBERS);
				}
			}
			setBorder(blackline);
			setVisible(true);
		}
	}
	
	   
	private class InputListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			//System.out.println(command);
			if(command.equals("Show Answer")) {
				System.out.println("Show Answer");
				controller.solveSudoku();
				reset();
				return;
			}
			else if(command.equals("Reset")) {
				controller.resetBoard();
				//System.out.println("Reset");
				reset();
				return;
			}
			else if(command.equals("Easy")) {
				controller.loadNewBoard(0);
				//System.out.println("Easy");
				reset();
				return;
			}
			else if(command.equals("Medium")) {
				controller.loadNewBoard(1);
				//System.out.println("Medium");
				reset();
				return;
			}
			else if(command.equals("Hard")) {
				controller.loadNewBoard(2);
				//System.out.println("Hard");
				reset();
				return;
			}
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
	        
	        
	        writeDigit(gridSelected, rowSelected, colSelected, -1);
	        if(controller.board.isSudokuSolved()) {
	        	JOptionPane.showMessageDialog(null, "Congratulation!");
	        	System.out.println(command);
	        }
	        
		}
		
	}
	
	private void setMenuBar() {
		mb = new JMenuBar();
		mainMenu = new JMenu("Menu");
		startNewGame = new JMenu("Start New Game");
		showAnswer = new JMenuItem("Show Answer");
		easy = new JMenuItem("Easy");
		medium = new JMenuItem("Medium");
		hard = new JMenuItem("Hard");
		reset = new JMenuItem("Reset");
		
		startNewGame.add(easy);
		startNewGame.add(medium);
		startNewGame.add(hard);
		
		
		mainMenu.add(startNewGame);
		mainMenu.add(reset);
		mainMenu.add(showAnswer);
		
		showAnswer.addActionListener(listener);
		reset.addActionListener(listener);
		easy.addActionListener(listener); 
		medium.addActionListener(listener); 
		hard.addActionListener(listener); 
        
		mb.add(mainMenu);
        setJMenuBar(mb);
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
        
    	controller.board.deleteDigit(row, col);
        
    	if(num == 0) {
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_BGCOLOR);
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setText("");
    	}
    	else if(controller.board.canPutNumber(row, col, num)) {
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setBackground(Color.GREEN);
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setText(num + "");
    	}
    	else {
    		subGrids[gridSelected].tfCells[rowSelected][colSelected].setBackground(Color.RED);
    	}
    	controller.board.writeDigit(row, col, num);
        
	}
	
	
	private void reset() {
		for(int gridSelected = 0; gridSelected < subGrids.length; ++gridSelected) {
			for(int rowSelected = 0; rowSelected < SUBGRID_SIZE; ++rowSelected) {
				for(int colSelected = 0; colSelected < SUBGRID_SIZE; ++colSelected) {
					int row = getRow(gridSelected, rowSelected, colSelected);
					int col = getCol(gridSelected, rowSelected, colSelected);
					int num = controller.board.getNum(row, col);
					
					if(controller.board.isEmptyDigit(row, col)) {
						subGrids[gridSelected].tfCells[rowSelected][colSelected].setText("");
						subGrids[gridSelected].tfCells[rowSelected][colSelected].setEditable(true);
						subGrids[gridSelected].tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_BGCOLOR);
						subGrids[gridSelected].tfCells[rowSelected][colSelected].addActionListener(listener);
					}
					else {
						subGrids[gridSelected].tfCells[rowSelected][colSelected].setText(Integer.toString(num));
						subGrids[gridSelected].tfCells[rowSelected][colSelected].setEditable(false);
						subGrids[gridSelected].tfCells[rowSelected][colSelected].setBackground(CLOSED_CELL_BGCOLOR);
						subGrids[gridSelected].tfCells[rowSelected][colSelected].setForeground(CLOSED_CELL_TEXT);
					}
				}
			}
		}
	}
}
