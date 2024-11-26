// ConnectFourGUI.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConnectFourGUI {
    private final JFrame frame;
    private final Color MENU_BACKGROUND = new Color(248, 249, 250);
    private final Color MENU_HOVER = new Color(240, 240, 240);
    private final Color MENU_TEXT = new Color(33, 37, 41);
    private GameBoardGUI gameBoardGUI;

    public ConnectFourGUI() {
        frame = new JFrame("Connect Four");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create stylish menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(MENU_BACKGROUND);
        menuBar.setBorder(BorderFactory.createEmptyBorder());

        // Create menus
        JMenu gameMenu = createStylishMenu("Game");
        JMenu sizeMenu = createStylishMenu("Board Size");
        JMenu helpMenu = createStylishMenu("Help");

        // Add items to Game menu
        addMenuItem(gameMenu, "New Game", e -> resetGame());
        gameMenu.addSeparator();
        addMenuItem(gameMenu, "Exit", e -> System.exit(0));

        // Add board size options
        String[] sizes = {"8x5", "10x6", "12x7"};
        for (String size : sizes) {
            addMenuItem(sizeMenu, size, e -> changeBoardSize(size));
        }

        // Add help items
        addMenuItem(helpMenu, "How to Play", e -> showHelpDialog());
        helpMenu.addSeparator();
        addMenuItem(helpMenu, "About", e -> showAboutDialog());

        menuBar.add(gameMenu);
        menuBar.add(sizeMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        // Initialize with default size
        gameBoardGUI = new GameBoardGUI(5, 8);
        frame.getContentPane().add(gameBoardGUI.getPanel());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JMenu createStylishMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        menu.setForeground(MENU_TEXT);
        menu.setBackground(MENU_BACKGROUND);

        // Add hover effect
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menu.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menu.setBackground(MENU_BACKGROUND);
            }
        });

        return menu;
    }

    private void addMenuItem(JMenu menu, String text, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        item.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        item.setBackground(MENU_BACKGROUND);
        item.setForeground(MENU_TEXT);

        // Hover effect
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                item.setBackground(MENU_HOVER);
                item.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(72, 175, 148)),
                        BorderFactory.createEmptyBorder(8, 12, 8, 15)
                ));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                item.setBackground(MENU_BACKGROUND);
                item.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            }
        });

        item.addActionListener(listener);
        menu.add(item);
    }

    private void resetGame() {
        frame.getContentPane().remove(gameBoardGUI.getPanel());
        gameBoardGUI = new GameBoardGUI(gameBoardGUI.getBoard().getRows(),
                gameBoardGUI.getBoard().getColumns());
        frame.getContentPane().add(gameBoardGUI.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void changeBoardSize(String size) {
        String[] dimensions = size.split("x");
        int cols = Integer.parseInt(dimensions[0]);
        int rows = Integer.parseInt(dimensions[1]);

        frame.getContentPane().remove(gameBoardGUI.getPanel());
        gameBoardGUI = new GameBoardGUI(rows, cols);
        frame.getContentPane().add(gameBoardGUI.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void showHelpDialog() {
        JDialog helpDialog = new JDialog(frame, "How to Play", true);
        helpDialog.setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        addStylishLabel(contentPanel, "Connect Four Rules:", true);
        addStylishLabel(contentPanel, "1. Players take turns dropping their discs into any column", false);
        addStylishLabel(contentPanel, "2. Navy player (X) goes first, followed by Turquoise player (O)", false);
        addStylishLabel(contentPanel, "3. First to connect four discs in a row wins!", false);
        addStylishLabel(contentPanel, "4. Connections can be horizontal, vertical, or diagonal", false);
        addStylishLabel(contentPanel, "5. If the board fills up with no winner, it's a draw", false);

        JButton closeButton = new JButton("Got it!");
        handleDialog(helpDialog, contentPanel, closeButton);
    }

    private void handleDialog(JDialog helpDialog, JPanel contentPanel, JButton closeButton) {
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeButton.addActionListener(e -> helpDialog.dispose());

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(closeButton);

        helpDialog.add(contentPanel);
        helpDialog.pack();
        helpDialog.setLocationRelativeTo(frame);
        helpDialog.setVisible(true);
    }

    private void addStylishLabel(JPanel panel, String text, boolean isHeader) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (isHeader) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            panel.add(Box.createVerticalStrut(10));
        } else {
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(Box.createVerticalStrut(5));
        }
        panel.add(label);
    }

    private void showAboutDialog() {
        JDialog aboutDialog = new JDialog(frame, "About Connect Four", true);
        aboutDialog.setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        addStylishLabel(contentPanel, "Connect Four", true);
        addStylishLabel(contentPanel, "Version 1.0", false);
        addStylishLabel(contentPanel, "", false);
        addStylishLabel(contentPanel, "A classic two-player strategy game", false);
        addStylishLabel(contentPanel, "Created with ♥ in Java", false);

        JButton closeButton = new JButton("Close");
        handleDialog(aboutDialog, contentPanel, closeButton);
    }
}