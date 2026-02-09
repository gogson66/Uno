package com.goran.uno.game;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Wildcard extends Card{
    

    public Wildcard(Sign sign) {
        super("/cards/" + sign.name().toLowerCase() + ".jpg", sign);
    }

    @Override
    public boolean isPlayable(Card throwCard, CardColor color) {
        return true;
    }

}
