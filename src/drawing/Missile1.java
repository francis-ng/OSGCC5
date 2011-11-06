package drawing;
import java.awt.Rectangle;

public class Missile1 extends Missile implements Runnable{
    
    public Missile1 (int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
        speed = 8;
        damage = 0;
        width = 10;
        height = 10;
        box = new Rectangle(width, height);
    }
}
