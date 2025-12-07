package game;
import java.util.*;

public class Player {

    private String name;
    private List<Card> ownCards;

    public Player(String name) {
        this.name = name;
    }

    public void setOwnCards(List<Card> ownCards) {
        this.ownCards = ownCards;
    }

    public void addToOwnCards(Card pulledCard) {
        this.ownCards.add(pulledCard);
        System.out.println("After adding pull card: " + this.ownCards);
    }

    public void removeFromOwnCards(Card throwedCard) {
        this.ownCards.remove(throwedCard);
    }

    public List<Card> getOwnCards() {
        return ownCards;
    } 
    
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public boolean isPlaying(List<Card> eligibleCards) {
        System.out.println("You have following eligible cards " + eligibleCards);
        System.out.println("Do you wanna play? (Y/N)");
        Scanner in = scanner();
        return in.nextLine().toUpperCase().equals("Y");
         
    }

    public Card chooseCard(List<Card> eligibleCards) {
        if (eligibleCards.size() == 1) return eligibleCards.get(0);
        System.out.println("You chose to play! Pick a card!");
        Scanner in = scanner();
        int cardIndex = in.nextInt();
        Card choosedCard = eligibleCards.get(cardIndex);
        return ownCards.remove(ownCards.indexOf(choosedCard));
    }

    public Color chooseWildcardColor() {
        System.out.println("Chose color:");
        Scanner in = scanner();
        return Color.valueOf(in.nextLine().toUpperCase()) ;

    }

    private Scanner scanner() {
        return new Scanner(System.in);
    }
}
