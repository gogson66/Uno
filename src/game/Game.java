package game;
import java.util.*;
import java.util.stream.Collectors;

public class Game {

     private final int NUMBER_OF_PLAYERS = 2;
     private final int NUMBER_OF_STARTING_CARDS = 6;
     private Deck deck = new Deck();
     private Player activePlayer;
     private List <Player> players = new ArrayList<>();
     private boolean isGameOver = false;
     private boolean isSecondMove = false;
     private Sign specialRules = Sign.NUMBER;

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



    private List<Card> eligibleMoves(Card firstDiscardedCard) {
        if (firstDiscardedCard instanceof EmptyCard) return new ArrayList<>(activePlayer.getOwnCards());
        List<Card> eligibleCards = activePlayer.getOwnCards().stream()
        .filter(card -> (card.getColor().equals(firstDiscardedCard.getColor())) || (card.getNumber() == firstDiscardedCard.getNumber()) || card.getSign().name().contains("WILDCARD"))
        .collect(Collectors.toList());

        System.out.println("Eligible cards: " + eligibleCards);

        return eligibleCards;
    }

    private void reverseDirection(int position) {

        List<Player> part1 = new ArrayList<>(players.subList(0, position));
        List<Player> part2 = new ArrayList<>(players.subList(position, players.size()));

        Collections.reverse(part1);
        Collections.reverse(part2);

        players = new ArrayList<>();
        players.addAll(part1);
        players.addAll(part2);


    }

    private boolean checkReverseDirection(int position) {
        if (specialRules.equals(Sign.REVERSE)) {
            reverseDirection(position);
            resetSpecialRules();
            return true;
        } else return false;
    }

    private void resetSpecialRules() {
        specialRules = Sign.NUMBER;
    }

    private boolean checkSkippableSpecialRules(int position) {
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
        }
        else return false;
    }
    

    /*public void start() {
        deal();
        while(!isGameOver) {
            for (int i = 0; i < players.size(); i++ ) {
                activePlayer = players.get(i);
                System.out.println(activePlayer.getName() + " is playing");
                System.out.println("Card on table is " + deck.getFirstDiscardedCard());
                if (checkReverseDirection(i-1)) break;
                play(i);                                 
                if (isSecondMove) play(i);
                if (checkGameOver()) break;
                System.out.println("Card on table now is " + deck.getFirstDiscardedCard());
                isSecondMove = false;
        }
        }
        
    }  */

        public void start() {

        }

    private void pullingCards(int num) {
        if (isSecondMove) return;
        int i = 0;
        while (i < num) {
            Card pulledCard = deck.getCard();
            activePlayer.addToOwnCards(pulledCard);
            System.out.println("You pulled " + pulledCard + " You now have " + activePlayer.getOwnCards());
            i++;
        }
        if (num == 1) isSecondMove = true;
         
    }

    public void deal() {
        deck.shuffle();
        for(Player player: players) {  
            List<Card> startingCards =  deck.getCards(NUMBER_OF_STARTING_CARDS);
            player.setOwnCards(startingCards);
            System.out.println("Starting cards: " + player.getOwnCards());
        }
 
    }

    public void play(int position) {
        if (checkSkippableSpecialRules(position)) return;
        List<Card> eligibleCards = eligibleMoves(deck.getFirstDiscardedCard());
        if (!eligibleCards.isEmpty()) {
            /*if (activePlayer.isPlaying(eligibleCards)) {*/
            if (activePlayer.isPlaying(eligibleCards)) {
                Card choosedCard = activePlayer.chooseCard(eligibleCards);
                specialRules = choosedCard.getSign();
                if (choosedCard instanceof ChangeColor) changeColor(choosedCard);
                deck.putOnTable(choosedCard);} 
            else pullingCards(1);
        } else pullingCards(1);
 
        eligibleCards.clear();
    }

    public void changeColor(Card choosedCard) {
        Color newColor = activePlayer.chooseWildcardColor();
        ChangeColor card = (ChangeColor) choosedCard;
        card.changeColor(newColor);
    }
    
}
  