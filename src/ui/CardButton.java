package ui;

import javax.swing.*;

import game.Card;

public class CardButton extends JButton {

    private Card card;

    public CardButton(Card card) {
        this.card = card;
        setText(card.toString());
        setFocusable(false);
        setIcon(new ImageIcon(getClass().getResource(card.getImagePath())));
    }

    public Card getCard() {
        return card;
    }

    
}
