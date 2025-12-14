package ui;

import javax.swing.*;
import java.awt.*;

import game.Card;

public class CardButton extends JButton {

    private Card card;

    public CardButton(Card card) {
        this.card = card;
        //setText(card.toString());
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
