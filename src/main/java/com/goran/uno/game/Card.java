package com.goran.uno.game;

import lombok.Getter;

@Getter
public abstract class Card {

    private String imagePath;
    private Sign sign;

    public Card(String imagePath, Sign sign) {
        this.imagePath = imagePath;
        this.sign = sign;
    }

    protected abstract boolean isPlayable(Card throwCard, CardColor color);
    
}
  