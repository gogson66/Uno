package com.goran.uno.game;
public class EmptyCard extends Card {

    public EmptyCard() {
        super("", Sign.EMPTY);
    }

    public boolean isPlayable(Card throwCard, CardColor color) {
        return false;
    }
    
}
