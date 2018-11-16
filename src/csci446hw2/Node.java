/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.ArrayList;
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

    /**
     * Node constructor
     * @param x The x position of the node
     * @param y The y position of the node
     */
    public Node(int x, int y) {
        children = new ArrayList<>();
        domain = new LinkedList<>();
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a child to the node
     * @param child 
     */
    public void addChild(Node child) {
        children.add(child);
    }

    /**
     * Method that returns a string representation of the node
     * including its color and its position
     * @return 
     */
    @Override
    public String toString() {
        return color + " : " + x + ", " + y;
    }

    /**
     * Checks if the node is consistent with the assignment
     * @param assignment
     * @return true if consistent, false if not
     */
    public boolean isConsistent(char[][] assignment) {
        if (Driver.SQUARE_CONSTRAINT == 1) {
            if (!isConsistentSquares(assignment)) {
                return false;
            }
        }

        // Check each child to see if they are consistent with their children
        for (Node child : children) {
            if (child.isBlank()) {
                if (Driver.BLANK_CHECK == 1) {
                    if (!child.isConsistentBlank(assignment)) {
                        return false;
                    }
                }
            // Check the child if it is consistent with its children
            } else if (!child.isConsistentWithChildren()) {
                return false;
            }

        }
        // Finally check if this node is consistent with its children
        return isConsistentWithChildren();
    }

    /**
     * Checks if a blank node is consistent
     * Assigns a color to the node and checks if it is consistent for one of 
     * them
     * @param assignment
     * @return If it is impossible to assign a color to the node it returns
     * false
     */
    public boolean isConsistentBlank(char[][] assignment) {
        boolean isConsistent = false;
        for (Character color : domain) {
            assign(color, assignment);
            if (isConsistentNoBlankCheck(assignment)) {
                isConsistent = true;
            }
            unassign(assignment);
            if (isConsistent) {
                break;
            }
        }
        
        return isConsistent;
    }

    /**
     * Duplicate method of isConsistent except it does not check for
     * blank nodes. This stops recursively checking all the nodes.
     * @param assignment
     * @return 
     */
    private boolean isConsistentNoBlankCheck(char[][] assignment) {
        if (Driver.SQUARE_CONSTRAINT == 1) {
            if (!isConsistentSquares(assignment)) {
                return false;
            }
        }

        //check each child to see if they are consistent
        for (Node child : children) {
            if (!child.isBlank() && !child.isConsistentWithChildren()) {
                return false;
            }

        }

        return isConsistentWithChildren();
    }

    /**
     * Method that checks a node to see if it is consistent with its children.
     * Allows for partial completeness
     * @return 
     */
    private boolean isConsistentWithChildren() {
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

    /**
     * Method to check if the node has no squares of color around it.
     * @param assignment
     * @return 
     */
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

    /**
     * Assigns the color to the node and the spot where that node is 
     * on the assignment.
     * @param color
     * @param assignment 
     */
    public void assign(char color, char[][] assignment) {
        this.color = color;
        assignment[y][x] = color;
    }

    /**
     * Unassigns the node and the spot where that node is on the assignment.
     * Sets the node's color to blank.
     * @param assignment 
     */
    public void unassign(char[][] assignment) {
        assignment[y][x] = '_';
        this.color = '_';
    }

    /**
     * Checks if this node is blank or not.
     * @return 
     */
    public boolean isBlank() {
        return color == '_';
    }
}
