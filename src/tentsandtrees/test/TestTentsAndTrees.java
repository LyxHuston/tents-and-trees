package tentsandtrees.test;

import org.junit.jupiter.api.*;
import tentsandtrees.backtracker.Configuration;
import tentsandtrees.backtracker.TentConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A Junit 5 unit test for the In-Lab activity.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTentsAndTrees {
    /** Test loading first puzzle file and fully storing all the data. */
    @Test
    @Order(1)
    public void testLoad1() {
        try {
            ITentsAndTreesTest config = new TentConfig("data/tents1.txt");

            // check square dimension
            assertEquals(2, config.getDIM());

            // check cursor - initially at (0, -1)
            assertEquals(0, config.getCursorRow());
            assertEquals(-1, config.getCursorCol());

            // check tents row counts
            final int[] ROW_COUNTS = {1, 0};
            for (int row = 0; row < config.getDIM(); ++row) {
                assertEquals(ROW_COUNTS[row], config.getTentsRow(row));
            }

            // check tents col counts
            final int[] COL_COUNTS = {1, 0};
            for (int col = 0; col < config.getDIM(); ++col) {
                assertEquals(COL_COUNTS[col], config.getTentsCol(col));
            }

            // check all cells
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, config.getCell(0,1));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(1,1));

            // check toString
            final String expected =
                    " ---" + System.lineSeparator() +
                    "|. %|1" + System.lineSeparator() +
                    "|. .|0" + System.lineSeparator() +
                    " ---" + System.lineSeparator() +
                    " 1 0 " + System.lineSeparator();
            assertEquals(expected, config.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** Test loading third puzzle file and fully storing all the data. */
    @Test
    @Order(2)
    public void testLoad3() {
        try {
            ITentsAndTreesTest config = new TentConfig("data/tents3.txt");

            // check square dimension
            assertEquals(4, config.getDIM());

            // check cursor - initially at (0, -1)
            assertEquals(0, config.getCursorRow());
            assertEquals(-1, config.getCursorCol());

            // check tents row counts
            final int[] ROW_COUNTS = {1, 1, 1, 0};
            for (int row = 0; row < config.getDIM(); ++row) {
                assertEquals(ROW_COUNTS[row], config.getTentsRow(row));
            }

            // check tents col counts
            final int[] COL_COUNTS = {0, 2, 0, 1};
            for (int col = 0; col < config.getDIM(); ++col) {
                assertEquals(COL_COUNTS[col], config.getTentsCol(col));
            }

            // check all cells
            assertEquals(ITentsAndTreesTest.TREE, config.getCell(0,0));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(0,1));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(0,2));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(0,3));

            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(1,1));
            assertEquals(ITentsAndTreesTest.TREE, config.getCell(1,2));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(1,3));

            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(2,0));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(2,1));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(2,2));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(2,3));

            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(3,0));
            assertEquals(ITentsAndTreesTest.TREE, config.getCell(3,1));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(3,2));
            assertEquals(ITentsAndTreesTest.EMPTY, config.getCell(3,3));

            // check toString
            final String expected =
                    " -------" + System.lineSeparator() +
                    "|% . . .|1" + System.lineSeparator() +
                    "|. . % .|1" + System.lineSeparator() +
                    "|. . . .|1" + System.lineSeparator() +
                    "|. % . .|0" + System.lineSeparator() +
                    " -------" + System.lineSeparator() +
                    " 0 2 0 1 " + System.lineSeparator();
            assertEquals(expected, config.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** Test successors for tents-1.txt for first 3 cells */
    @Test
    @Order(3)
    public void testSuccessors1() {
        try {
            TentConfig config = new TentConfig("data/tents1.txt");

            // check square dimension
            assertEquals(2, config.getDIM());

            // GENERATE ALL SUCCESSORS
            List<Configuration> successors00 = new ArrayList<>(config.getSuccessors());
            List<Configuration> grassSuccessors01 = new ArrayList<>(successors00.get(0).getSuccessors());
            List<Configuration> tentSuccessors01 = new ArrayList<>(successors00.get(1).getSuccessors());
            List<Configuration> grassGrassSucessors10 = new ArrayList<>(grassSuccessors01.get(0).getSuccessors());
            List<Configuration> grassTentSucessors10 = new ArrayList<>(tentSuccessors01.get(0).getSuccessors());

            // VERIFY successors in (0,0)
            assertEquals(2, successors00.size());

            // VERIFY grass at (0,0)
            TentConfig tc = (TentConfig) successors00.get(0);
            assertEquals(0, tc.getCursorRow());
            assertEquals(0, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.GRASS, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));

            // VERIFY tent at (0,0)
            tc = (TentConfig) successors00.get(1);
            assertEquals(0, tc.getCursorRow());
            assertEquals(0, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.TENT, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));

            // VERIFY successors in (0,1) - grass
            assertEquals(1, grassSuccessors01.size());

            // VERIFY tree at (0,1)
            tc = (TentConfig) grassSuccessors01.get(0);
            assertEquals(0, tc.getCursorRow());
            assertEquals(1, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.GRASS, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));

            // VERIFY successors in (0,1) - tent
            assertEquals(1, tentSuccessors01.size());

            // VERIFY tree at (0,1)
            tc = (TentConfig) tentSuccessors01.get(0);
            assertEquals(0, tc.getCursorRow());
            assertEquals(1, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.TENT, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));

            // VERIFY successors in (1, 0) - grass
            assertEquals(2, grassGrassSucessors10.size());

            // VERIFY grass at (1,0)
            tc = (TentConfig) grassGrassSucessors10.get(0);
            assertEquals(1, tc.getCursorRow());
            assertEquals(0, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.GRASS, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.GRASS, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));

            // VERIFY tent at (1,0)
            tc = (TentConfig) grassGrassSucessors10.get(1);
            assertEquals(1, tc.getCursorRow());
            assertEquals(0, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.GRASS, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.TENT, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));

            // VERIFY successors in (1, 0) - tent
            assertEquals(2, grassTentSucessors10.size());

            // VERIFY grass at (1,0)
            tc = (TentConfig) grassTentSucessors10.get(0);
            assertEquals(1, tc.getCursorRow());
            assertEquals(0, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.TENT, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.GRASS, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));

            // VERIFY tent at (1,0)
            tc = (TentConfig) grassTentSucessors10.get(1);
            assertEquals(1, tc.getCursorRow());
            assertEquals(0, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.TENT, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.TENT, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** Test successors for tents-2.txt for first cell */
    @Test
    @Order(4)
    public void testSuccessors2() {
        try {
            TentConfig config = new TentConfig("data/tents2.txt");

            // check square dimension
            assertEquals(3, config.getDIM());

            // GENERATE ALL SUCCESSORS
            List<Configuration> successors00 = new ArrayList<>(config.getSuccessors());

            // VERIFY successors in (0,0)
            assertEquals(2, successors00.size());

            // VERIFY grass at (0,0)
            TentConfig tc = (TentConfig) successors00.get(0);
            assertEquals(0, tc.getCursorRow());
            assertEquals(0, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.GRASS, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(0,2));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,2));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(2,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(2,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(2,2));

            // VERIFY tent at (0,0)
            tc = (TentConfig) successors00.get(1);
            assertEquals(0, tc.getCursorRow());
            assertEquals(0, tc.getCursorCol());
            assertEquals(ITentsAndTreesTest.TENT, tc.getCell(0,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(0,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(0,2));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(1,0));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(1,2));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(2,0));
            assertEquals(ITentsAndTreesTest.TREE, tc.getCell(2,1));
            assertEquals(ITentsAndTreesTest.EMPTY, tc.getCell(2,2));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
