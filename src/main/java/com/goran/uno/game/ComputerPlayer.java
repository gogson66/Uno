package com.goran.uno.game;

import java.util.List;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name, boolean isComputer) {
        super(name + " (AI)", isComputer);
    }

    public GameMode decideMove() {
        System.out.println("deciding move");
        if (getEligibleCards().size() == 0) {
            if (checkSecondTurn()) return GameMode.PASS;
            else return GameMode.DRAW;
        } else return GameMode.MOVE;

    }
    
    public Card chooseCard() {
        List<Card> eligibleCards = getEligibleCards();

        Card choosenCard = eligibleCards.stream()
        .filter(card -> !card.getSign().name().contains("WILDCARD"))
        .findFirst()
        .orElseGet(() -> eligibleCards.stream()
        .findFirst().orElseThrow(() -> new IllegalStateException("Card not found")));

        if (choosenCard.getSign().name().contains("WILDCARD")) chooseColor(choosenCard);
        System.out.println(choosenCard);

        return choosenCard;

    }

    public void chooseColor(Card card) {

        CardColor choosedColor = null;
        long maxCount = 0L;

        for (CardColor color: CardColor.values()) {
            long count = this.getOwnCards().stream().filter(c -> c.getColor() == color).count() ;
            System.out.println(color + " " + count);
            if (count >= maxCount) {
                maxCount = count;
                choosedColor = color;
            }
        }

        System.out.println(choosedColor);

       ChangeColor changingCard = (ChangeColor) card;
       changingCard.changeColor(choosedColor);
    }
    
}
