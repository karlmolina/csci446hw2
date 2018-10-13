/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 *
 * @author Karl
 */
public class CSP {
    HashSet<Node> variables;
    Stack<Node> unassignedVariables;
    char[][] sourceVariables;
    HashMap<Node, HashSet<Character>> domains;

    public CSP(Board board) {
        sourceVariables = board.grid;
        variables = new HashSet<>();
        unassignedVariables = new Stack<>();
        domains = new HashMap<>();
        HashSet<Character> allColors = new HashSet<>();
        for (Node[] nodeArray : board.nodes) {
            for (Node node : nodeArray) {
                if (node.isSource) {
                    allColors.add(node.color);
                } else {
                    unassignedVariables.push(node);
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
}
