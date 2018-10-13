/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

/**
 *
 * @author Karl
 */
public class Backtracking {
    //static int count;
    //static Instant now = Instant.now();
    
    public static char[][] search(CSP csp) {
        return recursiveSearch(csp.sourceVariables, csp);
    }

    public static char[][] recursiveSearch(char[][] assignment, CSP csp) {
        //count++;
        
        //System.out.print('.');
//brgyo
        if (csp.unassignedVariables.isEmpty()) {
            return assignment;
        }
        Node current = csp.unassignedVariables.pop();
        for (Character color : csp.domains.get(current)) {
            current.assign(color);
            //count++;
            //System.out.println(count);
            //System.out.println("trying " + current);
            //printArray(assignment);
            if (current.isConsistent()) {
                assignment[current.y][current.x] = color;

                char[][] result = recursiveSearch(assignment, csp);
                if (result != null) {
                    return result;
                }
                assignment[current.y][current.x] = '_';
            }
            current.unassign();
        }
        csp.unassignedVariables.push(current);
        return null;
    }

    static void printArray(char[][] array) {
        for (int i = 0; i < array.length + 1; i++) {
            for (int j = 0; j < array.length + 1; j++) {
                if (j == 0) {
                    System.out.print(Math.abs(i - 1));
                } else if (i == 0) {
                    System.out.print(j - 1);
                } else {
                    System.out.print(array[i - 1][j - 1]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
