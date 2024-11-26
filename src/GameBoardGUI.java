// GameBoardGUI.java

import javax.swing.*;
import java.awt.*;

public class GameBoardGUI {
    private final CircleButton[][] buttons;
    private final JPanel panel;
    private final JLabel statusLabel;
    private final Color BOARD_COLOR = new Color(229, 231, 235);
    private final Color PLAYER_X_COLOR = new Color(44, 62, 80);    // Navy
    private final Color PLAYER_O_COLOR = new Color(72, 175, 148);  // Turquoise
    private final Color HIGHLIGHT_COLOR = new Color(255, 215, 0, 200); // Golden highlight
    private Board board;
    private Timer winAnimation;

    public GameBoardGUI(int rows, int columns) {
        board = new Board(rows, columns);
        buttons = new CircleButton[rows][columns];
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(rows, columns, 8, 8));
        boardPanel.setBackground(BOARD_COLOR);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                CircleButton button = new CircleButton();
                button.setPreferredSize(new Dimension(80, 80));

                int finalJ = j;
                button.addActionListener(e -> handleMove(finalJ));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }

        // Create status panel with colored player indicator
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setBackground(Color.WHITE);

        JLabel playerTextLabel = new JLabel("Current Player: ");
        playerTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        statusLabel = new JLabel("X");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusLabel.setForeground(PLAYER_X_COLOR);

        statusPanel.add(playerTextLabel);
        statusPanel.add(statusLabel);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        panel.add(boardPanel, BorderLayout.CENTER);
        panel.add(statusPanel, BorderLayout.SOUTH);

        updateBoard();
    }

    public JPanel getPanel() {
        return panel;
    }

    public Board getBoard() {
        return board;
    }

    private void clearHighlights() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                buttons[i][j].setHighlighted(false);
            }
        }
        if (winAnimation != null) {
            winAnimation.stop();
            winAnimation = null;
        }
    }

    private void highlightWinningCells() {
        int[] winningCells = board.getWinningCells();

        winAnimation = new Timer(500, e -> {
            for (int i = 0; i < winningCells.length; i += 2) {
                int row = winningCells[i];
                int col = winningCells[i + 1];
                CircleButton button = buttons[row][col];
                button.setHighlighted(!button.isHighlighted());
            }
        });
        winAnimation.start();
    }

    private void showGameOverDialog() {
        if (winAnimation != null) {
            winAnimation.stop();
        }

        String message;
        if (board.getCurrentPlayer() == 'X' || board.getCurrentPlayer() == 'O') {
            message = "Player " + board.getCurrentPlayer() + " wins!";
        } else {
            message = "Game ended in a draw!";
        }

        int choice = JOptionPane.showOptionDialog(panel, message, "Game Over",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"New Game", "Exit"}, "New Game");

        if (choice == 0) {
            clearHighlights();
            board = new Board(board.getRows(), board.getColumns());
            updateBoard();
        } else {
            System.exit(0);
        }
    }

    private void handleMove(int column) {
        if (!board.isGameEnded() && board.makeMove(column)) {
            updateBoard();
            if (board.isGameEnded()) {
                if (board.getCurrentPlayer() == 'X' || board.getCurrentPlayer() == 'O') {
                    highlightWinningCells();
                }

                Timer dialogTimer = new Timer(2000, e -> showGameOverDialog());
                dialogTimer.setRepeats(false);
                dialogTimer.start();
            }
        }
    }

    private void updateBoard() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                buttons[i][j].setBackground(board.getField(i, j).getColor());
            }
        }

        statusLabel.setText(String.valueOf(board.getCurrentPlayer()));
        statusLabel.setForeground(board.getCurrentPlayer() == 'X' ? PLAYER_X_COLOR : PLAYER_O_COLOR);
    }

    private class CircleButton extends JButton {
        private boolean isHighlighted = false;

        public CircleButton() {
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(BOARD_COLOR);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int diameter = Math.min(getWidth(), getHeight()) - 10;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            g2.setColor(Color.WHITE);
            g2.fillOval(x, y, diameter, diameter);

            if (getBackground() != Color.WHITE) {
                g2.setColor(getBackground());
                g2.fillOval(x, y, diameter, diameter);

                if (isHighlighted) {
                    g2.setColor(HIGHLIGHT_COLOR);
                    g2.setStroke(new BasicStroke(4));
                    g2.drawOval(x, y, diameter, diameter);

                    // Add glow effect
                    int glowRadius = 10;
                    for (int i = glowRadius; i > 0; i--) {
                        float alpha = 0.1f * (float) i / glowRadius;
                        g2.setColor(new Color(1f, 1f, 0f, alpha));
                        g2.drawOval(x - i, y - i, diameter + 2 * i, diameter + 2 * i);
                    }
                }
            }

            g2.dispose();
        }

        public boolean isHighlighted() {
            return isHighlighted;
        }

        public void setHighlighted(boolean highlighted) {
            this.isHighlighted = highlighted;
            repaint();
        }
    }

}