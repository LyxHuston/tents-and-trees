package tentsandtrees.backtracker;

import tentsandtrees.test.ITentsAndTreesTest;

import java.io.*;
import java.util.Collection;
import java.util.ArrayList;

/**
 *  The full representation of a configuration in the TentsAndTrees puzzle.
 *  It can read an initial configuration from a file, and supports the
 *  Configuration methods necessary for the Backtracker solver.
 *
 *  @author RIT CS
 *  @author Lyx Huston
 */
public class TentConfig implements Configuration, ITentsAndTreesTest {

    /**
     * @param next tree that comes after this one in the search
     * @param col  x (column) value of tree
     * @param row  y (row) value of tree
     */
    private record TreeNode(int col, int row, TentConfig.TreeNode next) {
    }
    /** square dimension of field */
    private static int DIM;
    /** character representation of board */
    private final char[][] board;
    /** number of tents per row */
    private static int[] tentsPerRow;
    /** number of tents per column */
    private static int[] tentsPerColumn;
    /** check against number of tents per row */
    private final int[] checkTentsPerRow;
    /** check against number of tents per column */
    private final int[] checkTentsPerColumn;
    /** tree placing around */
    private TreeNode treeOn;
    /** direction to column change look array */
    private static final int[] dirToCol = {0, 1, 1, 1, 0, -1, -1, -1};
    /** direction to row change look array */
    private static final int[] dirToRow = {1, 1, 0, -1, -1, -1, 0, 1};

    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:
     * <pre>
     * 3        # square dimension of field
     * 2 0 1    # row looking values, top to bottom
     * 2 0 1    # column looking values, left to right
     * . % .    # row 1, .=empty, %=tree
     * % . .    # row 2
     * . % .    # row 3
     * </pre>
     * @param filename the name of the file to read from
     * @throws IOException if the file is not found or there are errors reading
     */
    public TentConfig(String filename) throws IOException {
        this.treeOn = null;
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // get the field dimension
            DIM = Integer.parseInt(in.readLine());

            String[] rowStore = in.readLine().split("\\s+");
            tentsPerRow = new int[DIM];
            for (int i = 0; i < DIM; i++) {
                tentsPerRow[i] = Integer.parseInt(rowStore[i]);
            }
            rowStore = in.readLine().split("\\s+");
            tentsPerColumn = new int[DIM];
            for (int i = 0; i < DIM; i++) {
                tentsPerColumn[i] = Integer.parseInt(rowStore[i]);
            }
            this.checkTentsPerColumn = tentsPerColumn.clone();
            this.checkTentsPerRow = tentsPerRow.clone();
            String[] stringBoard;
            this.board = new char[DIM][DIM];
            for (int row = 0; row < DIM; row++) {
                stringBoard = in.readLine().split("\\s+");
                for (int col = 0; col < DIM; col++) {
                    this.board[row][col] = stringBoard[col].charAt(0);
                    if (stringBoard[col].charAt(0) == TREE) {
                        this.treeOn = new TreeNode(col, row, this.treeOn);
                    }
                }
            }
        } // <3 Jim
    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     * @param other the config to copy
     */
    private TentConfig(TentConfig other) {
        this.checkTentsPerColumn = new int[DIM];
        this.checkTentsPerRow = new int[DIM];
        System.arraycopy(other.checkTentsPerColumn, 0, this.checkTentsPerColumn, 0, DIM);
        System.arraycopy(other.checkTentsPerRow, 0, this.checkTentsPerRow, 0, DIM);
        this.board = new char[DIM][DIM];
        //System.arraycopy(other.board, 0, this.board, 0, DIM);
        for (int i = 0; i < DIM; ++i) {
            System.arraycopy(other.board[i], 0, this.board[i], 0, DIM);
        }
        this.treeOn = other.treeOn.next;
    }

    /**
     * gets successors looking in all possible directions
     * @return arraylist of successors in all possible directions
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        return this.getSuccessors(0, 4);
    }

    /**
     * gets successors looking in a range of directions.
     * if it's the last tree, it returns itself
     * 0 is up, 3 if left
     * @param start start looking direction
     * @param finish finish looking direction
     * @return an arraylist with the successors
     */
    public Collection<Configuration> getSuccessors(int start, int finish) {
        ArrayList<Configuration> results = new ArrayList<>();
        if (this.treeOn == null) {
            results.add(this);
            return results;
        }
        for (int direction = start; direction < finish; direction++) {
            TentConfig successor = this.getSuccessor(direction);
            if (successor != null){
                results.add(successor);
            }
        }
        return results;
    }

    /**
     * gets a successor looking in a singular direction
     * only works with 0 to 3
     * @param direction direction looking in
     * @return new tentconfig
     */
    public TentConfig getSuccessor(int direction) {
        int lookRow = this.treeOn.row + dirToRow[direction * 2];
        int lookCol = this.treeOn.col + dirToCol[direction * 2];
        /* check to make sure it's ok to place there */
        if (!this.validPlace(lookRow, lookCol)) {
            return null;
        }
        TentConfig result = new TentConfig(this);
        result.board[lookRow][lookCol] = TENT;
        result.checkTentsPerRow[lookRow] -= 1;
        result.checkTentsPerColumn[lookCol] -= 1;
        return result;
    }

    /**
     * all checking offloaded on validPlace (because I decided why generate it
     * if I can check if it will be valid anyways)
     * @return always true (if the successor was made it will be valid)
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * handles most of the validity checking, makes sure it's ok to place a
     * tent in a certain row and column
     * @param row row to place in
     * @param col column to place in
     * @return checks if it's ok to place a tent there
     */
    public boolean validPlace(int row, int col) {
        /* check if inside the board */
        if (0 > row || DIM <= row) {
            return false;
        }
        if (0 > col || DIM <= col) {
            return false;
        }
        /* check if place is empty */
        if (this.board[row][col] != EMPTY) {
            return false;
        }
        /* checks against column and row requirements*/
        if (this.checkTentsPerRow[row] <= 0 || this.checkTentsPerColumn[col] <= 0) {
            return false;
        }
        /* check to make sure it's not next to tents */
        for (int direction = 0; direction < 8; direction++) {
            if (isTent(
                    row + dirToRow[direction],
                    col + dirToCol[direction])
            ) {
                return false;
            }
        }
        /* if passes all checks, returns true */
        return true;
    }

    /**
     * checks if a certain place is a tent, returns false if out of range
     * (because not tent)
     * @param row row looking at
     * @param col column looking at
     * @return if the cell is a tent
     */
    public boolean isTent(int row, int col) {
        /* check if inside the board */
        if (0 > row || DIM <= row) {
            return false;
        }
        if (0 > col || DIM <= col) {
            return false;
        }
        /* then returns if character is tent */
        return this.board[row][col] == '^';
    }

    @Override
    public boolean isGoal() {
        /* check that last tree has received a tent */
        if (this.treeOn != null) {
            return false;
        }
        /* check that all rows and columns have received correct amount
        *
        * during placing, it ticks them down.  If each place of the check arrays
        * have 0, then it placed the correct amount in each row and column
        * */
        for (int i = 0; i < DIM; i++) {
            if (this.checkTentsPerRow[i] != 0 ||
                    this.checkTentsPerColumn[i] != 0) {
                return false;
            }
        }
        /* changes empty to grass */
        for (int row = 0; row < DIM; row++) {
            for (int col = 0; col < DIM; col++) {
                if (this.board[row][col] == EMPTY) {
                    this.board[row][col] = GRASS;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return getDisplay();
    }


    @Override
    public int getDIM() {
        return DIM;
    }

    @Override
    public int getTentsRow(int row) {
        return tentsPerRow[row];
    }

    @Override
    public int getTentsCol(int col) {
        return tentsPerColumn[col];
    }

    @Override
    public char getCell(int row, int col) {
        return this.board[row][col];
    }

    @Override
    public int getCursorRow() {
        return this.treeOn.row;
    }

    @Override
    public int getCursorCol() {
        return this.treeOn.col;
    }
}
