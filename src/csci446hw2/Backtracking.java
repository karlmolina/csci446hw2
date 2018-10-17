/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import static csci446hw2.Driver.ANIMATE;
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
        for (Character color : current.domain) {
            current.assign(color);
//            if (current.connected.containsAll(csp.sourceColorToNodeMap.get(current.color))) {
//                for (Node node : csp.unassignedVariablesList) {
//                    node.domain.remove(current.color);
//                }
//            }
            if (ANIMATE) {
                Driver.boardFrame.f.repaint();
                Driver.boardFrame.f.revalidate();
            }
            if (current.isConsistent(assignment)) {
                assignment[current.y][current.x] = color;

                char[][] result = dumbRecursiveSearch(assignment, csp);
                if (result != null) {
                    return result;
                }
                assignment[current.y][current.x] = '_';
            }
            current.unassign();
        }
        csp.unassignedVariablesList.addFirst(current);
        return null;
    }

    public static char[][] dumbSearch2(CSP csp) {
        return dumbRecursiveSearch2(csp.sourceVariables, csp);
    }

    public static char[][] dumbRecursiveSearch2(char[][] assignment, CSP csp) {
        //count++;

        //System.out.print('.');
//brgyo
        if (csp.unassignedVariablesPQ.isEmpty()) {
            return assignment;
        }
        Node current = csp.unassignedVariablesPQ.poll();
        for (Character color : current.domain) {
            current.assign(color);
//            if (current.connected.containsAll(csp.sourceColorToNodeMap.get(current.color))) {
//                for (Node node : csp.unassignedVariablesList) {
//                    node.domain.remove(current.color);
//                }
//            }
            for (Node child : current.children) {
                if (csp.unassignedVariablesPQ.remove(child)) {
                    csp.unassignedVariablesPQ.add(child);
                }
            }
            if (ANIMATE) {
                Driver.boardFrame.f.repaint();
                Driver.boardFrame.f.revalidate();
            }
            if (current.isConsistent(assignment)) {
                assignment[current.y][current.x] = color;

                char[][] result = dumbRecursiveSearch2(assignment, csp);
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

    public static char[][] smartSearch(CSP csp) {
        return smartRecursiveSearch(csp.sourceVariables, csp);
    }

    public static char[][] smartRecursiveSearch(char[][] assignment, CSP csp) {
        //count++;

        //System.out.print('.');
//brgyo
        //Node current = csp.unassignedVariablesList.removeFirst();
        Node current = null;
        do {
            if (csp.nodeVariables.isEmpty()) {
                return assignment;
            }
            current = csp.nodeVariables.poll();
        } while (current.foundColor());

        for (Node child : current.nodeDomain) {
            current.child = child;
            child.color = current.color;
            child.setDomain();

            child.sources.addAll(current.sources);
            boolean violateConstraint = false;
            for (Node childChild : child.children) {
                if (child.sources.contains(childChild)) {
                    violateConstraint = true;
                }
                if (csp.nodeVariables.contains(childChild)) {
                    childChild.nodeDomain.remove(child);
                    csp.nodeVariables.remove(childChild);
                    csp.nodeVariables.add(childChild);
                }
            }
            if (violateConstraint) {
                continue;
            }
            child.sources.add(current);
            csp.nodeVariables.add(child);
            assignment[child.y][child.x] = child.color;

            if (ANIMATE) {
                Driver.boardFrame.f.repaint();
                Driver.boardFrame.f.revalidate();
            }
            if (child.isConsistent(assignment)) {
                char[][] result = smartRecursiveSearch(assignment, csp);
                if (result != null) {
                    return result;
                }
            }
            assignment[child.y][child.x] = '_';

            csp.nodeVariables.remove(child);
            child.color = '_';
            child.sources.clear();
            for (Node childChild : child.children) {
                if (csp.nodeVariables.contains(childChild)) {
                    childChild.nodeDomain.add(child);
//                    csp.nodeVariables.remove(childChild);
//                    csp.nodeVariables.add(childChild);
                }
            }
            child.nodeDomain.clear();
        }

        csp.nodeVariables.add(current);
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
