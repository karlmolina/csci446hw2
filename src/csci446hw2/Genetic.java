/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author h89q624
 */
public class Genetic {

    public static double MUTATION_RATE = 1;
    public static int POPULATION_SIZE = 10;

    public static Character[][] execute(Board board) {
        int size = board.size;
        Character[] colors = board.colors;
        boolean[][] sources = board.sources;
        Character[][] grid = board.grid;
        // Initialize population with random grids
        Set<Character[][]> population = new HashSet<>();
        for (int k = 0; k < POPULATION_SIZE; k++) {
            Character[][] array = new Character[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    array[i][j] = grid[i][j];
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (!sources[i][j]) {
                        int randomColorIndex = (int) (Math.random() * colors.length);
                        array[i][j] = colors[randomColorIndex];
                    }
                }
            }
            population.add(array);
        }

        PriorityQueue<Individual> pq = new PriorityQueue<>();

        int fitnessSum = 0;
        // Calculate fitness of initial population
        // And add them to priority queue sorted by highest
        // fitness first.
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

        int[] fitness = new int[POPULATION_SIZE];
        Individual[] individuals = new Individual[POPULATION_SIZE];

        int counter = 0;
        while (!pq.isEmpty()) {
            individuals[counter] = pq.poll();
            fitness[counter] = individuals[counter].fitness;
            fitnessSum += fitness[counter];
            counter++;
        }

        double[] weights = getWeights(fitness, fitnessSum);

        boolean check = true;
        Individual winner = null;
        while (check) {
            //System.out.println(fitness[0]);
            //System.out.println(pq.peek().fitness);
            //assert pq.size() == POPULATION_SIZE;
            for (int n = 0; n < POPULATION_SIZE; n++) {
                int firstIndex = rouletteSelect(weights),
                        secondIndex = rouletteSelect(weights);

                Individual i1 = individuals[firstIndex];

                if (i1.fitness == size * size) {
                    check = false;
                    winner = i1;
                }

                Individual i2 = individuals[secondIndex];

                boolean s = true;
                Character[][] child = new Character[size][size];

                // Recombination
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        s = !s;
                        if (s) {
                            child[i][j] = i1.grid[i][j];
                        } else {
                            child[i][j] = i2.grid[i][j];
                        }
                        // Mutation
                        if (!sources[i][j] && Math.random() > MUTATION_RATE) {
                            int randomColorIndex = (int) (Math.random() * colors.length);
                            child[i][j] = colors[randomColorIndex];
                        }
                    }
                }

                pq.add(new Individual(child, fitness(child, sources)));
            }

            counter = 0;
            fitnessSum = 0;
            while (!pq.isEmpty()) {
                individuals[counter] = pq.poll();
                fitness[counter] = individuals[counter].fitness;
                fitnessSum += fitness[counter];
                counter++;
            }
            weights = getWeights(fitness, fitnessSum);
        }
        return winner.grid;
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

// Returns the selected index based on the weights(probabilities)
    static int rouletteSelect(double[] weight) {
        // get a random value
        double value = randUniformPositive();
        //System.out.println(value);
        // locate the random value based on the weights
        for (int i = 0; i < weight.length; i++) {
            if (value < weight[i]) {
                return i;
            }
        }
        // when rounding errors occur, we return the last item's index 
        return weight.length - 1;
    }

// Returns a uniformly distributed double value between 0.0 and 1.0
    static double randUniformPositive() {
        // easiest implementation
        return new Random().nextDouble();
    }

    static double[] getWeights(int[] fitness, int fitnessSum) {
        double prevProb = 0;
        double[] weights = new double[fitness.length];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = prevProb + fitness[i] / (double) fitnessSum;
            prevProb = weights[i];
        }
        return weights;
    }
}
