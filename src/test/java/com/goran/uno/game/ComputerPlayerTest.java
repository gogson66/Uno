package com.goran.uno.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ComputerPlayerTest {

    private ComputerPlayer cp;
    private Card card;

    @BeforeEach
    void createInstance() {
        cp = new ComputerPlayer("player", true);
        card = new Card(CardColor.BLUE, 2);
        cp.setOwnCards(List.of(card));
    }

    @Test
    void decidePass() {
        cp.setEligibleCards(new Card(CardColor.GREEN, 1));
        cp.setSecondTurn(true);
        assertEquals(GameMode.PASS, cp.decideMove());
    }

    @Test
    void decideMove() {
        cp.setEligibleCards(new Card(CardColor.BLUE, 1));
        assertEquals(GameMode.MOVE, cp.decideMove());   
    }

    @Test
    void decideDraw() {
        cp.setEligibleCards(new Card(CardColor.GREEN, 1));
        assertEquals(GameMode.DRAW, cp.decideMove());   

    }

    @Test
    void chooseNumberdCard() {
        cp.setEligibleCards(new Card(CardColor.BLUE, 1));
        assertEquals(card, cp.chooseCard());

    }
    
}
