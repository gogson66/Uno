package com.goran.uno.game;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NumberCard extends Card implements ColoredCard {
    private final int number;
    private final CardColor color;

    public NumberCard(CardColor color, int number) {
        super("/cards/" + color.name().toLowerCase() + "_" + number + ".jpg");
        this.color = color;
        this.number = number;
    }

    @Override
    public boolean isPlayable(Card throwCard, CardColor color) {
        if (throwCard instanceof NumberCard) {
            NumberCard card = (NumberCard) throwCard;
            return this.color.equals(color) || this.number == card.getNumber();
        }
        else return this.color.equals(color);
    }

}
