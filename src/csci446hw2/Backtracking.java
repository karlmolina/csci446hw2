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
    public static char[][] orange(CSP csp) {
        return orange(csp.sourceVariables, csp);
    }

    public static char[][] orange(char[][] assignment, CSP csp) {
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
                char[][] result = orange(assignment, csp);
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
}
