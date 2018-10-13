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
    HashMap<Node, Character> sourceVariables;
    HashMap<Node, HashSet<Character>> domains;

    public CSP(Graph graph) {
        variables = new HashSet<>();
        sourceVariables = new HashMap<>();
        unassignedVariables = new HashSet<>();
        domains = new HashMap<>();
        HashSet<Character> allColors = new HashSet<>();
        for (Node[] nodeArray : graph.nodes) {
            for (Node node : nodeArray) {
                if (node.isSource) {
                    allColors.add(node.color);
                    sourceVariables.put(node, node.color);
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

    public static HashMap<Node, Character> backtrackingSearch(CSP csp) {
        return recursiveBacktracking(csp.sourceVariables, csp);
    }

    public static HashMap<Node, Character> recursiveBacktracking(HashMap<Node, Character> assignment, CSP csp) {
        if (csp.unassignedVariables.isEmpty()) {
            return assignment;
        }
        Node current = csp.unassignedVariables.iterator().next();
        csp.unassignedVariables.remove(current);
        for (Character color : csp.domains.get(current)) {
            if (isConsistent(current, color, assignment)) {
                assignment.put(current, color);
                HashMap<Node, Character> result = recursiveBacktracking(assignment, csp);
                if (result != null) {
                    return result;
                }
                assignment.remove(current);
            }
        }
        csp.unassignedVariables.add(current);
        return null;
    }

    public static boolean isConsistent(Node node, Character color, HashMap<Node, Character> assignment) {
        int sameColorCount = 0;
        if (node.isSource) {
            for (Node child : node.children) {
                if (assignment.containsKey(child) && child.color == color) {
                    sameColorCount++;
                }
                if (sameColorCount > 1) {
                    return false;
                }
            }
        } else {
            for (Node child : node.children) {
                if (assignment.containsKey(child) && child.color == color) {
                    sameColorCount++;
                }
                if (sameColorCount > 2) {
                    return false;
                }
            }
        }
        return true;
    }
}
