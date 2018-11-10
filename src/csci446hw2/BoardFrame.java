/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci446hw2;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Karl
 */
public class BoardFrame {

    public static final int NODE_SIZE = 40;
    private BoardPanel boardPanel;
    JFrame f;

    public BoardFrame(char[][] board) {
        boardPanel = new BoardPanel(board);

        f = new JFrame();
        int size = NODE_SIZE * (board.length + 2);
        f.setSize(size, size);
        f.add(boardPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private class BoardPanel extends JPanel {

        char[][] board;

        public BoardPanel(char[][] board) {
            this.board = board;
        }

        @Override
        public void paint(Graphics g) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    g.setColor(Board.colorOfChar(board[i][j]));
                    g.fillRect(j * NODE_SIZE, i * NODE_SIZE, NODE_SIZE, NODE_SIZE);
                }
            }
        }
    }
}
