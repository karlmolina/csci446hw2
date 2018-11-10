/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import static csci446hw2.Driver.ANIMATE;

/**
 *
 * @author Karl
 */
public class Backtracking {
    public static char[][] apple(CSP csp) {
        return apple(csp.sourceVariables, csp);
    }

    public static char[][] apple(char[][] assignment, CSP csp) {
        if (csp.unassignedVariablesList.isEmpty()) {
            return assignment;
        }
        Node current = csp.unassignedVariablesList.removeFirst();
        for (Character color : current.domain) {
            current.assign(color, assignment);
            assignment[current.y][current.x] = color;

            if (ANIMATE == 1) {
                Driver.boardFrame.f.repaint();
                Driver.boardFrame.f.revalidate();
            }
            if (current.isConsistent(assignment)) {
                boolean pathComplete = false;
                if (PathComplete.check(csp, color)) {
                    pathComplete = true;
                    for (Node node : csp.unassignedVariablesList) {
                        node.domain.remove(color);
                    }
                }
                char[][] result = apple(assignment, csp);
                if (result != null) {
                    return result;
                }
                if (pathComplete) {
                    for (Node node : csp.unassignedVariablesList) {
                        node.domain.addFirst(color);
                    }
                }
            }
            current.unassign(assignment);
        }
        csp.unassignedVariablesList.addFirst(current);
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
