package com.goran.uno.ui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.goran.uno.game.Card;
import com.goran.uno.game.CardColor;
import com.goran.uno.game.Game;
import com.goran.uno.game.Player;

public class GameWindow extends JFrame{

        private JButton dealButton;
        private JPanel centerPanel;
        private JPanel discardedCardPanel;
        private JPanel chooseColorPanel;
        private JPanel endGamePanel;
        private ImageIcon icon;
        private JButton tallonButton;
        private JButton skipButton;
        private CardButton discardedCard;
        private List<PlayerPanel> playerPanels = new ArrayList<>();
        private String[] positions = new String[] {BorderLayout.SOUTH, BorderLayout.NORTH, BorderLayout.WEST, BorderLayout.EAST};
        private boolean isChange = false;
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

            tallonButton = new JButton();
            icon = new ImageIcon(getClass().getResource("/cards/back.png"));
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(60, 90, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);
            tallonButton.setIcon(icon);

            skipButton = new JButton("Skip");
    
            tallonButton.addActionListener(e -> pullCard());
            skipButton.addActionListener(e -> skipPlay());

            skipButton.setVisible(false);

            JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            eastPanel.setBackground(Color.GREEN);
            eastPanel.add(tallonButton);
            eastPanel.add(skipButton);


            discardedCard.setEnabled(false);
            centerPanel.add(discardedCardPanel, BorderLayout.CENTER);
            centerPanel.add(eastPanel, BorderLayout.EAST);

            readState();

        }

        private void playCard(Card card) {

            game.playCard(card);
            if (card.getSign().name().contains("WILDCARD")) {
                getChangeColorPanel();
                isChange = true;
            }
            checkGameOver();
            readState();

            /*try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }*/

            computerTurn();

        }
        
        private void computerTurn() {
            game.playComputer();
            checkGameOver();
            readState(); 

            System.out.println("PLAY CARD GUI");

        }
        private void getChangeColorPanel() {
            chooseColorPanel = new JPanel();
            for (CardColor color: CardColor.values()) {
                JButton colorButton = new JButton(color.name());
                colorButton.addActionListener(e -> chooseColor(color));
                chooseColorPanel.add(colorButton);
                chooseColorPanel.setBackground(Color.GREEN);

            }
            centerPanel.add(chooseColorPanel);
        }

        private void chooseColor(CardColor color){
            game.changeColor(color);
            isChange = false;
            chooseColorPanel.removeAll();
            centerPanel.revalidate();
            centerPanel.repaint();
            tallonButton.setEnabled(true);
            readState();
        }

        private void pullCard() {
            System.out.println("PULL CARD");
            game.pullingCards(1);
            readState();
        } 

        private void skipPlay() {
            System.out.println("SKIP PLAY");
            game.nextPlayer();
            if (game.getActivePlayer().checkIsComputer()) computerTurn();
            readState();
        }

        private void markEligibleCards(Player currentPlayer) {
            List<Card> eligibleCards = currentPlayer.getEligibleCards();
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

        private void disableAllCards() {
            for (PlayerPanel panel: playerPanels) {
                for(Component c: panel.getComponents()) {
                    CardButton cardButton = (CardButton) c;
                    cardButton.setEnabled(false);
                }
            }

            tallonButton.setEnabled(false);
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
                    cardButton.addActionListener(e -> playCard(card));
                    playerPanel.add(cardButton);
                }
                playerPanel.revalidate();
                playerPanel.repaint();
            }

            checkTallon();
            discardedCardPanel.removeAll();
            discardedCard = new CardButton(game.getFrontCard());
            discardedCardPanel.add(discardedCard);
            if (isChange) disableAllCards();
            else markEligibleCards(game.getActivePlayer());

        }

        private void checkGameOver() {
            if (game.isGameOver()) {
                endGamePanel = new JPanel();
                JTextField endGameLabel = new JTextField(game.getActivePlayer() + " won!");
                endGamePanel.add(endGameLabel);
                centerPanel.add(endGamePanel);
                disableAllCards();
                centerPanel.revalidate();
                centerPanel.repaint();
            }
        }
    
        private void checkTallon() {
            if (game.getActivePlayer().checkSecondTurn()) {
                tallonButton.setVisible(false);
                skipButton.setVisible(true);
            } else {
                skipButton.setVisible(false);
                tallonButton.setVisible(true);
            }
        }
}
