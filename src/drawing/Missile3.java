package drawing;
import java.awt.Rectangle;

public class Missile3 extends Missile implements Runnable{
    
    public Missile3 (int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
        speed = 6;
        damage = 100;
        width = 7;
        height = 7;
        box = new Rectangle(width, height);
    }
}
