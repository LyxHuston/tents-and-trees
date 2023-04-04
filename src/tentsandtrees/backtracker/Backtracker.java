package tentsandtrees.backtracker;

import java.util.Optional;
import java.util.Stack;

/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 *
 * @author Lyx Husont
 * @author RIT CS
 */
public class Backtracker {
    /** Should debug output be enabled? */
    private final boolean debug;
    /** counts number of configurations generated */
    private int configCount;

    /**
     * Initialize a new backtracker.
     *
     * @param debug Is debugging output enabled?
     */
    public Backtracker(boolean debug) {
        this.debug = debug;
        if (this.debug) {
            System.out.println("Backtracker debugging enabled...");
        }
        this.configCount = 0;
        // changed because the initial config will be counted in solve()
    }

    /**
     * A utility routine for printing out various debug messages.
     *
     * @param msg The type of config being looked at (current, goal,
     *  successor, e.g.)
     * @param config The config to display
     */
    private void debugPrint(String msg, Configuration config) {
        if (this.debug) {
            System.out.print(msg + ": " + System.lineSeparator() + config);
        }
    }

    /**
     * Try find a solution, if one exists, for a given configuration.
     * <p>
     * changes made:
     * offloaded increasing configcount to when the function is called, by 1,
     * due to the fact that configurations are being generated as needed, not
     * prior in a batch.  There was no negligible way to compute which
     * successors would be valid prior to reaching them, so I didn't. It ends up
     * that configcount is only updated one time more than prior.
     * <p>
     * Also, due to changes made in TentConfig, uses the config itself as an
     * iterable.
     *
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public Optional<Configuration> solve(Configuration config) {
        Stack<TentConfig> configStack = new Stack<>();
        configStack.push((TentConfig) config);
        configCount++;
        while (!configStack.isEmpty()) {
            TentConfig onConfig = configStack.peek();
            if (onConfig.isGoal()) {
                return Optional.of(onConfig);
            }
            if (onConfig.hasNext()) {
                configStack.push(onConfig.next());
                configCount++;
            } else {
                configStack.pop();
            }
        }
        return Optional.empty();
//        configCount++;
//        if (configCount == 0) { // 30x30 goes over max integer count.  Somehow.
//            System.out.println("On config " + configCount + System.lineSeparator() + config);
//        }
//        debugPrint("Current config", config);
//        if (config.isGoal()) {
//            return Optional.of(config);
//        } else {
//            if (config.isValid()) {
//                debugPrint("Valid successor", config);
//                for (Configuration child : (TentConfig) config) {
//                    Optional<Configuration> sol = solve(child);
//                    if (sol.isPresent()) {
//                        return sol;
//                    }
//                }
//            } else {
//                debugPrint("Invalid successor", config);
//            }
//            // implicit backtracking happens here
//        }
//        return Optional.empty();
    }

    /**
     * Get the number of configurations processed during backtracking.
     *
     * @return config count
     */
    public int getConfigCount() {
        return this.configCount;
    }
}
