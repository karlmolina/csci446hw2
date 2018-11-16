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
 * Class to draw graphics of the csp getting solved.
 * @author Karl
 */
public class BoardFrame {
    /**
     * How big the nodes are on the graphic
     */
    public static final int NODE_SIZE = 40;
    private BoardPanel boardPanel;
    JFrame f;

    /**
     * BoardFrame constructor
     * @param board 
     * @param title 
     */
    public BoardFrame(char[][] board, String title) {
        boardPanel = new BoardPanel(board);

        f = new JFrame(title);
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

        /**
         * Method that draws the current board to the JPanel
         * @param g 
         */
        @Override
        public void paint(Graphics g) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    g.setColor(colorOfChar(board[i][j]));
                    g.fillRect(j * NODE_SIZE, i * NODE_SIZE, NODE_SIZE, NODE_SIZE);
                }
            }
        }

        /**
         * Method to get a real Color from a character
         * @param c
         * @return 
         */
        private Color colorOfChar(char c) {
            switch (c) {
                case 'O':
                    return Color.ORANGE;
                case 'C':
                    return Color.CYAN;
                case 'M':
                    return Color.MAGENTA;
                case 'Y':
                    return Color.YELLOW;
                case 'B':
                    return Color.BLUE;
                case 'D':
                    return Color.RED.darker();
                case 'G':
                    return Color.GREEN;
                case 'R':
                    return Color.RED;
                case 'A':
                    return Color.GRAY;
                case 'W':
                    return Color.WHITE;
                case 'P':
                    return Color.PINK;
                case 'K':
                    return Color.CYAN.darker();
                case 'Q':
                    return Color.DARK_GRAY;
                default:
                    return Color.BLACK;
            }
        }
    }
}
