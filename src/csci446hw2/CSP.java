/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 *
 * @author Karl
 */
public class CSP {

    /**
     * A list of the non-source variables in the csp.
     */
    LinkedList<Node> variables;
    
    /**
     * A 2D array of the source variables.
     */
    char[][] sourceVariables;
    
    /**
     * A map that maps colors to list of the 2 sources which have that color.
     */
    HashMap<Character, LinkedList<Node>> sourceColorToNodeMap;

    /**
     * CSP constructor.
     * Takes a board and converts it to a csp.
     * @param board 
     */
    public CSP(Board board) {
        // Initialize variables
        sourceVariables = board.grid;
        variables = new LinkedList<>();
        sourceColorToNodeMap = new HashMap<>();
        // Set to remember all the colors for the csp
        HashSet<Character> allColors = new HashSet<>();
        HashSet<Node> sources = new HashSet<>();
        
        // Loop through all the nodes in the board
        for (Node[] nodeArray : board.nodes) {
            for (Node node : nodeArray) {
                // If the node is a source add it to the sources, and store
                // its color in the allColors set
                if (node.isSource) {
                    allColors.add(node.color);
                    sources.add(node);
                    if (!sourceColorToNodeMap.containsKey(node.color)) {
                        sourceColorToNodeMap.put(node.color, new LinkedList<>());
                    }
                    sourceColorToNodeMap.get(node.color).add(node);
                } else {
                    // If the node is not a source add it to the variables list
                    if (Driver.TOP_LEFT_START == 1) {
                        variables.addLast(node);
                    } else {
                        variables.addFirst(node);
                    }
                }
            }
        }

        if (Driver.ORDER_DOMAIN_BY_DISTANCE == 1) {
            // Order the node's color domain by distance to a source node with
            // that color
            for (Node node : variables) {
                PriorityQueue<ColorDistancePair> pq = new PriorityQueue<>();
                for (Node source : sources) {
                    int distance = Math.abs(node.x - source.x) + Math.abs(node.y - source.y);
                    pq.add(new ColorDistancePair(distance, source.color));
                }
                while (!pq.isEmpty()) {
                    ColorDistancePair current = pq.poll();
                    if (!node.domain.contains(current.color)) {
                        node.domain.addLast(current.color);
                    }
                }
            }
        } else {
            // Do not order the colors before you add them to the node's domain
            for (Node node : variables) {
                if (!node.isSource) {
                    node.domain.addAll(allColors);
                }
            }
        }
    }

    /**
     * A helper class to order the colors and the distance from a current node
     * and that color.
     */
    private class ColorDistancePair implements Comparable {

        int distance;
        char color;

        /**
         * ColorDistancePair constructor
         * @param distance
         * @param color 
         */
        public ColorDistancePair(int distance, char color) {
            this.distance = distance;
            this.color = color;
        }

        @Override
        public int compareTo(Object o) {
            return this.distance - ((ColorDistancePair) o).distance;
        }
    }

    /**
     * Select an unassigned variable.
     * Iterates through the variables and returns the first node that is blank.
     * @return 
     */
    public Node selectUnassignedVariable() {
        Node returnNode = null;
        for (Node node : variables) {
            if (node.isBlank()) {
                returnNode = node;
                break;
            }
        }
        return returnNode;
    }
}
