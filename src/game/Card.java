package game;
public class Card {

    private Color color;
    private int number;
    private Sign sign;
    private String imagePath;

    public Card(Color color, int number) {
        this.color = color;
        this.number = number;
        this.sign = Sign.NUMBER;
        this.imagePath = "/cards/" + color.name().toLowerCase() + "_" + number + ".jpg";
    }

    public Card(Color color, Sign sign) {
        this.color = color;
        this.sign = sign;
        this.imagePath = "/cards/" + color.name().toLowerCase() + "_" + sign.name().toLowerCase() + ".jpg";

    }

    public Card(Sign sign) {
        this.color = Color.CHANGE;
        this.sign = sign;
        this.imagePath = "/cards/" + this.sign.name().toLowerCase() + ".jpg";
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

    public String getImagePath() {
        return imagePath;
    }

    public String toString() {
        String lastPart = this.sign == Sign.NUMBER ? this.number + "" : this.sign + "";
        return this.color + " "  + lastPart ;
    }
    
}
  