package com.goran.uno.game;
import java.util.*;

public class Deck {

    private List<Card> talonCards = new ArrayList<>();
    private List<Card> discardedCards= new ArrayList<>();
    private int NUMBER_OF_SPECIAL_CARDS = 4;
    private CardColor[] colors = {CardColor.BLUE, CardColor.GREEN, CardColor.RED, CardColor.YELLOW};

    public Deck() {

        for (CardColor color: colors) {
            talonCards.add(new Card(color, 0));
            for (int i = 1; i <= 9; i++ ) {
                talonCards.add(new Card(color, i));
                talonCards.add(new Card(color, i));
        }
            /*for (int i = 0; i < 2; i++) {
                talonCards.add(new Card(color, Sign.PLUS_TWO ));
                talonCards.add(new Card(color, Sign.SKIP ));
                talonCards.add(new Card(color, Sign.REVERSE ));
        }*/
      } 
      for (int i = 0; i < NUMBER_OF_SPECIAL_CARDS; i++) {
        talonCards.add(new Wildcard(Sign.WILDCARD));
        talonCards.add(new Wildcard(Sign.WILDCARD_PLUS));

      }

    }

    public List<Card> getCards(int number) {
        ArrayList<Card> cards = new ArrayList<>(talonCards.subList(0, number));
        removeCards(cards);
        return cards;
    }

    public Card getCard() {
        if (talonCards.size() == 0) {
            Card firstCard = discardedCards.getLast();
            discardedCards.removeLast();
            talonCards = new ArrayList<>(discardedCards);
            shuffle();
            discardedCards.clear();
            discardedCards.add(firstCard);
        } 
        Card card = talonCards.getFirst();
        talonCards.removeFirst();
        return card;
    }

    public void shuffle() {    
        Collections.shuffle(talonCards);
    }

    private void removeCards(List<Card> removedCards) {
        talonCards.subList(0, removedCards.size()).clear();
    }

    public void putOnTable(Card throwedCard) {
        discardedCards.add(throwedCard);
    }

    public Card getFirstDiscardedCard() {
        return (discardedCards.size() == 0) ? new EmptyCard(): discardedCards.getLast();
    }

    public List<Card> getDiscardeCards() {
        return discardedCards;
    }

    public String toString() {
        return talonCards.toString();
    }
    
}

