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

    public Card shedCard(Card card) {
        return ownCards.remove(ownCards.indexOf(card));
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
