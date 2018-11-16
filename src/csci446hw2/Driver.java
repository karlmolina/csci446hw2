/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.io.FileNotFoundException;

/**
 *
 * @author Karl, Jordan
 */
public class Driver {

    public static BoardFrame boardFrame;
    // Whether to animate the solving of the puzzle or not
    // 1 means animate, 0 means don't
    public static int ANIMATE = 0,
            // Heuristics options
            PATH_COMPLETE = 0,
            ORDER_DOMAIN_BY_DISTANCE = 0,
            TOUCHING_COLORS_FIRST = 0,
            // Other options

            // 0 = Start going through the unassigned variables from the top left
            // 1 = Start from bottom right 
            TOP_LEFT_START = 1,
            BLANK_CHECK = 0,
            SQUARE_CONSTRAINT = 1;

    /**
     * @param args The command line arguments
     * Takes in the path to the file containing the maze
     * 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    System.out.println("Dumb algorithm");
                    ORDER_DOMAIN_BY_DISTANCE = 0;
                } else {
                    System.out.println("Smart algorithm");
                    ORDER_DOMAIN_BY_DISTANCE = 1;
                }

                // The file name for that board
                String boardFile = args[i];
                System.out.println("Solving " + boardFile);

                // Create the board
                Board board = new Board(boardFile);
                board.print();
                
                // Convert the board to a csp
                CSP csp = new CSP(board);

                // Make the graphics
                boardFrame = new BoardFrame(board.grid, boardFile);
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
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                });
                
                // Thread to run the backtracking apple
                Thread runAlgorithm = new Thread(() -> {
                    long then = System.currentTimeMillis();
                    Backtracking.apple(csp);
                    System.out.println("The " + boardFile + " puzzle took " + (System.currentTimeMillis() - then) + " ms to solve.");
                    boardFrame.f.repaint();
                    board.print();

                    // Stop the timer thread
                    timer.interrupt();
                });

                // Start the timer and run the algorithm
                timer.start();
                runAlgorithm.start();

                // Wait for the algorithm to finish
                runAlgorithm.join();
                System.out.println();
            }
        }

        System.out.println("Close a window to exit the program.");
    }
}
