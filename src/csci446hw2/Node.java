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
    ArrayList<Node> nodeDomain;
    int childrenCount = 0, childrenAssigned = 0, childrenSameColor = 0;
    boolean isSource, isComplete;
    LinkedList<Character> domain;
    HashMap<Character, Integer> childrenColorCount;

    public Node(int x, int y) {
        nodeDomain = new ArrayList<>();
        children = new ArrayList<>();
        domain = new LinkedList<>();
        childrenColorCount = new HashMap<>();
        this.x = x;
        this.y = y;
    }

    public void addChild(Node child) {
        children.add(child);
        childrenCount++;
    }

    @Override
    public String toString() {
        return color + " : " + x + ", " + y + " domainSize: " + nodeDomain.size();
    }

    public boolean isConsistent(char[][] assignment) {
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
        //check each child to see if they are consistent
        for (Node child : children) {
            if (child.color == '_') {
                if (!child.isConsistentBlank(color)) {
                    return false;
                }

            } else if (!child.isConsistentWithChildren()) {
                return false;
            }

        }
        return isConsistentWithChildren();
    }

    public boolean isConsistentBlank(char color) {
        if (childrenColorCount.get(color) > 2) {
            return false;
        }
//        if (childrenAssigned == childrenCount && childrenColorCount.get(color) != 2) {
//            return false;
//        }
        return true;
    }

    public boolean isConsistentWithChildren() {
        if (isSource) {
            if (childrenAssigned == childrenCount && childrenSameColor != 1) {
                return false;
            }
        } else {
            if ((childrenAssigned == childrenCount && childrenSameColor != 2) || (childrenAssigned == childrenCount - 1 && childrenSameColor == 0)) {
                return false;
            }
        }
        return true;
    }

    public int childrenUnassigned() {
        return childrenCount - childrenAssigned;
    }

    public void assign(char color, char[][] assignment) {
        this.color = color;
        assignment[y][x] = color;
        for (Node child : children) {
            child.childrenAssigned++;
            if (child.childrenColorCount.containsKey(color)) {
                child.childrenColorCount.put(color, child.childrenColorCount.get(color) + 1);
            } else {
                child.childrenColorCount.put(color, 1);
            }
            if (child.color == color) {
                child.childrenSameColor++;
                childrenSameColor++;
            }
            if (child.isSource) {
                if (child.childrenSameColor == 1) {
                    child.isComplete = true;
                }
            } else {
                if (child.childrenSameColor == 2) {
                    child.isComplete = true;
                }
            }
        }
        if (this.isSource) {
            if (childrenSameColor == 1) {
                isComplete = true;
            }
        } else {
            if (childrenSameColor == 2) {
                isComplete = true;
            }
        }
    }

    public void unassign(char[][] assignment) {
        assignment[y][x] = '_';
        for (Node child : children) {
            child.childrenAssigned--;
            child.childrenColorCount.put(color, child.childrenColorCount.get(color) - 1);
            if (child.color == color) {
                child.childrenSameColor--;
                childrenSameColor--;
            }
            if (child.isSource) {
                if (child.childrenSameColor != 1) {
                    child.isComplete = false;
                }
            } else {
                if (child.childrenSameColor != 2) {
                    child.isComplete = false;
                }
            }
        }
        if (this.isSource) {
            if (childrenSameColor != 1) {
                isComplete = false;
            }
        } else {
            if (childrenSameColor != 2) {
                isComplete = false;
            }
        }
        this.color = '_';
    }

    public boolean isBlank() {
        return color == '_';
    }
}
