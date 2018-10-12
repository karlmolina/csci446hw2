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
    private ArrayList<Node> children;
    boolean isSource;
    
    public Node(int x, int y) {
        children = new ArrayList<>();
        this.x = x;
        this.y = y;
    }
    
    public void addChild(Node child) {
        children.add(child);
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the color
     */
    public char getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(char color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return color + " : " + x + ", " + y;
    }
    
    
}
