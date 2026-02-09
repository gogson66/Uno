package com.goran.uno.game;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ActionCard extends Card implements ColoredCard{

    private final CardColor color;

    public ActionCard(CardColor color, Sign sign) {
        super("/cards/" + color.name().toLowerCase() + "_" + sign.name().toLowerCase() + ".jpg", sign);
        this.color = color;
    }

    @Override
    public boolean isPlayable(Card throwCard, CardColor color) {
        if (throwCard instanceof ActionCard) {
            ActionCard card = (ActionCard) throwCard;
            return this.color.equals(color) || this.getSign() == card.getSign();
        }
        else return this.color.equals(color);
    }
    
}
