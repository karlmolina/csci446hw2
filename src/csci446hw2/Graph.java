/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

/**
 *
 * @author h89q624
 */
public class Graph {

    Node[][] nodes;
    int size;
    
    public Graph(Board board) {
        size = board.size;
        nodes = new Node[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Node current = new Node(i, j);
                if (board.grid[i][j] != '_') {
                    
                    current.isSource = true;
                }
                current.color = board.grid[i][j];
                nodes[i][j] = current;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Node current = nodes[i][j];
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (Math.abs(k) != Math.abs(l)) {
                            try {
                                current.addChild(nodes[i + k][j + l]);
                            } catch (IndexOutOfBoundsException e) {
                                //skip
                            }
                        }
                    }
                }
            }
        }
    }
}
