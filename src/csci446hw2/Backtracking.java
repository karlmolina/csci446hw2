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
    static int counter = 0;
    public static Character[][] orange(CSP csp) {
        return orange(csp.sourceVariables, csp);
    }

    public static Character[][] orange(Character[][] assignment, CSP csp) {
        if (csp.expandableNodes.isEmpty()) {
            return assignment;
        }
        Node current = csp.expandableNodes.poll();
        
        for (Node next : current.nodeDomain) {
            //System.out.println(counter++);
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
            Node nodeComplete = null;
            for (Node child : next.children) {
//                if (next.sources.contains(child)) {
//                    violateConstraint = true;
//                }
                if (csp.expandableNodes.contains(child)) {
                    // If you found the same color expandable node
                    if (child.color == next.color) {
                        // This color is complete
                        csp.expandableNodes.remove(child);
                        nodeComplete = child;
                        colorComplete = true;
                    } else {
                        // That child can no longer go to next
                        child.nodeDomain.remove(next);
                        // Remove and add child from expandable Nodes to update
                        // the priority queue
                        csp.expandableNodes.remove(child);
                        csp.expandableNodes.add(child);
                    }
                }
            }
//            if (violateConstraint) {
//                continue;
//            }

            next.sources.add(current);
            // If the color is not complete next should be added to expandable
            // Nodes
            if (!colorComplete) {
                csp.expandableNodes.add(next);
            }

            if (next.isConsistent(assignment)) {
                Character[][] result = orange(assignment, csp);
                if (result != null) {
                    return result;
                }
            }
            // This code is run when the past assignment failed
            
            next.unassign(assignment);
            if (animate == 1) {
                Driver.boardFrame.f.repaint();
                Driver.boardFrame.f.revalidate();
            }
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
            
            // If the color was complete we must add the complete node back
            // to the expandable nodes.
            if (colorComplete) {
                csp.expandableNodes.add(nodeComplete);
            }
            next.nodeDomain.clear();
        }

        csp.expandableNodes.add(current);
        return null;
    }
}
