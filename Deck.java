import java.util.*;

public class Deck {

    private List<Card> talonCards = new ArrayList<>();
    private List<Card> discardedCards= new ArrayList<>();
    private int NUMBER_OF_COLORED_CARDS = 26;
    private int NUMBER_OF_SPECIAL_CARDS = 4;
    private Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};

    public Deck() {
        for (Color color: colors) {
            for (int i = 0; i < NUMBER_OF_COLORED_CARDS; i++ ) {
                Card card = new Card(color, i/2);
                talonCards.add(card);
        }
      } 

      for (int i = 0; i < NUMBER_OF_SPECIAL_CARDS; i++) {
        Card wildCard = new Wildcard(Sign.WILDCARD);
        talonCards.add(wildCard);
        Card wildCardPlus = new Wildcard(Sign.WILDCARD_PLUS);
        talonCards.add(wildCardPlus);

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
        return (discardedCards.size() == 0) ? new EmptyCard(Color.NO_COLOR, -1): discardedCards.getLast();
    }

    public List<Card> getDiscardeCards() {
        return discardedCards;
    }
    
}

