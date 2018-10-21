/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author Karl
 */
public class CSP {

    Node[][] variables;
    LinkedList<Node> unassignedVariablesList;
    PriorityQueue<Node> unassignedVariablesPQ;
    char[][] sourceVariables;
    HashMap<Node, HashSet<Character>> domains;
    PriorityQueue<Node> expandableNodes;
    HashMap<Character, HashSet<Node>> sourceColorToNodeMap;
    

    public CSP(Board board) {
        expandableNodes = new PriorityQueue<>( (Node a, Node b) -> a.nodeDomain.size() - b.nodeDomain.size());
        sourceVariables = board.grid;
        variables = board.nodes;
        unassignedVariablesList = new LinkedList<>();
        unassignedVariablesPQ = new PriorityQueue<>((Node a, Node b) -> b.childrenAssigned - a.childrenAssigned);
        domains = new HashMap<>();
        sourceColorToNodeMap = new HashMap<>();
        HashSet<Character> allColors = new HashSet<>();
        for (Node[] nodeArray : board.nodes) {
            for (Node node : nodeArray) {
                if (node.isSource) {
                    allColors.add(node.color);
                    if (!sourceColorToNodeMap.containsKey(node.color)) {
                        sourceColorToNodeMap.put(node.color, new HashSet<>());
                    }
                    sourceColorToNodeMap.get(node.color).add(node);
                } else {
                    unassignedVariablesList.addFirst(node);
                    unassignedVariablesPQ.add(node);
                }
            }
        }
        //Collections.shuffle(unassignedVariables);
        HashMap<Character, Boolean> hasColor = new HashMap<>();
        for (Character color : allColors) {
            hasColor.put(color, false);
        }
        for (Node[] nodes : variables) {
            for (Node node : nodes) {
                if (!node.isSource) {
                    node.domain.addAll(allColors);
                } else {
                    node.setDomain();
                    //if (!hasColor.get(node.color)) {
                        expandableNodes.add(node);
                    //    hasColor.put(node.color, true);
                    //}
                }
            }
        }
    }
}
