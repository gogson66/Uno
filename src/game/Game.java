package game;
import java.util.*;
import java.util.stream.Collectors;

public class Game {

     private final int NUMBER_OF_PLAYERS = 2;
     private final int NUMBER_OF_STARTING_CARDS = 6;
     private Deck deck = new Deck();
     private Player activePlayer;
     private int activePlayerIndex = 0;
     private Card frontCard;
     private List <Player> players = new ArrayList<>();
     private boolean isGameOver = false;
     private boolean isSecondMove = false;
     private Sign specialRules = Sign.NUMBER;
     private int direction = 1;


     public Game() {
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            Player player = new Player("Player " + i);
            players.add(player); 
        }
     }

     public static void main(String[] args) {
        Game game = new Game();
        game.start();
     }

     public void start() {
        System.out.println("Game started");
     }

     private boolean checkGameOver() {
        if (activePlayer.getOwnCards().size() == 0) {
            System.out.println("Game over! " + activePlayer + " won!");
            return isGameOver = true;
        } 
        else return false;
     } 

     public List<Player> getPlayers() {
        return players;
     }

     public Player getActivePlayer() {
        return activePlayer;
     }


    public List<Card> getEligibleCards(Card firstDiscardedCard) {
        if (firstDiscardedCard instanceof EmptyCard) return new ArrayList<>(activePlayer.getOwnCards());
        List<Card> eligibleCards = activePlayer.getOwnCards().stream()
        .filter(card -> (card.getColor().equals(firstDiscardedCard.getColor())) || (card.getNumber() == firstDiscardedCard.getNumber() && card.getSign().equals(firstDiscardedCard.getSign())) || card.getSign().name().contains("WILDCARD"))
        .collect(Collectors.toList());
        return eligibleCards;
    }

    
    private boolean checkReverseDirection() {
        if (specialRules.equals(Sign.REVERSE)) {
            System.out.println(players);
            resetSpecialRules();
            nextPlayer();
            return true;
        } else return false;
    }

    private void resetSpecialRules() {
        specialRules = Sign.NUMBER;
    }

    private boolean checkSkippableSpecialRules() {
        
        if (specialRules.equals(Sign.PLUS_TWO)) {
            pullingCards(2);
            resetSpecialRules();
            return true;
        }
        else if (specialRules.equals(Sign.SKIP)) {
            resetSpecialRules();
            return true;
        }
        else if (specialRules.equals(Sign.WILDCARD_PLUS)) {
            pullingCards(4);
            resetSpecialRules();
            return true;
        } else return false;
    } 
    
        public Card getFrontCard() {
            frontCard = deck.getFirstDiscardedCard();
            return frontCard;
        }

    public void pullingCards(int num) {
        if (isSecondMove) {
            nextPlayer();
            return;
        }
        int i = 0;
        while (i < num) {
            Card pulledCard = deck.getCard();
            activePlayer.addToOwnCards(pulledCard);
            i++;
        }
        if (num == 1) isSecondMove = true;
         
    }

    public boolean isSecondMove() {
        return isSecondMove;
    }

    public void deal() {
        deck.shuffle();
        for(Player player: players) {  
            List<Card> startingCards =  deck.getCards(NUMBER_OF_STARTING_CARDS);
            player.setOwnCards(startingCards);
            System.out.println("Starting cards: " + player.getOwnCards());
        }
        activePlayer = players.get(activePlayerIndex);
 
    }


    public void play(Player player, Card card) {
        List<Card> eligibleCards = getEligibleCards(deck.getFirstDiscardedCard());
        if (eligibleCards.contains(card)) {
            activePlayer.shedCard(card);
            deck.putOnTable(card);
            specialRules = card.getSign();
            if (checkReverseDirection()) direction *= -1;
            nextPlayer();
        }
    }

    private void nextPlayer() {
        isSecondMove = false;
        activePlayerIndex = (activePlayerIndex + direction + players.size()) % players.size();
        activePlayer = players.get(activePlayerIndex);
        if (checkSkippableSpecialRules()) nextPlayer();
    
    }

    public void changeColor(CardColor color) {
        activePlayer.chooseWildcardColor(color, deck.getFirstDiscardedCard());
    }
    
}
  