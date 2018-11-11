/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 *
 * @author Karl
 */
public class Driver {

    public static BoardFrame boardFrame;
    // Whether to animate the solving of the puzzle or not
    // 1 means animate, 0 means don't
    public static int ANIMATE = 0,
            // board sizes: 5, 7, 8, 9, 10, 12, 14
            BOARD_SIZE = 14;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        //                  0  1  2  3  4   5   6
        Integer[] boardSizes = {5, 7, 8, 9, 10, 12, 14};
        int boardIndex = Arrays.asList(boardSizes).indexOf(BOARD_SIZE);
        System.out.println(boardIndex);
        //for (int i = boardIndex; i >= 0; i--) {
        for (int i = boardIndex; i > -1; i = -1) {
            //int boardSize = BOARD_SIZE;
            int boardSize = boardSizes[i];
            // What board you are running
            String boardName = boardSize + "x" + boardSize;
            System.out.println("Solving the " + boardName + " puzzle.");

            // The file name for that board
            String boardFile = "boards/" + boardName + "maze.txt";

            //Board board = new Board("boards/testmaze.txt");
            // Create the board
            Board board = new Board(boardFile);
            board.print();
            // Convert the board to a csp
            CSP csp = new CSP(board);

            // Make the graphics
            boardFrame = new BoardFrame(board.grid, boardName);
            boardFrame.f.repaint();

            // Solve all nodes that have a parent with only 1 child.
            boardFrame.f.repaint();

            //Timer thread
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
            Thread runAlgorithm = new Thread(() -> {
                long then = System.currentTimeMillis();
                Backtracking.apple(csp);
                System.out.println("The " + boardName + " puzzle took " +(System.currentTimeMillis() - then) / 1000.0 + " seconds to solve.");
                boardFrame.f.repaint();
                board.print();

                // Stop the timer thread
                timer.interrupt();
            });

            timer.start();
            runAlgorithm.start();

            runAlgorithm.join();
            System.out.println();
        }
        
        System.out.println("Close the window to exit the program.");
    }
}
