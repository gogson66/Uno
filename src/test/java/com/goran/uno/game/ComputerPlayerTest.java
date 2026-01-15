package com.goran.uno.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ComputerPlayerTest {


    private ComputerPlayer playerWithCards(Card card, Card frontCard) {
        ComputerPlayer cp = new ComputerPlayer("player", true);
        cp.setOwnCards(List.of(card));
        cp.setEligibleCards(frontCard);
        return cp;
    }

    @Test
    void shouldDecidePassIfSecondTurnAndNoEligibleCards() {
        ComputerPlayer cp = playerWithCards(new Card(CardColor.BLUE, 2), new Card(CardColor.GREEN, 1));
        cp.setSecondTurn(true);
        assertEquals(GameMode.PASS, cp.decideMove());
    }

    @Test
    void shoudDecideMoveIfEligibleCard() {
        ComputerPlayer cp = playerWithCards(new Card(CardColor.BLUE, 2), new Card(CardColor.BLUE, 1));
        assertEquals(GameMode.MOVE, cp.decideMove());   
    }

    @Test
    void shouldDecideDrawIfNoEligibleCards() {
        ComputerPlayer cp = playerWithCards(new Card(CardColor.BLUE, 2), new Card(CardColor.GREEN, 1));
        assertEquals(GameMode.DRAW, cp.decideMove());   

    }

    @Test
    void shouldChooseCardNumberedCardIfThereIsOne() {
        Card card = new Card(CardColor.BLUE, 2);
        ComputerPlayer cp = playerWithCards(card, new Card(CardColor.BLUE, 1));
        assertEquals(card, cp.chooseCard());

    }

    @Test
    void shouldChooseWildCardIfNoNumberedCards() {
        Card card = new Wildcard(Sign.WILDCARD);
        ComputerPlayer cp = playerWithCards(card, new Card(CardColor.BLUE, 1) );
        assertEquals(card, cp.chooseCard());

    }

    @Test
    void shouldChooseWildCardAndChooseBlueColor() {
        Card card = new Wildcard(Sign.WILDCARD);
        ComputerPlayer cp = playerWithCards(card, new Card(CardColor.BLUE, 1) );
        assertEquals(CardColor.YELLOW, cp.chooseColor());


    }

    
}
