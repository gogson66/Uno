package com.goran.uno.game;

import lombok.Getter;

@Getter
public abstract class Card {

    private String imagePath;

    public Card(String imagePath) {
        this.imagePath = imagePath;
    }

    protected abstract boolean isPlayable(Card throwCard, CardColor color);
    
}
  