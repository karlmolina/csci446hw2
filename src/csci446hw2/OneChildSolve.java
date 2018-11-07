/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import static csci446hw2.Driver.animate;
import java.util.Iterator;

/**
 *
 * @author h89q624
 */
public class OneChildSolve {

    public static void Execute(CSP csp) {
        boolean check = false;
        do {
            Iterator<Node> iter = csp.unassignedVariablesList.iterator();
            while (iter.hasNext()) {
                Node unassignedVariable = iter.next();
                boolean breakCheck = false;
                for (Node child : unassignedVariable.children) {
                    if (child.color != '_' && child.childrenCount - child.childrenAssigned == 1) {
                        unassignedVariable.assign(child.color, csp.sourceVariables);
                        breakCheck = true;
                        iter.remove();
                        check = true;
                        if (animate == 1) {
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
