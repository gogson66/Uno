package game;
public class Wildcard extends Card implements ChangeColor{
    

    public Wildcard(Sign sign) {
        super(sign);
    }

    public void changeColor(Color color) {
        setColor(color);
    }
}
