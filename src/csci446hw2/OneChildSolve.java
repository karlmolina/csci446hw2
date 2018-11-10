/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.util.Iterator;
import static csci446hw2.Driver.ANIMATE;

/**
 *
 * @author h89q624
 */
public class OneChildSolve {

    /**
     * Goes through unassigned variables in the csp and assigns them if they
     * have a child with only one unassigned child
     * @param csp 
     */
    public static void Execute(CSP csp) {
        boolean check;
        do {
            check = false;
            Iterator<Node> iter = csp.unassignedVariablesList.iterator();
            while (iter.hasNext()) {
                Node unassignedVariable = iter.next();
                boolean breakCheck = false;
                for (Node child : unassignedVariable.children) {
                    if (!child.isBlank() && !child.isComplete && child.childrenUnassigned() == 1) {
                        unassignedVariable.assign(child.color, csp.sourceVariables);
                        breakCheck = true;
                        iter.remove();
                        check = true;
                        if (ANIMATE == 1) {
                            Driver.boardFrame.f.repaint();
                            Driver.boardFrame.f.revalidate();
                        }
                        break;
                    }
                }
                if (breakCheck) {
                    break;
                }
            }
        } while (check);
    }
}
