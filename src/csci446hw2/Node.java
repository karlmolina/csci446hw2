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
    boolean isAssigned;
    
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
    
    
}
