package com.goran.uno.game;
import java.util.*;
import java.util.stream.Collectors;

public class Player {

    private String name;
    private List<Card> ownCards;
    private List<Card> eligibleCards = new ArrayList<>();
    private boolean isSecondTurn = false;
    private boolean isComputer = false;

    public Player(String name, boolean isComputer) {
        this.name = name;
        this.isComputer = isComputer;
    }

    public boolean checkIsComputer() {
        return isComputer;
    }

    public void setEligibleCards(Card firstDiscardedCard) {
        eligibleCards.clear();
        if (firstDiscardedCard instanceof EmptyCard) {
            eligibleCards = new ArrayList<>(this.getOwnCards());
            return;  } 
        eligibleCards = this.getOwnCards().stream()
        .filter(card -> (card.getColor().equals(firstDiscardedCard.getColor())) || (card.getNumber() == firstDiscardedCard.getNumber() && card.getSign().equals(firstDiscardedCard.getSign())) || card.getSign().name().contains("WILDCARD"))
        .collect(Collectors.toList());
    }

    public List<Card> getEligibleCards() {
        return eligibleCards;
    }

    public boolean checkSecondTurn() {
        return isSecondTurn;
    }

    public void setSecondTurn(boolean isSecondTurn) {
        this.isSecondTurn = isSecondTurn;
    }


    public void setOwnCards(List<Card> ownCards) {
        this.ownCards = ownCards;
    }

    public void addToOwnCards(Card pulledCard) {
        this.ownCards.add(pulledCard);
        System.out.println("After adding pull card: " + this.ownCards);
    }

    public void removeFromOwnCards(Card throwedCard) {
        this.ownCards.remove(throwedCard);
    }

    public List<Card> getOwnCards() {
        return ownCards;
    } 
    
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }


    public Card shedCard(Card card) {
        return ownCards.remove(ownCards.indexOf(card));
    }

    public void chooseWildcardColor(CardColor color, Card card) {
        ChangeColor changingCard = (ChangeColor) card;
        changingCard.changeColor(color);
    }

}
