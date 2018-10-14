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
    
    public static char[][] dumbSearch(CSP csp) {
        return dumbRecursiveSearch(csp.sourceVariables, csp);
    }

    public static char[][] dumbRecursiveSearch(char[][] assignment, CSP csp) {
        //count++;
        
        //System.out.print('.');
//brgyo
        if (csp.unassignedVariablesList.isEmpty()) {
            return assignment;
        }
        Node current = csp.unassignedVariablesList.removeFirst();
        for (Character color : csp.domains.get(current)) {
            current.assign(color);
            //count++;
            //System.out.println(count);
            //System.out.println("trying " + current);
            //printArray(assignment);
            //Driver.boardFrame.f.repaint();
            //Driver.boardFrame.f.revalidate();
            if (current.isConsistent()) {
                assignment[current.y][current.x] = color;

                char[][] result = dumbRecursiveSearch(assignment, csp);
                if (result != null) {
                    return result;
                }
                assignment[current.y][current.x] = '_';
            }
            current.unassign();
        }
        csp.unassignedVariablesList.push(current);
        return null;
    }
    
    public static char[][] smartSearch(CSP csp) {
        return smartRecursiveSearch(csp.sourceVariables, csp);
    }

    public static char[][] smartRecursiveSearch(char[][] assignment, CSP csp) {
        //count++;
        
        //System.out.print('.');
//brgyo
        if (csp.unassignedVariablesPQ.isEmpty()) {
            return assignment;
        }
        Node current = csp.unassignedVariablesPQ.remove();
        for (Character color : csp.domains.get(current)) {
            current.assign(color);
            //count++;
            //System.out.println(count);
            //System.out.println("trying " + current);
            //printArray(assignment);
            Driver.boardFrame.f.repaint();
            Driver.boardFrame.f.revalidate();
            if (current.isConsistent()) {
                assignment[current.y][current.x] = color;

                char[][] result = smartRecursiveSearch(assignment, csp);
                if (result != null) {
                    return result;
                }
                assignment[current.y][current.x] = '_';
            }
            current.unassign();
        }
        csp.unassignedVariablesPQ.add(current);
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
