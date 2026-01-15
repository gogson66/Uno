package com.goran.uno.game;
import java.util.*;

public class Game {

     private final int NUMBER_OF_PLAYERS = 2;
     private final int NUMBER_OF_STARTING_CARDS = 6;
     private Deck deck = new Deck();
     private Player activePlayer;
     private CardColor activeColor;
     private int activePlayerIndex = 0;
     private Card frontCard;
     private List <Player> players = new ArrayList<>();
     private boolean isGameOver = false;
     private Sign specialRules = Sign.NUMBER;
     private int direction = 1;


     public Game() {

            Player player = new Player("You", false);
            players.add(player); 

        for (int i = 0; i < NUMBER_OF_PLAYERS-1; i++) {
            Player computerPlayer = new ComputerPlayer("Player " + i, true);
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

     public CardColor getActiveColor() {
        return activeColor;
     }

     public void setActiveColor(CardColor activeColor) {
        this.activeColor = activeColor;
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
            activePlayer.setEligibleCards(frontCard);
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
            if (!(card instanceof Wildcard)) setActiveColor(card.getColor());
            frontCard = card;
            System.out.println("Front card is: " +  frontCard);
            specialRules = card.getSign();
            if(checkGameOver()) return;
            if (checkReverseDirection()) direction *= -1;
            nextPlayer();
        }
    }

    public void playComputer() {
        if (activePlayer instanceof ComputerPlayer) {
            System.out.println("Front card is: " + frontCard);
            ComputerPlayer computerPlayer = (ComputerPlayer) activePlayer;
            GameMode mode = computerPlayer.decideMove();
            System.out.println(mode);
            
            switch (mode) {
                case MOVE -> {
                    Card choosenCard = computerPlayer.chooseCard();
                    if (choosenCard instanceof Wildcard) {
                        CardColor choosenColor = computerPlayer.chooseColor();
                        setActiveColor(choosenColor);
                    } 
                    playCard(choosenCard);
                }
                case DRAW -> {
                    pullingCards(1);
                    playComputer();
                }
                case PASS -> nextPlayer();
                                
            }
        }
    }


    public Player nextPlayer() {
        activePlayer.setSecondTurn(false);
        activePlayerIndex = (activePlayerIndex + direction + players.size()) % players.size();
        activePlayer = players.get(activePlayerIndex);
        if (checkSkippableSpecialRules()) nextPlayer();
        activePlayer.setEligibleCards(frontCard);
        System.out.println("ACTIVE PLAYER: " + activePlayer);
        System.out.println(activePlayer.getEligibleCards());
        return activePlayer;
    
    }

    public void changeColor(CardColor color) {
        activePlayer.chooseWildcardColor(color, deck.getFirstDiscardedCard());
    }
    
}
  