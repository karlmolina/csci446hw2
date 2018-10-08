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
    private char[][] grid;
    private int height, width;
    public Board(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fileName));
        ArrayList<String> lines = new ArrayList<>();
        while (in.hasNext()) {
            lines.add(in.next());
        }
        height = lines.size();
        width = lines.get(0).length();
        
        grid = new char[height][width];
        
        for (int i = 0; i < height; i++) {
            grid[i] = lines.get(0).toCharArray();
        }
        
        
    }
}
