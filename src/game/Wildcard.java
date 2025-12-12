package game;
public class Wildcard extends Card implements ChangeColor{
    

    public Wildcard(Sign sign, String imagePath) {
        super(sign, imagePath);
    }

    public void changeColor(Color color) {
        setColor(color);
    }
}
