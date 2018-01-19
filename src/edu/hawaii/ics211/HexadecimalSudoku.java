package edu.hawaii.ics211;

import java.util.ArrayList;

/**
 * Class for recursively finding a solution to a Hexadecimal Sudoku problem.
 * 
 * @author Biagioni, Edoardo, Cam Moore
 * @date August 5, 2016
 * @missing solveSudoku, to be implemented by the students in ICS 211
 */
public class HexadecimalSudoku {

	/**
	 * Find an assignment of values to sudoku cells that makes the sudoku valid.
	 * 
	 * @param the
	 *            sudoku to be solved
	 * @return whether a solution was found if a solution was found, the sudoku
	 *         is filled in with the solution if no solution was found, restores
	 *         the sudoku to its original value
	 */
	public static boolean solveSudoku(int[][] sudoku) {
		// TODO: Implement this method recursively. You may use a recursive
		// helper method.
		//Iterate through the rows
		for (int i = 0; i < sudoku.length; i++) {
			//Iterate through the columns
			for(int j = 0; j < sudoku.length; j++) {
				//if the spot is empty
				if(sudoku[i][j] == -1) {
					//solve the sudoku puzzle recursively
					return solveSudoku(sudoku, i, j);
				}
			}
		}
		//and then return true to indicate that there is a solution for the puzzle
		return true;
	}

	/**
	 * Recursive Helper Method for Solving the sudoku
	 * 
	 * @param sudoku
	 *            - the sudoku to be solved
	 * @param row
	 *            - the row to try and solve
	 * @param col
	 *            - the column to try and solve
	 * 
	 * @return true if the sudoku has a solution
	 * @return false if the sudoku doesn't have a solution
	 * 
	 */

	private static boolean solveSudoku(int[][] sudoku, int row, int col) {
		//Create an arraylist of valid ints
		ArrayList<Integer> validInts = legalValues(sudoku, row, col);
		//Iterate through those ints
		for (Integer i: validInts) {
			//Try put an int in the cell
			sudoku[row][col] = i;
			//if it was the correct int (if solveSudoku would return true)
			if (solveSudoku(sudoku)) {
				//check the Sudoku Rules
				return checkSudoku(sudoku, false); 
			} else {
				//Otherwise, revert it back to its original state
				sudoku[row][col] = -1;
			}
		}
		//Otherwise return false indicating that there is no solution
		return false;
	}

	/**
	 * Find the legal values for the given sudoku and cell.
	 * 
	 * @param sudoku,
	 *            the sudoku being solved.
	 * @param row
	 *            the row of the cell to get values for.
	 * @param col
	 *            the column of the cell.
	 * @return an ArrayList of the valid values.
	 * 
	 * 
	 * Code based off of John 
	 * 
	 * 
	 */

	private static ArrayList<Integer> legalValues(int[][] sudoku, int row, int col) {
		//Create an ArrayList of legal values for each cell
		ArrayList<Integer> legalValues = new ArrayList<Integer>();
		//for all the comparison values
		for (int i = 0; i < 16; i++) {
			//check the row & check the column & check the square (4x4) for what legal values could be
			if (checkRow(sudoku, row, i) && checkColumn(sudoku, col, i) && checkSquare(sudoku, row, col, i)) {
				//Add each legal value to the ArrayList
				legalValues.add(i);
			}
		}
		//Return the legal values
		return legalValues;
	}
	
	/** helper method to check a row
	 * 
	 * @param sudoku - the sudoku example
	 * @param row  - the value of the row to check
	 * @param comp - the comparison number to check
	 * @return true if the value is not in the col
	 * @return false if the value is in the column
	 */

	private static boolean checkRow(int[][] sudoku, int row, int comp) {
		//Iterate through each row
		for (int i = 0; i < 16; i++) {
			//Check to see if the value in the row matches the value in the column
			if (sudoku[row][i] == comp) {
				//if so, return false
				return false;
			}
		}
		//If not, return true
		return true;
	}
	
	/** helper method to check the column
	 * 
	 * @param sudoku - the sudoku example
	 * @param col - the column to compare to
	 * @param comp - the comparison value
	 * @return true if the value isn't in the comparator row
	 * @return false if the value is in the row
	 */

	private static boolean checkColumn(int[][] sudoku, int col, int comp) {
		//Iterate through the columns
		for (int i = 0; i < 16; i++) {
			//If the value in the row comparator is the same as the value of the column
			if (sudoku[i][col] == comp) {
				//return false
				return false;
			}
		}
		//If not, return true
		return true;
	}
	
	
	/** helper method to check the 4x4 square
	 * 
	 * @param sudoku - the sudoku example
	 * @param row - the row in the square
	 * @param col - the column in the square
	 * @param comp - the comparison value
	 * @return true - if the single cell value does not match the comparison value
	 * @return false - if the single cell valeu does match tehc comp value
	 */

	private static boolean checkSquare(int[][] sudoku, int row, int col, int comp) {
		//Need to make a variable to hold the rows in the 4x4
		//Need to /4 because 16 rows total, only want 4
		//Need to *4 because there are 4 possible 4x4 squares and we need to check all of them
		int sqRow = (row / 4) * 4;
		//Need to make a variable to hold the columns in the 4x4
		//Need to /4 because 16 columns total, only want 4
		//Need to *4 because there are 4 possible 4x4 squares and we need to check all of them
		int sqCol = (col / 4) * 4;
		//iterate through the rows of each 4x4 square
		for(int i = sqRow; i < sqRow + 4; i++) {
			//iterate through the columns of the 4x4 square
			for (int j = sqCol; j < sqCol + 4; j++) {
				//if a value matches the comparison value
				if(sudoku[i][j] == comp) {
					//then return false
					return false;
				}
			}
		}
		//If not, return true
		return true;
	}

	/**
	 * checks that the sudoku rules hold in this sudoku puzzle. cells that
	 * contain 0 are not checked.
	 * 
	 * @param the
	 *            sudoku to be checked
	 * @param whether
	 *            to print the error found, if any
	 * @return true if this sudoku obeys all of the sudoku rules, otherwise
	 *         false
	 */
	public static boolean checkSudoku(int[][] sudoku, boolean printErrors) {
		if (sudoku.length != 16) {
			if (printErrors) {
				System.out.println("sudoku has " + sudoku.length + " rows, should have 16");
			}
			return false;
		}
		for (int i = 0; i < sudoku.length; i++) {
			if (sudoku[i].length != 16) {
				if (printErrors) {
					System.out.println("sudoku row " + i + " has " + sudoku[i].length + " cells, should have 16");
				}
				return false;
			}
		}
		/* check each cell for conflicts */
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				int cell = sudoku[i][j];
				if (cell == -1) {
					continue; /* blanks are always OK */
				}
				if ((cell < 0) || (cell > 16)) {
					if (printErrors) {
						System.out.println("sudoku row " + i + " column " + j + " has illegal value "
								+ String.format("%02X", cell));
					}
					return false;
				}
				/* does it match any other value in the same row? */
				for (int m = 0; m < sudoku.length; m++) {
					if ((j != m) && (cell == sudoku[i][m])) {
						if (printErrors) {
							System.out.println("sudoku row " + i + " has " + String.format("%X", cell)
									+ " at both positions " + j + " and " + m);
						}
						return false;
					}
				}
				/* does it match any other value it in the same column? */
				for (int k = 0; k < sudoku.length; k++) {
					if ((i != k) && (cell == sudoku[k][j])) {
						if (printErrors) {
							System.out.println("sudoku column " + j + " has " + String.format("%X", cell)
									+ " at both positions " + i + " and " + k);
						}
						return false;
					}
				}
				/* does it match any other value in the 4x4? */
				for (int k = 0; k < 4; k++) {
					for (int m = 0; m < 4; m++) {
						int testRow = (i / 4 * 4) + k; /* test this row */
						int testCol = (j / 4 * 4) + m; /* test this col */
						if ((i != testRow) && (j != testCol) && (cell == sudoku[testRow][testCol])) {
							if (printErrors) {
								System.out.println(
										"sudoku character " + String.format("%X", cell) + " at row " + i + ", column "
												+ j + " matches character at row " + testRow + ", column " + testCol);
							}
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Converts the sudoku to a printable string
	 * 
	 * @param the
	 *            sudoku to be converted
	 * @param whether
	 *            to check for errors
	 * @return the printable version of the sudoku
	 */
	public static String toString(int[][] sudoku, boolean debug) {
		if ((!debug) || (checkSudoku(sudoku, true))) {
			String result = "";
			for (int i = 0; i < sudoku.length; i++) {
				if (i % 4 == 0) {
					result = result + "+---------+---------+---------+---------+\n";
				}
				for (int j = 0; j < sudoku.length; j++) {
					if (j % 4 == 0) {
						result = result + "| ";
					}
					if (sudoku[i][j] == -1) {
						result = result + "  ";
					} else {
						result = result + String.format("%X", sudoku[i][j]) + " ";
					}
				}
				result = result + "|\n";
			}
			result = result + "+---------+---------+---------+---------+\n";
			return result;
		}
		return "illegal sudoku";
	}
}
