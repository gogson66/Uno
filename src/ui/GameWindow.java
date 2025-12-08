package ui;
import game.Card;
import game.Game;
import game.Player;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameWindow extends JFrame{

        private JButton dealButton;
        private JPanel playerOnePanel;
        private JPanel playerTwoPanel;
        private JPanel discardedCardsPanel;
        Game game;

        public GameWindow() {
            setTitle("UNO Game");
            setSize(900, 800);

            setDefaultCloseOperation(GameWindow.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            initComponents();
            game = new Game();
            game.start();
        }

        public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {
                GameWindow window = new GameWindow();
                window.setVisible(true);
                window.getContentPane().setBackground(Color.GREEN);
            });
        }

        private void initComponents() {

            dealButton = new JButton("Draw");
            playerOnePanel = new JPanel();
            playerTwoPanel = new JPanel();
            discardedCardsPanel = new JPanel();

            playerOnePanel.setBackground(Color.GREEN);
            playerTwoPanel.setBackground(Color.GREEN);
            discardedCardsPanel.setBackground(Color.GREEN);

            add(playerTwoPanel, BorderLayout.NORTH);
            add(discardedCardsPanel, BorderLayout.CENTER);
            add(playerOnePanel, BorderLayout.SOUTH);

            discardedCardsPanel.add(dealButton);

            dealButton.addActionListener(e -> dealCards());

        }

        private void dealCards() {

            ArrayList<CardButton> playerOneCards = new ArrayList<>();

            game.deal();

            List<Player> players = game.getPlayers();
            List<Card> cards = players.get(0).getOwnCards();

            for (Card card : cards) {
                playerOneCards.add(new CardButton(card));
            }

            for (CardButton cardButton : playerOneCards) {
                playerOnePanel.add(cardButton);
            }

            playerOnePanel.revalidate();
            playerOnePanel.repaint();
        }
    
}
