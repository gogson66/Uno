package com.goran.uno.game;
public class EmptyCard extends Card {

    public EmptyCard() {
        super("");
    }

    public boolean isPlayable(Card throwCard, CardColor color) {
        return false;
    }
    
}
