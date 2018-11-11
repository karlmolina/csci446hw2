/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Karl
 */
public class Board {

    char[][] grid;
    int size;
    Node[][] nodes;

    public Board(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fileName));
        ArrayList<String> lines = new ArrayList<>();
        while (in.hasNext()) {
            lines.add(in.next());
        }
        size = lines.size();

        grid = new char[size][size];
        nodes = new Node[size][size];

        for (int i = 0; i < size; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Node current = new Node(j, i);
                if (grid[i][j] != '_') {
                    current.isSource = true;
                }
                current.color = grid[i][j];
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

        for (Node[] nodeArray : nodes) {
            for (Node node : nodeArray) {
                if (node.color != '_') {
                    node.assign(node.color, grid);
                }
            }
        }
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }
}
