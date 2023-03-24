package tentsandtrees.test;

/**
 * An interface use by the JUnit tester, TestTentsAndTrees, to verify a puzzle is
 * being read in correctly and the successors are generated correctly.
 *
 * @author RIT CS
 */
public interface ITentsAndTreesTest {
    // INPUT CONSTANTS
    /** An empty cell */
    char EMPTY = '.';
    /** A cell occupied with grass */
    char GRASS = '-';
    /** A cell occupied with a tent */
    char TENT = '^';
    /** A cell occupied with a tree */
    char TREE = '%';

    // OUTPUT CONSTANTS
    /** A horizontal divider */
    char HORI_DIVIDE = '-';
    /** A vertical divider */
    char VERT_DIVIDE = '|';

    /**
     * Get the square dimension of the puzzle
     * @return square dimension
     */
    int getDIM();

    /**
     * Get the number of tents for a row.
     * @param row the row
     * @return number of tents
     */
    int getTentsRow(int row);

    /**
     * Get the number of tents for a column.
     * @param col the column
     * @return number of tents
     */
    int getTentsCol(int col);

    /**
     * Get the contents at a cell.
     * @param row the row
     * @param col the column
     * @return the contents
     */
    char getCell(int row, int col);

    /**
     * Get the cursor row location.
     * @return cursor row
     */
    int getCursorRow();

    /**
     * Get the cursor column location.
     * @return cursor column
     */
    int getCursorCol();


    /**
     * Get the string representation of the configuration.
     * @return the complete string
     */
    default String getDisplay() {
        StringBuilder result = new StringBuilder(" ");

        // top row, horizontal divider
        result.append(String.valueOf(HORI_DIVIDE).repeat(Math.max(0, getDIM() * 2 - 1)));
        result.append(System.lineSeparator());

        // field rows
        for (int row=0; row<getDIM() ; ++row) {
            result.append(VERT_DIVIDE);
            for (int col = 0; col<getDIM() ; ++col) {
                if (col != getDIM() -1) {
                    result.append(getCell(row, col)).append(" ");
                } else {
                    result.append(getCell(row, col)).append(VERT_DIVIDE).append(getTentsRow(row)).append(System.lineSeparator());
                }
            }
        }

        // bottom horizontal divider
        result.append(" ");
        result.append(String.valueOf(HORI_DIVIDE).repeat(Math.max(0, getDIM()  * 2 - 1)));

        // bottom row w/ look values for columns
        result.append(System.lineSeparator()).append(" ");

        for (int col=0; col<getDIM(); ++col) {
            result.append(getTentsCol(col)).append(" ");
        }
        result.append(System.lineSeparator());

        return result.toString();
    }
}
