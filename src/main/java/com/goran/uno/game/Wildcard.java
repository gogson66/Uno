package com.goran.uno.game;
public class Wildcard extends Card implements ChangeColor{
    

    public Wildcard(Sign sign) {
        super(sign);
    }

    public void changeColor(CardColor color) {
        setColor(color);
    }
}
