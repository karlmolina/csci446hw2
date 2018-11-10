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
public class PathComplete {

    public static boolean check(CSP csp, char c) {
        Node source = csp.sourceColorToNodeMap.get(c).getFirst();
        return check(source, null);
    }

    private static boolean check(Node source, Node previous) {
        for (Node node : source.children) {
            if (node.color == source.color && node != previous) {
                if (node.isSource) {
                    return true;
                }
                return check(node, source);
            }
        }

        return false;
    }
}
