package ui;

import game.Card;
import game.Game;
import game.Player;
import game.CardColor;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameWindow extends JFrame{

        private JButton dealButton;
        private JPanel centerPanel;
        private JPanel discardedCardPanel;
        private ImageIcon icon;
        private JButton tallon;
        private CardButton discardedCard;
        private List<PlayerPanel> playerPanels = new ArrayList<>();
        private String[] positions = new String[] {BorderLayout.SOUTH, BorderLayout.NORTH, BorderLayout.WEST, BorderLayout.EAST};
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

            centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBackground(Color.GREEN);
            add(centerPanel, BorderLayout.CENTER);

            centerPanel.add(dealButton);

            dealButton.addActionListener(e -> dealCards());

        }

        private void dealCards() {

            game.deal();
            dealButton.setVisible(false);

            discardedCard = new CardButton(game.getFrontCard());
            discardedCardPanel = new JPanel(new GridBagLayout());
            discardedCardPanel.setBackground(Color.GREEN);
            discardedCardPanel.add(discardedCard);

            tallon = new JButton();
            icon = new ImageIcon(getClass().getResource("/cards/back.png"));
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(60, 90, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);
            tallon.setIcon(icon);

            tallon.addActionListener(e -> pullCard());

        
            JPanel tallonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            tallonPanel.setBackground(Color.GREEN);
            tallonPanel.add(tallon);


            discardedCard.setEnabled(false);
            centerPanel.add(discardedCardPanel, BorderLayout.CENTER);
            centerPanel.add(tallonPanel, BorderLayout.EAST);

            readState();

        }

        private void playCard(Player player, Card card) {

            if (card.getColor() == Color.CHANGE)
            game.play(player, card);
            readState();

        }

        private void changeColor() {

        }

        private void pullCard() {
            game.pullingCards(1);
            readState();
        }

        private void markEligibleCards(Player currentPlayer) {
            List<Card> eligibleCards = game.getEligibleCards(game.getFrontCard());
            for (PlayerPanel panel: playerPanels) {
                if (panel.getPlayer().equals(currentPlayer)) {
                    for (Component c: panel.getComponents()) {
                        if (c instanceof CardButton) {
                            CardButton cardButton = (CardButton) c;
                            if (!eligibleCards.contains(cardButton.getCard())) cardButton.setEnabled(false);
                        }
                    }
                } else {
                    for (Component c: panel.getComponents()) {
                        if (c instanceof CardButton) c.setEnabled(false);
                    }
                }
            }

        }

        private void readState() {

            for (PlayerPanel panel: playerPanels) panel.removeAll();

            for(int i = 0; i < game.getPlayers().size(); i++) {
                Player player = game.getPlayers().get(i);
                PlayerPanel playerPanel = new PlayerPanel(player);
                add(playerPanel, positions[i]);
                playerPanels.add(playerPanel);
                for (Card card: player.getOwnCards()) {
                    CardButton cardButton = new CardButton(card);
                    cardButton.addActionListener(e -> playCard(player, card));
                    playerPanel.add(cardButton);
                }
                playerPanel.revalidate();
                playerPanel.repaint();
            }

            checkSecondMove();
            
            discardedCardPanel.removeAll();
            discardedCard = new CardButton(game.getFrontCard());
            discardedCardPanel.add(discardedCard);
            markEligibleCards(game.getActivePlayer());


        }
    
        private void checkSecondMove() {
            if (game.isSecondMove()) {
                tallon.setText("Skip play");
                tallon.setIcon(null);
            } else {
                tallon.setText("");
                tallon.setIcon(icon);
            }
        }
}
