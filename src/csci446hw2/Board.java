/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

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
    public Board(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fileName));
        ArrayList<String> lines = new ArrayList<>();
        while (in.hasNext()) {
            lines.add(in.next());
        }
        size = lines.size();
        
        grid = new char[size][size];
        
        for (int i = 0; i < size; i++) {
            grid[i] = lines.get(i).toCharArray();
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
