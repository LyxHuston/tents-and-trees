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
    /** square dimension of field */
    private final int DIM;
    /** character representation of board */
    private final char[][] board;
    /** number of tents per row */
    private final int[] tentsPerRow;
    /** number of tents per column */
    private final int[] tentsPerColumn;
    /** where looking at column */
    private int cursorCol;
    /** where looking at row */
    private int cursorRow;

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
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // get the field dimension
            this.DIM = Integer.parseInt(in.readLine());

            String[] rowStore = in.readLine().split("\\s+");
            this.tentsPerRow = new int[this.DIM];
            for (int i = 0; i < this.DIM; i++) {
                this.tentsPerRow[i] = Integer.parseInt(rowStore[i]);
            }
            rowStore = in.readLine().split("\\s+");
            this.tentsPerColumn = new int[this.DIM];
            for (int i = 0; i < this.DIM; i++) {
                this.tentsPerColumn[i] = Integer.parseInt(rowStore[i]);
            }
            String[] stringBoard;
            this.board = new char[this.DIM][this.DIM];
            for (int row = 0; row < this.DIM; row++) {
                stringBoard = in.readLine().split("\\s+");
                for (int col = 0; col < this.DIM; col++) {
                    this.board[row][col] = stringBoard[col].charAt(0);
                }
            }
        this.cursorCol = -1;
        this.cursorRow = 0;
        } // <3 Jim
    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     * @param other the config to copy
     */
    private TentConfig(TentConfig other) {
        this.DIM = other.DIM;
        this.tentsPerColumn = other.tentsPerColumn;
        this.tentsPerRow = other.tentsPerRow;
        this.board = other.board.clone();
        for (int i = 0; i < this.DIM; ++i) {
            this.board[i] = this.board[i].clone();
        }
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        int col = this.cursorCol + 1;
        int row = this.cursorRow;
        if (col == this.DIM) {
            row++;
            col = 0;
        }
        if (this.board[row][col] == '%') {
            TentConfig clone = new TentConfig(this);
            clone.cursorCol = col;
            clone.cursorRow = row;
            return new ArrayList<>() {{add(clone);}};
        }
        ArrayList<Configuration> successors = new ArrayList<>();
        TentConfig grass = new TentConfig(this);
        grass.cursorCol = col;
        grass.cursorRow = row;
        grass.board[row][col] = GRASS;
        TentConfig tent = new TentConfig(this);
        tent.cursorCol = col;
        tent.cursorRow = row;
        tent.board[row][col] = TENT;
        successors.add(grass);
        successors.add(tent);
        return successors;
    }

    @Override
    public boolean isValid() {
        // TODO
        return true;
    }

    @Override
    public boolean isGoal() {
        // TODO
        return false;
    }

    @Override
    public String toString() {
        return getDisplay();
    }


    @Override
    public int getDIM() {
        return this.DIM;
    }

    @Override
    public int getTentsRow(int row) {
        return this.tentsPerRow[row];
    }

    @Override
    public int getTentsCol(int col) {
        return this.tentsPerColumn[col];
    }

    @Override
    public char getCell(int row, int col) {
        return this.board[row][col];
    }

    @Override
    public int getCursorRow() {
        return this.cursorRow;
    }

    @Override
    public int getCursorCol() {
        return this.cursorCol;
    }
}
