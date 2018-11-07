/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.io.FileNotFoundException;

/**
 *
 * @author Karl
 */
public class Driver {

    public static BoardFrame boardFrame;
    public static int animate = 1;
    public static int search = 0;
    // possible boards 5, 7, 8, 9, 10, 12, 14
    public static int BOARD_SIZE = 9;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        System.out.println(BOARD_SIZE + "x" + BOARD_SIZE);
        String boardName = "boards/" + BOARD_SIZE + "x" + BOARD_SIZE + "maze.txt";
        //Board board = new Board("boards/testmaze.txt");
        Board board = new Board(boardName);
        board.print();

        CSP csp = new CSP(board);

        boardFrame = new BoardFrame(board.grid);
        boardFrame.f.repaint();
        //char[][] result = Backtracking.smartSearch(csp);

        long then = System.currentTimeMillis();
        
        Backtracking.orange(csp);
        
        System.out.println((System.currentTimeMillis() - then) / 1000.0);
        boardFrame.f.repaint();
        board.print();
        //boardFrame = new BoardFrame(board.grid);

        // System.out.println(Class.forName("Color.BLACK"));
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
