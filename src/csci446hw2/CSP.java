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
import javafx.util.Pair;

/**
 *
 * @author Karl
 */
public class CSP {

    LinkedList<Node> unassignedVariablesList;
    char[][] sourceVariables;
    HashMap<Character, LinkedList<Node>> sourceColorToNodeMap;

    public CSP(Board board) {
        sourceVariables = board.grid;
        unassignedVariablesList = new LinkedList<>();
        sourceColorToNodeMap = new HashMap<>();
        HashSet<Character> allColors = new HashSet<>();

        HashSet<Node> sources = new HashSet<>();
        for (Node[] nodeArray : board.nodes) {
            for (Node node : nodeArray) {
                if (node.isSource) {
                    allColors.add(node.color);
                    sources.add(node);
                    if (!sourceColorToNodeMap.containsKey(node.color)) {
                        sourceColorToNodeMap.put(node.color, new LinkedList<>());
                    }
                    sourceColorToNodeMap.get(node.color).add(node);
                } else {
                    unassignedVariablesList.addLast(node);
                }
            }
        }

        // Order the node's color domain by distance to a source node with
        // that color
//        for (Node node : unassignedVariablesList) {
//            PriorityQueue<ColorDistancePair> pq = new PriorityQueue<>();
//            for (Node source : sources) {
//                int distance = Math.abs(node.x - source.x) + Math.abs(node.y - source.y);
//                pq.add(new ColorDistancePair(distance, source.color));
//            }
//            while (!pq.isEmpty()) {
//                ColorDistancePair current = pq.poll();
//                if (!node.domain.contains(current.color)) {
//                    node.domain.addLast(current.color);
//                }
//            }
//        }

        for (Node node : unassignedVariablesList) {
            if (!node.isSource) {
                node.domain.addAll(allColors);
            }
        }
    }

    private class ColorDistancePair implements Comparable {

        int distance;
        char color;

        public ColorDistancePair(int distance, char color) {
            this.distance = distance;
            this.color = color;
        }

        @Override
        public int compareTo(Object o) {
            return this.distance - ((ColorDistancePair) o).distance;
        }
    }
    
    public Node selectUnassignedVariable() {
        Node returnNode = null;
        for (Node node : unassignedVariablesList) {
            if (node.isBlank()) {
                returnNode = node;
                break;
            }
        }
        return returnNode;
    }
}
