package ui;

import javax.swing.*;
import java.awt.*;

import game.Card;
import game.CardColor;

public class CardButton extends JButton {

    private Card card;
    private CardColor color;

    public CardButton(Card card) {
        this.card = card;
        setFocusable(false);
        color = card.getColor();
        ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath()));
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(60, 90, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);
        setIcon(icon);
        
    }

    public Card getCard() {
        return card;
    }

    public CardColor getColor() {
        return color;
    }

    
}
