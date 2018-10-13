/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Karl
 */
public class CSP {

    

    HashSet<Node> variables;
    HashSet<Node> unassignedVariables;
    char[][] sourceVariables;
    HashMap<Node, HashSet<Character>> domains;

    public CSP(Board board) {
        sourceVariables = board.grid;
        variables = new HashSet<>();
        unassignedVariables = new HashSet<>();
        domains = new HashMap<>();
        HashSet<Character> allColors = new HashSet<>();
        for (Node[] nodeArray : board.nodes) {
            for (Node node : nodeArray) {
                if (node.isSource) {
                    allColors.add(node.color);
                } else {
                    unassignedVariables.add(node);
                }
                variables.add(node);
            }
        }

        for (Node node : variables) {
            if (!node.isSource) {
                domains.put(node, allColors);
            }
        }
    }

    public static char[][] backtrackingSearch(CSP csp) {
        return recursiveBacktracking(csp.sourceVariables, csp);
    }

    public static char[][] recursiveBacktracking(char[][] assignment, CSP csp) {
        System.out.println();
        for (int i = 0; i < assignment.length + 1; i++) {
            for (int j = 0; j < assignment.length + 1; j++) {
                if (j == 0) {
                    System.out.print(Math.abs(i - 1));
                } else if (i == 0) {
                    System.out.print(j - 1);
                } else {
                    System.out.print(assignment[i - 1][j - 1]);
                }
            }
            System.out.println();
        }
        if (csp.unassignedVariables.isEmpty()) {
            return assignment;
        }
        Node current = csp.unassignedVariables.iterator().next();
        csp.unassignedVariables.remove(current);
        for (Character color : csp.domains.get(current)) {
            if (childrenConsistent(current, color, assignment)) {
                assignment[current.y][current.x] = color;
                current.isAssigned = true;
                current.color = color;
                char[][] result = recursiveBacktracking(assignment, csp);
                if (result != null) {
                    return result;
                }
                assignment[current.y][current.x] = '_';
                current.isAssigned = false;
                current.color = '_';
            }
        }
        csp.unassignedVariables.add(current);
        return null;
    }

    public static boolean isConsistent(Node node, Character color, char[][] assignment) {
        int sameColorCount = 0;
        if (node.isSource) {
            for (Node child : node.children) {
                if (child.isAssigned && child.color == color) {
                    sameColorCount++;
                }
                if (sameColorCount > 1) {
                    return false;
                }
            }
        } else {
            for (Node child : node.children) {
                if (child.isAssigned && child.color == color) {
                    sameColorCount++;
                }
                if (sameColorCount > 2) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static boolean childrenConsistent(Node current, Character color, char[][] assignment) {
        boolean check = true;
        for (Node child : current.children) {
            if (!isConsistent(child, color, assignment)) {
                check = false;
            }
        }
        return check;
    }
}
