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
     private Sign specialRules = Sign.NUMBER;
     private int direction = 1;


     public Game() {

            Player player = new Player("You");
            players.add(player); 

        for (int i = 0; i < NUMBER_OF_PLAYERS-1; i++) {
            Player computerPlayer = new ComputerPlayer("Player " + i);
            players.add(computerPlayer); 
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

     public boolean isGameOver() {
        return isGameOver;
     }

     public List<Player> getPlayers() {
        return players;
     }

     public Player getActivePlayer() {
        return activePlayer;
     }

    
    private boolean checkReverseDirection() {
        if (specialRules.equals(Sign.REVERSE)) {
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
        if (activePlayer.checkSecondTurn()) {
            nextPlayer();
            return;
        }
        int i = 0;
        while (i < num) {
            Card pulledCard = deck.getCard();
            activePlayer.addToOwnCards(pulledCard);
            i++;
        }
        if (num == 1) activePlayer.setSecondTurn(true);
         
    }


    public void deal() {
        deck.shuffle();
        for(Player player: players) {  
            List<Card> startingCards =  deck.getCards(NUMBER_OF_STARTING_CARDS);
            player.setOwnCards(startingCards);
            player.setEligibleCards(deck.getFirstDiscardedCard());
            System.out.println("Starting cards: " + player.getOwnCards());
        }
        activePlayer = players.get(activePlayerIndex);
 
    }


    public void playCard(Card card) {
        if (activePlayer.getEligibleCards().contains(card)) {
            activePlayer.shedCard(card);
            deck.putOnTable(card);
            specialRules = card.getSign();
            if(checkGameOver()) return;
            if (checkReverseDirection()) direction *= -1;
            nextPlayer();
        }
    }

    public void playComputer() {
        if (activePlayer instanceof ComputerPlayer) {
            activePlayer.setEligibleCards(frontCard);
            ComputerPlayer computerPlayer = (ComputerPlayer) activePlayer;
            GameMode mode = computerPlayer.decideMove();
            switch (mode) {
                case MOVE: {
                    Card choosedCard = computerPlayer.chooseCard();
                    playCard(choosedCard);
                }
                case PASS: nextPlayer();
                case DRAW: pullingCards(1);;
                                
            }
        }
    }

    private void nextPlayer() {
        activePlayer.setSecondTurn(false);
        activePlayerIndex = (activePlayerIndex + direction + players.size()) % players.size();
        activePlayer = players.get(activePlayerIndex);
        if (checkSkippableSpecialRules()) nextPlayer();
        activePlayer.setEligibleCards(frontCard);
    
    }

    public void changeColor(CardColor color) {
        activePlayer.chooseWildcardColor(color, deck.getFirstDiscardedCard());
    }
    
}
  