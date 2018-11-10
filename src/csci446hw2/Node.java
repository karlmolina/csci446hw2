/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author h89q624
 */
public class Node {

    int x, y;
    char color;
    ArrayList<Node> children;
    boolean isSource, isComplete;
    LinkedList<Character> domain;
    HashMap<Character, Integer> childrenColorCount;

    public Node(int x, int y) {
        children = new ArrayList<>();
        domain = new LinkedList<>();
        childrenColorCount = new HashMap<>();
        this.x = x;
        this.y = y;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return color + " : " + x + ", " + y;
    }

    public boolean isConsistent(char[][] assignment) {

        if (!isConsistentSquares(assignment)) {
            return false;
        }

        //check each child to see if they are consistent
        for (Node child : children) {
            if (child.color == '_') {
//                if (!child.isConsistentBlank(color)) {
//                    return false;
//                }

            } else if (!child.isConsistentWithChildren()) {
                return false;
            }

        }
        return isConsistentWithChildren();
    }

    public boolean isConsistentBlank() {
        if (childrenColorCount.get(color) > 2) {
            return false;
        }
        //for (Character clr: domain) {

        // }
//        if (childrenAssigned == children.size() && childrenColorCount.get(color) != 2) {
//            return false;
//        }
        return true;
    }

    public boolean isConsistentWithChildren() {
        int childrenAssigned = 0, childrenSameColor = 0;
        // Count the amount of children that are assigned
        // and the amount of children that are the same color as this node
        for (Node child : children) {
            if (!child.isBlank()) {
                childrenAssigned++;
                if (child.color == this.color) {
                    childrenSameColor++;
                }
            }
        }

        // If the node is a source and all of its children are assigned,
        // then it must have only 1 child that is the same color as it
        if (isSource) {
            
            if (childrenAssigned == children.size() && childrenSameColor != 1) {
                return false;
            }
            // It can never have more than 1 child of the same color
            if (childrenSameColor > 1) {
                return false;
            }
            
        // If the node is not a source and all its children are assigned,
        // then it must have exactly 2 children that are the same color as it
        } else {
            
            if ((childrenAssigned == children.size() && childrenSameColor != 2) || (childrenAssigned == children.size() - 1 && childrenSameColor == 0)) {
                return false;
            }
            // It can never have more than 2 children with the same color
            if (childrenSameColor > 2) {
                return false;
            }
        }
        return true;
    }

    private boolean isConsistentSquares(char[][] assignment) {
        //check for squares of the same color to the bottom right
        if (y < assignment.length - 1 && x < assignment.length - 1 && assignment[y + 1][x] == color && assignment[y][x + 1] == color && assignment[y + 1][x + 1] == color) {
            return false;
        }
        //check for square of the same color to the bottom left
        if (x > 0 && y < assignment.length - 1 && assignment[y + 1][x] == color && assignment[y][x - 1] == color && assignment[y + 1][x - 1] == color) {
            return false;
        }
        //check for square of the same color to the top left
        if (x > 0 && y > 0 && assignment[y - 1][x - 1] == color && assignment[y - 1][x] == color && assignment[y][x - 1] == color) {
            return false;
        }
        //check for square of the same color to the top right
        if (x < assignment.length - 1 && y > 0 && assignment[y - 1][x + 1] == color && assignment[y - 1][x] == color && assignment[y][x + 1] == color) {
            return false;
        }

        return true;
    }

    public void assign(char color, char[][] assignment) {
        this.color = color;
        assignment[y][x] = color;
    }

    public void unassign(char[][] assignment) {
        assignment[y][x] = '_';
        this.color = '_';
    }

    public boolean isBlank() {
        return color == '_';
    }
}
