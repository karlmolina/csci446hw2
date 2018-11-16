/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

/**
 * Class to check if a CSP has a complete path for a certain color.
 * This seems to just make the algorithm slower so there is an option if you
 * want to use it or not.
 * 
 * @see CSP
 * @author h89q624
 */
public class PathComplete {

    /**
     * Checks if the csp has a complete path for the color c
     * @param csp
     * @param c
     * @return 
     */
    public static boolean check(CSP csp, char c) {
        Node source = csp.sourceColorToNodeMap.get(c).getFirst();
        return check(source, null);
    }

    /**
     * Recursive helper function to check a source's children 
     * @param source
     * @param previous
     * @return 
     */
    private static boolean check(Node source, Node previous) {
        // Loops through the source's children
        for (Node node : source.children) {
            // If the child has the same color
            if (node.color == source.color && node != previous) {
                // If you find the other source that means there is a complete path
                if (node.isSource) {
                    return true;
                }
                // Otherwise recurse to the next node
                return check(node, source);
            }
        }

        return false;
    }
}
