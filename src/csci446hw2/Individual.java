/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

/**
 *
 * @author h89q624
 */
public class Individual implements Comparable{
    Character[][] grid;
    int fitness;
    
    public Individual(Character[][] grid, int fitness) {
        this.grid = grid;
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Object o) {
        //return ((Individual)o).fitness - this.fitness;
        return this.fitness - ((Individual)o).fitness;
    }
    
    @Override
    public String toString() {
        return "" + fitness;
    }
}
