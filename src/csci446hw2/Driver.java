/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

/**
 *
 * @author Karl
 */
public class Driver {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        final int BOARD_SIZE = 12;
        String boardName = "boards/"+BOARD_SIZE+"x"+BOARD_SIZE+"maze.txt";
        Board board = new Board("boards/testmaze.txt");
        board.print();
        
        CSP csp = new CSP(board);
        
        char[][] result = Backtracking.search(csp);
        
        board.print();
        BoardFrame boardFrame = new BoardFrame(result);
        
        System.out.println(Class.forName("Color.BLACK"));
        
//        int[] boardSizes = {5, 7, 8, 9, 10, 12, 14};
//        
//        for (int i = 0; i < boardSizes.length; i++) {
//            int boardSize = boardSizes[i];
//            String boardPath = "boards/" + boardSize + "x" + boardSize + "maze.txt";
//            System.out.println(boardPath);
//            Board board = new Board(boardPath);
//            board.print();
//        }
    }
}
