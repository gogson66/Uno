package com.goran.uno.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name, boolean isComputer) {
        super(name + " (AI)", isComputer);
    }

    public GameMode decideMove() {
        if (getEligibleCards().isEmpty()) return checkSecondTurn() ? GameMode.PASS : GameMode.DRAW;
        return GameMode.MOVE;

    }
    
    public Card chooseCard() {

        Card choosenCard = getEligibleCards().stream()
        .filter(card -> !(card instanceof Wildcard))
        .findFirst()
        .orElseGet(() -> getEligibleCards().stream()
        .findFirst().orElseThrow(() -> new IllegalStateException("Card not found")));

        System.out.println(choosenCard);
        return choosenCard;

    }

    public CardColor chooseColor() {


        CardColor choosedColor = null;
        long maxCount = 0L;
        List<ColoredCard> coloredCards = getOwnCards().stream().filter(c -> c instanceof ColoredCard).map(c -> (ColoredCard) c).collect(Collectors.toList());

        for (CardColor color: CardColor.values()) {
            long count = coloredCards.stream().filter(c -> c.getColor() == color).count() ;
            System.out.println(color + " " + count);
            if (count >= maxCount) {
                maxCount = count;
                choosedColor = color;
            }
        }

        System.out.println(choosedColor);
        return choosedColor;

    }    
}
