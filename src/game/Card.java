package game;
public class Card {

    private Color color;
    private int number;
    private Sign sign;

    public Card(Color color, int number) {
        this.color = color;
        this.number = number;
        if (number < 10) this.sign = Sign.NUMBER;
        else if (number == 10) this.sign = Sign.PLUS_TWO;
        else if (number == 11) this.sign = Sign.SKIP;
        else this.sign = Sign.SKIP;
    }

    public Card(Sign sign) {
        this.color = Color.CHANGE;
        this.number = -1;
        this.sign = sign;
    }

    public Color getColor() {
        return this.color;
    }

    public int getNumber() {
        return this.number;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Sign getSign() {  
        return this.sign;
    }

    public String toString() {
        String lastPart = this.sign == Sign.NUMBER ? this.number + "" : this.sign + "";
        return this.color + " "  + lastPart ;
    }
    
}
  