/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.ArrayList;

/**
 *
 * @author h89q624
 */
public class Node {

    int x;
    int y;
    char color;
    ArrayList<Node> children;
    boolean isSource;

    public Node(int x, int y) {
        children = new ArrayList<>();
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

    public boolean isConsistent() {
        for (Node child : children) {
            if (!child.hasFewEnoughChildren()) {
                return false;
            }
        }
        return hasFewEnoughChildren();
    }

    public boolean hasFewEnoughChildren() {
        if (color != '_') {
            if (isSource) {
                if (childrenAssigned() == childrenCount() && childrenSameColor() != 1) {
                    return false;
                }
            } else {
                if (childrenAssigned() == childrenCount() && childrenSameColor() != 2) {
                    return false;
                } else if (childrenAssigned() == childrenCount() - 1 && childrenSameColor() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int childrenSameColor() {
        int count = 0;
        for (Node child : children) {
            if (this.color == child.color) {
                count++;
            }
        }
        return count;
    }
    
    public int childrenAssigned() {
        int count = 0;
        for (Node child : children) {
            if (child.color != '_') {
                count++;
            }
        }
        return count;
    }
    
    public int childrenCount() {
        return children.size();
    }

    public void assign(char color) {
        this.color = color;
    }

    public void unassign() {
        this.color = '_';
    }

}
