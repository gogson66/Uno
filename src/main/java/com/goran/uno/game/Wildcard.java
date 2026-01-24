package com.goran.uno.game;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Wildcard extends Card{
    
    private final Sign sign;

    public Wildcard(Sign sign) {
        super("/cards/\" + this.sign.name().toLowerCase() + \".jpg");
        this.sign = sign;
    }

    @Override
    public boolean isPlayable(Card throwCard, CardColor color) {
        return true;
    }

}
