package game;

import java.util.List;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name) {
        super(name + " (AI)");
    }

    public GameMode decideMove() {
        if (getEligibleCards().size() == 0) {
            if (checkSecondTurn()) return GameMode.PASS;
            else return GameMode.DRAW;
        } else return GameMode.MOVE;

    }
    
    public Card chooseCard() {
        List<Card> eligibleCards = getEligibleCards();

        Card choosenCard = eligibleCards.stream()
        .filter(card -> card.getSign().equals(Sign.NUMBER))
        .findFirst()
        .orElse(eligibleCards.stream().filter(card -> card.getSign().name().contains("WILDCARD")).findFirst().get());

        return choosenCard;

    }

    /*public CardColor chooseColor() {

        CardColor choosedColor = this.getOwnCards()
        
    }*/
    
}
