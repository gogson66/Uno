package ui;

import javax.swing.*;

import game.Card;

public class CardButton extends JButton {

    Card card;

    public CardButton(Card card) {
        this.card = card;
        setText(card.toString());
        setFocusable(false);
    }

    
}
