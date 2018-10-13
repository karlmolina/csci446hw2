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

    static int count = 0;
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
        //System.out.println(count);
        //System.out.println();
//brgyo
        if (csp.unassignedVariables.isEmpty()) {
            return assignment;
        }
        Node current = csp.unassignedVariables.iterator().next();
        csp.unassignedVariables.remove(current);
        for (Character color : csp.domains.get(current)) {
            current.assign(color);
            count++;
            //System.out.println(count);
            //System.out.println("trying " + current);
            //printArray(assignment);
            if (current.isConsistent()) {
                assignment[current.y][current.x] = color;

                char[][] result = recursiveBacktracking(assignment, csp);
                if (result != null) {
                    return result;
                }
                assignment[current.y][current.x] = '_';
            }
            current.unassign();
        }
        csp.unassignedVariables.add(current);
        return null;
    }

    static void printArray(char[][] array) {
        for (int i = 0; i < array.length + 1; i++) {
            for (int j = 0; j < array.length + 1; j++) {
                if (j == 0) {
                    System.out.print(Math.abs(i - 1));
                } else if (i == 0) {
                    System.out.print(j - 1);
                } else {
                    System.out.print(array[i - 1][j - 1]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
