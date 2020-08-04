# Java Sudoku Game

### Sudoku Game Introduction

Sudoku is a logic-based number puzzle. The target is to fill a 9 * 9 board with numbers from 1 ~ 9, where each row, column and box has only no duplicated number.

### About the project

This project implements sudoku game using Java. The project start with a random sudoku puzzle, which has around 40 ~ 50 enpty digits. When a user places a valid digit (Not violating the sudoku rules), the text panel turns green, but that doesn't mean the input digit is the final answer. When a user places an invalid digit, the text panel turns red.

There's a Menu button on the menu bar, which has the following options:

**1. Start new game  (Easy, Medium, Hard)**: Board Generator generate a new board based on the demanded difficulty.

**2. Reset:** Reset the whole board to it's initial state.

**3. Show answer:** Game Solver solve the current board with back-tracking algorithm, then output the solved board to the user.

### Game Components

#### 1. SudokuBoard: 

Managing the content of a sudoku puzzle. It records the number of digits in each row, column and box. It provides digit manipulation functions such as writeDigit, deleteDigit and setupBoard, it also provides status checking functions such as isEmptyDigit, canPlaceDigit, isSudokuSolved.

#### 2. GameSolver:
Solving a sudoku puzzle using backtracking algorithm. Start from (0, 0), it tries to place a digit from 1 ~ 9 to an empty slot. Whenever the solver encounters a dead end, it delete the current digit and try another solution from the previous step. Note that in this project, Game Solver doesn't fill in digit from 1 to 9 in order. In each placing step, Game solver generate a random order of number from 1 ~ 9. This change is used for generating new sudoku puzzle, which will be introduced more detailed in Board Generator section. When Game Solver reach the last spot of the puzzle (8, 8), it means the puzzle has already been solved.


#### 3. BoardGenerator:
BoardGenerator generates new sudoku puzzles using GameSolver. Starting with an empty puzzle, BoardGenerator calls GameSolver to fill in digits (solving an empty puzzle). After filling in all the digits, it gets an completed sudoku puzzle. Since the digit-placing order is random, it can get different completed puzzle every time. After getting a solved puzzle, BoardGenerator randomly choose digits in the puzzle and delete them. Base on the choosen difficulty, BoardGenerator deletes different number of digits.

#### 4. BoardLayout:
BoardLayout is the user interface of this application, it takes in the input command, such as writing a digit, deleting a digit, starting a new game from users and deliver it back to BoardController to respond with corresponding action.

#### 5. BoardController:
BoardController is the coordinator of the application, it takes user commands from BoardLayout, and deliver the commands to other components.
For example, the following is the work flow when a user place a digit on the puzzle:

BoardLayout -> BoardController -> SudokuBoard.writeDigit(row, col, num)

### Reference

1. UCLA EE379K - Spring2014 Java Swing Tutorial: http://web.cs.ucla.edu/~miryung/teaching/EE379K-Spring2014/SwingTutorial.html
2. tutorialsPoint - Swing Examples - Add Border to JPanel: https://www.tutorialspoint.com/swingexamples/add_border_to_panel.htm
3. NTU - Java Graphics Programming Assignment - Sudoku: https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaGame_Sudoku.html
4. GeeksforGeeks - Java Swing | JMenuBar : https://www.geeksforgeeks.org/java-swing-jmenubar/
5. Java Naming Convensions: https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html
6. Sudoku Wikipedia: https://en.wikipedia.org/wiki/Sudoku
