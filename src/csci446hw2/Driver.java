/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Karl
 */
public class Driver {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        Board board = new Board("boards/5x5maze.txt");
        board.print();
        
        Graph graph = new Graph(board);
        
        CSP csp = new CSP(graph);
        
        HashMap<Node, Character> result = CSP.backtrackingSearch(csp);
        
        char[][] resultBoard = new char[board.size][board.size];
        
        for (Map.Entry<Node, Character> entry : result.entrySet()) {
            resultBoard[entry.getKey().x][entry.getKey().y] = entry.getValue();
        }
        
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                System.out.print(resultBoard[i][j]);
            }
            System.out.println();
        }
        
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
