package com.goran.uno.game;
public enum Sign {
    PLUS_TWO,
    REVERSE,
    SKIP,
    WILDCARD,
    WILDCARD_PLUS,
    NUMBER,
    EMPTY;


    public boolean isWildCard() {
        return this == WILDCARD || this == WILDCARD_PLUS;
    }
}
