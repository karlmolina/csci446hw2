/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.io.FileNotFoundException;
import static java.lang.Math.random;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Karl
 */
public class Driver {

    public static BoardFrame boardFrame;
    public static int animate = 1;
    public static int search = 0;
    // possible boards 5, 7, 8, 9, 10, 12, 14
    public static int BOARD_SIZE = 5;
            

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        int[] fitness = {1, 2, 3, 4};
        double[] weights = Genetic.getWeights(fitness, 10);
        for (int i = 0; i < weights.length; i++) {
            System.out.println(weights[i]);
        }
        
        int[] count = {0, 0, 0, 0};
        for (int i = 0; i < 100000; i++) {
            //System.out.println(new Random().nextDouble());
            count[Genetic.rouletteSelect(weights)]++;
        }
        System.out.println();
        for (int i = 0; i < count.length; i++) {
            System.out.println(count[i]);
        }
        
        
        System.out.println(BOARD_SIZE + "x" + BOARD_SIZE);
        String boardName = "boards/" + BOARD_SIZE + "x" + BOARD_SIZE + "maze.txt";
        //Board board = new Board("boards/testmaze.txt");
        Board board = new Board(boardName);
        board.print();
        CSP csp = new CSP(board);
        long then = System.currentTimeMillis();

        

//        for (Character[][] individual : population) {
//            for (int i = 0; i < individual.length; i++) {
//                for (int j = 0; j < individual.length; j++) {
//                    if (isConsistent(i, j, individual, board.sources)) {
//                        //fitness++;
//                    }
//                }
//            }
//        }

        boardFrame = new BoardFrame(board.grid);
        boardFrame.f.repaint();
        //char[][] result = Backtracking.smartSearch(csp);

        //Backtracking.orange(csp);
        board.grid = Genetic.execute(board);
        boardFrame = new BoardFrame(board.grid);
        System.out.println((System.currentTimeMillis() - then) / 1000.0);
        boardFrame.f.repaint();
        boardFrame.f.revalidate();
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
