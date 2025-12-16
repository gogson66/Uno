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

     public Game() {
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            Player player = new Player("Player " + i);
            players.add(player); 
            System.out.println(deck);  
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

     public Player getActivePlayer() {
        return activePlayer;
     }


    public List<Card> getEligibleCards(Card firstDiscardedCard) {
        if (firstDiscardedCard instanceof EmptyCard) return new ArrayList<>(activePlayer.getOwnCards());
        List<Card> eligibleCards = activePlayer.getOwnCards().stream()
        .filter(card -> (card.getColor().equals(firstDiscardedCard.getColor())) || (card.getNumber() == firstDiscardedCard.getNumber() && card.getSign().equals(firstDiscardedCard.getSign())) || card.getSign().name().contains("WILDCARD"))
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

        public Card getFrontCard() {
            frontCard = deck.getFirstDiscardedCard();
            return frontCard;
        }


        public void start() {

        }

    public void pullingCards(int num) {
        if (isSecondMove) {
            activePlayer = nextPlayer();
            return;
        }
        int i = 0;
        while (i < num) {
            Card pulledCard = deck.getCard();
            activePlayer.addToOwnCards(pulledCard);
            System.out.println("You pulled " + pulledCard + " You now have " + activePlayer.getOwnCards());
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
        System.out.println(specialRules);
        List<Card> eligibleCards = getEligibleCards(deck.getFirstDiscardedCard());
        if (eligibleCards.contains(card)) {
            activePlayer.shedCard(card);
            deck.putOnTable(card);
            specialRules = card.getSign();
            activePlayer = nextPlayer();
        }
        /*if (!eligibleCards.isEmpty()) {
            if (activePlayer.isPlaying(eligibleCards)) {
                Card choosedCard = activePlayer.chooseCard(eligibleCards);
                specialRules = choosedCard.getSign();
                if (choosedCard instanceof ChangeColor) changeColor(choosedCard);
                deck.putOnTable(choosedCard);} 
            else pullingCards(1);
        } else pullingCards(1);*/

 
        //eligibleCards.clear();
    }

    private Player nextPlayer() {
        activePlayerIndex++;
        isSecondMove = false;
        return players.get(activePlayerIndex % players.size());
    }

    public void changeColor(Card choosedCard) {
        Color newColor = activePlayer.chooseWildcardColor();
        ChangeColor card = (ChangeColor) choosedCard;
        card.changeColor(newColor);
    }
    
}
  