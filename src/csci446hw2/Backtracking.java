/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import static csci446hw2.Driver.ANIMATE;
import java.util.LinkedList;

/**
 *
 * @author Karl
 */
public class Backtracking {

    public static char[][] apple(CSP csp) {
        return apple(csp.sourceVariables, csp);
    }

    public static char[][] apple(char[][] assignment, CSP csp) {
        // Get an unassigned variable from the csp
        Node current = csp.selectUnassignedVariable();

        // If there are no more unassigned variables then we are done
        if (current == null) {
            return assignment;
        }

        LinkedList<Character> currentDomainCopy = null;
        if (Driver.TOUCHING_COLORS_FIRST == 1) {
            // Order domain so that it tries the colors
            // next to the current node first.
            // This heuristic makes the 14x14 very fast 
            // (4.602 seconds)
            // Hold a clone of the current.domain to change back
            // if it fails
            currentDomainCopy = (LinkedList<Character>) current.domain.clone();

            // Clear the domain
            current.domain.clear();
            // Add the colors next to the current node
            // to its domain
            for (Node child : current.children) {
                if (!child.isBlank()) {
                    current.domain.add(child.color);
                }
            }
            // Add back the rest of the colors
            for (Character color : currentDomainCopy) {
                if (!current.domain.contains(color)) {
                    current.domain.add(color);
                }
            }
        }
        // Try to assign the colors in the current node's
        // domain
        for (Character color : current.domain) {
            current.assign(color, assignment);
            if (current.isConsistent(assignment)) {

                // Show the animation of the graphics
                if (ANIMATE == 1) {
                    Driver.boardFrame.f.repaint();
                    Driver.boardFrame.f.revalidate();
                }

                boolean pathComplete = false;
                if (Driver.PATH_COMPLETE == 1) {
                    // Check for a complete path for the color
                    // that you just added.
                    // If the color has a complete path, then
                    // we will remove it from all the domains
                    // of all the unassigned nodes.
                    if (PathComplete.check(csp, color)) {
                        pathComplete = true;
                        for (Node node : csp.variables) {
                            if (node.isBlank()) {
                                node.domain.remove(color);
                            }
                        }
                    }
                }
                char[][] result = apple(assignment, csp);
                if (result != null) {
                    return result;
                }
                // This is the backtracking
                // We must undo all our changes above.

                if (Driver.PATH_COMPLETE == 1) {
                    // Add the color back to the nodes if
                    // the path was complete.
                    if (pathComplete) {
                        for (Node node : csp.variables) {
                            node.domain.addFirst(color);
                        }
                    }
                }
            }
            current.unassign(assignment);
        }

        if (Driver.TOUCHING_COLORS_FIRST == 1) {
            // Reset the current's domain. 
            current.domain = currentDomainCopy;
        }
        return null;
    }
}
