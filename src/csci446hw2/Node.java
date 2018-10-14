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
    int childrenCount = 0, childrenAssigned = 0, childrenSameColor = 0;
    boolean isSource;

    public Node(int x, int y) {
        children = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    public void addChild(Node child) {
        children.add(child);
        childrenCount++;
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
                if (childrenAssigned == childrenCount && childrenSameColor != 1) {
                    return false;
                }
            } else {
                if ((childrenAssigned == childrenCount && childrenSameColor != 2) || (childrenAssigned == childrenCount - 1 && childrenSameColor == 0)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int childrenUnassigned() {
        return childrenCount - childrenAssigned;
    }

    public void assign(char color) {
        this.color = color;
        for (Node child : children) {
            child.childrenAssigned++;
            
            if (child.color == color) {
                child.childrenSameColor++;
                childrenSameColor++;
            }
        }
    }

    public void unassign() {
        for (Node child : children) {
            child.childrenAssigned--;
            
            if (child.color == color) {
                child.childrenSameColor--;
                childrenSameColor--;
            }
        }
        this.color = '_';
    }

}
