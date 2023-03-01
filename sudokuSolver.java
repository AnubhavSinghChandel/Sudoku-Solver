import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 *=====================
 *   Sudoku Solver
 *=====================
 *
 * Started On - February, 2023
 * Objective - Takes in Sudoku puzzles and outputs the solution.
*/

class SudokuFrame {
    int[][] sudokuFrame = new int[9][9]; // This holds all the values in the matrix.
    boolean[][] editableFrame = new boolean[9][9]; // This tells us the values which are editable.
    Scanner sc = new Scanner(System.in); // Global scanner object for input.

    /**
     * @desc This constructor calls the menu() func to provide the menu.
     * @param none
     * @throws IOException
     */

    public SudokuFrame() throws IOException {
        menu();
    }

    /**
     * @desc Displays a menu to the user when the SudokuFrame objects in
     *       instantiated
     *       (Displayed at start of the program) to show all possible options
     *       available to the user.
     * @param none
     * @throws IOException
     */

    void menu() throws IOException {
        System.out.println("\n======================\n");
        System.out.println("    Sudoku Solver\n");
        System.out.println("======================\n\n");

        System.out.println("Welcome to Sudoku Solver!\n");
        System.out.println("Before we start, you will have to input the puzzle into this program.\n");
        System.out.println("Currently you can:-\n");
        System.out.println("\t1. Input the puzzle by entering the values manually. (Enter 1)\n");
        System.out.println("\t2. Input the puzzle by reading a file with values in it. (Enter 2)\n");
        System.out.println("Before selecting option 2 please note: \n");
        System.out.println("\t The file must not have a name greater than 20 characters.\n");
        System.out.println("\t The file must be in the same directory as this java file.\n");
        System.out.println("\t The file must have all 81 values seperated with spaces.\n");
        System.out.println("\t Blank cells must be filled in as 0 (eg; 1 0 0 2 0 0 ...).\n");
        System.out.println("\t --> ");

        int option;
        option = sc.nextInt();

        if (option == 1)
            readFrameValues();

        else if (option == 2)
            try {
                readFrameValueFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        else {
            while (true) {
                System.out.println("\nInvalid input for option selection. Try again.\n");
                System.out.println("\t   --> ");
                option = sc.nextInt();

                if (option == 1)
                    readFrameValues();

                else if (option == 2)
                    try {
                        readFrameValueFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                else
                    continue;

                break;
            }
        }
    }

    /**
     * @desc Reads the values for the Sudoku Frame cell-by-cell.
     * @param none
     */
    void readFrameValues() {
        System.out.println("\nEnter the specified value when prompted.\n");
        System.out.println("Enter 0 if cell is empty.\n\n");

        int rowIter, colIter;

        for (rowIter = 0; rowIter < 9; rowIter++) { // Iterating over cells to read vals.
            for (colIter = 0; colIter < 9; colIter++) {
                int enteredValue;

                System.out.println("Enter value for cell[" + (rowIter + 1) + "][" + (colIter + 1) + "] --> ");
                enteredValue = sc.nextInt();

                if (!(enteredValue >= 0 && enteredValue <= 9)) { // Checking for bounds in input.
                    while (true) { // We loop until valid input is read from user.
                        System.out.println("Oops! You seem to have entered a wrong value! Try again.\n");
                        System.out.println(
                                "Enter value for cell [" + (rowIter + 1) + "][" + (colIter + 1) + "] --> ");
                        enteredValue = sc.nextInt();

                        if (enteredValue >= 0 && enteredValue <= 9)
                            break;
                    }
                }

                sudokuFrame[rowIter][colIter] = enteredValue;

                if (enteredValue == 0)
                    editableFrame[rowIter][colIter] = true;
                else
                    editableFrame[rowIter][colIter] = false;
            }
            System.out.println("-------\n"); // Display a break after every row for convenience.
        }
    }

    void readFrameValueFile() throws IOException {
        System.out.println("\nEnter the name of the file (with the extension) --> ");
        String fileName = sc.next();
        try {
            Scanner scanner = new Scanner(new File(fileName));

            for (int i = 0; i < 9; i++) {
                String line = scanner.nextLine();

                for (int j = 0; j < 9; j++) {
                    int value = Integer.parseInt(Character.toString(line.charAt(j)));
                    sudokuFrame[i][j] = value;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    /**
     * @desc Assigns the passed-in number to the specified row and col.
     * @param row (int) row of the specified cell
     * @param col (int) col of the specified cell
     */

    public void setValue(int row, int col, int num) {
        if (editableFrame[row][col])
            sudokuFrame[row][col] = num;
    }

    /**
     * @desc Returns the value of the cell at the specified row and col.
     * @param row (int) row of the specified cell
     * @param col (int) col of the specified cell
     * @return (int) cellValue value at the specified cell
     */

    public int getValue(int row, int col) {
        int cellValue = sudokuFrame[row][col];
        return cellValue;
    }

    /**
     * @desc Returns true/false depending on editableFrame values.
     * @param row (int) row of the required cell
     * @param col (int) col of the required cell
     * @return (boolean) true if editable; false if not
     */

    public boolean isEditable(int row, int col) {
        return editableFrame[row][col];
    }

    /**
     * @desc Clears frame of all values, other than the question values, from
     *       the specified cell to the last cell.
     * @param row (int) row of the specified cell
     * @param col (int) col of the specified cell
     */
    public void clearFrameFrom(int row, int col) {
        int jcount = 0;
        int rowIter, colIter;

        for (rowIter = row; rowIter < 9; rowIter++) {

            if (jcount == 0)
                colIter = col;
            else
                colIter = 0;

            for (; colIter < 9; colIter++) {
                if (editableFrame[rowIter][colIter])
                    sudokuFrame[rowIter][colIter] = 0;
            }

            jcount++;

        }
    }

    /**
     * @desc Displays the values stored in the frame with designs. We also use
     *       ANSI colors, using escape sequences, to display the frame.
     * @param none
     */

    public void displayFrame() {

        System.out.println("\033[0;36m++=====================================++");
        int rowIter, colIter;

        for (rowIter = 0; rowIter < 9; rowIter++) {
            int cellIter = 1;

            System.out.println("\n\033[0;36m||");
            for (colIter = 0; colIter < 9; colIter++) {
                if (cellIter == 3) {
                    System.out.println("\033[0m " + sudokuFrame[rowIter][colIter] + " ");
                    System.out.println("\033[0;36m||");
                    cellIter = 1;
                } else {
                    System.out.println("\033[0m " + sudokuFrame[rowIter][colIter] + "  ");
                    cellIter++;
                }
            }

            if (rowIter % 3 != 2)
                System.out.println("\n\033[0;36m++-----------++-----------++-----------++");
            else
                System.out.println("\n\033[0;36m++=====================================++");
        }

    }

}

/**
 * This class provides the user a very simple way to iterate through
 * the possibilities of a specified cell. This object utilises linked lists.
 */

// class Possbilities {
// class node {
// int value;
// node next;
// }

// node head;
// node pos;

// /**
// * @desc This constructor initialises the head (or sentinel) node.
// * @param none
// */
// public Possbilities() {
// head = new node();
// head.next = null;
// }
// }

class SudokuSolver {

}

public class sudokuSolver {
    public static void main(String[] args) throws IOException {
        SudokuFrame sudokuFrame = new SudokuFrame();
    }
}