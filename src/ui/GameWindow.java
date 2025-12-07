package ui;
import game.Game;

import java.awt.BorderLayout;

import javax.swing.*;

public class GameWindow extends JFrame{

        private JButton dealButton;
        private JPanel player1Panel;
        private JPanel player2Panel;
        private JPanel discardedCardsPanel;

        public GameWindow() {
            setTitle("UNO Game");
            setSize(800, 600);

            setDefaultCloseOperation(GameWindow.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            initComponents();
            Game game = new Game();
            game.start();
        }

        public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {
                GameWindow window = new GameWindow();
                window.setVisible(true);
            });
        }

        private void initComponents() {

            dealButton = new JButton("Draw");
            player1Panel = new JPanel();
            add(dealButton, BorderLayout.SOUTH);
            add(player1Panel, BorderLayout.CENTER);

            dealButton.addActionListener(e -> dealCards());

        }

        private void dealCards() {
            System.out.println("Dealing cards");
        }
    
}
