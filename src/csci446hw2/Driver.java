/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
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
    public static int BOARD_SIZE = 5,
            POPULATION_SIZE = 20;

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
        int size = board.size;
        boolean[][] sources = board.sources;
        CSP csp = new CSP(board);
        long then = System.currentTimeMillis();

        Set<Character[][]> population = new HashSet<>();
        for (int k = 0; k < POPULATION_SIZE; k++) {
            Character[][] array = new Character[board.size][board.size];
            for (int i = 0; i < board.size; i++) {
                for (int j = 0; j < board.size; j++) {
                    array[i][j] = board.grid[i][j];
                }
            }
            for (int i = 0; i < board.size; i++) {
                for (int j = 0; j < board.size; j++) {
                    if (!board.sources[i][j]) {
                        int randomColorIndex = (int) (Math.random() * board.colors.length);
                        array[i][j] = board.colors[randomColorIndex];
                    }
                }
            }
            population.add(array);
        }

        PriorityQueue<Individual> pq = new PriorityQueue<>();
        for (Character[][] individual : population) {
            for (int i = 0; i < individual.length; i++) {
                for (int j = 0; j < individual.length; j++) {
                    //System.out.print(individual[i][j]);
                }
                //System.out.println();
            }
            int fitness = fitness(individual, sources);
            //System.out.println("fitness: " + fitness);
            //System.out.println();
            pq.add(new Individual(individual, fitness));
        }

        boolean check = true;
        Individual winner = null;
        while (check) {
            PriorityQueue<Individual> pq2 = new PriorityQueue<>();
            while (!pq.isEmpty()) {
                Individual i1 = pq.remove();
                //System.out.println();
                //System.out.println(i1.fitness);
                if (i1.fitness == size*size) {
                    check = false;
                    winner = i1;
                }
                Individual i2 = pq.remove();
                pq2.add(i1);
                pq2.add(i2);

                boolean s = true;
                Character[][] child = new Character[size][size];

                // Recombination
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (s) {
                            child[i][j] = i1.grid[i][j];
                        } else {
                            child[i][j] = i2.grid[i][j];
                        }
                        // Mutation
                        if (!board.sources[i][j] && Math.random() > .6) {
                            int randomColorIndex = (int) (Math.random() * board.colors.length);
                            child[i][j] = board.colors[randomColorIndex];
                        }
                    }
                }

                pq2.add(new Individual(child, fitness(child, sources)));
            }

            for (int i = 0; i < 10; i++) {
                pq.add(pq2.remove());
            }
        }

        for (Character[][] individual : population) {
            for (int i = 0; i < individual.length; i++) {
                for (int j = 0; j < individual.length; j++) {
                    if (isConsistent(i, j, individual, board.sources)) {
                        //fitness++;
                    }
                }
            }
        }

        boardFrame = new BoardFrame(board.grid);
        boardFrame.f.repaint();
        //char[][] result = Backtracking.smartSearch(csp);


        //Backtracking.orange(csp);
        board.grid = winner.grid;
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

    public static boolean isConsistent(int i, int j, Character[][] assignment, boolean[][] sources) {
        Character color = assignment[i][j];
        int sameColorChildren = 0;
        int[][] diffs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] diff : diffs) {
            int y = diff[0] + i, x = diff[1] + j;
            try {
                if (assignment[y][x] == color) {
                    sameColorChildren++;
                }
            } catch (Exception e) {
                //skip this
            }
        }

        if (sources[i][j]) {
            if (sameColorChildren == 1) {
                return true;
            }
        } else {
            if (sameColorChildren == 2) {
                return true;
            }
        }
        return false;
    }

    public static int fitness(Character[][] grid, boolean[][] sources) {
        int fitness = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (isConsistent(i, j, grid, sources)) {
                    fitness++;
                }
            }
        }
        return fitness;
    }
}
