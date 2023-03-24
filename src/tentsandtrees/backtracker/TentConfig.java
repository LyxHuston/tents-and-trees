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
 *  @author YOUR NAME HERE
 */
public class TentConfig implements Configuration, ITentsAndTreesTest {
    /** square dimension of field */
    private static int DIM;

    // TODO

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
            DIM = Integer.parseInt(in.readLine());

            // TODO

        } // <3 Jim
    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     * @param other the config to copy
     */
    private TentConfig(TentConfig other) {
        // TODO
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        // TODO
        return new ArrayList<>();
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
        // TODO
        return 0;
    }

    @Override
    public int getTentsRow(int row) {
        // TODO
        return 0;
    }

    @Override
    public int getTentsCol(int col) {
        // TODO
        return 0;
    }

    @Override
    public char getCell(int row, int col) {
        // TODO
        return 0;
    }

    @Override
    public int getCursorRow() {
        // TODO
        return 0;
    }

    @Override
    public int getCursorCol() {
        // TODO
        return 0;
    }
}
