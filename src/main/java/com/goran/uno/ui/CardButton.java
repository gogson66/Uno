package com.goran.uno.ui;

import javax.swing.*;

import com.goran.uno.game.Card;

import java.awt.*;

public class CardButton extends JButton {

    private Card card;

    public CardButton(Card card) {
        this.card = card;
        setFocusable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath()));
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(60, 90, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);
        setIcon(icon);
        
    }

    public Card getCard() {
        return card;
    }

    
}
