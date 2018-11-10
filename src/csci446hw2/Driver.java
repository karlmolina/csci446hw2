/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karl
 */
public class Driver {

    public static BoardFrame boardFrame;
    // Whether to animate the solving of the puzzle or not
    // 1 means animate, 0 means don't
    public static final int ANIMATE = 1,
            // possible boards 5, 7, 8, 9, 10, 12, 14
            BOARD_SIZE = 12;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        // What board you are running
        System.out.println(BOARD_SIZE + "x" + BOARD_SIZE);
        // The file name for that board
        String boardName = "boards/" + BOARD_SIZE + "x" + BOARD_SIZE + "maze.txt";
        //Board board = new Board("boards/testmaze.txt");

        // Create the board
        Board board = new Board(boardName);
        board.print();

        // Convert the board to a csp
        CSP csp = new CSP(board);

        // Make the graphics
        boardFrame = new BoardFrame(board.grid);
        boardFrame.f.repaint();

        // Solve all nodes that have a parent with only 1 child.
        OneChildSolve.Execute(csp);

        

        // Timer thread
        Thread timer = new Thread(() -> {
            long then = System.currentTimeMillis();
            int second = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println(second);
                    second++;
                } catch (InterruptedException ex) {
                    break;
                }
            }
        });
        
        // Thread to run the backtracking apple
        // For some reason it does not animate it inside the thread.
        Thread runAlgorithm = new Thread(() -> {
            long then = System.currentTimeMillis();
            Backtracking.apple(csp);
            System.out.println((System.currentTimeMillis() - then) / 1000.0);
            boardFrame.f.repaint();
            board.print();
            timer.interrupt();
        });

        timer.start();
        runAlgorithm.start();

        // Code to run all the boards one after another
        //boardFrame = new BoardFrame(board.grid);
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
