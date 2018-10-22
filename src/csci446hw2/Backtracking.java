/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import static csci446hw2.Driver.animate;

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

//            if (current.connected.containsAll(csp.sourceColorToNodeMap.get(current.color))) {
//                for (Node node : csp.unassignedVariablesList) {
//                    node.domain.remove(current.color);
//                }
//            }
            current.assign(color, assignment);
            assignment[current.y][current.x] = color;

            if (animate == 1) {
                Driver.boardFrame.f.repaint();
                Driver.boardFrame.f.revalidate();
            }
            if (current.isConsistent(assignment)) {

                char[][] result = dumbRecursiveSearch(assignment, csp);
                if (result != null) {
                    return result;
                }
            }
            current.unassign(assignment);
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
            current.assign(color, assignment);
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
            if (animate == 1) {
                Driver.boardFrame.f.repaint();
                Driver.boardFrame.f.revalidate();
            }
            if (current.isConsistent(assignment)) {

                char[][] result = dumbRecursiveSearch2(assignment, csp);
                if (result != null) {
                    return result;
                }
                assignment[current.y][current.x] = '_';
            }
            current.unassign(assignment);
        }
        csp.unassignedVariablesPQ.add(current);
        return null;
    }

    public static char[][] smartSearch(CSP csp) {
        return smartRecursiveSearch(csp.sourceVariables, csp);
    }

    public static char[][] smartRecursiveSearch(char[][] assignment, CSP csp) {
        if (csp.expandableNodes.isEmpty()) {
            return assignment;
        }
        Node current = csp.expandableNodes.poll();

        for (Node next : current.nodeDomain) {
            next.assign(current.color, assignment);
            if (animate == 1) {
                Driver.boardFrame.f.repaint();
                Driver.boardFrame.f.revalidate();
            }
            current.child = next;
            next.setDomain();

            next.sources.addAll(current.sources);
            boolean violateConstraint = false;
            boolean colorComplete = false;
            for (Node child : next.children) {
//                if (next.sources.contains(child)) {
//                    violateConstraint = true;
//                }
                if (csp.expandableNodes.contains(child)) {
                    if (child.color == next.color) {
                        csp.expandableNodes.remove(child);
                        colorComplete = true;
                    } else {
                        child.nodeDomain.remove(next);
                        csp.expandableNodes.remove(child);
                        csp.expandableNodes.add(child);
                    }
                }
            }
//            if (violateConstraint) {
//                continue;
//            }

            next.sources.add(current);
            if (!colorComplete) {
                csp.expandableNodes.add(next);
            }

            if (next.isConsistent(assignment)) {
                char[][] result = smartRecursiveSearch(assignment, csp);
                if (result != null) {
                    return result;
                }
            }
            next.unassign(assignment);

            csp.expandableNodes.remove(next);
            next.sources.clear();
            for (Node child : next.children) {
                if (csp.expandableNodes.contains(child)) {
                    if (child.color == next.color) {
                        csp.expandableNodes.add(child);
                    } else {
                        child.nodeDomain.add(next);
                        csp.expandableNodes.remove(child);
                        csp.expandableNodes.add(child);
                    }
                }
            }
            next.nodeDomain.clear();
        }

        csp.expandableNodes.add(current);
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
