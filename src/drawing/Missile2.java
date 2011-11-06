package drawing;
import java.awt.Rectangle;

public class Missile2 extends Missile implements Runnable{
    
    public Missile2 (int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
        speed = 5;
        damage = 5;
        width = 4;
        height = 4;
        box = new Rectangle(width, height);
    }
}
