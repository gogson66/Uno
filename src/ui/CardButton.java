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
        //setIcon(new ImageIcon(getClass().getResource("/cards/blue_0.jpg")));
        ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath()));
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);
        setIcon(icon);
        
    }

    public Card getCard() {
        return card;
    }

    
}
